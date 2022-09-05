Using our approach, you can perform mutation analysis on TDL test suites written for executable models.
This is the result of integrating our approach with [WODEL-Test](https://link.springer.com/article/10.1007/s10270-020-00827-0) which is a model-based framework for language-independent mutation testing. 

## Setup
**Requirements**:
- WODEL framework ([udpate site](http://gomezabajo.github.io/Wodel/update-site)): You may face an error during installation related to emfjason. In this case, you need to first install emfjason [from this update site](http://ghillairet.github.io/p2/).

## Usage
To peform mutation testing for a model conforming to an xDSL:

1. you need to first create a WODEL-TDL project for the considered xDSL.
NOTE: the project 
- is extending the WODELTest extension point
- built by WodelTestBuilder
- has WodelTestNature

2. To avoid the generation of invalid and syntactically wrong mutants, you need to enable two options in the preferences
: Window -> Preference -> WODEL -> ... 

3. Run the workspace using "Eclipse Application" Run configuration
4. In the new workspace, perform step (2) and then right click on the project explorer and press -> Wodel-Test -> Load Projects and Run Wodel-Test
here you need to import, first the project containing the model under test (from now on referred to as SUT project) and second the project containing the test suite
NOTE: Both of these projects must have JavaNature

Wodel will 
1. apply the mutation operators on the model under test
2. generate the mutants and save them in the SUT project
3. run the test cases on the mutants (using the TDL Interpreter)
4. save the mutation testing result

5. To see the result of mutation testing, select the SUT project and then
: Window -> show view -> others -> Wodel-Test -> ...
from the list of available views, select the one you needed
