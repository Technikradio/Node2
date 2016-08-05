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
import org.junit.Test;
import org.technikradio.node.engine.event.EventType;

/**
 * The test case for EventType
 * @author doralitze
 * 
 */
public class EventTypeTest {

	private EventType et;
	
	/**
	 * @throws java.lang.Exception In case of an exception
	 */
	@Before
	public void setUp() throws Exception {
		et = new EventType("com.example.exampleevent", 5, false);
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventType#getIdentifier()}.
	 */
	@Test
	public final void testGetIdentifier() {
		assertEquals("com.example.exampleevent", et.getIdentifier());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventType#getPriority()}.
	 */
	@Test
	public final void testGetPriority() {
		assertEquals(5, et.getPriority());
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventType#shouldAutomaticallyThrowCrash()}.
	 */
	@Test
	public final void testShouldAutomaticallyThrowCrash() {
		assertFalse(et.shouldAutomaticallyThrowCrash());
	}
	
	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventType#MAXIMUM_PRIORITY} and
	 * {@link org.technikradio.node.engine.event.EventType#MINIMUM_PRIORITY}.
	 */
	@Test
	public final void testMaxPriorityGreaterThanMinPriority(){
		assertTrue(EventType.MAXIMUM_PRIORITY > EventType.MINIMUM_PRIORITY);
		assertTrue(EventType.MINIMUM_PRIORITY > 0);
		assertTrue(EventType.MAXIMUM_PRIORITY > 0);
	}
	
	/**
	 * Test method for {@link org.technikradio.node.engine.event.EventType#compareTo(Object)}.
	 */
	@Test
	public final void testComparison(){
		EventType a = new EventType("a", 1, false);
		EventType b = new EventType("b", 2, false);
		EventType c = new EventType("c", 1, false);
		assertEquals("EventType A and C should be worth the same.", 0, a.compareTo(c));
		assertEquals("B should be more worth than C.", 1, b.compareTo(c));
		assertEquals("A should be less worth than B.", -1, a.compareTo(b));
	}

}
