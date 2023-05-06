package com.aventurier.app.backend.business.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

/***
 * 
 * @author motmani
 *
 */
@Data
public class Map {
	

	private int numberOfElementsInMap;
	private List<Point> allMapPoints = new ArrayList<>();
	private List<String> lines = new ArrayList<>();
	
	public List<Point> getCrossablePoints() {
		return this.allMapPoints.stream().filter(point -> point.isCrossable())			
        .sorted(Comparator.comparingInt(Point::getIndex))
        .collect(Collectors.toList());
	}
	
	public List<Point> getUnCrossablePoints() {
		return this.allMapPoints.stream().filter(point -> !point.isCrossable()).toList();
	}

}
