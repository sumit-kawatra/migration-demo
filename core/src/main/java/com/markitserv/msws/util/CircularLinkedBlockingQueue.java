package com.markitserv.msws.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.NotImplementedException;

/**
 * Implementation of LinkedBlockingQueue that will remove the element from the
 * top of the queue if the size limit is reached and new element is being added.
 * Is thread safe
 * 
 * @author roy.truelove
 * 
 */
public class CircularLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

	private static final long serialVersionUID = 1L;

	public CircularLinkedBlockingQueue(int capacity) {
		super(capacity);
	}

	@Override
	public void put(E e) {
		throw new NotImplementedException("Use add()");
	}
	
	

	@Override
	public E take() throws InterruptedException {
		
		E elem = super.take();
		return elem;
	}

	@Override
	public boolean add(E e) {
		
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		
		boolean couldAdd = false;

		try {

			if (this.remainingCapacity() == 0) {
				this.remove();
			}

			couldAdd = super.add(e);

		} finally {
			lock.unlock();
		}
		
		return couldAdd;
	}
}
