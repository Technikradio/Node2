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
package org.technikradio.node.tests.engine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventHandler;
import org.technikradio.node.engine.event.EventRegistry;
import org.technikradio.node.engine.event.EventResponder;
import org.technikradio.node.engine.event.EventType;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * @author doralitze
 * This is the test case for the event registry.
 */
public class EventRegistryTest {
	
	@Rule
    public Timeout globalTimeout = Timeout.seconds(120);
	
	private int handledA = 0;

	private EventType typeA = new EventType("org.technikradio.node.tests.engine.EventTypeA", EventType.MINIMUM_PRIORITY, false){};
	private EventType typeB = new EventType("org.technikradio.node.tests.engine.EventTypeB", EventType.MAXIMUM_PRIORITY, false){};
	private EventResponder<String> er;
	private Event e;
	
	/**
	 * @throws java.lang.Exception In case of an exception
	 */
	@Before
	public void setUp() throws Exception {
		EventRegistry.addEventHandler(typeA, new EventHandler(){
			
			@Override
			public void handleEvent(Event e) {
				handledA++;
				Console.log(LogType.StdOut, this, "Executed event handler...");
				if(handledA > 2){
					fail("Handled event too often!");
				}
			}});
		er = new EventResponder<String>();
		e = new Event(typeA, null, "test", er);
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventRegistry#raiseEvent(org.technikradio.node.engine.event.Event)}.
	 */
	@Test
	public final void testRaiseEvent() {
		assertEquals(er, EventRegistry.raiseEvent(e, true));
		assertEquals(er, EventRegistry.raiseEvent(e, false));
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventRegistry#hasHandlers(org.technikradio.node.engine.event.EventType)}.
	 */
	@Test
	public final void testHasHandlers() {
		assertTrue(EventRegistry.hasHandlers(typeA));
		assertFalse(EventRegistry.hasHandlers(typeB));
		EventHandler eh = new EventHandler(){
			@Override
			public void handleEvent(Event e) {}};
		assertFalse(EventRegistry.isEventHandlerSomewhereRegistered(eh));
		assertTrue(EventRegistry.addEventHandler(typeB, eh));
		assertFalse(EventRegistry.addEventHandler(typeB, new EventHandler(){
			@Override
			public void handleEvent(Event e) {}}));
		assertTrue(EventRegistry.hasHandlers(typeB));
		assertFalse(EventRegistry.removeEventHandler(null, null));
		assertFalse(EventRegistry.removeEventHandler(typeA, null));
		assertFalse(EventRegistry.removeEventHandler(typeA, eh));
		assertTrue(EventRegistry.isEventHandlerRegistered(typeB, eh));
		assertTrue(EventRegistry.removeEventHandler(typeB, eh));
		assertFalse(EventRegistry.isEventHandlerRegistered(typeB, eh));
		assertFalse(EventRegistry.addEventHandler(typeB, eh));
		assertTrue(EventRegistry.isEventHandlerSomewhereRegistered(eh));
		assertFalse(EventRegistry.removeEventHandler(null));
		assertTrue(EventRegistry.removeEventHandler(eh));
		assertFalse(EventRegistry.isEventHandlerSomewhereRegistered(eh));
	}

}
