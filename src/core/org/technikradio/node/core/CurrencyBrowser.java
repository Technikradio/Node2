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
package org.technikradio.node.core;

import java.util.Currency;

import org.technikradio.node.engine.CurrencyCode;
import org.technikradio.universal_tools.Console;
import org.technikradio.universal_tools.Console.LogType;

/**
 * This class is designed to let the user choose a currency.
 * @author doralitze
 *
 */
public class CurrencyBrowser {
	
	private static CurrencyCode defaultCurrency;

	/**
	 * Use this method in order to display a currency browser and get the result.
	 * @param message The message to prompt the user for.
	 * @param start The default currency to display.
	 * @return The currency selected by the user.
	 */
	public static Currency browse(String message, CurrencyCode start){
		return browse(message, "", start);
	}
	
	/**
	 * Use this method in order to display a currency browser and get the result.
	 * @param message The message to prompt the user for.
	 * @param title The title of the dialog created.
	 * @param start The default currency to display.
	 * @return The currency selected by the user.
	 */
	public static Currency browse(String message, String title, CurrencyCode start){
		return start.getCorrespondingCurrency(); //TODO change
	}

	/**
	 * Use this method in order to set the default currency of this system.
	 * @return the default currency
	 */
	public static CurrencyCode getDefaultCurrency() {
		return defaultCurrency;
	}

	/**
	 * Use this method in order to set the default currency.
	 * @param defaultCurrency the currency to use as the default one.
	 */
	public static void setDefaultCurrency(CurrencyCode defaultCurrency) {
		Console.log(LogType.StdOut, "CurrencyBrowser", "Setting default currency to: " + defaultCurrency.getCorrespondingCurrency().getCurrencyCode());
		CurrencyBrowser.defaultCurrency = defaultCurrency;
	}
}
