package com.markitserv.ssa.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.markitserv.ssa.res.Book;
import com.markitserv.ssa.res.Participant;
import com.markitserv.ssa.res.User;

@Component
public class HardcodedData implements InitializingBean {

	Logger log = LoggerFactory.getLogger(HardcodedData.class);

	@Autowired
	private RandomNameGenerator nameGen;

	public Map<Long, Participant> participants;
	public Map<Long, Book> books;

	private void initData() {

		participants = new HashMap<Long, Participant>();

		// only create one for now

		Participant p = new Participant(1l, "Mega Bank");

		Collection<Book> availableBooks = createAvailableBooks(50);
		p.setBooks(availableBooks);
		participants.put(1l, p);
	}

	private Map<Long, User> createAllUsers(int len, Participant p) {

		Map<Long, User> users = new HashMap<Long, User>();

		for (int x = 1; x <= len; x++) {
			User u = new User();
			u.setFirstName(nameGen.compose(2));
			u.setLastName(nameGen.compose(3));
			u.setId(x);
			// avg of 80% of users will be active
			u.setActive((Math.random() < .8) ? true : false);
			// TODO not done yet
		}

		return users;
	}

	private Collection<Book> createAvailableBooks(int numOfBooks) {
		if (numOfBooks > 52) {
			throw new RuntimeException(
					"Can't have more than 52 fake books.  Sorry.");
		}
		// create available books
		this.books = new HashMap<Long, Book>();
		for (int x = 1; x <= numOfBooks; x++) {
			int charCode = (x <= 26) ? 65 : 71; // start at capital A, then
												// lower case A.
			int charx = (charCode + (x - 1));
			String name = "Book " + (char) charx;
			Book b = new Book(x, name);
			this.books.put((long) x, b);
		}
		return this.books.values();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initData();
	}
}
