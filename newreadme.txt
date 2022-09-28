1. load plugins of the testing_framework directory
the tdlInterpreter plugin will have an error due to missing the coverage computation plugins
2. load plugins from the coverage computation directory
3. to use fault localization facilities, import the plugins from fault_localization directory
4. to perform test amplification, import the plugins from test_amplification directory
the plugin will have an error as it needs the plugins from mutation_analysis, so
5. import the plugins from mutation_analysis directory

to use the framework and the tools for a specific xDSL, you need to import the xDSL implementation. 
For example, we ahve provided a set of xDSLs in the language_workbench directory, so import each one you want
here we import the xArduino-reactive

Now we can run the workspace using the "Run as Eclipse Application" to use the framework for xArduino models

in the newly opened workspace, import the xModel you want to test. 
For example, we provided a set of models in the modeling-workbench directory.
here, we import the xAruino-reactive-models

to write test cases for the models of this folder, create a new Modeling project

now you can run the tdl library generator to generate the tdl library for the xArduino.
to do this, you can use the icon in the toolbar or menubar.
it asks for the test project \ie the project you just created, and the name of the xDSL which is "org.imt.arduino.reactiveArduino"

