package com.markitserv.hawthorne;

import java.util.List;

import com.markitserv.hawthorne.types.InterestGroup;
import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.Product;
import com.markitserv.hawthorne.types.TradingRequest;
import com.markitserv.hawthorne.types.TradingRequestStatus;
import com.markitserv.hawthorne.types.User;

public interface HawthorneBackend {
	
	List<LegalEntity> getLegalEntities();
	List<TradingRequestStatus> getTradingRequestStatuses();
	List<TradingRequest> getTradingRequests();
	List<User> getUsersForLegalEntity();
	List<User> getUsers();
	List<Participant> getParticipants();
	List<InterestGroup> getInterestGroups();
	List<Product> getProducts();

}
