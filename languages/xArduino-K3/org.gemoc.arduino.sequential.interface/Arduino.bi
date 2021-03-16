BehavioralInterface ArduinoInterface
	accepted event run:
		parameters = [sketch: org.gemoc.arduino.sequential.xarduino.arduino.Sketch]
	accepted event button_pressed:
		parameters = [button: org.gemoc.arduino.sequential.xarduino.arduino.PushButton]
	accepted event button_released:
		parameters = [button: org.gemoc.arduino.sequential.xarduino.arduino.PushButton]
	exposed event led_level_changed:
		parameters = [led: org.gemoc.arduino.sequential.xarduino.arduino.LED, level: java.lang.Integer]
