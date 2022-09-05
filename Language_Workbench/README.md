the implementation of four Executable Domain-Specific Languages (xDSLs) of our case study, including:
    
2.1. **xFSM** (taken from [GEMOC official samples](https://github.com/eclipse/gemoc-studio/tree/master/official_samples/K3FSM))

2.2. **xArduino** (inspired from [Arduino Designer Project](https://github.com/mbats/arduino))

2.3. **xPSSM** (taken from [examples of behavioral interface project](https://github.com/tetrabox/examples-behavioral-interface/tree/master/languages/statemachines))

2.4. **xMiniJava** (inspired from [MiniJava project](https://www.cambridge.org/resources/052182060X) and taken from [MiniJava implementation in GEMOC](https://github.com/gemoc/minijava))
    
The implementation of xDSLs involves several projects:

- <u>Abstract Syntax</u>: containing the `Ecore` metamodel of the xDSL and the java code generated from it using the `.genmodel` file.
- <u>Operational Semantics</u>: containing the interpreter of the xDSL implemented in `Xtend`.
- <u>Behavioral Interface</u>: containing a `.bi` file that is the interface of the xDSL and a java class that do the setups, so GEMOC engines can find and use the interface (Please note that only *xArduino* and *xPSSM* have such an interface)
- <u>Coverage Rules</u>: definition of a set of DSL-Specific coverage rules in a `.cov` file (Please note that the *xFSM* does not have any coverage rule)
- <u>Executable DSL</u>: containing a `.dsl` file that specifies the name of the xdsl, the path to the `.ecore` file, the list of execution rules of the operational semantics, the id of the behavioral interface project, and the path to the coverage rules.
- <u>Mutation Operators</u>: containing a `.mutator` file which includes the mutation operators defined for the xDSL using [WODEL language](https://gomezabajo.github.io/Wodel/)

    **NOTE**: Currently, we do not provide any graphical syntax for the xDSLs.
