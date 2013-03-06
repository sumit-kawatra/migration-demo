package com.markitserv.hawthorne;

import java.util.List;

import com.markitserv.hawthorne.types.InterestGroup;
import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.Product;
import com.markitserv.hawthorne.types.SubGroup;
import com.markitserv.hawthorne.types.User;

public interface HawthorneBackend {

	// TODO change these all to 'Sets'!!!!!

	List<LegalEntity> getLegalEntities();

	List<User> getUsersForLegalEntity(int id);

	List<User> getUsersForParticipant(int id);

	List<User> getUser(String userName);

	List<User> getAllUsers();

	List<Participant> getParticipants();

	List<InterestGroup> getInterestGroups();

	List<Product> getProducts();

	List<SubGroup> getSubGroups(Integer participantId, String userName);

}
