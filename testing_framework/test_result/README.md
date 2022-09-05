**Objective**: generating the test execution result, serializing it as an xmi file, and presenting it in a graphical view.

There are a set of classes for reporting the test execution result in different granularity levels:
- `TDLMessageResult`: providing the result of executing every single message of a test case
- `TDLTestCaseResult`: providing the result of executing each test case of the test suite
- `TDLTestPackageResult`: providing the result of the test suite execution