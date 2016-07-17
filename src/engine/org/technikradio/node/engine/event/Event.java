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
 * This class represents an event that can be raised using the EventRegisty class.
 * @author doralitze
 */
@SuppressWarnings("rawtypes")
public class Event {

	private final EventType type;
	private final Plugin source;
	private final String messageDetails;
	private final EventResponder responder;
	
	/**
	 * This constructor generates an instance of an event using the given parameters.
	 * It will use an empty text as the message details.
	 * @param type The type of the event
	 * @param source The source of the event
	 * @param responder A responder to keep for the source in order to process the responses later
	 */
	public Event(EventType type, Plugin source, EventResponder responder){
		this(type, source, "", responder);
	}

	/**
	 * This constructor generates an instance of an event using the given parameters.
	 * @param type The type of the event
	 * @param source The source of the event
	 * @param messageDetails Some details of the event
	 * @param responder A responder to keep for the source in order to process the responses later
	 */
	public Event(EventType type, Plugin source, String messageDetails, EventResponder responder){
		super();
		this.type = type;
		this.source = source;
		this.messageDetails = messageDetails;
		this.responder = responder;
	}
	
	/**
	 * @return the type
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * @return the source
	 */
	public Plugin getSource() {
		return source;
	}

	/**
	 * @return the messageDetails
	 */
	public String getMessageDetails() {
		return messageDetails;
	}

	/**
	 * @return the responder
	 */
	public EventResponder getResponder() {
		return responder;
	}
}
