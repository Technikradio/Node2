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
package org.technikradio.node.engine.plugin;

/**
 * This exception gets raised when something try's to access data that has not been loaded yet.
 * @author doralitze
 */
public class DataNotYetLoadedException extends RuntimeException {

	private static final long serialVersionUID = 5875240080547358562L;

	/**
	 * This initializes an empty exception.
	 */
	public DataNotYetLoadedException() {
		super();
	}

	/**
	 * This initializes an exception containing a message
	 * @param message The message to contain
	 */
	public DataNotYetLoadedException(String message) {
		super(message);
	}

	/**
	 * This initializes an exception containing the cause of the exception
	 * @param cause The cause to provide
	 */
	public DataNotYetLoadedException(Throwable cause) {
		super(cause);
	}

	/**
	 * This initializes an exception containing the cause of the exception and a message
	 * @param message The message to use
	 * @param cause The cause to provide
	 */
	public DataNotYetLoadedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * This initializes an exception providing all kind of rich data.
	 * @param message The message to throw
	 * @param cause The cause to declare
	 * @param enableSuppression Should the exception be ignore able?
	 * @param writableStackTrace The stack trace to write down
	 */
	public DataNotYetLoadedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
