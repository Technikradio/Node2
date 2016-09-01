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

/**
 * This class is a prototype for an color theme
 * @author doralitze
 *
 */
public abstract class ColorPalette {

	/**
	 * Use this method to get the background color.
	 * @return The provided color.
	 */
	public abstract Color getMainBackground();
	
	/**
	 * Use this method to get the secondary background color.
	 * @return The provided color.
	 */
	public abstract Color getSecondaryBackground();
	
	/**
	 * Use this color to get the color of separators used inside the UI.
	 * @return The provided color.
	 */
	public abstract Color getSeparatorBackground();
	
	/**
	 * Use this method to get the color of the component shadow.
	 * @return The provided color.
	 */
	public abstract Color getComponentShadow();
	
	/**
	 * Use this method to get the size of the shadow in pixels.
	 * @return The size of the desired shadow.
	 */
	public abstract int getShadingDeph();
	
	/**
	 * Use this method to get the text color.
	 * @return The provided color.
	 */
	public abstract Color getTextColor();
	
	/**
	 * Use this method in order to get the accent color.
	 * @return The provided color.
	 */
	public abstract Color getAccentColor();
	
	/**
	 * Use this method to dispose all created colors.
	 */
	public abstract void dispose();
	
}
