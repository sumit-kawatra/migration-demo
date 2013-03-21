package com.markitserv.hawthorne.util;

/**
 * Defines the URL params and filters
 * 
 * @author lavanya.sakala
 * 
 */
public interface HawthorneParamsAndFilters {
	// Params
	static final String PARAM_PARTICIPANT_ID = "ParticipantId";
	static final String PARAM_USER_ID = "UserId";
	static final String PARAM_INTERESTGROUP_ID = "InterestGroupId";
	static final String PARAM_LEGAL_ENTITY_ID = "LegalEntityId";
	static final String PARAM_USER_NAME = "UserName";

	// Filters
	static final String FILTER_SUBSTR_INTERESTGROUP_NAME = "substrName";
	static final String FILTER_SUBSTR_INTERESTGROUP_SHORT_NAME = "substrShortName";
	static final String FILTER_SUBSTR_LAST_NAME = "substrLastName";
	static final String FILTER_SUBSTR_USER_NAME = "substrUserName";
	static final String FILTER_SUBSTR_FIRST_NAME = "substrFirstName";
	static final String FILTER_ACTIVE = "active";
}
