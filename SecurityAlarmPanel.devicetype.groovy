/**
 *  SecurityAlarmPanel
 *
 *  Author: ObyCode
 *   based on work by Josh Foshee
 *  Modified by Yves Racine to include system statuses
 *  Date: 2014-06-10
 */



metadata {
    // Automatically generated. Make future change here.
    definition (name: "SecurityAlarmPanel", author: "brice@obycode.com") {
        capability "Alarm"
        capability "Switch"
        capability "Motion Sensor"
        capability "Contact Sensor"
        capability "Refresh"
        
        attribute "alarmStatus", "string"
        attribute "frontDoor", "string"
        attribute "garageSideDoor", "string"
        attribute "patioDoor", "string"
        attribute "garageEntryDoor", "string"
        attribute "frontMotion", "string"
        attribute "2ndFloorMotion", "string"
        attribute "basementMotion", "string"
        attribute "basementBedMotion", "string"
        attribute "switchAway", "string"
        attribute "switchStay", "string"
        attribute "panic", "string"
        attribute "systemStatus", "string"
        attribute "response", "string"


        command "armAway"
        command "armStay"
        command "disarm"
        command "clear"
        command "update"
        command "chimeToggle"
        command "panic"
        command "away"
    }

        // Simulator metadata
    simulator {
            // status messages
            status "ping": "catchall: 0104 0000 01 01 0040 00 6A67 00 00 0000 0A 00 0A70696E67"
            status "hello": "catchall: 0104 0000 01 01 0040 00 0A21 00 00 0000 0A 00 0A48656c6c6f20576f726c6421"
    }

        // UI tile definitions
    tiles {
        
                standardTile("alarmStatus", "device.alarmStatus", width: 2, height: 2, canChangeIcon: false, canChangeBackground: false) {
                        state "ready", label: 'Ready', action: "armStay", icon: "st.Home.home2", backgroundColor: "#ffffff"
                        state "disarmed", label: 'Ready', action: "armAway", icon: "st.Home.home2", backgroundColor: "#ffffff"
                        state "notready", label: 'Not Ready', icon: "st.Home.home2", backgroundColor: "#ffa81e"
                        state "away", label: 'Away', action: "disarm", icon: "st.Home.home3", backgroundColor: "#add8e6"
                        state "stay", label: 'Stay', action: "disarm", icon: "st.Home.home4", backgroundColor: "#f1d801"
                        state "arming", label: 'Arming', action: "disarm", icon: "st.Home.home2", backgroundColor: "#B8B8B8"
                        state "alarm", label: 'Alarm', action: "clear", icon: "st.Home.home2", backgroundColor: "#ff0000"
                }
                standardTile("away", "device.awaySwitch", width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
                        state "on", label: "Away", action: "disarm", icon: "st.Home.home3", backgroundColor: "#add8e6"
                        state "off", label: "Away", action: "armAway",icon: "st.Home.home3", backgroundColor: "#ffffff"
                }
                standardTile("stay", "device.staySwitch", width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
                        state "on", label: "Stay", action: "disarm", icon: "st.Home.home4", backgroundColor: "#f1d801"
                        state "off", label: "Stay", action: "armStay",icon: "st.Home.home4", backgroundColor: "#ffffff"
                }
                
                // For the moment, each sensor has its own states due to a smartthings UI framework issue on android.
                // To be fixed later: states should be 'open', 'closed', 'active', 'inactive'
                
                standardTile("frontDoor", "device.frontDoor",width: 1, height: 1, canChangeIcon: false, canChangeBackground: true) {
                        state "FD open", label:'front\nDoor', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                        state "FD closed", label:'front\nDoor', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                }
                standardTile("garageDoor", "device.garageEntryDoor", width: 1, height: 1,inactiveLabel: false,  canChangeIcon: true, canChangeBackground: true) {
                        state "GED open", label:'garage\nEntry Door', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                        state "GED closed", label:'garage\nEntry Door', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                }
                standardTile("garageSideDoor", "device.garageSideDoor", width: 1, height: 1,inactiveLabel: false, canChangeIcon: true, canChangeBackground: true) {
                        state "GSD open", label:'garage\nSide Door', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                        state "GSD closed", label:'garage\nSide Door', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                }
                standardTile("patioDoor", "device.patioDoor", width: 1, height: 1,inactiveLabel: false,canChangeIcon:true, canChangeBackground: true) {
                        state "PD open", label:'patio\nDoor', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                        state "PD closed", label:'patio\nDoor', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                }
                standardTile("basementMotion", "device.basementMotion", width: 1, height: 1,inactiveLabel: false, canChangeIcon:true, canChangeBackground: true) {
                        state "BM active", label:'basement\nMotion', icon: "st.motion.motion.active", backgroundColor: "#ffa81e"
                        state "BM inactive", label:'basement\nMotion', icon: "st.motion.motion.inactive", backgroundColor: "#79b821"
                }
                standardTile("2ndFloorMotion", "device.2ndFloorMotion", width: 1, height: 1,inactiveLabel: false,canChangeIcon:true, canChangeBackground: true) {
                        state "2FM active", label:'2ndFloor\nMotion', icon: "st.motion.motion.active", backgroundColor: "#ffa81e"
                        state "2FM inactive", label:'2ndFloor\nMotion', icon: "st.motion.motion.inactive", backgroundColor: "#79b821"
                }
                standardTile("frontMotion", "device.frontMotion", width: 1, height: 1, inactiveLabel: false,canChangeIcon:true, canChangeBackground: true) {
                        state "FM active", label:'front\nMotion', icon: "st.motion.motion.active", backgroundColor: "#ffa81e"
                        state "FM inactive", label:'front\nMotion', icon: "st.motion.motion.inactive", backgroundColor: "#79b821"
                }
                standardTile("basementBedMotion", "device.basementBedMotion", width: 1, height: 1,inactiveLabel: false,canChangeIcon:true, canChangeBackground: true) {
                        state "BBM active", label:'basement\nBed Motion', icon: "st.motion.motion.active", backgroundColor: "#ffa81e"
                        state "BBM inactive", label:'basement\nBed Motion', icon: "st.motion.motion.inactive", backgroundColor: "#79b821"
                }
                
                standardTile("chime", "device.chime", width:1, height: 1, canChangeIcon: false, canChangeBackground: false) {
                        state "chimeOff", label:'Chime', action:'chimeToggle', icon:"st.secondary.off", backgroundColor: "#ffffff"
                        state "chimeOn", label:'', action:'chimeToggle', icon:"st.secondary.beep", backgroundColor: "#ffffff"
                }
                standardTile("panic", "device.panic", width: 1, height: 1, canChangeIcon: false, canChangeBackground: true) {
                        state "panic", label:'Panic', action:"panic", icon:"st.alarm.alarm.alarm", backgroundColor:"#ff0000"
                }
		        valueTile("systemStatus", "device.systemStatus", inactiveLabel: false,
		 	               decoration: "flat", width: 3, height: 1) {
			               state "default", label: '${currentValue}'
		        }
                standardTile("refresh", "device.refresh", inactiveLabel: false, width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
                        state "default", action:"refresh", icon:"st.secondary.refresh"
                }
                main(["alarmStatus"])
                details(["alarmStatus","away","stay","frontDoor","garageSideDoor","patioDoor","garageDoor","frontMotion","2ndFloorMotion","basementMotion","basementBedMotion","basementBedWindow", "chime","systemStatus","refresh","panic"])
        }
}



// Parse incoming device messages to generate events

def parse(String description) {

    log.debug description
    def stateToDisplay
    
    def msg = zigbee.parse(description)?.text
    log.debug "Received ${msg}"
    def result
    
    if (!msg || msg.trim() == "ping") {
        result = createEvent(name: null, value: msg)
//        update() 
    } else if ( msg.length() >= 4 ) {
        if ( msg.substring(0, 2) == "RD" ) {
            if (msg[3] == "0") {
                result = createEvent(name: "alarmStatus", value: "notready")
                // When status is "Not Ready" we cannot arm
                sendEvent(name: "awaySwitch", value: "off")
                sendEvent(name: "staySwitch", value: "off")
                sendEvent(name: "contact", value: "open")
                sendEvent(name: "response",  value: "alarmStatus notready", type: alarmStatus)
            }
            else {
                result = createEvent(name: "alarmStatus", value: "ready")
                // When status is "Ready" we can arm
                sendEvent(name: "awaySwitch", value: "off")
                sendEvent(name: "staySwitch", value: "off")
                sendEvent(name: "switch", value: "off")
                sendEvent(name: "panic", value: "off")
                sendEvent(name: "contact", value: "open")
                sendEvent(name: "systemStatus", value: "System Status:No events")
                sendEvent(name: "response",  value: "alarmStatus ready", type: alarmStatus)
            }
        // Process arm update
        } else if ( msg.substring(0, 2) == "AR" ) {
            if (msg[3] == "0") {
                result = createEvent(name: "alarmStatus", value: "disarmed") 
                sendEvent(name: "awaySwitch", value: "off")
                sendEvent(name: "staySwitch", value: "off")
                sendEvent(name: "switch", value: "off")
                sendEvent(name: "contact", value: "open")
                sendEvent(name: "response",  value: "alarmStatus disarmed", type: alarmStatus)
            }
            else if (msg[3] == "1") {
                if (msg[4] == "0" | msg[4] == "2") {
                    result = createEvent(name: "alarmStatus", value: "away")
                	sendEvent(name: "awaySwitch", value: "on")
                	sendEvent(name: "staySwitch", value: "off")
                    sendEvent(name: "switch", value: "on")
                    sendEvent(name: "contact", value: "closed")
                    sendEvent(name: "response",  value: "alarmStatus away", type: alarmStatus)
                }
                else if (msg[4] == "1" | msg[4] == "3") {
                    result = createEvent(name: "alarmStatus", value: "stay")
                    sendEvent(name: "awaySwitch", value: "off")
                    sendEvent(name: "staySwitch", value: "on")
                    sendEvent(name: "switch", value: "on")
                    sendEvent(name: "contact", value: "closed")
                    sendEvent(name: "response",  value: "alarmStatus stay", type: alarmStatus)
                }
            }
            else if (msg[3] == "2") {
                result = createEvent(name: "alarmStatus", value: "arming")
                sendEvent(name: "awaySwitch", value: "off")
                sendEvent(name: "staySwitch", value: "off")
                sendEvent(name: "switch", value: "on")
                sendEvent(name: "response",  value: "alarmStatus arming", type: alarmStatus)
            }
        } else if ( msg.substring(0, 2) == "SY" ) {
         // Process various system statuses
            if ( msg.substring(3, 3) == "658")  {
            
                result = createEvent(name: "systemStatus", value: "System Status\nKeypad Lockout")
            
            }
            else if ( msg.substring(3, 3) == "670")  {
            
                result = createEvent(name: "systemStatus", value: "System Status\nInvalid Access Code")
            
            }
            else if ( msg.substring(3, 3) == "672")  {
            
                result = createEvent(name: "systemStatus", value: "System Status\nFailed to arm")
            
            }
            else if ( msg.substring(3, 3) == "802")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nPanel AC Trouble")

            }
            else if ( msg.substring(3, 3) == "803")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nPanel AC Trouble Rest")

            }
            else if ( msg.substring(3, 3) == "806")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nSystem Bell Trouble")

            }
            else if ( msg.substring(3, 3) == "807")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nSystem Bell Trouble Rest")

            }
            else if ( msg.substring(3, 3) == "810")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nTLM line 1 Trouble")

            }
            else if ( msg.substring(3, 3) == "811")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nTLM line 1 Trouble Rest")

            }
            else if ( msg.substring(3, 3) == "812")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nTLM line 2 Trouble")

            }
            else if ( msg.substring(3, 3) == "813")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nTLM line 2 Trouble Rest")

            }
            else if ( msg.substring(3, 3) == "821")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nLow Battery at " + substring(6,3))

            }
            else if ( msg.substring(3, 3) == "822")  {

                
                result = createEvent(name: "systemStatus", value: "System Status\nLow Battery Rest at " + substring(6,3))

            }
            else if ( msg.substring(3, 3) == "829")  {

                result = createEvent(name: "systemStatus", value: "System Status\nSystem Tamper")

            }
            else if ( msg.substring(3, 3) == "830")  {

                result = createEvent(name: "systemStatus", value: "System Status\nSystem Tamper Rest")

            }
            else if ( msg.substring(3, 3) == "840")  {

                result = createEvent(name: "systemStatus", value: "System Status\nTrouble Status(LCD)")

            }
            else if ( msg.substring(3, 3) == "841")  {

                result = createEvent(name: "systemStatus", value: "System Status\nTrouble Status Rest")

            }
            else if ( msg.substring(3, 3) == "896")  {

                result = createEvent(name: "systemStatus", value: "System Status\nKeybus fault")

            }
            else if ( msg.substring(3, 3) == "897")  {

                result = createEvent(name: "systemStatus", value: "System Status\nKeybus Fault Rest")

            }
         
        // Process alarm update
        } else if ( msg.substring(0, 2) == "AL" ) {
            if (msg[3] == "1") {
                result = createEvent(name: "alarmStatus", value: "alarm")
                sendEvent(name: "response",  value: "alarmStatus alarm", type: alarmStatus)
            }
        // Process chime update
        } else if ( msg.substring(0, 2) == "CH" ) {
            if (msg[3] == "1") {
                result = createEvent(name: "chime", value: "chimeOn")
            } else {
                result = createEvent(name: "chime", value: "chimeOff")
            }    
        // Process zone update
        } else if ( msg.substring(0, 2) == "ZN" ) {
            if ( msg.substring(3, 9) == "609001" ){
                stateToDisplay = "2FM active"
                result = createEvent(name: "2ndFloorMotion", value: stateToDisplay)
                sendEvent(name: "motion", value: "active")
                sendEvent(name: "response",  value: "r 1 active", type: "Motion Detector")
            }
            else if ( msg.substring(3, 9) == "610001" ){
                stateToDisplay = "2FM inactive"
                result = createEvent(name: "2ndFloorMotion", value: stateToDisplay)
                sendEvent(name: "motion", value: "inactive")
                sendEvent(name: "response",  value: "r 1 inactive", type: "Motion Detector")

            }
            else if ( msg.substring(3, 9) == "609002" ){
                stateToDisplay = "BM active"
                result = createEvent(name: "basementMotion", value: stateToDisplay)
                sendEvent(name: "motion", value: "active")
                sendEvent(name: "response",  value: "r 2 active", type: "Motion Detector") 
            }
            else if ( msg.substring(3, 9) == "610002" ){
                stateToDisplay = "BM inactive"
                result = createEvent(name: "basementMotion", value: stateToDisplay)
                sendEvent(name: "motion", value: "inactive")
                sendEvent(name: "response",  value: "r 2 inactive", type: "Motion Detector") 
            }
            else if ( msg.substring(3, 9) == "609003" ){
                stateToDisplay = "BBM active"
                result = createEvent(name: "basementBedMotion", value: stateToDisplay)
                sendEvent(name: "motion", value: "active")
                sendEvent(name: "response",  value: "r 3 active", type: "Motion Detector")            
            }
            else if ( msg.substring(3, 9) == "610003" ){
                stateToDisplay = "BBM inactive"
                result = createEvent(name: "basementBedMotion", value: stateToDisplay)
                sendEvent(name: "motion", value: "inactive")
                sendEvent(name: "response",  value: "r 3 inactive", type: "Motion Detector")            
            }
            else if ( msg.substring(3, 9) == "609004" ){
                stateToDisplay = "FM active"
                result = createEvent(name: "frontMotion", value: stateToDisplay)
                sendEvent(name: "motion", value:  "active")
                sendEvent(name: "response",  value: "r 4 active", type: "Motion Detector")            
            }
            else if ( msg.substring(3, 9) == "610004" ){
                stateToDisplay = "FM inactive"
                result = createEvent(name: "frontMotion", value: stateToDisplay)
                sendEvent(name: "response",  value: "r 4 inactive", type: "Motion Detector")            
            }
            else if ( msg.substring(3, 9) == "609005" ){
                stateToDisplay = "GSD open"
                result = createEvent(name: "garageSideDoor", value: stateToDisplay)
            }
            else if ( msg.substring(3, 9) == "610005" ){
                stateToDisplay = "GSD closed"
                result = createEvent(name: "garageSideDoor", value: stateToDisplay)
                sendEvent(name: "response",  value: "r 5 open", type: "Open/Closed Sensor")            
            }
            else if ( msg.substring(3, 9) == "609006" ){
                stateToDisplay = "PD open"
                result = createEvent(name: "patioDoor", value: stateToDisplay)
                sendEvent(name: "response",  value: "r 6 open", type: "Open/Closed Sensor")            
            }
            else if ( msg.substring(3, 9) == "610006" ){
                stateToDisplay = "PD closed"
                result = createEvent(name: "patioDoor", value: stateToDisplay)
                sendEvent(name: "response",  value: "r 6 open", type: "Open/Closed Sensor")            
            }     
            else {
                log.debug "Unhandled zone: " + msg
            }
        }
    }
    
    log.debug "Parse returned ${result?.descriptionText}"
    return result
}

// Implement "switch" (turn alarm on/off)
def on() {
    armStay()
}

def off() {
    disarm()
}


def away() {
    armAway()
}

// Commands sent to the device
def armAway() {
    log.debug "Sending arm command"
    zigbee.smartShield(text: "armAway").format()
}

def armStay() {
    log.debug "Sending arm command"
    zigbee.smartShield(text: "armStay").format()
}

def disarm() {
    log.debug "Sending disarm command"
    zigbee.smartShield(text: "disarm").format()
}

def strobe() {
    panic()
}

def siren() {
    panic()
} 

def both() {
    panic()
}

def chimeToggle() {
    log.debug "Toggling chime"
    zigbee.smartShield(text: "chimeOn").format()
}

def panic() {
    log.debug "Sending panic command"
    zigbee.smartShield(text: "panic").format()
}

// TODO: Need to send off, on, off with a few secs in between to stop and clear the alarm
def clear() {
    disarm()
}

def refresh() {
    update()
}

def update() {
    log.debug "Sending update command"
    zigbee.smartShield(text: "update").format()
}

def configure() {
    update()
}
