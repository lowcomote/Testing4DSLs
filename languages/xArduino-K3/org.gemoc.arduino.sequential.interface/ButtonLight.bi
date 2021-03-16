BehavioralInterface ButtonLightInterface
	accepted event start:
	accepted event button_pushed:
		parameters = [button_id: java.lang.String]
	exposed event light_on:
		parameters = [light_id: java.lang.String]
	exposed event light_off:
		parameters = [light_id: java.lang.String]
	exposed event light_blinked:
		parameters = [light_id: java.lang.String]
