# Event-Driven XTDL: Executable Test Description Language for Reactive Executable DSLs
Event-Driven XTDL is a generic approach that provides testing facilities for reactive executable DSLs.

**Contents of this repository:**

- *Language_Workbench*: For all the following DSLs, the operational semantics is implemented using [Kermeta3 metaprogramming approach](http://diverse-project.github.io/k3/) and the behavioral interface is implemented using the [GEMOC metalanguage](https://github.com/eclipse/gemoc-studio-modeldebugging/tree/master/framework/execution_framework/plugins/org.eclipse.gemoc.executionframework.behavioralinterface). 

| DSL Definition| 
| ------ |
*PSSM_Reactive*: the complete definition of a reactive xDSL for UML state machines conforming to the [PSSM standard](https://www.omg.org/spec/PSSM/1.0/About-PSSM/).
*xArduino-Reactive*: the complete definition of a reactive xDSL for Arduinos.

- *Modeling_Workbench*: A set of models conforming to the provided reactive xDSLs along with a set of event-driven TDL test cases written for each of them.
- *org.eclipse.gemoc.execution.eventBasedEngine*: An event-based model execution engine originally provided by GEMOC Studio.
- *org.eclipse.gemoc.executionframework.event.manager*: An event manager that communicates events with the event-based execution engine using a given behavioral interface. It is also provided by the [GEMOC studio](https://github.com/eclipse/gemoc-studio-modeldebugging/tree/master/framework/execution_framework/plugins/org.eclipse.gemoc.executionframework.event.manager) and we adapted it for our own purposes.
- *org.etsi.mts.graphical...*: A set of plugins from [TDL open-source project](https://labs.etsi.org/rep/top/ide) which define the graphical concrete syntax of the TDL. We extended the standard to provide model animation.
- *org.imt.atl.ecore2tdl*: An ATL transformation from Ecore to TDL.
- *org.imt.k3tdl.tdlInterpreter*: The operational semantics of TDL language implemented using Kermeta3 metaprogramming approach.
- *org.imt.tdl.configuration*: The automatic test configuration to execute test cases on the executable models either reactive or non-reactive. It is currently integrated to three model execution engines (Kermeta3, ALE, and event-based engine) and also uses [Eclipse OCL API](https://download.eclipse.org/ocl/javadoc/6.4.0/) to evaluate OCL queries on the models.
- *org.imt.tdl.libraryGenerator*: By having an xDSL as input, it generates a TDL library specific to it. The library provides the DSL_specific TDL data types for the definition of test data, the required test configurations, a set of commands for requesting the execution of the model under test, and facilities for using OCL queries when writing test cases.
- *org.imt.tdl.rt.ui*: Providing GUI icons for running the TDL library generator, and a graphical view to report the test execution result.
- *org.imt.tdl.runner*: A java-based runner for the TDL test cases.
- *org.imt.tdl.testResult*: A set of classes for reporting the test execution result in different granularity levels:
1. `TDLMessageResult`: providing the result of executing every single message of a test case
2. `TDLTestCaseResult`: providing the result of executing each test case of the test suite
3. `TDLTestPackageResult`: providing the result of the test suite execution
- *org.imt.tdl.xdsml*: The definition of the executable TDL language

## Using the XTDL
**Requirements**: 
- Gemoc studio Version 3.3.0-SNAPSHOT (Based on Eclipse IDE 2020-12) ([download link](https://download.eclipse.org/gemoc/packages/nightly/gemoc_studio-win32.win32.x86_64.zip))
- TDL tools Version 1.0.0 ([udpate site](https://tdl.etsi.org/eclipse/latest/))
- ATL transformation language Version 4.4.0 ([update site](http://download.eclipse.org/mmt/atl/updates/releases/4.4.0))

**Setup**: 
Find the `GenerateData.mwe` from org.etsi.mts.tdl.graphical.labels.data -> src -> org.etsi.mts.tdl.graphical.labels
Right click and select Run As -> MWE2 Workflow

**Using the XTDL**: 
To use XTDL for a given xDSL, the following steps have to be followed.
1. Import the intended input reactive xDSL into the workspace. For example, any of the xDSLs in the *Language_Workbench* folder can be used.
2. Run as `Eclipse Application`
3. In the second workspace, define the models conforming to the input xDSL which are going to be tested. For example, the models in the *Modeling_Workbench* folder can be imported.
4. Define test cases for the models defined in the previous step. For example, the tests in the *Modeling_Workbench* folder can be imported. 
To write test cases from scratch, do the following steps:
- Create a new `Modeling project` for the definition of the test cases.
- Run the library generator using the GUI icons provided for it (it is in the toolbar and also in the menubar): It pop-ups a new window asking a project and an xDSL to be chosen from a list. Choose the project you just created and the xDSL imported in the first workspace as the input xDSL. By clicking the `Finish` button, a set of TDL files (i.e., the TDL library specific to the input xDSL) will be defined under the selected project.
- Define the TDL test cases using the generated TDL files. To do so, you can define a new TDL file with the filename ending with `.tdlan2`, or you can use the generated `testSuite.tdlan2` file containing the required packages for writing test cases.
5. Run the test cases using the `Executable model with GEMOC Java engine` run configuration (For the sample test suites existing in the *Modeling_Workbench* folder, the required launch configurations are provided).
6. To see the test result follow: Window -> Show View -> Other -> TDL -> TDL Test Results. This is ![a sample screenshot of our tool](https://gitlab.univ-nantes.fr/naomod/faezeh-public/xtdl/-/blob/xTDL_EventManager/testResult.PNG).

## Mutation Analysis
Using our approach, you can perform mutation analysis on TDL test suites written for executable models.
This is the result of integrating our approach with [WODEL-Test](https://link.springer.com/article/10.1007/s10270-020-00827-0) which is a model-based framework for language-independent mutation testing. 

**Requirements**:
- WODEL framework ([udpate site](http://gomezabajo.github.io/Wodel/update-site)): You may face an error during installation related to emfjason. In this case, you need to first install emfjason [from this update site](http://ghillairet.github.io/p2/).

For example, we performed mutation analysis on PSSM test suites in the `Mutation Testing/PSSMMutation` project.
The result is accessbile in the `Modeling_Workbench` folder under [MutationTesting4PSSM.TDL](https://gitlab.univ-nantes.fr/naomod/faezeh-public/xtdl/-/tree/xTDL_EventManager/Modeling_Workbench/MutationTesting4PSSM.TDL).
This is ![a sample screenshot of our tool](https://gitlab.univ-nantes.fr/naomod/faezeh-public/xtdl/-/blob/xTDL_EventManager/mutation.PNG).

