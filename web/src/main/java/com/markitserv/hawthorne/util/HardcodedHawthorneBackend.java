package com.markitserv.hawthorne.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.Book;
import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.TradingRequest;
import com.markitserv.hawthorne.types.TradingRequestStatus;
import com.markitserv.hawthorne.types.User;

/**
 * Hardcodes data that will eventually come from the server. This class will not
 * live here forever, and should be used only for testing and development.
 * 
 * @author roy.truelove
 * 
 */
@Service
public class HardcodedHawthorneBackend implements InitializingBean,
		HawthorneBackend {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RandomNameGenerator nameGen;
	private List<LegalEntity> legalEntities; 
	private Stack<TradingRequestStatus> tradingRequestStatuses;
	private List<TradingRequest> tradingRequests;
	private List<User> legalEntityUsers;
	private List<Book> books;
	private List<Participant> participants;
	
	

	private void initData() {
		populateLegalEntities(100000);
		populateTradingRequestStatuses();
		populateTradingRequests(10);
		populateLegalEntityUsers(10000);
	}

	
	
	
	public List<Participant> getParticipants() {
		participants = new ArrayList<Participant>();
		int j = 20;
		int start = 1;
		int end = 1;
		for (int i = 1; i < j; i++) {
			if(i == 1){
				 start = 1;
				 end = 50;	
			}else{
				start = end;
				end = end +50;
			}
			Participant p = new Participant();
			p.setId(i);
			p.setName("Participant "+i);
			p.setBookList(populateBooks(start, end));
			p.setUsers(getUserList(i));
			participants.add(p);
		}
		return participants;
	}

	private List<User> getUserList(int j) {
		 List<User> list = new ArrayList<User>();
		for (int i = j; i <= j; i++) {
			User user = new User();
			user.setUserId(i);
			user.setFirstName("First Name"+j);
			user.setLastName("LastName"+j);
			user.setUserName(user.getFirstName()+" "+user.getLastName());
			user.setLegalEntityId(j);
			user.setParticipantId(j);
			list.add(user);
		}
		return list;
	}

	private List<Book> populateBooks(int start, int end) {
		books = new ArrayList<Book>();
		for (int j = start; j < end; j++) {
			Book book = new Book();
			book.setId(j);
			book.setName("Book "+j);
			books.add(book);
		}
		return books;
	}

	private void populateLegalEntityUsers(int count) {
		int i =1;
		int j = 1000;
		legalEntityUsers = new ArrayList<User>();
		
		for (int k = 0; k < count; k++) {
			legalEntityUsers.add(createUser(k));
		}
		
		
		for (User user : legalEntityUsers) {			
			user.setLegalEntityId(j);
			if(i ==10){
				i=1;
				j=j+1;
			}
			i++;			
		}	
	}

	@Override
	public List<User> getUsers() {
		List<User> userList = new ArrayList<User>();
		for(int i=0;i<100;i++){
			userList.add(createUser(i));
		}
		return userList;
	}
	
	private User createUser(int j) {
		User user = new User();
		user.setUserId(j);
		user.setFirstName("First Name"+j);
		user.setLastName("LastName"+j);
		user.setUserName(user.getFirstName()+" "+user.getLastName());
		user.setLegalEntityId(j);
		user.setParticipantId(j);
		return user;
	}

	private void populateLegalEntities(int count) {
		
		legalEntities = new ArrayList<LegalEntity>();

		for (int i = 1; i <= count; i++) {
			legalEntities.add(createLegalEntity(i));
		}
	}

	private void populateTradingRequestStatuses() {
		
		tradingRequestStatuses = new Stack<TradingRequestStatus>();
		
		tradingRequestStatuses.add(new TradingRequestStatus(1, "Cancelled"));
		tradingRequestStatuses.add(new TradingRequestStatus(2, "Live"));
		tradingRequestStatuses.add(new TradingRequestStatus(3, "No Relationship"));
		tradingRequestStatuses.add(new TradingRequestStatus(4, "On Hold"));
		tradingRequestStatuses.add(new TradingRequestStatus(5, "Else"));
	}
	
	private void populateTradingRequests(int count) {
		tradingRequests = new ArrayList<TradingRequest>();
		for (int i = 1, j=0; i <= count; i++,j++) {
			j = j == 4 ? 0 : j;
			tradingRequests.add(createTradingRequest(i));
		}
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
	
	private TradingRequest createTradingRequest(long id) {
		TradingRequest tr = new TradingRequest();
		tr.setId(id);
		try {
			tr.setRequestStatus(tradingRequestStatuses.get((int) id-1));
		} catch (Exception exception) {
			tr.setRequestStatus(tradingRequestStatuses.get(0));
		}
		return tr;
	}

	public List<LegalEntity> getLegalEntities() {
		return legalEntities;
	}
	
	public List<TradingRequestStatus> getTradingRequestStatuses() {
		return this.tradingRequestStatuses;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("Starting to populate hardcoded data");
		initData();
		log.debug("Finished populating hardcoded data");
	}

	@Override
	public List<TradingRequest> getTradingRequests() {
		return this.tradingRequests;
	}

	@Override
	public List<User> getUsersForLegalEntity() {		
		return this.legalEntityUsers;
	}
}
