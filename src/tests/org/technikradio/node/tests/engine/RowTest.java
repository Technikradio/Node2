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
import org.technikradio.node.engine.plugin.Row;
import org.technikradio.node.engine.resources.Localisation;

/**
 * This class is designed to test the row class.
 * @author doralitze
 *
 */
public class RowTest {
	
	private static final String comma = Localisation.getString("org.technikradio.engine.plugin.row.comma", ".");

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Row#Row(long, long, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testRow() {
		Row r = new Row(101, 2, "description", "costcentre", "date");
		assertEquals("The value is incorrect", r.getValue(), 101);
		assertEquals("The ID is incorrect", r.getId(), 2);
		assertEquals("The description is incorrect", r.getDescription(), "description");
		assertEquals("The costcentre isincorrect", r.getCostCenter(), "costcentre");
		assertEquals("The date is incorrect", r.getDate(), "date");
	}

	/**
	 * Test method for {@link org.technikradio.node.engine.plugin.Row#getDString(long, boolean, java.lang.String)}.
	 */
	@Test
	public final void testGetDString() {
		assertEquals(Row.getDString(101, false, "€"), "1" + comma + "01 €");
		assertEquals(Row.getDString(110, false, "€"), "1" + comma + "10 €");
		assertEquals(Row.getDString(101, true, "€"), "€ 1" + comma + "01");
		assertEquals(Row.getDString(101, false, "$"), "1" + comma + "01 $");
		assertEquals(Row.getDString(110, false, "$"), "1" + comma + "10 $");
		assertEquals(Row.getDString(101, true, "$"), "$ 1" + comma + "01");
	}

}
