# XTDL: Executable Test Description Language for Executable DSLs
XTDL is a generic approach that provides testing facilities for executable DSLs.

**Contents of this repository:**

- *languages:*

| DSL Definition| 
| ------ |
*XArduino-ALE*: the complete definition of an executable Arduino DSL. The operational semantics are implemented using [ALE metaprogramming approach](http://gemoc.org/ale-lang/). 
*XArduino-K3*: the complete definition of an executable Arduino DSL. The operational semantics are implemented using [Kermeta3 metaprogramming approach](http://diverse-project.github.io/k3/).
*XBPMN-ALE*: a representative of [Microflow DSL](https://docs.mendix.com/refguide/microflows). The operational semantics are implemented using ALE metaprogramming approach.
*XFSM-ALE*: the complete definition of an executable FSM DSL. The operational semantics are implemented using ALE metaprogramming approach.
*XFSM-K3*: the complete definition of an executable FSM DSL. The operational semantics are implemented using Kermeta3 metaprogramming approach.

- *org.etsi.mts....*: A set of plugins from [TDL open source project](https://labs.etsi.org/rep/top/ide) which providing the definition of the TDL abstract syntax (metamodel), the concrete syntax (both textual and graphical), and the constraints.
- *org.imt.atl.ecore2tdl*: An ATL transformation from Ecore to TDL
- *org.imt.k3tdl.tdlInterpreter*: The operational semantics of TDL language implemented using Kermeta3 metaprogramming approach.
- *org.imt.tdl.configuration*: The automatic test configuration to execute test cases on the model under test. It is integrated to the model execution engines and also uses [Eclipse OCL API](https://download.eclipse.org/ocl/javadoc/6.4.0/) to evaluate OCL queries on the models.
- *org.imt.tdl.libraryGenerator*: By having a DSL as input, it generates a TDL library specific to it.
- *org.imt.tdl.rt.ui*: Providing GUI icons for TDL library generator
- *org.imt.tdl.xdsml*: The definition of the executable TDL language
- *runtime.models&tests*: A set of models conforming to the provided languages along with their TDL test cases.

## Using the XTDL
To use XTDL for a given DSL, the following steps have to be followed.
1. Import the intended input DSL into the plugins workspace. For example, any of the DSLs in the *languages* folder can be used.
2. Run as *Eclipse Application*
3. In the second workspace, define the models conforming to the input DSL which are going to be tested. For example, the models in the *runtime.models&tests* folder can be imported.
4. Create a new *Modeling project* for the definition of the test cases.
5. Run the library generator using the GUI icon provided for it (it is in the toolbar and in the menubar): It pop-ups a new window asking a project and a DSL to be choosed from a list. Choose the project created in the previous step as the project and the input DSL imported in the plugins workspace as the DSL. By clicking the *Finish* button, a set of TDL files will be defind under the selected project.
6. Define the TDL test cases using the generated TDL files. To do so, you can define a new TDL file with the filename ending with .tdlan2, or you can use the generated genericTestCases.tdlan2 and oclTestCases.tdlan2 files containing the required packages for writing test cases. For example, a set of TDL test cases are provided in the *runtime.models&tests* folder.
7. Run the test cases using the *Executable model with GEMOC Java engine* run configuration
