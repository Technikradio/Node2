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
 * This class represents a way to separate different events from each other so
 * that the behavior doesn't get ridiculous. For example an instance of this
 * class may define a save event an another one an load event.
 * 
 * This an instance of this class will be compared with another instance of this
 * class based on the priority. A higher priority means a higher rating.
 * 
 * @author doralitze
 * @see java.lang.Comparable For further information on the comparable
 *      interface.
 */
@SuppressWarnings("rawtypes")
public class EventType implements Comparable {

	/**
	 * This constant represents the minimum priority an event can have.
	 */
	public static final int MINIMUM_PRIORITY = 1;
	/**
	 * This constant represents the maximum priority an Event can have.
	 */
	public static final int MAXIMUM_PRIORITY = 100;

	private final String identifier;
	private final int priority;
	private final boolean autothrow;

	/**
	 * This constructor creates a new event type object. It initializes the auto
	 * throw flag with false and the priority with the minimum priority.
	 * 
	 * @param identifier
	 *            The identifier used to separate two different events.
	 * @throws RuntimeException
	 *             if the given priority is less than the minimum priority ore
	 *             greater than the maximum priority.
	 */
	public EventType(String identifier) {
		this(identifier, MINIMUM_PRIORITY);
	}

	/**
	 * This constructor creates a new event type object. It initializes the auto
	 * throw flag with false.
	 * 
	 * @param identifier
	 *            The identifier used to separate two different events.
	 * @param priority
	 *            The priority to process the event (smaller being less
	 *            important).
	 * @throws RuntimeException
	 *             if the given priority is less than the minimum priority ore
	 *             greater than the maximum priority.
	 */
	public EventType(String identifier, int priority) {
		this(identifier, priority, false);
	}

	/**
	 * This constructor creates a new event type object.
	 * 
	 * @param identifier
	 *            the identifier used to separate two different events.
	 * @param priority
	 *            the priority to process the event (smaller being less
	 *            important).
	 * @param autothrow
	 *            should the event automatically throw an exception if raised?
	 * @throws RuntimeException
	 *             if the given priority is less than the minimum priority ore
	 *             greater than the maximum priority.
	 */
	public EventType(String identifier, int priority, boolean autothrow) {
		if (priority < MINIMUM_PRIORITY || priority > MAXIMUM_PRIORITY)
			throw new RuntimeException("The given priority was out of range.");
		this.identifier = identifier;
		this.priority = priority;
		this.autothrow = autothrow;
	}

	/**
	 * This method is used to get the identifier.
	 * 
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * This method is used to determinate the processing priority and order.
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * This method is used to determinate if the raising of this event should
	 * automatically throw an exception.
	 * 
	 * @return the autothrow flag
	 */
	public boolean shouldAutomaticallyThrowCrash() {
		return autothrow;
	}

	/**
	 * This method overrides Object.toString().
	 * 
	 * @return The identifier of this EventType.
	 */
	@Override
	public String toString() {
		return identifier;
	}

	/**
	 * This method is used to satisfy the comparison needs for this class. A
	 * higher priority means a higher rating.
	 */
	@Override
	public int compareTo(Object o) {
		if (!(o instanceof EventType))
			return 0;
		EventType e = (EventType) o;
		if (this.priority == e.priority)
			return 0;
		else if (this.priority > e.priority)
			return 1;
		else
			return -1;
	}

}
