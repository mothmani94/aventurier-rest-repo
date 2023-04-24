package com.aventurier.app.backend.business.service;


import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;

/***
 * 
 * @author motmani
 *
 */
@ExtendWith(MockitoExtension.class)
public class PositionResolverServiceTest {

	@InjectMocks
	PositionResolverService positionResolverService;
	
	@Test
	public void testResolveNextPos() {
		// act
		Map map = new Map();
		map.getAllMapPoints().add(new Point(9,2, true));
		Point startingPos = new Point( 3,0);
		String pathToFollow = "S-S-S-S-E-E-E-E-E-E-N-N";
		
		Optional<Point> posResult = positionResolverService.resolveNextPos(map, startingPos, pathToFollow);
		
		// verify
		Assertions.assertNotNull(posResult.get());
		Assertions.assertEquals(posResult.get().getX(), 9);
		Assertions.assertEquals(posResult.get().getY(), 2);
	}
}
