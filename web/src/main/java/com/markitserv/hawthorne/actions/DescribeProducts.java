package com.markitserv.hawthorne.actions;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.Product;
import com.markitserv.hawthorne.types.User;
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
import com.markitserv.msws.filters.PropertyEqualsReflectionFilter;
import com.markitserv.msws.filters.SubstringReflectionFilter;
import com.markitserv.msws.validation.CollectionSizeValidation;
import com.markitserv.msws.validation.RequiredValidation;

/**
 * Describe Products
 * 
 * @author swati.choudhari
 * 
 */
@Service
public class DescribeProducts extends AbstractPaginatedAction {

	private static final String FILTER_NAME_SUBSTR_PRODUCT_NAME = "substr";
	private static final String PARAMETER_NAME_USERNAME = "UserName";
	private static final String PPODUCT_NAME = "productName";
	private static final String USER_NAME = "userName";

	Logger log = LoggerFactory.getLogger(DescribeProducts.class);

	@Autowired
	private HawthorneBackend data;

	@Override
	protected ParamsAndFiltersDefinition createParameterDefinition() {

		// Add validation
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		// UserName
		def.addValidation(PARAMETER_NAME_USERNAME, new RequiredValidation());

		// Sorting
		SortingPresetDefinitionBuilder sortBuilder = new SortingPresetDefinitionBuilder();
		sortBuilder = sortBuilder.setDefaultSort(PPODUCT_NAME, SortOrder.Asc);

		def.mergeWith(sortBuilder.build());

		return def;
	}

	@Override
	protected ParamsAndFiltersDefinition createFilterDefinition() {
		ParamsAndFiltersDefinition def = new ParamsAndFiltersDefinition();

		def.addValidation(FILTER_NAME_SUBSTR_PRODUCT_NAME, new CollectionSizeValidation(
				CollectionSizeValidation.UNLIMITED, 1));

		return def;
	}

	private List<Product> applyFilters(ActionParameters p, ActionFilters f,
			List<User> users) {
		List<Product> productList = new ArrayList<Product>();
		List<User> UserList = new ArrayList<User>();
		User filterdUser = new User();

		if (p.isParameterSet(PARAMETER_NAME_USERNAME)) {
			String userNeme = (String) p.getParameter(PARAMETER_NAME_USERNAME, String.class);
			UserList = PropertyEqualsReflectionFilter.filter(users, USER_NAME, userNeme);

		}
		filterdUser = UserList.get(0);
		productList = new ArrayList(filterdUser.getProducts());
		if (f.isFilterSet(FILTER_NAME_SUBSTR_PRODUCT_NAME)) {
			productList = SubstringReflectionFilter.filter(productList, PPODUCT_NAME,
					f.getSingleFilter(FILTER_NAME_SUBSTR_PRODUCT_NAME));
		}

		int pageStartIndex = p.getParameter(CommonParamKeys.PageStartIndex.toString(),
				Integer.class);
		int pageSize = p.getParameter(CommonParamKeys.PageSize.toString(), Integer.class);
		PaginationFilter.filter(productList, pageStartIndex, pageSize);

		return productList;
	}

	@Override
	protected ActionResult performAction(ActionParameters params, ActionFilters filters) {
		List<User> userList = data.getAllUsers();
		List<Product> totalProductList = data.getProducts();
		int totalsize = totalProductList.size();

		List<Product> productList = applyFilters(params, filters, userList);

		PaginatedActionResult res = new PaginatedActionResult(productList);
		res.getPaginatedMetaData().setTotalRecords(totalsize);
		return res;
	}

}
