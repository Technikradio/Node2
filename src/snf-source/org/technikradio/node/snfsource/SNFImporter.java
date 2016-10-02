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

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.technikradio.node.core.CurrencyBrowser;
import org.technikradio.node.engine.CurrencyCode;
import org.technikradio.node.engine.plugin.Row;
import org.technikradio.node.engine.plugin.TableObject;
import org.technikradio.node.engine.plugin.WorkFile;
import org.technikradio.node.engine.resources.Localisation;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class contains support methods in order to load up SNF files.
 * @author doralitze
 *
 */
final class SNFImporter {

	/**
	 * This method loads an SNF file and returns a work file.
	 * @param f the file to load
	 * @return The work file or null if it failed.
	 */
	protected static WorkFile load(File f){
		if(!f.toString().toUpperCase().endsWith(".SNF"))
			return null;
		//WorkFile wf = new WorkFile(CurrencyBrowser.browse(Localisation.getString("org.technikradio.node.snfsource.SNFImporter.browserMessage", "Please enter the desired currency for the opened file."), CurrencyBrowser.getDefaultCurrency()));
		//TODO fix
		WorkFile wf = new WorkFile(CurrencyCode.EUR.getCorrespondingCurrency());
		try{
			String[] containers = new String(Files.readAllBytes(Paths.get(f.getAbsolutePath())), StandardCharsets.UTF_8).split("!'##\\*!");
			TableObject t = new TableObject("File:" + f.toString());
			String[] metaentries = containers[0].split("&\\$%");
			String[] dataentries = containers[1].split("&§%!!");
			containers = null; //Just freeing some memory
			t.setTitle(metaentries[0]);
			t.setMetadata("HTMLPRE", metaentries[1]);
			t.setMetadata("HTMLAFT", metaentries[2]);
			metaentries = null;
			for(String s : dataentries){
				String[] fields = s.split("&\\$%");
				Row r = new Row(Long.parseLong(fields[2]), Long.parseLong(fields[0]), fields[1], fields[3], fields[4]);
				t.addRow(r);
			}
			wf.addChild(t);
		} catch (Exception e){
			Console.log(LogType.Error, "SNFImporter", "Couldn't load up file: " + f.toString());
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		}
		return wf;
	}
}
