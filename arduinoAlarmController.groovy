/**
 *  Arduino Alarm Controller
 *
 *  Copyright 2014 Kevin Lewis
 *  Modified by Yves Racine to being able to integrate with SecurityAlarmPanel
 * 	
 *	Need to manually add device types for "smartthings : Open/Closed Sensor" and "smartthings : Motion Detector" (use smartthings sample code)
 */
definition(
    name: "Arduino Alarm Controller",
    namespace: "coolkev",
    author: "Kevin Lewis",
    description: "Turn your hardwired alarm into smart sensors",
    category: "Safety & Security",
    iconUrl: "https://graph.api.smartthings.com/api/devices/icons/st.Home.home3-icn",
    iconX2Url: "https://graph.api.smartthings.com/api/devices/icons/st.Home.home3-icn?displaySize=2x",
    iconX3Url: "https://graph.api.smartthings.com/api/devices/icons/st.Home.home3-icn?displaySize=3x")

 
preferences {

    page(name:"controllerSetup")
	
    page(name: "wiredZoneSetup")
    
    page(name: "wirelessZoneSetup")
    
    
    
    
}


def controllerSetup() {

    
	dynamicPage(name: "controllerSetup",title: "Controller Setup", nextPage:"wiredZoneSetup", uninstall:true) {
        
        section("Which Arduino shield?") {
            input "arduino", title: "Shield","capability.alarm"
        }    
        
        section("Wired Zones") {
            input "zoneCount", title: "How many wired zones?","number"
        }    
        
        section("Wireless Zones") {
            input "wirelessZoneCount", title: "How many wireless zones?","number", required: false
        }    
    }

}
def wiredZoneSetup() {

	
    def nextPage = "wirelessZoneSetup"
    
    if (settings.wirelessZoneCount==null || settings.wirelessZoneCount==0) {
    //	nextPage = null;
    }
    
   	dynamicPage(name: "wiredZoneSetup", title: "Wired Zone Setup", nextPage: nextPage) {
    
    	for (int i=1;i<=settings.zoneCount;i++) {
        	section("Zone " + i) {
                input "zone" + i, title: "Name", "string", description:"Zone " + i, required: false
                input "typezone" + i, "enum", title: "Type", options:["Open/Closed Sensor","Motion Detector","Light Sensor","Temperature Sensor", "Button"], required: false
            }
        }
        
        
        
    }
    
    
}

def wirelessZoneSetup() {

	
    dynamicPage(name: "wirelessZoneSetup", title: "Wireless Zone Setup", install:true) {
    
    	for (int i=1;i<=settings.wirelessZoneCount;i++) {
        	section("Zone " + i) {
                input "wirelesszone" + i, title: "Name", "string", description:"Zone " + i, required: false
                input "wirelesszonetype" + i, "enum", title: "Type", options:["Open/Closed Sensor","Motion Detector","Light Sensor","Temperature Sensor", "Button"], required: false
            }
        }
        
        
        
    }
    
    
}
def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}


def initialize() {

    
    subscribe(arduino, "response", zonestatusChanged)
    
    for (int i=1;i<=settings.zoneCount;i++) {
    	
    	def name = "zone$i"
	def value = settings[name]

        log.debug "checking device: ${name}, value: $value"

        def zoneType = settings["type" + name];

        if (zoneType == null || zoneType == "")
        {
            zoneType = "Open/Closed Sensor"
        }

        def existingDevice = getChildDevice(name)
        if(!existingDevice) {
            log.debug "creating device: ${name}"
            def childDevice = addChildDevice("smartthings", zoneType, name, null, [name: "Device.${name}", label: value, completedSetup: true])
        }
        else {
            //log.debug existingDevice.deviceType
            //existingDevice.type = zoneType
            existingDevice.label = value
            existingDevice.take()
            log.debug "device already exists: ${name}"

        }


    }
    
    for (int i=1;i<=settings.wirelessZoneCount;i++) {
    	
    	def name = "wirelesszone$i"
	def value = settings[name]

        log.debug "checking device: ${name}, value: $value"

        def zoneType = settings["wirelesszonetype$i"];

        if (zoneType == null || zoneType == "")
        {
            zoneType = "Open/Closed Sensor"
        }

        def existingDevice = getChildDevice(name)
        if(!existingDevice) {
            log.debug "creating device: ${name}"
            def childDevice = addChildDevice("smartthings", zoneType, name, null, [name: "$name", label: value, completedSetup: true])
        }
        else {
            //log.debug existingDevice.deviceType
            //existingDevice.type = zoneType
            existingDevice.label = value
            existingDevice.take()
            log.debug "device already exists: ${name}"

        }


    }
    
    
    
    def delete = getChildDevices().findAll { it.deviceNetworkId.startsWith("zone") && !settings[it.deviceNetworkId] }

    delete.each {
        log.debug "deleting child device: ${it.deviceNetworkId}"
        deleteChildDevice(it.deviceNetworkId)
    }
    

	runIn(300, "checkHeartbeat")
    
}

def uninstalled() {
    //removeChildDevices(getChildDevices())
}

private removeChildDevices(delete) {
    delete.each {
    	log.debug "deleting child device: ${it.deviceNetworkId}"
        deleteChildDevice(it.deviceNetworkId)
    }
}
def zonestatusChanged(evt)
{

    
    log.debug "zonestatusChanged ${evt.name} ${evt.value} ${evt.type}"
    
    def parts = evt.value.split();
        
    def part0 = parts[0]


    if (evt.type=="alarmStatus") {
    	
       	state.lastHeartbeat = now()
        log.debug "received heartbeat: ${state.lastHeartbeat}"
        
    }
    else if ((evt.type == "Open/Closed Sensor") || (evt.type == "Motion Detector")) {
    
        
        def zone = parts[1]
        def status = parts[2]

        def deviceName = "zone$zone"
        def typeSettingName = "typezone$zone"

        if (part0=="w") 
        {
            deviceName = "wirelesszone$zone"
            typeSettingName = "wirelesszonetype$zone"
        }
        log.debug "$part0 zone $zone status=$status"
        log.debug "deviceName = $deviceName"

        def device = getChildDevice(deviceName)

        if (device)
        {
            

            def zoneType = settings[typeSettingName];

            def eventName;

			if (zoneType == null || zoneType == "" || zoneType=="Open/Closed Sensor") {
 				eventName = "contact"
                status = ((status=="0") || (status == "open"))  ? "open" : "closed"
            }
            else if (zoneType=="Motion Detector")
            {
                eventName = "motion";
                status = ((status=="0") || (status == "active")) ? "active" : "inactive"
            }   
            else if (zoneType=="Light Sensor")
            {
                eventName = "illuminance"           
            }   
            else if (zoneType=="Temperature Sensor") {
            	eventName = "temperature"
            }
 
            else if (zoneType=="Button") {
            	eventName = "button"
            	status = "pushed"
            }
            
			log.debug "$device statusChanged $status"

            device.sendEvent(name: eventName, value: status, isStateChange:true)
        }
        else {

            log.debug "couldn't find device for zone ${zone}"

        }
        
    }
}



def checkHeartbeat() {


	def elapsed = now() - state.lastHeartbeat;
    log.debug "checkHeartbeat elapsed: $elapsed"
    
	if (elapsed > 30000) {
    
    	log.debug "Haven't received heartbeat in a while - alarm is offline"
        sendPush("Arduino Alarm appears to be offline - haven't received a heartbeat in over 5 minutes");
    }

	
}
