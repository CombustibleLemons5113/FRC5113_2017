import numpy as np
import cv2
from networktables import NetworkTable as nettab
import math
import logging
import copy

M = 8.25  # actual distance between xCenter of both contours
CAMERA_OFFSET = 8.5  # offset of camera to where the peg goes in
CAM_FOV = 55  # I think that this is right
X_RESOLUTION = 640  # may be 1280
Y_RESOLUTION = 480  # may be 720
DPP = CAM_FOV / X_RESOLUTION  # distance per pixel
DX = -1
Y_RANGE = 40 #15
RANGE_OFFSET = -30

mode = -1

# H: 0 - 360
# S: 0 - 255
# V: 0 - 255
real = False
lower_thresh = np.array([0, 40, 0])
upper_thresh = np.array([180, 200, 60])

if (not real):
    lower_thresh = np.array([0, 50, 0])
    upper_thresh = np.array([800, 200, 60])

#lower_thresh[0] /= 2
#upper_thresh[0] /= 2

nettab.setIPAddress("10.51.13.2")
nettab.setClientMode()
nettab.initialize(server="10.51.13.88")
table = nettab.getTable("contoursReport")

print(nettab.isConnected())

cap = cv2.VideoCapture(0)  # 1280 x 720

t = 200
c = 240

while (True):
    # Capture frame-by-frame
    ret, frame = cap.read()  # frame = None, which is causing the run time error

    #frame = frame[c - t / 2:c + t / 2, 0:639]

    # Our operations on the frame come here

    new = cv2.inRange(frame, lower_thresh, upper_thresh)

    kernel = np.ones((5, 5), np.uint8)
    dialated = cv2.dilate(new, kernel, iterations=1)

    contoursImage, contours, hierarchy = cv2.findContours(dialated, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

    filteredContours = []

    for cnt in contours:
        if cv2.contourArea(cnt) > 200:
            filteredContours.append(cnt)

    #cv2.drawContours(frame, filteredContours, -1, (0, 0, 255), 5)
    '''
    keypoints = detector.detect(new)
    print(len(keypoints))
    if(len(keypoints) > 0):
        for i in keypoints:
            #print("Coords: " + str(i.pt))
            #print("Size: " + str(i.size))

            frame = cv2.circle(frame, (int(i.pt[0]), int(i.pt[1])), 5, (0, 0, 255), 3, 8, 0)

    #Display the resulting frame

    '''
    widths = []
    heights = []
    ys = []
    xs = []
    areas = []

    contours = []

    for c in filteredContours:
        x, y, w, h = cv2.boundingRect(c)
        contours.append({'area': cv2.contourArea(c), 'x': x + w / 2, 'y': y + h / 2, 'width': w, 'height': h})


    for c in contours:
        frame = cv2.circle(frame, (int(c['x']), int(c['y'])), 5, (0, 255, 255))

    print('Length: ' + str(len(contours)))
    for i in range(len(contours) - 1, -1, -1):#c in contours:
        c = contours[i]

        if math.fabs(Y_RESOLUTION / 2 - c['y'] + RANGE_OFFSET) > Y_RANGE:
            contours.remove(c)
            #print(str(i) + '. removed: (' + str(c['x']) + ', ' + str(c['y']) + ')')
            continue
            '''elif (c['width'] * c['height'] - c['area']) / (
            c['width'] * c['height']) > 100:#0.3:  # FIGURE OUT THIS NUMBER - NOT FINAL
            contours.remove(c)
            continue'''
        else:
            frame = cv2.circle(frame, (int(c['x']), int(c['y'])), int(c['height'] / 2), (0, 0, 255), 5)
            #print(str(i) + '. not removed!')

    if len(contours) == 2:
        mode = 1
        contour1 = contours[0]
        contour2 = contours[1]

        DX = math.fabs(contour1['x'] - contour2['x'])

        if DX != 0:
            distance = M / (2 * math.tan(DX / 2 * (CAM_FOV * math.pi / 180) / X_RESOLUTION))
            locationRight = math.atan((CAMERA_OFFSET + M / 2) / distance) * X_RESOLUTION / (CAM_FOV * math.pi / 180)
            locationLeft = math.atan((CAMERA_OFFSET - M / 2) / distance) * X_RESOLUTION / (CAM_FOV * math.pi / 180)
        else:
            distance = -1
            locationLeft = -1
            locationRight = -1
    elif len(contours) == 1:
        mode = 2
        contour = contours[0]

        DX = contour['width']
        print('Width: ' + str(DX))

        if DX != 0:
            distance = 2 / (2 * math.tan(DX / 2 * (CAM_FOV * math.pi / 180) / X_RESOLUTION))
            locationLeft = math.atan((CAMERA_OFFSET - M / 2) / distance) * X_RESOLUTION / (CAM_FOV * math.pi / 180)
            locationRight = -1
    else:
        distance = -1
        locationLeft = -1
        locationRight = -1

    zone = -1
    area = 0

    #Finding zones
    if len(contours) == 2:
        zone2 = [X_RESOLUTION / 2 + locationLeft - 150, X_RESOLUTION / 2 + locationLeft + 150]
        midpoint = (contours[0]['x'] + contours[1]['x']) / 2

        if midpoint < zone2[0]:
            zone = 1
        elif midpoint > zone2[1]:
            zone = 3
        else:
            zone = 2
    elif len(contours) == 1:
        zone2 = [X_RESOLUTION / 2 + locationLeft - 20, X_RESOLUTION / 2 + locationLeft + 20]
        contour = contours[0]
        area = contour['area']

        #print(math.fabs(Y_RESOLUTION / 2 - contour['y']))
    elif len(contours) > 2 or len(contours) == 0:
        mode = -1
        print('No contours!')

    '''
    for c in contours:
        widths.append(c['width'])
        heights.append(c['height'])
        ys.append(c['y'])
        xs.append(c['x'])
        areas.append(c['area'])

    table.putNumberArray("widths", widths)
    table.putNumberArray("heights", heights)
    table.putNumberArray("ys", ys)
    table.putNumberArray("xs", xs)
    table.putNumberArray("areas", areas)

    print("Widths: " + str(widths))
    print("heights: " + str(heights))
    print("ys: " + str(ys))
    print("xs: " + str(xs))
    print("areas: " + str(areas))
    '''

    print('Location Right: ' + str(locationRight))
    print('Location Left: ' + str(locationLeft))

    #print(str(contours))
    print('Distance: ' + str(distance) + ', Zone: ' + str(zone) + ', Area: ' + str(area))
    table.putNumber('distance', distance)
    table.putNumber('locationRight', locationRight)
    table.putNumber('locationLeft', locationLeft)
    table.putNumber('zone', zone)
    table.putNumber('mode', mode)
    table.putBoolean('connected', True)
    #print('should be connected')

    #Green Zone Markings
    if(distance > 5500 / X_RESOLUTION + 0.2):
        frame = cv2.line(frame, (int(zone2[0]), 0), (int(zone2[0]), Y_RESOLUTION), (0, 255, 0));
        frame = cv2.line(frame, (int(zone2[1]), 0), (int(zone2[1]), Y_RESOLUTION), (0, 255, 0));

    #Red Coarse-Y Check Markings
    frame = cv2.line(frame, (0, int(Y_RESOLUTION / 2 - Y_RANGE + RANGE_OFFSET)), (int(X_RESOLUTION), int(Y_RESOLUTION / 2 - Y_RANGE + RANGE_OFFSET)), (0, 0, 255))
    frame = cv2.line(frame, (0, int(Y_RESOLUTION / 2 + Y_RANGE + RANGE_OFFSET)), (int(X_RESOLUTION), int(Y_RESOLUTION / 2 + Y_RANGE + RANGE_OFFSET)), (0, 0, 255))

    #Circles on x centers
    #for c in contours:
        #frame = cv2.circle(frame, (c['x'], c['y']), 10, (255, 0, 255))

    print(mode)
    print('----------------------------')

    #cv2.imshow('BW', new)
    #cv2.imshow('Color', frame)

    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()
