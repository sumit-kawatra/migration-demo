package com.markitserv.hawthorne.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.commands.PopulateHardcodedDataCommand;
import com.markitserv.hawthorne.types.Book;
import com.markitserv.hawthorne.types.BookList;
import com.markitserv.hawthorne.types.InterestGroup;
import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.LegalEntityList;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.Product;
import com.markitserv.hawthorne.types.ProductList;
import com.markitserv.hawthorne.types.SubGroup;
import com.markitserv.hawthorne.types.User;
import com.markitserv.msws.command.CommandDispatcher;
import com.markitserv.msws.exceptions.ProgrammaticException;

/**
 * Hardcodes data that will eventually come from the server. This class will not
 * live here forever, and should be used only for testing and development.
 * 
 * @author roy.truelove
 * 
 */
@Service
public class HardcodedHawthorneBackend implements HawthorneBackend,
		ApplicationListener<ContextRefreshedEvent> {

	private static final int SIZE_PRODUCTS = 30;

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RandomNameGenerator nameGen;

	@Autowired
	private CommandDispatcher cmdDispatcher;

	private DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");

	private Map<Integer, Participant> participantMap = new HashMap<Integer, Participant>();
	private Map<Integer, Product> productMap = new HashMap<Integer, Product>();
	private Map<Integer, ProductList> productListMap = new HashMap<Integer, ProductList>();
	private Map<Integer, Book> bookMap = new HashMap<Integer, Book>();
	private Map<Integer, BookList> booklistMap = new HashMap<Integer, BookList>();
	private Map<Integer, LegalEntity> legalEntityMap = new HashMap<Integer, LegalEntity>();
	private Map<Integer, LegalEntityList> legalEntityListMap = new HashMap<Integer, LegalEntityList>();
	private Map<Integer, SubGroup> subGroupMap = new HashMap<Integer, SubGroup>();
	private Map<Integer, User> userMap = new HashMap<Integer, User>();
	// TODO Interest Group
	private Map<Integer, InterestGroup> interestGroupMap = new HashMap<Integer, InterestGroup>();

	private int nextBookListId = 1;
	private int nextBookId = 1;
	private int nextLegalEntityId = 1;
	private int nextLegalEntityListId = 1;
	private int nextProductListId = 1;
	private int nextSubGroupId = 1;
	private int nextInterestGroupId = 1;
	private int nextUserId = 1;

	// NOTE we shouldn't need these after the cleanup
	private List<LegalEntity> legalEntities;
	private List<Book> books;
	private List<BookList> bookLists;
	private List<Participant> participants;
	private List<InterestGroup> interestGroupList;
	private List<Product> products;
	private List<User> users;

	private Set<InterestGroup> interestGroupSet;

	private Set<InterestGroup> emptyInterestGrpSet = new HashSet<InterestGroup>();
	private Set<User> emptyUserSet = new HashSet<User>();

	@Override
	@Deprecated
	public List<Participant> getParticipants() {
		if (participants == null) {
			populateAllHardcodedData();
		}
		return participants;
	}

	@Override
	@Deprecated
	public List<User> getAllUsers() {
		if (users == null) {
			populateUsers(10000);
		}
		return users;
	}

	@Override
	@Deprecated
	public List<LegalEntity> getLegalEntities() {
		if (legalEntities == null) {
			populateLegalEntities(100000);
		}
		return legalEntities;
	}

	@Deprecated
	public List<InterestGroup> getInterestGroups() {
		if (interestGroupList == null) {
			populateInterestGroupList(10000);
		}
		return this.interestGroupList;
	}

	@Deprecated
	public Set<InterestGroup> retrieveInterestGroups() {
		if (interestGroupMap.size() == 0) {
			populateAllHardcodedData();
		}
		Set<InterestGroup> grpSet = new HashSet<InterestGroup>(interestGroupMap.values());
		return grpSet;
	}

	@Override
	@Deprecated
	public List<Product> getProducts() {
		if (products == null) {
			return populateAllProducts();
		} else {
			return products;
		}
	}

	@Override
	public Set<User> getUsersForLegalEntity(int id) {
		Set<User> userSet = new HashSet<User>();
		LegalEntity le = this.legalEntityMap.get(id);
		if (le != null) {
			Set<User> participantUsers = getUsersForParticipant(le.getParticipantId());
			for (User user : participantUsers) {
				if (user.getLegalEntityId() == id) {
					userSet.add(user);
				}
			}
		}
		return userSet;
	}

	@Override
	public Set<User> getUsersForParticipant(int id) {
		if (getParticipant(id) != null) {
			return getParticipant(id).getUsers();
		}
		return emptyUserSet;
	}

	@Override
	public Set<User> getUser(String userName) {
		Set<User> userSet = new HashSet<User>();
		for (User user : this.userMap.values()) {
			if (user.getUserName().equals(userName)) {
				userSet.add(user);
			}
		}
		return userSet;
	}

	@Override
	@Deprecated
	public List<SubGroup> getSubGroups(Integer participantId, String userName) {
		if (participants == null) {
			populateAllHardcodedData();
		}
		if (participantId != null) {
			for (Participant participant : participants) {
				if (participant.getId() == participantId) {
					return new ArrayList(participant.getSubgroups());
				}
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			for (Participant participant : participants) {
				Set<User> userList = participant.getUsers();
				for (User user : userList) {
					if (userName.contains(user.getUserName())
							|| userName.equalsIgnoreCase(user.getUserName())) {
						return new ArrayList(participant.getSubgroups());
					}
				}
			}
		}
		return null;
	}

	@Deprecated
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

	@Deprecated
	private LegalEntity createLegalEntity(int id) {

		LegalEntity le = new LegalEntity(id, null);
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

	@Deprecated
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

	// TODO !!! Move this to private. Only public now for testing
	public Map<Integer, Participant> populateAllHardcodedData() {

		if (participantMap.size() > 0) {
			return participantMap;
		}

		// Only 2 participants for now, hardcoded

		Participant p = new Participant();
		p.setId(1);
		p.setName("Hawthorne Test Bank Alpha");

		p.setProducts(new HashSet<Product>(getRandomSamplingFrom(buildAndGetProducts(), 25)
				.values()));

		p.setProductLists(new HashSet<ProductList>(buildProductList(p, 100).values()));
		p.setBooks(new HashSet<Book>(buildBooks(p, 100).values()));
		p.setBookLists(new HashSet<BookList>(buildBookLists(p, 100).values()));
		p.setLegalEntities(new HashSet<LegalEntity>(buildLegalEntities(p, 100).values()));
		p.setLegalEntityLists(new HashSet<LegalEntityList>(buildLegalEntityList(p, 100)
				.values()));
		p.setSubgroups(new HashSet<SubGroup>(buildSubGroups(p, 100, 20).values()));
		p.setUsers(new HashSet<User>(buildUserList(p, 50).values()));
		p.setInterestGroups(new HashSet<InterestGroup>(buildInterestGroups(p, 1000, 10)
				.values()));

		// TODO Users

		Participant p1 = new Participant();
		p1.setId(2);
		p1.setName("Hawthorne Test Bank Beta");

		p1.setProducts(new HashSet<Product>(
				getRandomSamplingFrom(buildAndGetProducts(), 25).values()));

		p1.setProductLists(new HashSet<ProductList>(buildProductList(p1, 100).values()));
		p1.setBooks(new HashSet<Book>(buildBooks(p1, 100).values()));
		p1.setBookLists(new HashSet<BookList>(buildBookLists(p1, 100).values()));
		p1.setLegalEntities(new HashSet<LegalEntity>(buildLegalEntities(p1, 100).values()));
		p1.setLegalEntityLists(new HashSet<LegalEntityList>(buildLegalEntityList(p1, 100)
				.values()));
		p1.setSubgroups(new HashSet<SubGroup>(buildSubGroups(p1, 100, 20).values()));
		p1.setUsers(new HashSet<User>(buildUserList(p1, 50).values()));
		p1.setInterestGroups(new HashSet<InterestGroup>(buildInterestGroups(p1, 10, 5)
				.values()));

		participantMap.put(1, p);
		participantMap.put(2, p1);
		// TODO make another participant

		return participantMap;

		/*
		 * participants = new ArrayList<Participant>();
		 * 
		 * int j = 20; int start = 1; int end = 1; for (int i = 1; i < j; i++) {
		 * if (i == 1) { start = 1; end = 50; } else { start = end; end = end +
		 * 50; } Participant p = new Participant(); p.setId(i);
		 * p.setName("Participant " + i); p.setBookList(populateBooks(start,
		 * end)); p.setListOfBookList(populateBookLists(start, end));
		 * p.setUsers(populateUserList(start, end, p.getId()));
		 * p.setSubGroupList(populateSubGroups(start, end, p.getUsers()));
		 * participants.add(p); }
		 */
	}

	private Map<Integer, User> buildUserList(Participant p, int size) {

		int lastId = nextUserId + size;

		for (; nextUserId <= lastId; nextUserId++) {
			User usr = createUser(nextUserId, p);
			userMap.put(nextUserId, usr);
		}

		return userMap;
	}

	private Map<Integer, InterestGroup> buildInterestGroups(Participant p, int size,
			int memberSizes) {
		int lastId = nextInterestGroupId + size;
		for (; nextInterestGroupId <= lastId; nextInterestGroupId++) {
			InterestGroup i1 = new InterestGroup(nextInterestGroupId, "InterestGroup "
					+ nextInterestGroupId);
			i1.setParticipantId(p.getId());
			i1.setShortName("IG-" + nextInterestGroupId);
			i1.setActive(true);
			i1.setUsers(getRandomSamplingFrom(p.getUsers(), memberSizes));
			interestGroupMap.put(nextInterestGroupId, i1);
		}
		return interestGroupMap;
	}

	private <T> HashMap<Integer, T> sliceMap(Map<Integer, T> srcMap, double offsetPercent,
			double slicePercent) {

		HashMap<Integer, T> targetMap = new HashMap<Integer, T>();

		int size = srcMap.size();

		// NOTE this is not exactly right - it will create some overlaps. That's
		// ok for now..
		int offset = (int) Math.round(offsetPercent * size);
		int sliceSize = (int) Math.round(slicePercent * size);
		int sliceEnd = offset + sliceSize;

		log.debug(String.format("Slices = offset: %d, sliceSize: %d, sliceEnd: %d", offset,
				sliceSize, sliceEnd));

		for (int i = offset; i <= sliceEnd; i++) {
			targetMap.put(i, srcMap.get(i));
		}

		return targetMap;
	}

	/**
	 * Gets a random sampling from a map
	 * 
	 * @param map
	 * @param size
	 * @return
	 */
	private <R, T> Map<R, T> getRandomSamplingFrom(Map<R, T> map, int size) {

		Set<R> keys = map.keySet();
		List<R> randomizedKeys = new ArrayList<R>(keys);
		Collections.shuffle(randomizedKeys);
		randomizedKeys = randomizedKeys.subList(0, size);

		Map<R, T> newMap = new HashMap<R, T>();

		for (R key : randomizedKeys) {
			newMap.put(key, map.get(key));
		}
		return newMap;
	}

	private <T> Set<T> getRandomSamplingFrom(Set<T> set, int size) {

		List<T> list = new LinkedList<T>(set);
		Collections.shuffle(list);
		return new HashSet<T>(list.subList(0, size));
	}

	@Deprecated
	private List<SubGroup> populateSubGroups(int start, int end, List<User> subGrpUsers) {
		List<SubGroup> subGroupList = new ArrayList<SubGroup>();
		for (int j = start; j < end; j++) {
			SubGroup group = null;
			group.setName("SubGroup" + j);
			group.setShortName("SG" + j);
			group.setActive(new Random().nextBoolean());
			// group.setSubGroupUser(subGrpUsers);
			subGroupList.add(group);
		}
		return subGroupList;
	}

	@Deprecated
	private List<User> populateUserList(int start, int end, int participantId) {
		List<User> list = new ArrayList<User>();
		for (int j = start; j < end; j++) {
			User user = new User();
			// user.setUserId(j);
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

	@Deprecated
	private List<Book> populateBooks(int start, int end) {
		if (books == null) {
			books = new ArrayList<Book>();
			for (int j = start; j < end; j++) {
				Book book = new Book(j, "Book" + j);
				book.setId(j);
				book.setName("Book " + j);
				books.add(book);
			}
		}
		return books;
	}

	private Map<Integer, Book> buildBooks(Participant p, int size) {

		int lastId = nextBookId + size;

		for (; nextBookId <= lastId; nextBookId++) {
			Book bl = new Book(nextBookId, "Book" + nextBookId);
			bl.setParticipantId(p.getId());
			bookMap.put(nextBookId, bl);
		}

		return bookMap;
	}

	private Map<Integer, LegalEntity> buildLegalEntities(Participant p, int size) {

		int lastId = nextLegalEntityId + size;

		for (; nextLegalEntityId <= lastId; nextLegalEntityId++) {
			LegalEntity bl = new LegalEntity(nextLegalEntityId, "LegalEntity "
					+ nextLegalEntityId);
			bl.setParticipantId(p.getId());
			bl.setBic("LE-" + nextLegalEntityId);
			bl.setActive(true);
			legalEntityMap.put(nextLegalEntityId, bl);
		}

		return legalEntityMap;
	}

	private Map<Integer, LegalEntityList> buildLegalEntityList(Participant p, int size) {

		int lastId = nextLegalEntityListId + size;

		for (; nextLegalEntityListId <= lastId; nextLegalEntityListId++) {
			LegalEntityList bl = new LegalEntityList(nextLegalEntityId, "LegalEntityList "
					+ nextLegalEntityId);
			bl.setParticipantId(p.getId());
			// TODO this should populate some LE's from the participant's list
			bl.setLegalEntities(getRandomSamplingFrom(p.getLegalEntities(), 2));
			legalEntityListMap.put(nextLegalEntityListId, bl);
		}

		return legalEntityListMap;
	}

	private Map<Integer, SubGroup> buildSubGroups(Participant p, int size, int memberSizes) {

		int lastId = nextSubGroupId + size;

		for (; nextSubGroupId <= lastId; nextSubGroupId++) {
			SubGroup bl = new SubGroup(nextSubGroupId, "SubGroup " + nextSubGroupId);
			bl.setParticipantId(p.getId());

			bl.setProductList(getRandomSamplingFrom(p.getProductLists(), memberSizes));
			bl.setProduct(getRandomSamplingFrom(p.getProducts(), memberSizes));
			bl.setBookLists(getRandomSamplingFrom(p.getBookLists(), memberSizes));
			bl.setBooks(getRandomSamplingFrom(p.getBooks(), memberSizes));
			bl.setLegalEntities(getRandomSamplingFrom(p.getLegalEntities(), memberSizes));
			bl.setLegalEntityLists(getRandomSamplingFrom(p.getLegalEntityLists(),
					memberSizes));

			subGroupMap.put(nextSubGroupId, bl);
		}

		return subGroupMap;
	}

	private Map<Integer, ProductList> buildProductList(Participant p, int size) {

		int lastId = nextProductListId + size;

		for (; nextProductListId <= lastId; nextProductListId++) {
			ProductList bl = new ProductList(nextProductListId, "ProductList "
					+ nextProductListId);
			bl.setParticipantId(p.getId());
			bl.setProducts(getRandomSamplingFrom(p.getProducts(), 3));
			// TODO add some random products from the participant to these lists
			productListMap.put(nextProductListId, bl);
		}

		return productListMap;
	}

	private Map<Integer, BookList> buildBookLists(Participant p, int size) {

		int lastId = nextBookListId + size;

		for (; nextBookListId <= lastId; nextBookListId++) {
			BookList bl = new BookList(nextBookListId, "BookList" + nextBookListId);
			bl.setParticipantId(p.getId());
			// TODO this should also add books to the list from the participant.
			bl.setBooks(getRandomSamplingFrom(p.getBooks(), 2));
			booklistMap.put(nextBookListId, bl);
		}

		return booklistMap;
	}

	/**
	 * private Map<Integer, SubGroup> buildAndGetBooks() {
	 * 
	 * if (bookMap == null) {
	 * 
	 * bookMap = new HashMap<Integer, SubGroup>();
	 * 
	 * for (int i = 1; i <= SIZE_BOOKS; i++) { SubGroup bl = new SubGroup(i,
	 * "Subgroup" + i); bookMap.put(i, bl); } }
	 * 
	 * return bookMap; }
	 **/

	@Deprecated
	private void populateUsers(int count) {
		users = new ArrayList<User>();
		for (int i = 0; i < count; i++) {
			users.add(createUser(i));
		}
	}

	private User createUser(int id, Participant p) {

		User user = new User();
		user.setId(id);
		user.setFirstName(nameGen.compose(2));
		user.setLastName(nameGen.compose(3));
		user.setUserName(StringUtils.left(user.getFirstName(), 1).toLowerCase()
				+ StringUtils.left(user.getLastName(), 7));

		user.setParticipantId(p.getId());
		user.setEmailAddress(user.getFirstName() + "." + user.getLastName()
				+ "@hawthorneBank.com");
		user.setPhoneNumber(randomPhoneNumGen());
		DateTime date = formatter.parseDateTime(randomDateGen());
		user.setLastLogin(date);

		// TODO finish this up!
		user.setBooks(getRandomSamplingFrom(p.getBooks(), 5));
		user.setBookLists(getRandomSamplingFrom(p.getBookLists(), 5));
		user.setParticipantId(p.getId());
		user.setProductList(getRandomSamplingFrom(p.getProductLists(), 3));
		user.setProducts(getRandomSamplingFrom(p.getProducts(), 5));
		user.setLegalEntityId(getRandomSamplingFrom(p.getLegalEntities(), 1).iterator()
				.next().getId());
		user.setLegalEntities(getRandomSamplingFrom(p.getLegalEntities(), 5));
		user.setLegalEntityLists(getRandomSamplingFrom(p.getLegalEntityLists(), 3));

		return user;
	}

	@Deprecated
	private User createUser(int j) {
		User user = new User();
		user.setId(j);
		user.setFirstName("FirstName" + j);
		user.setLastName("LastName" + j);
		user.setUserName(user.getFirstName() + " " + user.getLastName());
		user.setLegalEntityId(j);
		user.setParticipantId(1); // only one particpant for now
		user.setEmailAddress(user.getFirstName() + "." + user.getLastName()
				+ "@example.com");
		user.setPhoneNumber(randomPhoneNumGen());
		DateTime date = formatter.parseDateTime(randomDateGen());
		user.setLastLogin(date);
		// user.setProducts(populateProducts(getRandomNumberFrom(0, 4)));
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

	private void populateLegalEntities(int count) {
		legalEntities = new ArrayList<LegalEntity>();

		for (int i = 1; i <= count; i++) {
			legalEntities.add(createLegalEntity(i));
		}
	}

	@Deprecated
	private List<Product> populateAllProducts() {
		products = new ArrayList<Product>();
		products.add(new Product(1, "Credit"));
		products.add(new Product(2, "Rates"));
		products.add(new Product(3, "Equity"));
		products.add(new Product(4, "FX"));
		return products;
	}

	private Map<Integer, Product> buildAndGetProducts() {

		productMap = new HashMap<Integer, Product>();

		for (int i = 1; i <= SIZE_PRODUCTS; i++) {
			String productName = "Product " + generateLetter(i);
			productMap.put(i, new Product(i, productName));
		}

		return productMap;
	}

	private static int getRandomNumberFrom(int min, int max) {
		Random num = new Random();
		int randomNumber = num.nextInt((max + 1) - min) + min;
		return randomNumber;
	}

	private char generateLetter(int index) {

		if (index > 52) {
			throw new ProgrammaticException("Sorry, cant do > 52");
		}

		int code = index + 64;
		if (index > 26) {
			code = code + 6;
		}
		return Character.toChars(code)[0];
	}

	@Override
	public Set<InterestGroup> retrieveInterestGrpsForUser(int id) {
		// TODO filter the interest groups where the user is part of the grp

		return null;
	}

	public void setNameGen(RandomNameGenerator namegen) {
		this.nameGen = namegen;
	}

	@Override
	public Set<InterestGroup> getInterestGroupsForParticipant(int participantId) {
		if (getParticipant(participantId) != null) {
			return this.getParticipant(participantId).getInterestGroups();
		}
		return emptyInterestGrpSet;
	}

	@Override
	public Participant getParticipant(int participantId) {
		return this.participantMap.get(participantId);
	}

	@Override
	public Set<User> getUsersForInterestGrp(int interestGroupId) {

		if (interestGroupMap.get(interestGroupId) != null) {
			return interestGroupMap.get(interestGroupId).getUsers();
		}
		return emptyUserSet;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		cmdDispatcher.dispatchAsyncCommand(new PopulateHardcodedDataCommand());
	}
}
