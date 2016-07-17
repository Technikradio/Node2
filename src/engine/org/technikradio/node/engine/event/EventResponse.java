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

/**
 * This class represents an response to an event from a processor.
 * Multiple responses are possible.
 * @author doralitze
 */
public class EventResponse<E> {

	private final String responseidentifier;
	private final boolean reacted;
	private final E reaction;
	
	/**
	 * This constructor creates a new event response.
	 * Note if the processor isn't able of getting a result at all do not create a response at all.
	 * Not even with an empty reaction and valid response set to false.
	 * @param ownIdentifier The identifier of the responding object
	 * @param validResponse Was the processor able of creating a full or part answer
	 * @param reaction The answer of the processor
	 */
	public EventResponse(String ownIdentifier, boolean validResponse, E reaction) {
		this.responseidentifier = ownIdentifier;
		this.reacted = validResponse;
		this.reaction = reaction;
		if(this.responseidentifier == null){
			throw new RuntimeException("The response identifier isn't allowed to be null, but it is.");
		}
		if(this.responseidentifier.equals("")){
			throw new RuntimeException("The response identifier isn't allowed to be empty, but it is.");
		}
		if(this.reaction == null){
			throw new RuntimeException("The response reaction isn't allowed to be null, but it is.");
		}
	}

	/**
	 * This method is used to evaluate the source of the response.
	 * @return the responseidentifier
	 */
	public String getResponseSourceIdentifier() {
		return responseidentifier;
	}

	/**
	 * This method is used to determinate if the responder was
	 * able of answering correctly.
	 * @return the reacted
	 */
	public boolean hasReactedProperly() {
		return reacted;
	}

	/**
	 * This method is used to get the reaction.
	 * @return the reaction
	 */
	public E getReaction() {
		return reaction;
	}

}
