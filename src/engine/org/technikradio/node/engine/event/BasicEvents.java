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
package org.technikradio.node.engine.event;

/**
 * This class contains some fundamental events.
 * @author doralitze
 *
 */
public class BasicEvents {
	/**
	 * This event will be raised if the application is about to close.
	 */
	public static final EventType APPLICATION_CLOSING_EVENT = new EventType("org.technikradio.node.engine.event.basic_events.app_closing");
	/**
	 * This event will be raised if the application crashed.
	 */
	public static final EventType APPLICATION_CRASHED_EVENT = new EventType("org.technikradio.node.engine.event.basic_events.app_crashed");
	/**
	 * This event will be raised if the application finished loading.
	 */
	public static final EventType APPLICATION_LOADED_EVENT = new EventType("org.technikradio.node.engine.event.basic_events.app_loaded");
	/**
	 * This event will be raised when a data source finished the loading query and is about to load the work file.<br>
	 * NOTE that this event should also be risen if a data source creates a new work file.
	 */
	public static final EventType WORK_FILE_LOADED = new EventType("org.technikradio.node.engine.event.basic_events");
}
