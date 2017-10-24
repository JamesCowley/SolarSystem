# Java GUI test

### Background
Created in year 2 of undergrad physics. The project's main purpose was to learn how to build a basic GUI in Java.


### Requirements
The project's minimum requirements were:

* The sun

* At least 3 planets

* Planets moving in circular orbits

I expanded on these with:

* Correct elliptical orbits

* All planets + some comets, asteroids and [dwarf planets](http://i.imgur.com/aAt54Nm.jpg)

* Sliders to adjust the relative speed and scale of the model

### Running it

You'll need the JDK (Java Development Kit) from [Oracle](http://www.oracle.com). To compile from the command line, do

`javac SolarGUI/*java`

To run the compiled program do

`java SolarGUI.SolarSystem`

Alternatively, you can compile to an executable called `Solar.jar` (which you can "[run anywhere](https://en.wikipedia.org/wiki/Write_once,_run_anywhere)") by doing

`jar cfm Solar.jar MANIFEST.MF SolarGUI/*.class`

This can then be run from the command line with

`java -jar Solar.jar`
