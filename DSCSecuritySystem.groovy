/**
 *  AlarmThing
 *
 *  Author: ObyCode
 *   based on work by Josh Foshee
 *  Date: 2014-06-10
 */

metadata {
    // Automatically generated. Make future change here.
    definition (name: "AlarmThing", author: "brice@obycode.com") {
        capability "Alarm"
        capability "Switch"
        capability "Motion Sensor"
        capability "Contact Sensor"
        
        attribute "alarmStatus", "string"
        attribute "frontDoor", "string"
        attribute "sideDoor", "string"
        attribute "backDoor", "string"
        attribute "basementDoor", "string"
        attribute "firstFloorMotion", "string"
        attribute "secondFloorMotion", "string"
        attribute "thirdFloorMotion", "string"
        attribute "basementMotion", "string"

        command "armAway"
        command "armStay"
        command "disarm"
        command "clear"
        command "update"
        command "chimeToggle"
        command "panic"
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
                        state "ready", label: 'Ready', action: "armAway", icon: "st.Home.home2", backgroundColor: "#ffffff"
                        state "disarmed", label: 'Ready', action: "armAway", icon: "st.Home.home2", backgroundColor: "#ffffff"
                        state "notready", label: 'Not Ready', icon: "st.Home.home2", backgroundColor: "#ffa81e"
                        state "away", label: 'Away', action: "disarm", icon: "st.Home.home3", backgroundColor: "#add8e6"
                        state "stay", label: 'Stay', action: "disarm", icon: "st.Home.home4", backgroundColor: "#0000ff"
                        state "arming", label: 'Arming', action: "disarm", icon: "st.Home.home2", backgroundColor: "#B8B8B8"
                        state "alarm", label: 'Alarm', action: "clear", icon: "st.Home.home2", backgroundColor: "#ff0000"
                }
                standardTile("away", "device.awaySwitch", width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
                        state "on", label: "Away", action: "armAway", icon: "st.Home.home3", backgroundColor: "#add8e6"
                        state "off", label: "Away", icon: "st.Home.home3", backgroundColor: "#ffffff"
                }
                standardTile("stay", "device.staySwitch", width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
                        state "on", label: "Stay", action: "armStay", icon: "st.Home.home4", backgroundColor: "#0000ff"
                        state "off", label: "Stay", icon: "st.Home.home4", backgroundColor: "#ffffff"
                }

                standardTile("frontDoor", "device.frontDoor", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
                        state "open", label: 'Front Door', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                        state "closed", label: 'Front Door', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                }
                standardTile("sideDoor", "device.sideDoor", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
                        state "open", label: 'Side Door', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                        state "closed", label: 'Side Door', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                }
                standardTile("backDoor", "device.backDoor", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
                        state "open", label: 'Back Door', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                        state "closed", label: 'Back Door', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                }
                standardTile("basementDoor", "device.basementDoor", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
                        state "open", label: 'Base Door', icon: "st.contact.contact.open", backgroundColor: "#ffa81e"
                        state "closed", label: 'Base Door', icon: "st.contact.contact.closed", backgroundColor: "#79b821"
                }
                standardTile("firstFloorMotion", "device.firstFloorMotion", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
                        state "active", label: '1st Flr', icon: "st.motion.motion.active", backgroundColor: "#ffa81e"
                        state "inactive", label: '1st Flr', icon: "st.motion.motion.inactive", backgroundColor: "#79b821"
                }
                standardTile("secondFloorMotion", "device.secondFloorMotion", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
                        state "active", label: '2nd Flr', icon: "st.motion.motion.active", backgroundColor: "#ffa81e"
                        state "inactive", label: '2nd Flr', icon: "st.motion.motion.inactive", backgroundColor: "#79b821"
                }
                standardTile("thirdFloorMotion", "device.thirdFloorMotion", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
                        state "active", label: '3rd Flr', icon: "st.motion.motion.active", backgroundColor: "#ffa81e"
                        state "inactive", label: '3rd Flr', icon: "st.motion.motion.inactive", backgroundColor: "#79b821"
                }
                standardTile("basementMotion", "device.basementMotion", width: 1, height: 1, canChangeIcon: true, canChangeBackground: true) {
                        state "active", label: 'Basement', icon: "st.motion.motion.active", backgroundColor: "#ffa81e"
                        state "inactive", label: 'Basement', icon: "st.motion.motion.inactive", backgroundColor: "#79b821"
                }
                standardTile("chime", "device.chime", width:1, height: 1, canChangeIcon: false, canChangeBackground: false) {
                        state "chimeOff", label:'Chime', action:'chimeToggle', icon:"st.secondary.off", backgroundColor: "#ffffff"
                        state "chimeOn", label:'', action:'chimeToggle', icon:"st.secondary.beep", backgroundColor: "#ffffff"
                }
                standardTile("panic", "device.panic", width: 1, height: 1, canChangeIcon: false, canChangeBackground: true) {
                        state "panic", label:'Panic', action:"panic", icon:"st.alarm.alarm.alarm", backgroundColor:"#ff0000"
                }
                standardTile("refresh", "device.refresh", inactiveLabel: false, width: 1, height: 1, canChangeIcon: false, canChangeBackground: false) {
                        state "default", action:"update", icon:"st.secondary.refresh"
                }
                main(["alarmStatus"])
                details(["alarmStatus","away","stay","frontDoor","sideDoor","backDoor","basementDoor","firstFloorMotion","secondFloorMotion","thirdFloorMotion","basementMotion","chime","refresh","panic"])
        }
}

// Parse incoming device messages to generate events
def parse(String description) {
    log.debug description
    def msg = zigbee.parse(description)?.text
    log.debug "Received ${msg}"
    def result
    if (!msg || msg == "ping") {
        result = createEvent(name: null, value: msg)
    } else if ( msg.length() >= 4 ) {
        if ( msg.substring(0, 2) == "RD" ) {
            if (msg[3] == "0") {
                result = createEvent(name: "alarmStatus", value: "notready")
                // When status is "Not Ready" we cannot arm
                //createEvent(name: "away", value: "off")
                //createEvent(name: "stay", value: "off")
                sendEvent(name: "switchAway", value: "off")
                sendEvent(name: "switchStay", value: "off")
            }
            else {
                result = createEvent(name: "alarmStatus", value: "ready")
                // When status is "Ready" we can arm
                //createEvent(name: "away", value: "on")
                //createEvent(name: "stay", value: "on")
                sendEvent(name: "switchAway", value: "on")
                sendEvent(name: "switchStay", value: "on")
                sendEvent(name: "switch", value: "off")
                sendEvent(name: "panic", value: "off")
            }
        // Process arm update
        } else if ( msg.substring(0, 2) == "AR" ) {
            if (msg[3] == "0") {
                result = createEvent(name: "alarmStatus", value: "disarmed")
                sendEvent(name: "switch", value: "off")
            }
            else if (msg[3] == "1") {
                if (msg[4] == "0" | msg[4] == "2") {
                    result = createEvent(name: "alarmStatus", value: "away")
                    sendEvent(name: "switch", value: "on")
                }
                else if (msg[4] == "1" | msg[4] == "3") {
                    result = createEvent(name: "alarmStatus", value: "stay")
                    sendEvent(name: "switch", value: "on")
                }
            }
            else if (msg[3] == "2") {
                result = createEvent(name: "alarmStatus", value: "arming")
                sendEvent(name: "switch", value: "on")
            }
        // Process alarm update
        } else if ( msg.substring(0, 2) == "AL" ) {
            if (msg[3] == "1") {
                result = createEvent(name: "alarmStatus", value: "alarm")
            }
        // Process chime update
        } else if ( msg.substring(0, 2) == "CH" ) {
            if (msg[3] == "1")
                result = createEvent(name: "chime", value: "chimeOn")
            else
                result = createEvent(name: "chime", value: "chimeOff")
        // Process zone update
        } else if ( msg.substring(0, 2) == "ZN" ) {
            if ( msg.substring(3, 9) == "609001" ){
              def state = "open"
              result = createEvent(name: "frontDoor", value: state)
              sendEvent(name: "contact", value: state)
            }
            else if ( msg.substring(3, 9) == "610001" ){
              def state = "closed"
              result = createEvent(name: "frontDoor", value: state)
              sendEvent(name: "contact", value: state)
            }
            else if ( msg.substring(3, 9) == "609002" ){
              def state = "open"
              result = createEvent(name: "sideDoor", value: state)
              sendEvent(name: "contact", value: state)
            }
            else if ( msg.substring(3, 9) == "610002" ){
              def state = "closed"
              result = createEvent(name: "sideDoor", value: state)
              sendEvent(name: "contact", value: state)
            }
            else if ( msg.substring(3, 9) == "609003" ){
              def state = "open"
              result = createEvent(name: "backDoor", value: state)
              sendEvent(name: "contact", value: state)
            }
            else if ( msg.substring(3, 9) == "610003" ){
              def state = "closed"
              result = createEvent(name: "backDoor", value: state)
              sendEvent(name: "contact", value: state)
            }
            else if ( msg.substring(3, 9) == "609004" ){
              def state = "open"
              result = createEvent(name: "basementDoor", value: state)
              sendEvent(name: "contact", value: state)
            }
            else if ( msg.substring(3, 9) == "610004" ){
              def state = "closed"
              result = createEvent(name: "basementDoor", value: state)
              sendEvent(name: "contact", value: state)
            }
            else if ( msg.substring(3, 9) == "609013" ){
              def state = "active"
              result = createEvent(name: "basementMotion", value: state)
              sendEvent(name: "motion", value: state)
            }
            else if ( msg.substring(3, 9) == "610013" ){
              def state = "inactive"
              result = createEvent(name: "basementMotion", value: state)
              sendEvent(name: "motion", value: state)
            }
            else if ( msg.substring(3, 9) == "609014" ){
              def state = "active"
              result = createEvent(name: "firstFloorMotion", value: state)
              sendEvent(name: "motion", value: state)
            }
            else if ( msg.substring(3, 9) == "610014" ){
              def state = "inactive"
              result = createEvent(name: "firstFloorMotion", value: state)
              sendEvent(name: "motion", value: state)
            }
            else if ( msg.substring(3, 9) == "609015" ){
              def state = "active"
              result = createEvent(name: "thirdFloorMotion", value: state)
              sendEvent(name: "motion", value: state)
            }
            else if ( msg.substring(3, 9) == "610015" ){
              def state = "inactive"
              result = createEvent(name: "thirdFloorMotion", value: state)
              sendEvent(name: "motion", value: state)
            }
            else if ( msg.substring(3, 9) == "609016" ){
              def state = "active"
              result = createEvent(name: "secondFloorMotion", value: state)
              sendEvent(name: "motion", value: state)
            }
            else if ( msg.substring(3, 9) == "610016" ){
              def state = "inactive"
              result = createEvent(name: "secondFloorMotion", value: state)
              sendEvent(name: "motion", value: state)
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

def update() {
    log.debug "Sending update command"
    zigbee.smartShield(text: "update").format()
}

def configure() {
    update()
}
