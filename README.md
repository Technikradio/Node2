# Node2 [![Build Status](https://travis-ci.org/Technikradio/Node2.svg?branch=master)](https://travis-ci.org/Technikradio/Node2)
The Version 2 of Node - Now open source!

Node is a modular financial management software designed to support bookkeeping.
It's developed to support local associations but should be scalable to large size
companies.

##Reason why new approach
After further development of node v1.x became entirely impossible, I've decided
to start from scratch.

Please note that this software distribution contains third party libraries.
Please read the licenses files inside the lib folder for further information.

##Build dependancies:
 * JDK7 or JDK8, regardless whether oracle JDK or open JDK
 * Apache ant
 * mono-xbuild (from the mono toolkit)
 * scala2

In order to start the build process simply type ant inside the terminal.
There are also the following useful targets:
 * install: installs the application to your local file system
   (not yet implemented correctly)
 * test: run the unit tests
 * doc: create the documentation of Node

Please add -Dscala.home=$SCALA_HOME to the command before you enter the desired
targets in order to tell ant where your installation of ant is located. Note that
if you haven't set your system variables to contain SCALA_HOME replace it with
the path pointing to your installation.

This software is tested under multiple versions of linux and under Mac OSX 10.11.
It may work under windows yet but we haven't tested it yet.
