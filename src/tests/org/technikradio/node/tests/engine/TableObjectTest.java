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

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.Row;
import org.technikradio.node.engine.plugin.TableObject;

/**
 * This class is designed to test the TableObject class.
 * @author doralitze
 *
 */
public class TableObjectTest {
	
	private TableObject to;

	/**
	 * @throws java.lang.Exception
	 *             if anything goes wrong
	 */
	@Before
	public void setUp() throws Exception {
		to = new TableObject("Test table object");
		for(int i = 0; i < 150500; i++){
			Date d = new Date();
			to.addRow(new Row(i * 100, i, "Row description #" + Integer.toString(i), "AMF", d.toString()));
		}
	}

	/**
	 * Test method for
	 * {@link org.technikradio.node.engine.plugin.TableObject#getRows(int, int)}.
	 */
	@Test
	public final void testGetRows() {
		for(int i = 0; i < to.getLength() / 5; i ++){
			Row[] frag = to.getRows(i * 5, (i + 1) * 5);
			for(int j = 0; j < 5; j++){
				if(frag[j] == null){
					fail("The query test failed.");
				}
			}
		}
	}

	/**
	 * Test method for
	 * {@link org.technikradio.node.engine.plugin.TableObject#getSum()}.
	 */
	@Test
	public final void testGetSum() {
		assertTrue("The computed sum must be greater than 11325049700", to.getSum() > 11325049700L);
	}

}
