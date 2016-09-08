/**
 * 
 */
package org.technikradio.node.engine.event;

/**
 * This interface is used to add an listener designed to be called when
 * responses are added to an event responder.
 * 
 * @author doralitze
 *
 */
public interface ResponseListener<E> {

	/**
	 * This method gets called when an event response is added to the event
	 * responder.
	 * 
	 * @param e
	 *            The response that was added.
	 */
	public abstract void processResponse(EventResponse<E> e);
}
