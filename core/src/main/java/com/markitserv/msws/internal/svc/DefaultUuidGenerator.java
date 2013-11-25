package com.markitserv.msws.internal.svc;

import java.util.UUID;

import com.markitserv.msws.svc.UuidGenerator;

/**
 * This is abstracted out because Uuid generation is considered to be slow. If
 * this becomes a bottleneck we can perhaps create a queue of them to have them
 * ready before they're needed.
 * 
 * @author roy.truelove
 * 
 */
public class DefaultUuidGenerator implements UuidGenerator {

	public String generateUuid() {
		return UUID.randomUUID().toString();
	}
} 