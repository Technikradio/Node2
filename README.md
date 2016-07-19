# Node2
The Version 2 of Node - Now open source!

After further development of node v1.x became entirely impossible, I've decided
to start from scratch.

Please note that this software distribution contains third party libraries.
Please read the licenses files inside the lib folder for further information.

[![Build Status](https://travis-ci.org/Technikradio/Node2.svg?branch=master)](https://travis-ci.org/Technikradio/Node2)

##Build dependancies:
 * JDK7 or JDK8, regardless whether oracle JDK or open JDK
 * Apache ant
 * mono-xbuild (from the mono toolkit)

In order to start the build process simply type ant inside the terminal.
There are also the following useful targets:
 * install: installs the application to your local file system
   (not yet implemented correctly)
 * test: run the unit tests
 * doc: create the documentation of Node

This software is tested under multiple versions of linux and under Mac OSX 10.11.
It may work under windows yet but we haven't tested it yet.
