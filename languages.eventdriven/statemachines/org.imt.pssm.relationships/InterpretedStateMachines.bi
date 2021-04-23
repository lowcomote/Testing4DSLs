BehavioralInterface PSSM
	accepted event run:
		parameters = [state_machine: StateMachine]
	accepted event signal_received:
		parameters = [state_machine: StateMachine, signal: Signal]
	exposed event behavior_effected:
		parameters = [behavior: Behavior]
