package com.markitserv.ssa.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.markitserv.ssa.res.Book;
import com.markitserv.ssa.res.BrokerCode;
import com.markitserv.ssa.res.LegalEntity;
import com.markitserv.ssa.res.Participant;
import com.markitserv.ssa.res.User;

@Component
public class HardcodedData implements InitializingBean {

	Logger log = LoggerFactory.getLogger(HardcodedData.class);

	@Autowired
	private RandomNameGenerator nameGen;

	public Map<Long, Participant> participants;
	public Map<Long, Book> books;
	public Map<Long, User> users;
	public Map<Long, LegalEntity> legalEntities;
	public HashMap<Long, BrokerCode> brokerCodes;

	private Random rand;


	private void initData() {

		participants = new HashMap<Long, Participant>();

		// only create one for now

		Participant p = new Participant(1l, "Mega Bank");
		p.setBooks(createAvailableBooks(5));
		p.setUsers(createAllUsers(50, p));
		p.setLegalEntities(createAllLegalEntities(10, p));
		p.setBrokerCodes(createAllBrokerCodes(10, p));

		participants.put(1l, p);
	}

	/**
	 * @param len
	 * @param p
	 * @return
	 */
	private Collection<User> createAllUsers(int len, Participant p) {

		this.users = new HashMap<Long, User>();

		for (long x = 1; x <= len; x++) {
			User u = new User();
			u.setFirstName(nameGen.compose(rand.nextInt(2) + 2));
			u.setLastName(nameGen.compose(rand.nextInt(4) + 2));
			u.setId(x);
			// avg of 80% of users will be active
			u.setActive((Math.random() < .8) ? true : false);
			u.setParticipant(p);
			u.setPassword(RandomStringUtils.randomAlphanumeric(10));
			users.put(x, u);
		}

		long nextId = len + 1;

		User u = new User();
		u.setFirstName("Super");
		u.setLastName("Man");
		u.setId(nextId);
		u.setActive(true);
		u.setSuperUser(true);
		u.setParticipant(p);
		u.setPassword("password123");

		users.put(nextId, u);

		return users.values();
	}

	private Collection<Book> createAvailableBooks(int numOfBooks) {

		if (numOfBooks > 52) {
			throw new RuntimeException(
					"Can't have more than 52 fake books.  Sorry.");
		}
		// create available books
		this.books = new HashMap<Long, Book>();
		for (int x = 1; x <= numOfBooks; x++) {
			String name = "Book " + getIncrementalCharFromInt(x);
			Book b = new Book(x, name);
			this.books.put((long) x, b);
		}
		return this.books.values();
	}

	private Collection<LegalEntity> createAllLegalEntities(int num,
			Participant p) {

		if (num > 52) {
			throw new RuntimeException("Can't have more than 52 LEs.  Sorry.");
		}

		this.legalEntities = new HashMap<Long, LegalEntity>();
		for (int x = 1; x <= num; x++) {
			String name = "Company " + getIncrementalCharFromInt(x);
			LegalEntity b = new LegalEntity(x, p, name);
			this.legalEntities.put((long) x, b);
		}
		return this.legalEntities.values();
	}
	
	private Collection<BrokerCode> createAllBrokerCodes(int num,
			Participant p) {

		if (num > 52) {
			throw new RuntimeException("Can't have more than 52 broker codes.  Sorry.");
		}

		this.brokerCodes = new HashMap<Long, BrokerCode>();
		for (int x = 1; x <= num; x++) {
			String name = "Broker Code " + getIncrementalCharFromInt(x);
			BrokerCode b = new BrokerCode(x, name, p);
			this.brokerCodes.put((long) x, b);
		}
		return this.brokerCodes.values();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		rand = new Random();
		initData();
	}

	private char getIncrementalCharFromInt(int x) {
		// start at capital A, then lower case A.
		int charCode = (x <= 26) ? 65 : 71;
		return (char) (charCode + (x - 1));

	}
}
