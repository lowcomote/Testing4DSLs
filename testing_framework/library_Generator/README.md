**Objective**: Generating a domain-specific TDL library for a given xDSL.

- *org.imt.tdl.libraryGenerator*: By having an xDSL as input, it generates a TDL library specific to it. The library provides the DSL_specific TDL data types for the definition of test data, the required test configurations, a set of commands for requesting the execution of the model under test, and facilities for using OCL queries when writing test cases.
- *org.imt.atl.ecore2tdl*: An ATL transformation from Ecore to TDL.