How to define a reactive executable DSL?
If you need to define your own reactive xDSL, you need to follow this process:

1) define the abstract syntax of your xDSL using 'ecore'
- create a new modeling project: file -> new -> Modeling Project -> set a name for your project
- optional: create a new folder in the project and name it as 'model'
- put/define your 'ecore' file in this project (or in the 'model' folder if you have created it)

2) generate java code for your ecore classes using 'EMF Generator Model'
- in the project you created in step (1): file -> new -> EMF Generator Model
- select your ecore file when it asks for an ecore file
- when it finished, the 'genmodel' will be opened automatically (otherwise, open it). 
Then right click on its root element-> 'Generate Model Code'

3) define the behavioral interface of your xDSL
- create a new java project: file -> new -> Java Project -> set a name for your project
- create a new file in this project with '.bi' postfix: this is the file where you define your behavioral interface
- define the behavioral interface of your language by specifying its 'accepted event' and 'exposed event'

4) define the interpreter (i.e, the operational semantics) of your xDSL
- create a new k3 project: file -> new -> K3 project -> set a name for your project
- define a dependency to your abstract syntax: add dependency in the MANIFEST.MF file of your k3 project
- create an 'xtend' file in this project: file -> new -> Xtend Class
- implement your execution rules: Note that for each 'event' of your interface, you have to define an execution rule which actually implements that event

**NOTE: you need to import two additional plugins into your workspace from the following links:
* eventBasedEngine: a GEMOC execution engine for running reactive models
https://gitlab.univ-nantes.fr/naomod/faezeh-public/xtdl/-/tree/xTDL_EventManager/org.eclipse.gemoc.execution.eventBasedEngine
* eventManager: a component that communicates instances of behavioral interface events with a running reactive model by getting connected to the eventBasedEngine
https://gitlab.univ-nantes.fr/naomod/faezeh-public/xtdl/-/tree/xTDL_EventManager/org.eclipse.gemoc.executionframework.event.manager

5) define implementation relationship between the behavioral interface and the interpreter of your xDSL
- create a new java class in the java project of your behavioral interface created in step (3): file -> new -> java class
- this java class must extends 'SimpleImplementationRelationship': to know how to define the mappings between the events of the behavioral interface and the execution rules of the interpreter, refer to the provided exampls
- define an extension with the following settings:
* extension point: 'org.eclipse.gemoc.executionframework.event.implementationrelationship'
* class: the java class you just created for the implementation relationship
* id: a unique id

6) finally, define your xDSL:
- create a new 'GEMOC Java xDSML Project': it asks for your ecore file and your interpreter and then generates a '.dsl' file
- in the created '.dsl' file, add a new entry as follows:
* key: implemRelId
* value: the id of the extension you have defined in step (5)
