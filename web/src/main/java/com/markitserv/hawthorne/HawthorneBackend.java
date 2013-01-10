package com.markitserv.hawthorne;

import java.util.Set;

import com.markitserv.hawthorne.types.LegalEntity;
import com.markitserv.hawthorne.types.TradingRequestStatus;

public interface HawthorneBackend {
	Set<LegalEntity> getLegalEntities();
	Set<TradingRequestStatus> getTradingRequestStatuses();
}
