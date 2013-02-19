/**
 * 
 */
package com.markitserv.hawthorne.actions;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.Book;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.hawthorne.types.User;
import com.markitserv.msws.action.AbstractPaginatedAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.action.PaginatedActionResult;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.filters.PaginationFilter;
import com.markitserv.msws.filters.SubstringReflectionFilter;
import com.markitserv.msws.validation.IntegerMaxMinValidation;
import com.markitserv.msws.validation.IntegerValidation;
import com.markitserv.msws.validation.MutuallyExclusiveWithValidation;
import com.markitserv.msws.validation.RequiredIfAllNotProvidedValidation;

/**
 * @author kiran.gogula
 * 
 */
@Service
public class DescribeBooks extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR_BOOK_NAME = "substrBookName";

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		// UserName
		def.addValidation("UserName", new MutuallyExclusiveWithValidation(new String[]{"ParticipantId"}));
		def.addValidation("UserName", new RequiredIfAllNotProvidedValidation(new String[]{"ParticipantId"}));
	
		// Participant ID
		def.addValidation("ParticipantId", new MutuallyExclusiveWithValidation(new String[]{"UserName"}));
		def.addValidation("ParticipantId", new RequiredIfAllNotProvidedValidation(new String[]{"UserName"}));
		def.addValidation("ParticipantId", new IntegerValidation());
		def.addValidation("ParticipantId", new IntegerMaxMinValidation(1, IntegerMaxMinValidation.UNLIMITED));
		
		return def;
	}

	private List<Book> getBooks(List<Participant> pariticipantList,
			Integer participantId, String userName) {
		if (participantId != null) {
			for (Participant participant : pariticipantList) {
				if (participant.getId() == participantId) {
					return participant.getBookList();
				}
			}
		}
		if (StringUtils.isNotBlank(userName)) {
			for (Participant participant : pariticipantList) {
				List<User> userList = participant.getAllUsers();
				for (User user : userList) {
					if (userName.contains(user.getUserName()) || 
						userName.equalsIgnoreCase(user.getUserName())) {
						return participant.getBookList();
					}
				}
			}
		}
		return null;
	}

	@Override
	protected ActionResult performAction(ActionParameters params,
			ActionFilters filters) {
		String userName = null;
		int participantId = 0; 
		List<Participant> paList = data.getParticipants();
		
		if (params.isParameterSet("ParticipantId")) {
			 participantId = Integer.parseInt((String)params.getParameter("ParticipantId"));
		}

		if (params.isParameterSet("UserName")) {
			userName = (String)params.getParameter("UserName");
		}

		List<Book> bookList = getBooks(paList, participantId, userName);

		int totalRecords = bookList.size();

		bookList = applyFilters(params, filters, bookList);

		PaginatedActionResult res = new PaginatedActionResult(bookList);
		res.getPaginatedMetaData().setTotalRecords(totalRecords);

		return res;
	}
	
	private List<Book> applyFilters(ActionParameters p, ActionFilters f,
			List<Book> books) {

	
		
		if (f.isFilterSet(FILTER_NAME_SUBSTR_BOOK_NAME)) {
			books  = SubstringReflectionFilter.filter(
					books, "name",
					f.getSingleFilter(FILTER_NAME_SUBSTR_BOOK_NAME));
		}

		int pageNumber = p.getParameterAsInt(CommonParamKeys.PageNumber
				.toString());
		int pageSize = p.getParameterAsInt(CommonParamKeys.PageSize.toString());

		books = PaginationFilter.filter(books,
				pageNumber, pageSize);
		return books;
	}

}
