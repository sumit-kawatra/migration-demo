package com.markitserv.msws.util;

import java.util.Collection;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.markitserv.msws.internal.exceptions.ProgrammaticException;

/**
 * Implementation of a LinkedBlockingQueue that, once capacity is reached, will
 * pop off older elements. Thread safe.
 * 
 * @author roy.truelove
 * 
 * @param <E>
 */
public class CircularLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

	private static final long serialVersionUID = 1L;

	public CircularLinkedBlockingQueue() {
		super();
	}

	public CircularLinkedBlockingQueue(Collection<? extends E> c) {
		super(c);
	}

	public CircularLinkedBlockingQueue(int capacity) {
		super(capacity);
	}

	@Override
	public void put(E e) throws InterruptedException {

		// tries repeatedly to put something on the queue.
		while ((super.offer(e)) != false) {
			this.remove();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit)
			throws InterruptedException {

		// we don't need the timeout because there will always be room!

		return this.offer(e);

	}

	@Override
	public boolean add(E e) {
		try {
			this.put(e);
		} catch (InterruptedException e1) {
			throw new IllegalStateException(
					"Could not 'add' an element to the queue.  Very unexpected");
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// !!! only safe because we know the underlying implementation is
		// calling 'add' under the covers.
		return super.addAll(c);
	}

	@Override
	public boolean offer(E e) {
		try {
			this.put(e);
		} catch (InterruptedException e1) {
			throw new IllegalStateException(
					"Could not 'offer' an element to the queue.  Very unexpected");
		}
		return true;
	}
}
