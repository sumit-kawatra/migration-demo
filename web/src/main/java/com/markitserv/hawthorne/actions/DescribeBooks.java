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
import com.markitserv.msws.validation.IntegerMaxMinValidationAndConversion;
import com.markitserv.msws.validation.IntegerValidationAndConversion;
import com.markitserv.msws.validation.MutuallyExclusiveWithValidation;
import com.markitserv.msws.validation.RequiredIfAllNotProvidedValidation;

/**
 * @author kiran.gogula
 * 
 */
@Service
public class DescribeBooks extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR_BOOK_NAME = "substrBookName";
	private static final String PARAM_NAME_USER_NAME = "UserName";
	private static final String PARAM_PARTICIPANT_ID = "ParticipantId";

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		// UserName
		def.addValidation(PARAM_NAME_USER_NAME, new MutuallyExclusiveWithValidation(
				new String[] {
					PARAM_PARTICIPANT_ID
				}));
		def.addValidation(PARAM_NAME_USER_NAME, new RequiredIfAllNotProvidedValidation(
				new String[] {
					PARAM_PARTICIPANT_ID
				}));

		// Participant ID
		def.addValidation(PARAM_PARTICIPANT_ID, new MutuallyExclusiveWithValidation(
				new String[] {
					PARAM_NAME_USER_NAME
				}));
		def.addValidation(PARAM_PARTICIPANT_ID, new RequiredIfAllNotProvidedValidation(
				new String[] {
					PARAM_NAME_USER_NAME
				}));

		def.addValidationAndConversion(PARAM_PARTICIPANT_ID,
				new IntegerValidationAndConversion());
		def.addValidationAndConversion(PARAM_PARTICIPANT_ID,
				new IntegerMaxMinValidationAndConversion(1,
						IntegerMaxMinValidationAndConversion.UNLIMITED));

		return def;
	}

	private List<Book> getBooks(List<Participant> pariticipantList, Integer participantId,
			String userName) {
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
					if (userName.contains(user.getUserName())
							|| userName.equalsIgnoreCase(user.getUserName())) {
						return participant.getBookList();
					}
				}
			}
		}
		return null;
	}

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {
		String userName = null;
		int participantId = 0;
		List<Participant> paList = data.getParticipants();

		if (params.isParameterSet(PARAM_PARTICIPANT_ID)) {
			participantId = params.getParameter(PARAM_PARTICIPANT_ID, Integer.class);
		}

		if (params.isParameterSet(PARAM_NAME_USER_NAME)) {
			userName = (String) params.getParameter(PARAM_NAME_USER_NAME, String.class);
		}

		List<Book> bookList = getBooks(paList, participantId, userName);

		int totalRecords = bookList.size();

		bookList = applyFilters(params, filters, bookList);

		PaginatedActionResult res = new PaginatedActionResult(bookList);
		res.getPaginatedMetaData().setTotalRecords(totalRecords);

		return res;
	}

	private List<Book> applyFilters(ActionParameters p, ActionFilters f, List<Book> books) {

		if (f.isFilterSet(FILTER_NAME_SUBSTR_BOOK_NAME)) {
			books = SubstringReflectionFilter.filter(books, "name",
					f.getSingleFilter(FILTER_NAME_SUBSTR_BOOK_NAME));
		}

		int pageNumber = p.getParameter(CommonParamKeys.PageNumber.toString(),
				Integer.class);
		int pageSize = p.getParameter(CommonParamKeys.PageSize.toString(), Integer.class);

		books = PaginationFilter.filter(books, pageNumber, pageSize);
		return books;
	}
}
