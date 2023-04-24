package com.aventurier.app.backend.business.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aventurier.app.backend.business.enums.CardinalDirectionsEnum;
import com.aventurier.app.backend.business.model.Map;
import com.aventurier.app.backend.business.model.Point;

/***
 * 
 * @author motmani
 *
 */
@Service
public class PositionResolverService {

	
	public Optional<Point> resolveNextPos(Map map, Point startingPos, String pathToFollow) {		
		System.out.println("path to follow >> "+pathToFollow);
		int x = startingPos.getX();
		int y = startingPos.getY();
		String ss[] = pathToFollow.split("-");

		for (int i =0; i < ss.length; i ++) {
		
			CardinalDirectionsEnum cardinalDirection = CardinalDirectionsEnum.getByCode(ss[i]);
			switch( cardinalDirection ) {
				case NORD:
					y--;
		            break;
		        case SUD:
		        	y++;
		            break;
		        case EST:
		        	x++;
		            break;
		        case OUEST:
		        	x--;
				default:     
					// do nothing
			}
		}
		
		final int newX = x;
		final int newY = y;
		return map.getCrossablePoints().stream().filter(elem -> (elem.getX() == newX) && (elem.getY() == newY)).findFirst();

	}
	
	
}
