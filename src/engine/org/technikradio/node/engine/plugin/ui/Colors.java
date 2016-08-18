/*
Copyright (c) 2016, Technikradio
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

* Redistributions of source code must retain the above copyright notice, this
  list of conditions and the following disclaimer.

* Redistributions in binary form must reproduce the above copyright notice,
  this list of conditions and the following disclaimer in the documentation
  and/or other materials provided with the distribution.

* Neither the name of Node2 nor the names of its
  contributors may be used to endorse or promote products derived from
  this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * 
 */
package org.technikradio.node.engine.plugin.ui;

import org.eclipse.swt.graphics.Color;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class contains a set of default colors.
 * @author doralitze
 *
 */
public class Colors {

	/**
	 * This field represents a pure red.
	 */
	public static final Color RED = new Color(DisplayFactory.getDisplay(), 255,0,0);
	/**
	 * This field represents a pure green.
	 */
	public static final Color GREEN = new Color(DisplayFactory.getDisplay(), 0,255,0);
	/**
	 * This field represents a pure blue.
	 */
	public static final Color BLUE = new Color(DisplayFactory.getDisplay(), 0,0,255);
	/**
	 * This field represents a pure black.
	 */
	public static final Color BLACK = new Color(DisplayFactory.getDisplay(), 0,0,0);
	/**
	 * This field represents a pure yellow.
	 */
	public static final Color YELLOW = new Color(DisplayFactory.getDisplay(), 255,255,0);
	/**
	 * This field represents a brown color.
	 */
	public static final Color BROWN = new Color(DisplayFactory.getDisplay(), 165,101,42);
	
	/**
	 * This method is used to dispose all colors.
	 */
	protected static void disposeAll(){
		Console.log(LogType.StdOut, "Colors", "Cleaning up all created colors.");
		RED.dispose();
		GREEN.dispose();
		BLUE.dispose();
		BLACK.dispose();
		YELLOW.dispose();
		BROWN.dispose();
	}
}
