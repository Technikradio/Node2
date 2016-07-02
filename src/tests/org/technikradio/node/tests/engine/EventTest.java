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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.event.Event;
import org.technikradio.node.engine.event.EventResponder;
import org.technikradio.node.engine.event.EventType;

/**
 * @author doralitze
 * The test case for the event class
 */
public class EventTest {
	
	private EventType et;
	private EventResponder<String> er;
	private Event e;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		et = new EventType("et", 5, false);
		er = new EventResponder<String>();
		e = new Event(et, null, "test", er);
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.Event#getType()}.
	 */
	@Test
	public final void testGetType() {
		assertEquals(et, e.getType());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.Event#getSource()}.
	 */
	@Test
	public final void testGetSource() {
		assertNull(e.getSource());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.Event#getMessageDetails()}.
	 */
	@Test
	public final void testGetMessageDetails() {
		Event el = new Event(et, null, er);
		assertEquals("", el.getMessageDetails());
		assertEquals("test", e.getMessageDetails());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.Event#getResponder()}.
	 */
	@Test
	public final void testGetResponder() {
		assertEquals(er, e.getResponder());
	}

}
