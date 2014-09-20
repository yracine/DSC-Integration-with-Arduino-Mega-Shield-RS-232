DSCAlarmSmartThings
===================
DSC Alarm interface to SmartThings. Able to arm/disarm, monitor zone status and trigger the panic alarm.

Author: Vassilis Varveropoulos, Brice Dobry

Modified by Yves Racine


Required hardware
------------------
* Compatible DSC Alarm System 
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
The IT-100 interface is connected to the communication and power lines of the DSC alarm panel (AUX+/AUX- for power and GRN/YEL for data). An Arduino is used to decode the commands from the alarm system and interface with the SmartThings hub. The output of the IT-100 interface is RS232 at 12V levels therefore it can't be directly connected to the Arduino board (it will only accept 5V levels), therefore an RS232 shield is used to convert to the right levels.  You may also use a 9v (1A) power adapter connected to the Arduino Mega to have a steady power source.

A SmartThings shield is used to interface with the SmartThings hub and cloud. The Arduino needs to be capable of receiving and transmitting through two serial ports (one for the IT-100 and another for the SmartThings shield). An Arduino Mega was chosen since it has more than two serial ports. An attempt was made to use an Arduino Uno with one hardware and one software serial port but it was not fast enough to handle all communications and as a result messages were lost. The SmartThings serial port is operating a 2400 bps and if a software serial port is used it blocks all other communication while a message is sent to the hub, which at those speeds it could be several ms.

The official SmartThings Arduino library will only support a software serial port, as a result the library had to be modified in order to utilize a hardware serial port. 

The order of the shield stacking is the following (from bottom to top), Arduino Mega, SmartThings shield and RS232 shield. Make sure that the SmartThings shield serial port selection switch is set to pins 1,2. By default the RS232 shield will also use pins 1 &2.

All four lines had to be re-routed to the dedicated serial ports of the Arduino Mega. You do it by bending the pins, and running wires to the appropriate ports. The Rx and Tx pins of the RS232 shield are bent, and wires are run to connect Rx to port 19 (Rx1) of the MEGA, and Tx to port 18 (Tx1). Similarly, the Rx and Tx pins of the SmartThings shield are bent and wires are run to pins 17 (Rx2) and 16 (Tx2), respectively. See the pictures (IMG_426*.JPG) for details. 

Before programming the Arduino board you will need to modify the SmartthingsDCSAlarm.ino file with your pin code (3 places, see comments), otherwise arming and disarming will not work.

Once you have finished transfering the code to the Arduino, you can remove the USB and power the Arduino using a 9V transformer.

A few pictures of the hardware can be found in the repository.

Arduino Code
------------

To load the code onto the Arduino, you will need the Arduino developer environment:

http://arduino.cc/en/main/software

Once the software is installed, the first thing to do is obtain the required libraries.

Timer library was created by Simon Monk as modified by JChristensen. A copy is included in this repository/release for your convenience.

SmartThings Mega library contained in this repository/release
SoftwareSerial library was default library provided with Arduino IDE

Once you have the required files downloaded you can import them within the Arduino IDE. Go to the Sketch:Import Library;Add Library drop down menu. Once you have added the libraries, they will show up under Sketch:Add Library:Contributed as "Timer" and "SmartThingsMega". Be sure the Timer library is installed named as "Timer"

You can connect the Arduino Mega to your computer via an USB cable, create a new sketch, paste the code from github into the Arduino IDE and then transfer to tehe ArduinoMEGA

Setup of the Arduino Shield and Smartthings custom device type
---------------------------------------------------------------

Pairing instructions for the Arduino shield to the SmartThings hub can be found at SmartThings.com and are copied here:

“To join the shield to your SmartThings hub, go to “Add SmartThings” mode in the
SmartThings app by hitting the “+” icon in the desired location, and then press the Switch button on the shield. You should see the shield appear in the app.

To unpair the shield, press and hold the Switch button for 6 seconds and release. The shield will now be unpaired from your SmartThings Hub.”

The source code for the device type is also provided. You will need to modify this file to setup the proper zone numbers for your system. Use the provided source code and publish the device type. Pair your SmartThings shield to you hub and then manually change its device type to the one you created. You should now be able to see your new device and will be able to arm/disarm, monitor the state of the zones, trigger the panic alarm, etc.

Credits
--------
* Timer library from Simon Monk
* SmartThings shield library from SmartThings (modified for ArduinoMega)
* Original code forked from Vassilis Varveropoulos and Brice Dobry
