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
package org.technikradio.node.engine.event;

import java.util.ArrayList;

/**
 * This class is designed to provide objects where event
 * processors can submit their results.
 * @author doralitze
 */
public class EventResponder<E> {

	private boolean isFinal;
	private int pointer;
	private ArrayList<EventResponse<E>> responses;
	
	/**
	 * This constructor simply creates an empty instance of this class.
	 */
	public EventResponder() {
		isFinal = false;
		pointer = 0;
		responses = new ArrayList<EventResponse<E>>();
	}
	
	/**
	 * This method is used to submit an response to an event.
	 * @param response The response to submit
	 * @return true if it was able to register the response or otherwise false.
	 */
	public boolean respond(EventResponse<E> response){
		return respond(response, false);
	}
	
	/**
	 * This method is used to cycle through the responses.
	 * @return The next answer from the list
	 */
	public EventResponse<E> getNextResponse(){
		if(responses.size() > pointer){
			pointer++;
			return responses.get(pointer - 1);
		} else {
			return null;
		}
	}
	
	/**
	 * This method is used to response to an event. It will return false
	 * if the final-response-given-flag was already set or the response
	 * was already registered before. 
	 * @param response The response to register
	 * @param shouldBeFinal Should this be the last response available?
	 * @return true if it was able to register the response or otherwise false.
	 */
	public boolean respond(EventResponse<E> response, boolean shouldBeFinal){
		if(isFinal)
			return false;
		if(responses.contains(response))
			return false;
		responses.add(response);
		if(shouldBeFinal)
			isFinal = true;
		return true;
	}

	/**
	 * This method checks if the final answer was already given.
	 * @return true if the final answer already exists or otherwise false.
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * @return the pointer
	 */
	public int getPointer() {
		return pointer;
	}

}
