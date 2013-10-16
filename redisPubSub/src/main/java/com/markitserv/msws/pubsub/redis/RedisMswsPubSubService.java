package com.markitserv.msws.pubsub.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.markitserv.msws.pubsub.MswsEvent;
import com.markitserv.msws.pubsub.MswsPubSubService;

@Service
public class RedisMswsPubSubService implements MswsPubSubService {

	@Override
	public void send(MswsEvent event) {
		// TODO Auto-generated method stub
		
	}
} 