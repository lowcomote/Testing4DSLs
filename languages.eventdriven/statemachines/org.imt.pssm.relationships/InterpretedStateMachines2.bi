BehavioralInterface PSSM
	accepted event call_run:
		parameters = [state_machine: StateMachine]
	exposed event run_called:
		parameters = [state_machine: StateMachine]
	exposed event run_returned:
		parameters = [state_machine: StateMachine]
	accepted event call_signal_received:
		parameters = [state_machine: StateMachine, signal: Signal]
	exposed event signal_received_called:
		parameters = [state_machine: StateMachine, signal: Signal]
	exposed event signal_received_returned:
		parameters = [state_machine: StateMachine, signal: Signal]
	accepted event call_behavior_effected:
		parameters = [behavior: Behavior]
	exposed event behavior_effected_called:
		parameters = [behavior: Behavior]
	exposed event behavior_effected_exposed:
		parameters = [behavior: Behavior]