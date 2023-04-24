package com.aventurier.app.backend.business.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;

/***
 * 
 * @author motmani
 *
 */
@Service
public class AventurierGameFacadeService {
	
	@Autowired
	ReadFileService readFileService;
	
	@Autowired
	PositionResolverService positionResolverService;
	
	 public Map readFileAsString() {		
		 return readFileService.readFileAsString();
	 }

	public Optional<Point> resolvePosition(Map map, Point selectedPosition, String pathToFollow) {
		return positionResolverService.resolveNextPos(map, selectedPosition, pathToFollow);
	}

}
