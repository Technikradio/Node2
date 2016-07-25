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
package org.technikradio.node.engine.plugin;

/**
 * This exception is designed to get thrown in case of an unsolved dependency
 * tree. Typically this means that a plugin requires another plugin to operate
 * but the other plugin is not installed. It may also be the case that two
 * installed plugins are conflicting with each other. See the message detail of
 * this exception in order to separate both cases.
 * 
 * @author doralitze
 *
 */
public class UnsolvedPependencyException extends Exception {

	private static final long serialVersionUID = -4598389328851172940L;

	/**
	 * Put this constant at the beginning of the the message detail of this
	 * exception in order to mark the case that this exception is thrown due to
	 * a couple conflicting plugin installations.
	 */
	public static final String CONFLICTING_PLUGINS = "[CONFLICT]";
	/**
	 * Put this constant at the beginning of the the message detail of this
	 * exception in order to mark the case that this exception is thrown due to
	 * a couple of missing dependency of other plugins.
	 */
	public static final String UNSOLVED_DEPENDENCY = "[DEPPROBL]";

}
