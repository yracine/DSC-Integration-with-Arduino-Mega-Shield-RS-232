#include <Event.h>
#include <Timer.h>
#include <SmartThingsMega.h>

//#define DEBUG_ENABLE 1
  
#define PIN_THING_LED  13

#define BUFFER_SIZE 128
#define MAX_ZONES 64

#define UPDATE_PERIOD_ARM 25283
#define UPDATE_PERIOD_ALARM 30123
#define UPDATE_PERIOD_ZONE 50234
 
SmartThingsCallout_t messageCallout;    // call out function forward decalaration
SmartThingsMega smartthing(&Serial2, messageCallout, "GenericShield", false);  // constructor
Timer timer;
char buffer[BUFFER_SIZE];
boolean zones[MAX_ZONES];
char armed;
char mode;
boolean readyStatus;
boolean alarm;
int bufferIdx;
  
void setup() 
{
  // setup hardware pins 
  pinMode(PIN_THING_LED, OUTPUT);     // define PIN_LED as an output
  digitalWrite(PIN_THING_LED, LOW);   // set value to LOW (off) to match stateLED=0
  smartthing.shieldSetLED(0, 0, 0);
  // setup IT-100 serial port
  Serial1.begin(9600);
  
  // setup debug port
#ifdef DEBUG_ENABLE
  Serial.begin(9600);
  Serial.println("Ready");
#endif
  // initialize variables
  bufferIdx = 0;
  readyStatus = true;
  armed = '0';
  alarm = false;

  // Now increase the baud rate
//  alarmSetBaudRate();
  
  alarmStatusRequest();
}
 
 
void loop()
{
  char data;
  // update periodic events
  timer.update();
  // run smartthing logic
  smartthing.run();
  // process IT-100 messages
  if (Serial1.available() > 0)
  {
    data = Serial1.read();
#ifdef DEBUG_ENABLE
    Serial.print(data);
#endif
    // if end of message then send to the cloud
    if (data == '\r' && bufferIdx > 0)
    {
#ifdef DEBUG_ENABLE
      Serial.println("processing cmd");
#endif
      // create String object
      buffer[bufferIdx] = '\0';
      bufferIdx = 0;
      String str(buffer);
      // process IT-100 message
      if (str.length() > 4)
      {
        String cmd = str.substring(0,3);
        // zone open
        if (cmd.equals("609"))
        {
          String msg = "ZN:" + str.substring(0, 6);
#ifdef DEBUG_ENABLE
          Serial.println("Zone " + str.substring(3,6) + " opened");
#endif
          smartthing.send(msg);
        }
        // zone closed
        else if (cmd.equals("610"))
        {
          String msg = "ZN:" + str.substring(0, 6);
#ifdef DEBUG_ENABLE
          Serial.println("Zone " + str.substring(3,6) + " closed");
#endif
          smartthing.send(msg);
        }
        // ready
        else if (cmd.equals("650"))
        {
          readyStatus = true;
#ifdef DEBUG_ENABLE
            Serial.println("Ready");
#endif
          sendReadyStatus();
        }
        // not ready
        else if (cmd.equals("651"))
        {
          readyStatus = false;
#ifdef DEBUG_ENABLE
            Serial.println("Not Ready");
#endif
          sendReadyStatus();
        }
        // exit delay (arming)
        else if (cmd.equals("656"))
        {
          armed = '2';
#ifdef DEBUG_ENABLE
          Serial.println("Arming");
#endif
          sendArmStatus();
        }
        // armed
        else if (cmd.equals("652"))
        {
          digitalWrite(PIN_THING_LED, HIGH);  // turn LED on
          smartthing.shieldSetLED(0, 0, 1);
          armed = '1';
          mode = str[4];
#ifdef DEBUG_ENABLE
            Serial.println("Armed " + mode);
#endif
          sendArmStatus();
        }
        // disarmed (and alarm clear)
        else if (cmd.equals("655"))
        {
          // update status
          digitalWrite(PIN_THING_LED, LOW);  // turn LED off
          smartthing.shieldSetLED(0, 0, 0);
          armed = '0';
          alarm = false;
#ifdef DEBUG_ENABLE
            Serial.println("Disarmed");
#endif
          sendArmStatus(); 
          sendAlarmStatus();
        } 
        // alarm triggered
        else if (cmd.equals("654"))
        {
          alarm = true;
#ifdef DEBUG_ENABLE
            Serial.println("Alarming");
#endif
          sendAlarmStatus();  
        }
        else if (cmd.equals("900"))
        {
#ifdef DEBUG_ENABLE
            Serial.println("Need code");
#endif
          alarmSendCode();
        }
        // LCD Update
        else if (cmd.equals("901"))
        {
#ifdef DEBUG_ENABLE
          Serial.println("LCD update");
          Serial.println(str);
#endif
          // See if the chime was turned on or off
          if (str.indexOf("Door Chime") >= 0) {
            if (str.indexOf("ON") >= 0)
              sendChimeStatus(true);
            else
              sendChimeStatus(false);
          }
        }
#ifdef DEBUG_ENABLE
        else {
           Serial.println("unsupported message"); 
           Serial.println(str);
        }
#endif
      }
    }
    // otherwise append to buffer (ignore \n)
    else if (data != '\n')
    {
      buffer[bufferIdx] = data;
      bufferIdx++;
      // check for buffer overruns
      if (bufferIdx >= BUFFER_SIZE) 
      {
        smartthing.send("ER:Buffer overrun"); 
        bufferIdx = 0;
      }
    }
  }
}

void sendArmStatus()
{
  String msg = String("AR:");
  msg = msg + armed + mode;
  smartthing.send(msg);
#ifdef DEBUG_ENABLE
  Serial.println();
  Serial.print(msg);
#endif
}

void sendReadyStatus()
{
  String msg = String("RD:");
  if (readyStatus)
  {
    msg = msg + "1";
  }
  else
  {
    msg = msg + "0";
  }
  smartthing.send(msg);
#ifdef DEBUG_ENABLE
  Serial.println();
  Serial.print(msg);
#endif
}

void sendAlarmStatus()
{
  String msg = String("AL:");
  if (alarm) 
  {
    msg = msg + "1";
  }
  else
  {
    msg = msg + "0";
  }
  smartthing.send(msg); 
#ifdef DEBUG_ENABLE
  Serial.println();
  Serial.print(msg);
#endif  
}

void sendChimeStatus(boolean state)
{
  String msg = String("CH:");
  if (state) 
  {
    msg = msg + "1";
  }
  else
  {
    msg = msg + "0";
  }
  smartthing.send(msg); 
#ifdef DEBUG_ENABLE
  Serial.println();
  Serial.print(msg);
#endif
}

void sendZoneStatus()
{
  String msg = String("ZN:");
  for (int n=0; n<MAX_ZONES; ++n) 
  {
    if (zones[n]) 
    {
      msg = msg + "1";
    } 
    else
    {
      msg = msg + "0"; 
    }
  }
  smartthing.send(msg); 
#ifdef DEBUG_ENABLE
  Serial.println();
  Serial.print(msg);
#endif  
}
 
void alarmArm()
{
  // setup and send arm command
  String cmd = String("0331****00"); //<-------- IMPORTANT: replace * with pin code 
  cmd = appendChecksum(cmd);
  Serial1.print(cmd);
#ifdef DEBUG_ENABLE
  Serial.print(cmd);
#endif  
}

void alarmArmAway()
{
  // setup and send arm command
  String cmd = String("0301");
  cmd = appendChecksum(cmd);
  Serial1.print(cmd);
#ifdef DEBUG_ENABLE
  Serial.print(cmd);
#endif  
}

// No delay
void alarmArmStay()
{
  // setup and send arm command
  String cmd = String("0321");
  cmd = appendChecksum(cmd);
  Serial1.print(cmd);
#ifdef DEBUG_ENABLE
  Serial.print(cmd);
#endif  
}

void alarmDisarm()
{
  // setup and send disarm command
  String cmd = String("0401****00"); //<------- IMPORTANT: replace * with pin code
  cmd = appendChecksum(cmd);
  Serial1.print(cmd);
#ifdef DEBUG_ENABLE
  Serial.print(cmd);
#endif    
}

void alarmSendBreak()
{
  String virtBreak = String("070^");
  virtBreak = appendChecksum(virtBreak);
  Serial1.print(virtBreak);
#ifdef DEBUG_ENABLE
  Serial.print(virtBreak);
#endif  
}

void alarmChimeToggle()
{
  String virtC = String("070c");
  virtC = appendChecksum(virtC);
  Serial1.print(virtC);
#ifdef DEBUG_ENABLE
  Serial.print(virtC);
#endif
  timer.after(1800, alarmSendBreak);
}

void alarmPanic()
{
  // setup and send panic command
  String cmd = String("0603");
  cmd = appendChecksum(cmd);
  Serial1.print(cmd);
#ifdef DEBUG_ENABLE
  Serial.print(cmd);
#endif  
}

void alarmSendCode()
{
  String code = String("2001****00"); //<------- IMPORTANT: replace * with pin code
  code = appendChecksum(code);
  Serial1.print(code);
#ifdef DEBUG_ENABLE
  Serial.print(code);
#endif
}

void alarmStatusRequest()
{
  // setup and send panic command
  String cmd = String("001");
  cmd = appendChecksum(cmd);
  Serial1.print(cmd);
#ifdef DEBUG_ENABLE
  Serial.print(cmd);
#endif
}

void alarmSetBaudRate()
{
  // setup and send baud rate command
  // 0=9600, 1=19200, 2=38400, 3=57600, 4=115200
  String cmd = String("0800");
  cmd = appendChecksum(cmd);
  Serial1.print(cmd);
#ifdef DEBUG_ENABLE
  Serial.print(cmd);
#endif
}
  
void messageCallout(String message)
{
  Serial.println("received: " + message);
  if (message.equals("update"))
  {
    alarmStatusRequest();
  }
  else if (message.equals("arm"))
  {
    alarmArm();
  }
  else if (message.equals("armAway"))
  {
    alarmArmAway();
  }
  else if (message.equals("armStay"))
  {
    alarmArmStay();
  }
  else if (message.equals("chimeOn"))
  {
    alarmChimeToggle();
  }
  else if (message.equals("chimeOff"))
  {
    alarmChimeToggle();
  }
  else if (message.equals("disarm"))
  {
    alarmDisarm();
  }
  else if (message.equals("panic"))
  {
    alarmPanic();
  }
}

String appendChecksum(String str)
{
  unsigned char cs = 0;
  for (int n = 0; n < str.length(); ++n)
  {
    cs += (unsigned char)str.charAt(n);
  }
  String csStr = String(cs, HEX);
  csStr.toUpperCase();
  return String(str + csStr + "\r\n");
}
