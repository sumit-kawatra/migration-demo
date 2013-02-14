package com.markitserv.hawthorne;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;


import com.markitserv.hawthorne.types.Book;
import com.markitserv.hawthorne.types.InterestGroup;

import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.Product;
import com.markitserv.hawthorne.types.TradingRequest;
import com.markitserv.hawthorne.types.TradingRequestStatus;
import com.markitserv.hawthorne.types.User;
import com.markitserv.msws.exceptions.NotYetImplementedException;

/**
 * Back end that hits the database
 * @author roy.truelove
 *
 */
@Profile(value={"dev"})
@Service
public class HawthorneDatabaseBackend implements HawthorneBackend {

	@Override
	public List<LegalEntity> getLegalEntities() {
		throw NotYetImplementedException.standardException();
	}

	@Override
	public List<TradingRequestStatus> getTradingRequestStatuses() {
		throw NotYetImplementedException.standardException();
	}

	@Override
	public List<TradingRequest> getTradingRequests() {
		throw NotYetImplementedException.standardException();
	}

	@Override
	public List<User> getUsersForLegalEntity() {
		throw NotYetImplementedException.standardException();
	}
	
	public List<User> getUsers(){
		throw NotYetImplementedException.standardException();
	}

	@Override
	public List<Product> getProducts() {
		throw NotYetImplementedException.standardException();
	}
	
	public List<Participant> getParticipants(){
		throw NotYetImplementedException.standardException();
	}
	

	public List<InterestGroup> getInterestGroups(){
		throw NotYetImplementedException.standardException();
	}
	
}



