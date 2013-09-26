package com.markitserv.msws.util;

/**
 * Transient queue that will always pop the latest item in the queue.  Blocks.
 * @author roy.truelove
 *
 * @param <E>
 */
public class TransientBlockingQueue<E> extends CircularLinkedBlockingQueue<E> {
	
	private static final long serialVersionUID = 1L;

	public TransientBlockingQueue() {
		super(1);
	}

}
