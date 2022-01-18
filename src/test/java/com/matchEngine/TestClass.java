package com.matchEngine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import fr.eql.matchingEngine.MatchingEngineApplication;
import fr.eql.matchingEngine.dto.constant.TradingPair;
import fr.eql.matchingEngine.dto.entity.Ordre;
import fr.eql.matchingEngine.services.servicesinterface.MatchingService;

/*
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")

 */
@ContextConfiguration(classes = MatchingEngineApplication.class)
@SpringBootTest
class MatchingServiceTest {
	
	

	@Test
	public void test() {
		
		assertEquals(1+1, 2);
	}

}
