BehavioralInterface PSSM
	accepted event run:
		parameters = [state_machine: StateMachine]
	accepted event signal_received:
		parameters = [state_machine: StateMachine, signal: Signal]
	accepted event callOperation_received:
		parameters = [state_machine: StateMachine, operation: Operation]
	exposed event behavior_executed:
		parameters = [behavior: Behavior]