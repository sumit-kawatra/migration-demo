package com.markitserv.mwws.validation;

import java.util.Map;

public interface Validation {
	ValidationResponse isValid(Object target, Map<String, ? extends Object> map);
} 