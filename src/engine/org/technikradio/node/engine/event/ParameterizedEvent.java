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

import org.technikradio.node.engine.plugin.Plugin;

/**
 * This class extends the Event class in order to provide further information by
 * passing an specialized object. This class also requires a specified
 * EventResponder due to safety reasons.
 * 
 * @author doralitze
 * @see Event The super class for further information.
 * @see EventResponder The EventResponder class for further information.
 */
public class ParameterizedEvent<V, E> extends Event {

	private final V arg;

	/**
	 * Except for the parameter argument this constructor is pretty much the
	 * same like the corresponding one of the super class.
	 * 
	 * @param type
	 *            The type of the event.
	 * @param source
	 *            The source of the event.
	 * @param responder
	 *            A responder to keep for the source in order to process the
	 *            responses later.
	 * @param parameter
	 *            The extensive information object provided with this class.
	 * @see Event#Event(EventType, Plugin, EventResponder) for further
	 *      information.
	 * @see Event The super class for further information
	 */
	public ParameterizedEvent(EventType type, Plugin source, EventResponder<E> responder, V parameter) {
		super(type, source, responder);
		arg = parameter;
	}

	/**
	 * Beside initializing the special fields of this method this constructor is
	 * pretty much the same like the corresponding one of the super class.
	 * 
	 * @param type
	 *            The type of the event.
	 * @param source
	 *            The source of the event.
	 * @param messageDetails
	 *            Some details of the event.
	 * @param responder
	 *            A responder to keep for the source in order to process the
	 *            responses later.
	 * @param parameter
	 *            The extensive information object provided with this class.
	 * @see Event#Event(EventType, Plugin, String, EventResponder) The super
	 *      constructor for further information.
	 * @see Event The super class for further information
	 */
	public ParameterizedEvent(EventType type, Plugin source, String messageDetails, EventResponder<E> responder,
			V parameter) {
		super(type, source, messageDetails, responder);
		arg = parameter;
	}

	/**
	 * Use this method in order to retrieve the extensive informations shipped
	 * with this object.
	 * 
	 * @return the parameter provided with this event.
	 */
	public V getExtensiveParameters() {
		return arg;
	}

}
