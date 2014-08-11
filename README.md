DSCAlarmSmartThings
===================
DSC Alarm interface to SmartThings. Able to arm/disarm, monitor zone status and trigger the panic alarm.

Author: Vassilis Varveropoulos, Brice Dobry

Date: 6/10/2014

Required hardware
------------------
* DSC Alarm System
* IT-100 interface
* Arduino MEGA
* Arduino RS232 shield
* Arduino SmartThings shield

Required software
-----------------
* Arduino IDE (>v1)
* Libraries and source of the Arduino folder in this repository
* Open the HomeAlarm.ino file and enter your pin at the right location (see the comments)

How to connect the hardware
---------------------------
The IT-100 interface is connected to the communication and power lines of the DSC alarm panel (AUX+/AUX- for power and GRN/YEL for data). An Arduino is used to decode the commands from the alarm system and interface with the SmartThings hub. The output of the IT-100 interface is RS232 at 12V levels therefore it can't be directly connected to the Arduino board (it will only accept 5V levels), therefore an RS232 shield is used to convert to the right levels.

A SmartThings shield is used to interface with the SmartThings hub and cloud. The Arduino needs to be capable of receiving and transmitting through two serial ports (one for the IT-100 and another for the SmartThings shield). An Arduino Mega was chosen since it has more than two serial ports. An attempt was made to use an Arduino Uno with one hardware and one software serial port but it was not fast enough to handle all communications and as a result messages were lost. The SmartThings serial port is operating a 2400 bps and if a software serial port is used it blocks all other communication while a message is sent to the hub, which at those speeds it could be several ms.

The official SmartThings Arduino library will only support a software serial port, as a result the library had to be modified in order to utilize a hardware serial port. By default the RS232 shield will use pins 1, 2 and the SmartThings shield will use pins 3, 4. All four of those lines had to be re-routed to the dedicated serial ports of the Arduino Mega. I did this by bending the pins, and running wires to the appropriate ports. The Rx and Tx pins of the RS232 shield are bent, and wires are run to connect Rx to port 19 (Rx1) of the MEGA, and Tx to port 18 (Tx1). Similarly, the Rx and Tx pins of the SmartThings shield are bent and wires are run to pins 17 (Rx2) and 16 (Tx2), respectively. See the pictures (IMG_426*.JPG) for details. 

The order of the shield stacking is the following (from bottom to top), Arduino Mega, SmartThings shield and RS232 shield. Make sure that the SmartThings shield serial port selection switch is set to pins 2,3.

Another option using the prototyping shield is described in the project this was forked from: The Arduino prototyping shield was used to achieve that. Pins 1, 2, 4 and 4 of the prototyping shield were bent (so no connection is made to the Arduino board) and re-routed to the appropriate Arduino Mega pins. Then through the prototyping shield pin 0 was routed to pin 19 (RX1), pin 1 to pin 18 (TX1), pin 2 to pin 16 (TX2), pin 3 to pin 17 (RX2). In the Arduino firmware Serial1 was assigned to the IT-100 interface and Serial2 to the SmartThings shield.

There are obviously other ways to achieve the above, for example it is possible to reroute the pins without a prototyping shield. It is also possible to build the RS232 level translator on the prototyping shield using a MAX232 or equivalent device.

Before programming the Arduino board you will need to modify the HomeAlarm.ino file with your pin code (3 places, see comments), otherwise arming and disarming will not work.

A few pictures of the hardware can be found in the repository.

Setup of the custom device type
-------------------------------
The source code for the device type is also provided. You will need to modify this file to setup the proper zone numbers for your system. Use the provided source code and publish the device type. Pair your SmartThings shield to you hub and then manually change its device type to the one you created. You should now be able to see your new device and will be able to arm/disarm, monitor the state of the zones, trigger the panic alarm, etc.

Credits
--------
* Timer library from Simon Monk
* SmartThings shield library from SmartThings (modified for ArduinoMega)
* Original code forked from Vassilis Varveropoulos
