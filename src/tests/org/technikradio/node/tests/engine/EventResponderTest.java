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

import org.junit.Test;
import org.technikradio.node.engine.event.EventResponder;
import org.technikradio.node.engine.event.EventResponse;

/**
 * @author doralitze
 * Test case for the EventResponder class
 */
public class EventResponderTest {

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventResponder#respond(org.technikradio.node.engine.event.EventResponse)}.
	 */
	@Test
	public final void testRespondEventResponseOfE() {
		EventResponder<String> er = new EventResponder<String>();
		EventResponse<String> r1 = new EventResponse<String>("id1", true, "test1");
		EventResponse<String> r2 = new EventResponse<String>("id2", true, "test2");
		EventResponse<String> r3 = new EventResponse<String>("id3", true, "test3");
		assertTrue(er.respond(r1));
		assertFalse(er.respond(r1));
		assertTrue(er.respond(r2));
		assertTrue(er.respond(r3));
		assertFalse(er.respond(r2));
		assertFalse(er.respond(r3));
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventResponder#getNextResponse()}.
	 */
	@Test
	public final void testGetNextResponse() {
		EventResponder<String> er = new EventResponder<String>();
		EventResponse<String> r1 = new EventResponse<String>("id1", true, "test1");
		EventResponse<String> r2 = new EventResponse<String>("id2", true, "test2");
		EventResponse<String> r3 = new EventResponse<String>("id3", true, "test3");
		er.respond(r1);
		er.respond(r2);
		assertEquals(r1, er.getNextResponse());
		assertEquals(r2, er.getNextResponse());
		assertNull(er.getNextResponse());
		er.respond(r3);
		assertEquals(r3, er.getNextResponse());
		assertNull(er.getNextResponse());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventResponder#respond(org.technikradio.node.engine.event.EventResponse, boolean)}.
	 */
	@Test
	public final void testRespondEventResponseOfEBoolean() {
		EventResponder<String> er = new EventResponder<String>();
		EventResponse<String> r1 = new EventResponse<String>("id1", true, "test1");
		EventResponse<String> r2 = new EventResponse<String>("id2", true, "test2");
		EventResponse<String> r3 = new EventResponse<String>("id3", true, "test3");
		EventResponse<String> r4 = new EventResponse<String>("id4", true, "test4");
		assertTrue(er.respond(r1, false));
		assertFalse(er.respond(r1, false));
		assertTrue(er.respond(r2, false));
		assertFalse(er.respond(r2, false));
		assertTrue(er.respond(r3, true));
		assertFalse(er.respond(r4, false));
		assertFalse(er.respond(r3, false));
		assertFalse(er.respond(r3, true));
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventResponder#isFinal()}.
	 */
	@Test
	public final void testIsFinal() {
		EventResponder<String> er = new EventResponder<String>();
		EventResponse<String> r1 = new EventResponse<String>("id1", true, "test1");
		EventResponse<String> r2 = new EventResponse<String>("id2", true, "test2");
		assertTrue(er.respond(r1, false));
		assertFalse(er.isFinal());
		assertTrue(er.respond(r2, true));
		assertTrue(er.isFinal());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventResponder#getPointer()}.
	 */
	@Test
	public final void testGetPointer() {
		EventResponder<String> er = new EventResponder<String>();
		EventResponse<String> r1 = new EventResponse<String>("id1", true, "test1");
		EventResponse<String> r2 = new EventResponse<String>("id2", true, "test2");
		EventResponse<String> r3 = new EventResponse<String>("id3", true, "test3");
		EventResponse<String> r4 = new EventResponse<String>("id4", true, "test4");
		assertTrue(er.respond(r1, false));
		assertEquals(0, er.getPointer());
		assertEquals(r1, er.getNextResponse());
		assertEquals(1, er.getPointer());
		assertTrue(er.respond(r2, false));
		assertTrue(er.respond(r3, false));
		assertTrue(er.respond(r4, false));
		assertEquals(r2, er.getNextResponse());
		assertEquals(2, er.getPointer());
		assertEquals(r3, er.getNextResponse());
		assertEquals(3, er.getPointer());
		assertEquals(r4, er.getNextResponse());
		assertEquals(4, er.getPointer());
		assertNull(er.getNextResponse());
		assertEquals(4, er.getPointer());
	}

}
