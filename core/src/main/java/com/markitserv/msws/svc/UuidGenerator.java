package com.markitserv.msws.svc;

import java.util.UUID;

import org.springframework.stereotype.Service;

/**
 * This is abstracted out because Uuid generation is considered to be slow. If
 * this becomes a bottleneck we can perhaps create a queue of them to have them
 * ready before they're needed.
 * 
 * @author roy.truelove
 * 
 */
@Service
public interface UuidGenerator {
	public String generateUuid();
} 