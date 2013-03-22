package com.markitserv.hawthorne;

import java.util.List;
import java.util.Set;

import com.markitserv.hawthorne.types.Book;
import com.markitserv.hawthorne.types.BookList;
import com.markitserv.hawthorne.types.InterestGroup;
import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.Product;
import com.markitserv.hawthorne.types.SubGroup;
import com.markitserv.hawthorne.types.User;

public interface HawthorneBackend {

	// TODO change these all to 'Sets'!!!!!

	List<LegalEntity> getLegalEntities();

	Set<User> getUsersForLegalEntity(int id);

	Set<User> getUsersForParticipant(int id);

	Set<User> getUser(String userName);

	List<User> getAllUsers();

	List<Participant> getParticipants();

	Set<Product> getProducts();

	List<SubGroup> getSubGroups(Integer participantId, String userName);

	Set<InterestGroup> retrieveInterestGrpsForUser(int id);

	Set<InterestGroup> getInterestGroupsForParticipant(int participantId);

	Set<Book> getBooksForParticipant(int participantId);

	Set<BookList> getBookListsForParticipant(int participantId);

	Participant getParticipant(int participantId);

	Set<User> getUsersForInterestGrp(int interestGroupId);

}
