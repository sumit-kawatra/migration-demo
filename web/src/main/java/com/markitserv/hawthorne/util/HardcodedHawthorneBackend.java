package com.markitserv.hawthorne.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.Book;
import com.markitserv.hawthorne.types.BookList;
import com.markitserv.hawthorne.types.InterestGroup;
import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.Product;
import com.markitserv.hawthorne.types.SubGroup;
import com.markitserv.hawthorne.types.User;

/**
 * Hardcodes data that will eventually come from the server. This class will not
 * live here forever, and should be used only for testing and development.
 * 
 * @author roy.truelove
 * 
 */
@Service
public class HardcodedHawthorneBackend implements HawthorneBackend {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RandomNameGenerator nameGen;
	private List<LegalEntity> legalEntities;
	private List<Book> books;
	private List<BookList> bookLists;
	private List<Participant> participants;
	private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
	private List<InterestGroup> interestGroupList;
	private List<Product> products;
	private List<User> users;

	private void populateInterestGroupList(int count) {
		interestGroupList = new ArrayList<InterestGroup>();
		for (int i = 1; i <= count; i++) {
			InterestGroup group = new InterestGroup();
			group.setName("Interest Group " + i);
			group.setShortName("G " + i);
			group.setActive(new Random().nextBoolean());
			interestGroupList.add(group);
		}

	}

	public List<Participant> getParticipants() {
		if (participants == null) {
			populateParticipants();
		}
		return participants;
	}

	private void populateParticipants() {
		participants = new ArrayList<Participant>();
		int j = 20;
		int start = 1;
		int end = 1;
		for (int i = 1; i < j; i++) {
			if (i == 1) {
				start = 1;
				end = 50;
			} else {
				start = end;
				end = end + 50;
			}
			Participant p = new Participant();
			p.setId(i);
			p.setName("Participant " + i);
			p.setBookList(populateBooks(start, end));
			p.setListOfBookList(populateBookLists(start, end));
			p.setUsers(getUserList(start, end, p.getId()));
			p.setSubGroupList(populateSubGroups(start, end, p.getUsers()));
			participants.add(p);
		}
	}

	private List<SubGroup> populateSubGroups(int start, int end, List<User> subGrpUsers) {
		List<SubGroup> subGroupList = new ArrayList<SubGroup>();
		for (int j = start; j < end; j++) {
			SubGroup group = new SubGroup();
			group.setName("SubGroup" + j);
			group.setShortName("SG" + j);
			group.setActive(new Random().nextBoolean());
			group.setSubGroupUser(subGrpUsers);
			subGroupList.add(group);
		}
		return subGroupList;
	}

	private List<User> getUserList(int start, int end, int participantId) {
		List<User> list = new ArrayList<User>();
		for (int j = start; j < end; j++) {
			User user = new User();
			user.setUserId(j);
			user.setFirstName("FirstName" + j);
			user.setLastName("LastName" + j);
			user.setUserName(user.getFirstName() + " " + user.getLastName());
			user.setLegalEntityId(j);
			user.setParticipantId(participantId);
			user.setEmailAddress(user.getFirstName() + "." + user.getLastName()
					+ "@example.com");
			user.setPhoneNumber(randomPhoneNumGen());
			list.add(user);
		}
		return list;
	}

	private List<Book> populateBooks(int start, int end) {
		if (books == null) {
			books = new ArrayList<Book>();
			for (int j = start; j < end; j++) {
				Book book = new Book();
				book.setId(j);
				book.setName("Book " + j);
				books.add(book);
			}
		}
		return books;
	}

	private List<BookList> populateBookLists(int start, int end) {
		if (bookLists == null) {
			bookLists = new ArrayList<BookList>();
			for (int j = start; j < end; j++) {
				BookList bookList = new BookList();
				bookList.setId(j);
				bookList.setName("BookList " + j);
				bookLists.add(bookList);
			}
		}
		return bookLists;
	}
	private void populateUsers(int count) {
		users = new ArrayList<User>();
		for (int i = 0; i < 100; i++) {
			users.add(createUser(i));
		}
	}

	@Override
	public List<User> getAllUsers() {
		if (users == null) {
			populateUsers(100);
		}
		return users;
	}

	private User createUser(int j) {
		User user = new User();
		user.setUserId(j);
		user.setFirstName("FirstName" + j);
		user.setLastName("LastName" + j);
		user.setUserName(user.getFirstName() + " " + user.getLastName());
		user.setLegalEntityId(j);
		user.setParticipantId(j);
		user.setEmailAddress(user.getFirstName() + "." + user.getLastName()
				+ "@example.com");
		user.setPhoneNumber(randomPhoneNumGen());
		DateTime date = formatter.parseDateTime(randomDateGen());
		user.setLastLogin(date);
		user.setProducts(populateProducts(getRandomNumberFrom(0, 4)));
		return user;
	}

	private String randomPhoneNumGen() {
		Random generator = new Random();
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		num1 = generator.nextInt(600) + 100;
		num2 = generator.nextInt(641) + 100;
		num3 = generator.nextInt(8999) + 1000;
		return num1 + "-" + num2 + "-" + num3;
	}

	private String randomDateGen() {
		Random generator = new Random();
		int num1 = 0;
		int num2 = 0;
		num1 = generator.nextInt(9) + 10;
		num2 = generator.nextInt(10) + 1;
		return num1 + "/" + num2 + "/" + 2012;
	}

	public static int getRandomNumberFrom(int min, int max) {
		Random num = new Random();
		int randomNumber = num.nextInt((max + 1) - min) + min;
		return randomNumber;

	}

	private void populateLegalEntities(int count) {
		legalEntities = new ArrayList<LegalEntity>();

		for (int i = 1; i <= count; i++) {
			legalEntities.add(createLegalEntity(i));
		}
	}

	private List<Product> populateAllProducts() {
		products = new ArrayList<Product>();
		products.add(new Product(1, "Credit"));
		products.add(new Product(2, "Rates"));
		products.add(new Product(3, "Equity"));
		products.add(new Product(4, "FX"));
		return products;
	}

	private List<Product> populateProducts(int count) {
		List<Product> products = new ArrayList<Product>();
		if (count == 1) {
			products.add(new Product(1, "Credit"));
		} else if (count == 2) {
			products.add(new Product(1, "Credit"));
			products.add(new Product(2, "Rates"));
		} else if (count == 3) {
			products.add(new Product(1, "Credit"));
			products.add(new Product(2, "Rates"));
			products.add(new Product(3, "Equity"));
		} else if (count == 4) {
			products.add(new Product(1, "Credit"));
			products.add(new Product(2, "Rates"));
			products.add(new Product(3, "Equity"));
			products.add(new Product(4, "FX"));
		} else if (count == 0) {
			// Do nothing
		}
		return products;

	}

	private LegalEntity createLegalEntity(int id) {

		LegalEntity le = new LegalEntity();
		// for easy & consistent search by name - "john boby test LLC"
		String firstName;
		String secondName;
		String thirdName;
		String name;
		String bic;

		if (id == 1) {
			firstName = "john";
			secondName = "boby";
			thirdName = "test";
			name = firstName + " " + secondName + " " + thirdName + " LLC";
			bic = "JBT";

			le.setId(id);
			le.setName(name);
			le.setBic(bic);
		} else {
			firstName = nameGen.compose(3);
			secondName = nameGen.compose(2);
			thirdName = nameGen.compose(4);
			name = firstName + " " + secondName + " " + thirdName + " LLC";

			// first 4 letters of first and second name
			bic = StringUtils.upperCase(StringUtils.left(firstName, 4))
					+ StringUtils.upperCase(StringUtils.left(secondName, 4));

			le.setId(id);
			le.setName(name);
			le.setBic(bic);
			le.setActive(new Random().nextBoolean());

		}
		return le;
	}

	public List<LegalEntity> getLegalEntities() {
		if (legalEntities == null) {
			populateLegalEntities(100000);
		}
		return legalEntities;
	}

	public List<InterestGroup> getInterestGroups() {
		if (interestGroupList == null) {
			populateInterestGroupList(10000);
		}
		return this.interestGroupList;
	}

	@Override
	public List<Product> getProducts() {
		if (products == null) {
			return populateAllProducts();
		} else {
			return products;
		}
	}

	@Override
	public List<User> getUsersForLegalEntity(int id) {
		List<User> userList = new ArrayList<User>();
		for (User user : this.users) {
			if (user.getLegalEntityId() == id) {
				userList.add(user);
			}
		}
		return userList;
	}

	@Override
	public List<User> getUsersForParticipant(int id) {
		List<User> userList = new ArrayList<User>();
		for (User user : this.users) {
			if (user.getParticipantId() == id) {
				userList.add(user);
			}
		}
		return userList;
	}

	@Override
	public List<User> getUser(String userName) {
		List<User> userList = new ArrayList<User>();
		for (User user : this.users) {
			if (user.getUserName().equals(userName)) {
				userList.add(user);
			}
		}
		return userList;
	}

	@Override
	public List<SubGroup> getSubGroups(Integer participantId, String userName) {
		if (participants == null) {
			populateParticipants();
		}
		if (participantId != null) {
			for (Participant participant : participants) {
				if (participant.getId() == participantId) {
					return participant.getSubGroupList();
				}
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			for (Participant participant : participants) {
				List<User> userList = participant.getAllUsers();
				for (User user : userList) {
					if (userName.contains(user.getUserName())
							|| userName.equalsIgnoreCase(user.getUserName())) {
						return participant.getSubGroupList();
					}
				}
			}
		}
		return null;
	}

}
