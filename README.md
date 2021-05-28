# XTDL: Executable Test Description Language for Executable DSLs
XTDL is a generic approach that provides testing facilities for executable DSLs.

**Contents of this repository:**

- *Language.sequential_Workbench*

| DSL Definition| 
| ------ |
*XArduino-K3&ALE*: the complete definition of an executable Arduino DSL. The operational semantics is implemented using two different metaprogramming approaches: [Kermeta3 metaprogramming approach](http://diverse-project.github.io/k3/) and [ALE metaprogramming approach](http://gemoc.org/ale-lang/). 
*XBPMN-ALE*: a representative of [Microflow DSL](https://docs.mendix.com/refguide/microflows). The operational semantics is implemented using the ALE metaprogramming approach.
*XFSM-ALE*: the complete definition of an executable FSM DSL. The operational semantics is implemented using the ALE metaprogramming approach.
*XFSM-K3*: the complete definition of an executable FSM DSL. The operational semantics is implemented using the Kermeta3 metaprogramming approach.

- *Modeling.sequential_Workbench*: A set of models conforming to the provided DSLs along with a set of test cases for each of them.

- *org.etsi.mts.graphical...*: A set of plugins from [TDL open-source project](https://labs.etsi.org/rep/top/ide) which define the graphical concrete syntax of the TDL. We extended the standard to provide model animation.
- *org.imt.atl.ecore2tdl*: An ATL transformation from Ecore to TDL.
- *org.imt.k3tdl.tdlInterpreter*: The operational semantics of TDL language implemented using Kermeta3 metaprogramming approach.
- *org.imt.tdl.configuration*: The automatic test configuration to execute test cases on the executable models. It is currently integrated to two model execution engines (Kermeta3 and ALE) and also uses [Eclipse OCL API](https://download.eclipse.org/ocl/javadoc/6.4.0/) to evaluate OCL queries on the models.
- *org.imt.tdl.libraryGenerator*: By having a DSL as input, it generates a TDL library specific to it. The library provides the DSL_specific TDL data types for the definition of test data, the required test configurations, a set of commands for requesting the execution of the model under test, and facilities for using OCL queries when writing test cases.
- *org.imt.tdl.rt.ui*: Providing GUI icons for running the TDL library generator, and a graphical view to report the test execution result.
- *org.imt.tdl.runner*: A java-based runner for the TDL test cases.
- *org.imt.tdl.testResult*: A set of classes for reporting the test execution result in different granularity levels:
1. `TDLMessageResult`: providing the result of executing every single message of a test case
2. `TDLTestCaseResult`: providing the result of executing each test case of the test suite
3. `TDLTestPackageResult`: providing the result of the test suite execution
- *org.imt.tdl.xdsml*: The definition of the executable TDL language

## Using the XTDL
**Requirements**: 
- Gemoc studio V3.2.0 ([udpate site](http://download.eclipse.org/gemoc/updates/releases/3.2.0))
- TDL tools ([udpate site](https://tdl.etsi.org/eclipse/latest/))
- ATL transformation language ([update site](http://download.eclipse.org/mmt/atl/updates/releases/4.4.0))

**Setup**: 
Find the `GenerateData.mwe` from org.etsi.mts.tdl.graphical.labels.data -> src -> org.etsi.mts.tdl.graphical.labels
Right click and select Run As -> MWE2 Workflow

**Using the XTDL**: 
To use XTDL for a given DSL, the following steps have to be followed.
1. Import the intended input DSL into the plugins workspace. For example, any of the DSLs in the *Language.sequential_Workbench* folder can be used.
2. Run as `Eclipse Application`
3. In the second workspace, define the models conforming to the input DSL which are going to be tested. For example, the models in the *Modeling.sequential_Workbench* folder can be imported.
4. Define test cases for the models defined in the previous step. For example, the tests in the *Modeling.sequential_Workbench* folder can be imported. 
To write test cases from scratch, do the following steps:
- Create a new `Modeling project` for the definition of the test cases.
- Run the library generator using the GUI icon provided for it (it is in the toolbar and also in the menubar): It pop-ups a new window asking a project and a DSL to be chosen from a list. Choose the project you just created and the DSL imported in the plugins workspace as the input DSL. By clicking the `Finish` button, a set of TDL files (i.e., the TDL library specific to the input DSL) will be defined under the selected project.
- Define the TDL test cases using the generated TDL files. To do so, you can define a new TDL file with the filename ending with `.tdlan2`, or you can use the generated `testSuite.tdlan2` file containing the required packages for writing test cases.
5. Run the test cases using the `Executable model with GEMOC Java engine` run configuration (For the sample test suites existing in the *Modeling.sequential_Workbench* folder, the required launch configurations are provided).
6. To see the test result follow Window -> Show View -> Other -> TDL -> TDL Test Results. This is ![a sample screenshot of our tool](https://gitlab.univ-nantes.fr/naomod/faezeh-public/xtdl/-/blob/master/tool.PNG).
