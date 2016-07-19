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

/**
 * This enumeration defines the position of an element inside a window. View
 * this <a href="https://technikradio.org/?attachment_id=290">link</a> to get a
 * better understanding of it.
 * 
 * @author doralitze
 *
 */
public enum WindowOrientation {
	/**
	 * This element defines the container position inside the tool bar.
	 */
	TOOLBAR,
	/**
	 * This element defines the container position between the tool bar and the
	 * center container
	 */
	TOP,
	/**
	 * This element defines the container position inside the center of the
	 * window. Multiple containers will be arranged in tabs here.
	 */
	CENTER,
	/**
	 * This element defines the container position under the center container.
	 */
	BOTTOM,
	/**
	 * This element defines the container position left to the center container.
	 * Note: the left tray is higher than the top container but lower than the
	 * tool bar.
	 */
	LEFT_TRAY,
	/**
	 * This element defines the container position right to the center
	 * container. Note: the right tray is higher than the top container but
	 * lower than the tool bar.
	 */
	RIGHT_TRAY
}
