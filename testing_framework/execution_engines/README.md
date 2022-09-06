**Objective**: Automatic configurations for connecting to the external components, so the test cases can be executed on both non-reactive and reactive executable models.
- *org.imt.tdl.configuration*: Configurations for 
1. connecting to the GEMOC model execution engines to execute models under test, including: [Java Engine](https://github.com/eclipse/gemoc-studio-execution-java), [ALE Engine](https://github.com/eclipse/gemoc-studio-execution-ale), and Event-driven engine as described below.
2. using [Eclipse OCL](https://download.eclipse.org/ocl/javadoc/6.4.0/) to evaluate OCL queries on models. 
- *org.eclipse.gemoc.execution.eventBasedEngine*: An event-based model execution engine originally provided by GEMOC Studio.
- *org.eclipse.gemoc.executionframework.event.manager*: An event manager that communicates events with the event-based execution engine using a given behavioral interface. It is also provided by the [GEMOC studio](https://github.com/eclipse/gemoc-studio-modeldebugging/tree/master/framework/execution_framework/plugins/org.eclipse.gemoc.executionframework.event.manager) and we adapted it for our own purposes.