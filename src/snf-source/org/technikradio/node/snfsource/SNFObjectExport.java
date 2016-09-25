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
package org.technikradio.node.snfsource;

import java.io.PrintStream;

import org.technikradio.node.engine.plugin.DataObject;
import org.technikradio.node.engine.plugin.Row;
import org.technikradio.node.engine.plugin.TableObject;

/**
 * This class is used to generate an SNF file from an data object.
 * @author doralitze
 *
 */
public class SNFObjectExport {
	
	private final PrintStream ps;

	/**
	 * This constructor creates a new instance capable of outputting to the given print stream.
	 * @param output The print stream to output to.
	 */
	public SNFObjectExport(PrintStream output) {
		this.ps = output;
	}
	
	/**
	 * Use this method in order to serialize the object to the prior specified print stream.
	 * NOTE that this will only work with TableObjects. This method will throw an exception otherwise.
	 * @param o The object to serialize. (This must be an TableObject)
	 * @param autoclose use true here if you wan't the stream to be closed after performing the serialization.
	 */
	public void serialize(DataObject o, boolean autoclose){
		serialize(o);
		if(autoclose)
			ps.close();
	}
	
	/**
	 * Use this method in order to serialize the object to the prior specified print stream.
	 * @param o The object to serialize. (This must be an TableObject)
	 */
	public void serialize(DataObject o){
		if(!(o instanceof TableObject))
			throw new RuntimeException("Only instances of the TableObject class or its subclasses can be serialized.");
		
		TableObject t = (TableObject) o;
		ps.print(t.getTitle());
		ps.print("&$%");
		ps.print(t.getMetadataValue("HTMLPRE"));
		ps.print("&$%");
		ps.print(t.getMetadataValue("HTMLAFT"));
		ps.println("&$%");
		ps.print("!'##*!");
		for(int i = 0; i < t.getLength(); i++){
			Row r = t.getRows(i, i + 1)[0];
			ps.print(Long.toString(r.getId()));
			ps.print("&$%");
			ps.print(r.getDescription());
			ps.print("&$%");
			ps.print(Long.toString(r.getValue()));
			ps.print("&$%");
			ps.print(r.getCostCenter());
			ps.print("&$%");
			ps.print(r.getDate());
			if(i + 1 != t.getLength())
				ps.println("&§%!!");
			ps.flush();
		}
	}

}
