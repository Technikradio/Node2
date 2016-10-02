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
package org.technikradio.node.tests.snfsource;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;
import org.technikradio.node.engine.plugin.Row;
import org.technikradio.node.engine.plugin.TableObject;
import org.technikradio.node.snfsource.SNFObjectExport;

/**
 * This class is designed to test the SNF export feature
 * 
 * @author doralitze
 *
 */
public class SNFObjectExportTest {
	private SNFObjectExport e;
	private BufferedReader br;

	/**
	 * @throws java.lang.Exception
	 *             This exception gets thrown if anything goes wrong.
	 */
	@Before
	public void setUp() throws Exception {
		PipedInputStream pis = new PipedInputStream();
		e = new SNFObjectExport(new PrintStream(new PipedOutputStream(pis)));
		br = new BufferedReader(new InputStreamReader(pis), 10_000);
	}

	/**
	 * Test method for
	 * {@link org.technikradio.node.snfsource.SNFObjectExport#serialize(org.technikradio.node.engine.plugin.DataObject)}.
	 */
	@Test
	public final void testSerialize() {
		TableObject to = new TableObject("TestObject");
		to.setTitle("TestTitle");
		to.setMetadata("HTMLPRE", "<!-- html-pre -->");
		to.setMetadata("HTMLAFT", "<!-- html-after -->");
		for (int i = 1; i <= 10; i++) {
			to.addRow(new Row(i * 100, i, "Row description #" + Integer.toString(i), "AMF", "some date"));
		}
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				boolean run = true;
				StringBuilder sb = new StringBuilder();
				while (run) {
					try {
						String s = br.readLine();
						if (s == null) {
							run = false;
							break;
						}
						sb.append(s);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				assertTrue("The strings must be equal.",
						sb.toString().equals("TestTitle&$%<!-- html-pre -->&$%<!-- html-after -->&$%!'##*!"
								+ "1&$%Row description #1&$%100&$%AMF&$%some date&§%!!2&$%Row description #"
								+ "2&$%200&$%AMF&$%some date&§%!!3&$%Row description #3&$%300&$%AMF&$%some "
								+ "date&§%!!4&$%Row description #4&$%400&$%AMF&$%some date&§%!!5&$%Row descr"
								+ "iption #5&$%500&$%AMF&$%some date&§%!!6&$%Row description #6&$%600&$%AMF"
								+ "&$%some date&§%!!7&$%Row description #7&$%700&$%AMF&$%some date&§%!!8&$%"
								+ "Row description #8&$%800&$%AMF&$%some date&§%!!9&$%Row description #9&$%"
								+ "900&$%AMF&$%some date&§%!!10&$%Row description #10&$%1000&$%AMF&$%some date"));
			}
		});
		t.setUncaughtExceptionHandler(Thread.currentThread().getUncaughtExceptionHandler());
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
		e.serialize(to, true);
	}

}
