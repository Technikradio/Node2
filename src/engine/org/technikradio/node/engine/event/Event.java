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
 * This class represents an event that can be raised using the EventRegisty
 * class. The same comparison rules like the ones of the EventType class apply
 * to this class.
 * 
 * @author doralitze
 * @see java.lang.Comparable The comparable interface
 * @see EventType The EventType class.
 */
@SuppressWarnings("rawtypes")
public class Event implements Comparable {

	private final EventType type;
	private final Plugin source;
	private final String messageDetails;
	private final EventResponder responder;
	private Object eventHint;

	/**
	 * This constructor generates an instance of an event using the given
	 * parameters. It will use an empty text as the message details.
	 * 
	 * @param type
	 *            The type of the event
	 * @param source
	 *            The source of the event
	 * @param responder
	 *            A responder to keep for the source in order to process the
	 *            responses later
	 */
	public Event(EventType type, Plugin source, EventResponder responder) {
		this(type, source, "", responder);
	}

	/**
	 * This constructor generates an instance of an event using the given
	 * parameters.
	 * 
	 * @param type
	 *            The type of the event
	 * @param source
	 *            The source of the event
	 * @param messageDetails
	 *            Some details of the event
	 * @param responder
	 *            A responder to keep for the source in order to process the
	 *            responses later
	 */
	public Event(EventType type, Plugin source, String messageDetails, EventResponder responder) {
		super();
		this.type = type;
		this.source = source;
		this.messageDetails = messageDetails;
		this.responder = responder;
	}

	/**
	 * Use this message to get the type of this event.
	 * 
	 * @return the type of this event
	 */
	public EventType getType() {
		return type;
	}

	/**
	 * Use this method in order to get the source of this event.
	 * 
	 * @return the source of this event
	 */
	public Plugin getSource() {
		return source;
	}

	/**
	 * Use this method in order to get advanced information about the event.
	 * 
	 * @return the message details
	 */
	public String getMessageDetails() {
		return messageDetails;
	}

	/**
	 * Use this method to get the event responder for this event.
	 * 
	 * @return the responder
	 */
	public EventResponder getResponder() {
		return responder;
	}

	/**
	 * This method is used to compare two Events with each other.
	 * 
	 * @see EventType#compareTo(Object) The underlaying rules for further
	 *      information.
	 * @see java.lang.Comparable Have a look at the Comparable interface for
	 *      further information on it.
	 */
	@Override
	public int compareTo(Object o) {
		if (!(o instanceof Event))
			return 0;
		Event e = (Event) o;
		return this.getType().compareTo(e.getType());
	}

	/**
	 * Use this method to get the Event hint. An event hint is an object usually
	 * provided by the creator of the event if a string isn't enough information
	 * in order to process the event.
	 * 
	 * @return the event hint
	 */
	public Object getEventHint() {
		return eventHint;
	}

	/**
	 * Use this method to set the event hint to a desired value. An event hint
	 * is an object usually provided by the creator of the event if a string
	 * isn't enough information in order to process the event.
	 * 
	 * @param eventHint
	 *            the event hint to set
	 */
	public void setEventHint(Object eventHint) {
		this.eventHint = eventHint;
	}
}
