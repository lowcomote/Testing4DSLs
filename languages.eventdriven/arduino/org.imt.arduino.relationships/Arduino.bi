BehavioralInterface ArduinoInterface
	accepted event run:
		parameters = [sketch: Sketch]
	accepted event button_pressed:
		parameters = [button: PushButton]
	accepted event button_released:
		parameters = [button: PushButton]
	exposed event led_level_changed:
		parameters = [led: LED]
