package com.aventurier.app.backend.business.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aventurier.app.backend.business.model.Map;

/***
 * 
 * @author motmani
 *
 */
@ExtendWith(MockitoExtension.class)
public class ReadFileServiceTest {

	@InjectMocks
	ReadFileService readFileService;
	
	@Test
	public void testReadFileAsString() {
	
		// act
		Map map = readFileService.readFileAsString();
		
		// verify
		Assertions.assertNotNull(map);
		Assertions.assertEquals(400, map.getAllMapPoints().size());
		Assertions.assertEquals(187, map.getUnCrossablePoints().size());
		Assertions.assertEquals(213, map.getCrossablePoints().size());
		Assertions.assertEquals(20, map.getLines().size());

	}
	
}
