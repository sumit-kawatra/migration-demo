package com.markitserv.hawthorne.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.markitserv.hawthorne.types.LegalEntity;

/**
 * Hardcodes data that will eventually come from the server. This class will not
 * live here forever, and should be used only for testing and development.
 * 
 * @author roy.truelove
 * 
 */
@Service
public class HardcodedData implements InitializingBean {

	@Autowired
	private RandomNameGenerator nameGen;
	private Set<LegalEntity> legalEntities = new HashSet<LegalEntity>();

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
	
	public Set<LegalEntity> getLegalEntities() {
		return legalEntities;
	}

	public void setLegalEntities(Set<LegalEntity> legalEntities) {
		this.legalEntities = legalEntities;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		initData();
	}
}
