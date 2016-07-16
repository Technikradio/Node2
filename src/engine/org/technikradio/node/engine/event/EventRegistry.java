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
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import org.technikradio.node.engine.action.Application;

/**
 * @author doralitze
 * This class is used to handle events and raise them.
 */
public class EventRegistry {

	private static Hashtable<EventType, List<EventHandler>> handlers;
	private static ArrayList<Event> processListSync;
	private static List<Event> processListAssync;
	private static EventProcessor[] processors;
	
	/**
	 * This subclass is used to process raised events.
	 * @author doralitze
	 */
	private static class EventProcessor implements Runnable{
		
		private boolean running = false;
		
		@Override
		public void run() {
			setRunning(true);
			Event e = getNextEvent();
			while(e != null){
				EventType type = e.getType();
				boolean handled = false;
				for(EventHandler handler : handlers.get(type)){
					handler.handleEvent(e);
					//TODO implement exit clause when final answer was given
					handled = true;
				}
				if(!handled && e.getType().shouldAutomaticallyThrowCrash())
					Application.crash(e);
				e = getNextEvent();
			}
			setRunning(false);
		}
		
		/**
		 * This method is used to determinate whether this processor is currently running or not.
		 * @return true if the processor is currently running otherwise false
		 */
		public boolean isRunning() {
			return running;
		}
		
		/**
		 * @param running the running flag to set
		 */
		private void setRunning(boolean running) {
			this.running = running;
		}
		
		/**
		 * This method is used to get the next event to process from the stack.
		 * @return It will return the next element or null if the stack is empty.
		 */
		protected synchronized Event getNextEvent(){
			if(processListAssync.size() == 0)
				return null;
			Event e = processListAssync.get(0);
			processListAssync.remove(e);
			return e;
		}
		
		/**
		 * This method is used to start the processor.
		 * It won't do anything if the processor already runs.
		 */
		public void start(){
			if(isRunning())
				return;
			Thread t = new Thread(this);
			t.setName("EventProcessor");
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
		}
		
	}
	
	/**
	 * This class is used to deal with the events which need to be processed in order.
	 * @author doralitze
	 */
	private static class SynchronizedEventProcessor extends EventProcessor{
		
		/**
		 * 
		 * @return
		 */
		@Override
		protected synchronized Event getNextEvent(){
			if(processListSync.size() == 0)
				return null;
			Event e = processListSync.get(0);
			processListSync.remove(e);
			return e;
		}
	}
	
	/**
	 * This method initializes the event registry
	 */
	static{
		handlers = new Hashtable<EventType, List<EventHandler>>();
		processListSync = new ArrayList<Event>();
		processListAssync = Collections.synchronizedList(new ArrayList<Event>());
		ArrayList<EventProcessor> ps = new ArrayList<EventProcessor>();
		ps.add(new SynchronizedEventProcessor());
		for(int i = 0; i < Runtime.getRuntime().availableProcessors(); i++){
			ps.add(new EventProcessor());
		}
		processors = ps.toArray(new EventProcessor[ps.size()]);
	}
	
	/**
	 * This method raises an event and starts the processing of it.
	 * Note that if you're using this event responder you may have to
	 * cast it to the desired responder type.
	 * This method puts all events inside the parallel processing stack.
	 * @param event The event to raise
	 * @return The EventResponder of the raised event
	 */
	@SuppressWarnings("rawtypes")
	public static EventResponder raiseEvent(Event event){
		return raiseEvent(event, false);
	}
	
	/**
	 * This method raises an event and starts the processing of it.
	 * Note that if you're using this event responder you may have to
	 * cast it to the desired responder type.
	 * @param event The event to raise
	 * @param synchron Should the event be placed inside the synchron list?
	 * @return The EventResponder of the raised event
	 */
	@SuppressWarnings("rawtypes")
	public static EventResponder raiseEvent(Event event, boolean synchron){
		if(synchron){
			processListSync.add(processListSync.size() * (event.getType().getPriority() / EventType.MAXIMUM_PRIORITY), event);
			processors[0].start();
		} else {
			synchronized(processListAssync){
				processListAssync.add(processListAssync.size() * (event.getType().getPriority() / EventType.MAXIMUM_PRIORITY), event);
				for(int i = 0; i < processListAssync.size() && i < processors.length; i++){
					processors[i + 1].start();
				}
			}
		}
		return event.getResponder();
	}
	
	/**
	 * This method is used to register event handlers.
	 * @param type The event type to listen for
	 * @param eventHandler The handler to register
	 * @return true if it is the first handler or otherwise false
	 */
	public static boolean addEventHandler(EventType type, EventHandler eventHandler){
		boolean first = false;
		if(!handlers.containsKey(type)){
			List<EventHandler> l = Collections.synchronizedList(new ArrayList<EventHandler>());
			handlers.put(type, l);
			first = true;
		}
		handlers.get(type).add(eventHandler);
		return first;
	}
	
	/**
	 * This method is used to check if there are event handlers
	 * registered for a specific event type.
	 * @param type The event type to look for
	 * @return true if there are handlers registered for this specific event type or otherwise false
	 */
	public static boolean hasHandlers(EventType type){
		return handlers.containsKey(type);
	}
	
	/**
	 * This method is used to register a bunch of event handlers.
	 * @param type The event type to register the handlers for
	 * @param handlers The handlers to register
	 * @return true if they were the first handlers or otherwise false
	 */
	public static boolean addEventHandlers(EventType type, EventHandler[] handlers){
		boolean first = false;
		for(EventHandler h : handlers){
			if(addEventHandler(type, h))
				first = true;
		}
		return first;
	}
}
