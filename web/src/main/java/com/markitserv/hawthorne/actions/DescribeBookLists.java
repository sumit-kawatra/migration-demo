/**
 * 
 */
package com.markitserv.hawthorne.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.BookList;
import com.markitserv.hawthorne.types.Participant;
import com.markitserv.msws.action.AbstractPaginatedAction;
import com.markitserv.msws.action.ActionFilters;
import com.markitserv.msws.action.ActionParameters;
import com.markitserv.msws.action.ActionResult;
import com.markitserv.msws.action.CommonParamKeys;
import com.markitserv.msws.action.PaginatedActionResult;
import com.markitserv.msws.action.SortOrder;
import com.markitserv.msws.definition.ParamsAndFiltersDefinition;
import com.markitserv.msws.definition.SortingPresetDefinitionBuilder;
import com.markitserv.msws.filters.PaginationFilter;
import com.markitserv.msws.filters.SubstringReflectionFilter;
import com.markitserv.msws.validation.CollectionSizeValidation;
import com.markitserv.msws.validation.IntegerMaxMinValidationAndConversion;
import com.markitserv.msws.validation.IntegerValidationAndConversion;
import com.markitserv.msws.validation.MutuallyExclusiveWithValidation;
import com.markitserv.msws.validation.RequiredIfAllNotProvidedValidation;

/**
 * @author kiran.gogula
 * 
 */
@Service
public class DescribeBookLists extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR_BOOK_LIST_NAME = "substrBookListName";
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

		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort("Id", SortOrder.Asc);
		sortBuilder = sortBuilder.addSortOption("StartDate");

		def.mergeWith(sortBuilder.build());

		return def;
	}

	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation(FILTER_NAME_SUBSTR_BOOK_LIST_NAME, new CollectionSizeValidation(
				CollectionSizeValidation.UNLIMITED, 1));

		return def;
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

		List<BookList> bookLists = new ArrayList<BookList>(
				data.getBookListsForParticipant(participantId));

		int totalRecords = bookLists.size();

		bookLists = applyFilters(params, filters, bookLists);

		sortById(bookLists);

		PaginatedActionResult res = new PaginatedActionResult(bookLists);
		res.getPaginatedMetaData().setTotalRecords(totalRecords);

		return res;
	}

	/**
	 * NOTE right now this is hardcoded..
	 * 
	 * @param bookLists
	 */
	private void sortByName(List<BookList> bookLists) {
		Comparator<BookList> byName = new Comparator<BookList>() {
			public int compare(BookList bl1, BookList bl2) {
				return bl1.getName().compareTo(bl2.getName());
			}
		};
		Collections.sort(bookLists, byName);
	}

	/**
	 * NOTE right now this is hardcoded..
	 * 
	 * @param bookLists
	 */
	private void sortById(List<BookList> bookLists) {
		Comparator<BookList> byId = new Comparator<BookList>() {
			public int compare(BookList bl1, BookList bl2) {
				return bl1.getId() - bl2.getId();
			}
		};
		Collections.sort(bookLists, byId);
	}

	private List<BookList> applyFilters(ActionParameters p, ActionFilters f,
			List<BookList> booklists) {

		if (f.isFilterSet(FILTER_NAME_SUBSTR_BOOK_LIST_NAME)) {
			booklists = SubstringReflectionFilter.filter(booklists, "name",
					f.getSingleFilter(FILTER_NAME_SUBSTR_BOOK_LIST_NAME));
		}

		int pageStartIndex = p.getParameterAsInt(CommonParamKeys.PageStartIndex.toString());
		int pageSize = p.getParameterAsInt(CommonParamKeys.PageSize.toString());

		booklists = PaginationFilter.filter(booklists, pageStartIndex, pageSize);
		return booklists;
	}
}
