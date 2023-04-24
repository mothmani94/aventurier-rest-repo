package com.aventurier.app.backend.business.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;

/***
 * 
 * @author motmani
 *
 */
@ExtendWith(MockitoExtension.class)
public class AventurierGameFacadeServiceTest {
	
	@InjectMocks
	AventurierGameFacadeService aventurierGameFacadeService;
	
	@Mock
	ReadFileService readFileService;
	
	@Mock
	PositionResolverService positionResolverService;
	
	@Test
	public void testReadFileAsString() {
		// act
		aventurierGameFacadeService.readFileAsString();
		
		// verify
		verify(readFileService, times(1)).readFileAsString();
	}
	
	@Test
	public void resolvePosition() {
	
		// act
		Map map = new Map();
		Point point = new Point(0, 0);
		String str = "";
		aventurierGameFacadeService.resolvePosition(map, point, str);
		
		// verify
		verify(positionResolverService, times(1)).resolveNextPos(Mockito.any(Map.class), Mockito.any(Point.class), Mockito.anyString());
	}


}
