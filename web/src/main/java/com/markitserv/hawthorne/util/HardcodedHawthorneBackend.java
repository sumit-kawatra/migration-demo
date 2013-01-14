package com.markitserv.hawthorne.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.HawthorneBackend;
import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.TradingRequestStatus;

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

	@Autowired
	private RandomNameGenerator nameGen;
	private List<LegalEntity> legalEntities; 
	private Stack<TradingRequestStatus> tradingRequestStatuses;

	private void initData() {
		populateLegalEntities(100000);
		populateTradingRequestStatuses();
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

	private LegalEntity createLegalEntity(int id) {

		LegalEntity le = new LegalEntity();

		String firstName = nameGen.compose(3);
		String secondName = nameGen.compose(2);
		String thirdName = nameGen.compose(4);
		String name = firstName + " " + secondName + " " + thirdName + " LLC";

		// first 4 letters of first and second name
		String bic = StringUtils.upperCase(StringUtils.left(firstName, 4))
				+ StringUtils.upperCase(StringUtils.left(secondName, 4));

		le.setId(id);
		le.setName(name);
		le.setBic(bic);

		return le;
	}

	public List<LegalEntity> getLegalEntities() {
		return legalEntities;
	}
	
	public List<TradingRequestStatus> getTradingRequestStatuses() {
		return this.tradingRequestStatuses;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initData();
	}
}
