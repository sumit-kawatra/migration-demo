package com.markitserv.hawthorne.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private List<LegalEntity> legalEntities = new ArrayList<LegalEntity>();

	private void initData() {
		populateLegalEntities(20);
	}

	private void populateLegalEntities(int count) {

		for (int i = 1; i <= count; i++) {
			legalEntities.add(createLegalEntity("" + i));
		}
	}

	private LegalEntity createLegalEntity(String id) {

		LegalEntity le = new LegalEntity();

		String firstName = nameGen.compose(3);
		String secondName = nameGen.compose(2);
		String name = firstName + " " + secondName + " LLC";

		// first 4 letters of first and second name
		String bic = StringUtils.upperCase(StringUtils.left(firstName, 4))
				+ StringUtils.upperCase(StringUtils.left(secondName, 4));

		le.setId(id);
		le.setName(name);
		le.setBic(bic);

		return le;
	}

	@Override
	public List<TradingRequestStatus> getTradingRequestStatuses() {

		ArrayList<TradingRequestStatus> statuses = new ArrayList<TradingRequestStatus>();
		statuses.add(new TradingRequestStatus(1, "Cancelled"));
		statuses.add(new TradingRequestStatus(1, "Live"));
		statuses.add(new TradingRequestStatus(1, "No Relationship"));
		statuses.add(new TradingRequestStatus(1, "On Hold"));
		statuses.add(new TradingRequestStatus(1, "Else"));

		return statuses;
	}

	public List<LegalEntity> getLegalEntities() {
		return legalEntities;
	}

	public void setLegalEntities(List<LegalEntity> legalEntities) {
		this.legalEntities = legalEntities;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initData();
	}

}
