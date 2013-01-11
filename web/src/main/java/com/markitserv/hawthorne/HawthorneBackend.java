package com.markitserv.hawthorne;

import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;

import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.TradingRequestStatus;

public interface HawthorneBackend {
	
	List<LegalEntity> getLegalEntities();
	List<TradingRequestStatus> getTradingRequestStatuses();
}
