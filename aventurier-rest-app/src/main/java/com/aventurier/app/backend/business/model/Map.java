package com.aventurier.app.backend.business.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/***
 * 
 * @author motmani
 *
 */
public class Map {
	

	private int numberOfElementsInMap;
	private List<Point> allMapPoints = new ArrayList<>();
	private List<String> lines = new ArrayList<>();
	

	public int getNumberOfElementsInMap() {
		return numberOfElementsInMap;
	}

	public void setNumberOfElementsInMap(int numberOfElementsInMap) {
		this.numberOfElementsInMap = numberOfElementsInMap;
	}

	public List<Point> getAllMapPoints() {
		return allMapPoints;
	}

	public void setAllMapPoints(List<Point> allMapPoints) {
		this.allMapPoints = allMapPoints;
	}
	
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	
	public List<Point> getCrossablePoints() {
		return this.allMapPoints.stream().filter(point -> point.isCrossable())			
        .sorted(Comparator.comparingInt(Point::getIndex))
        .collect(Collectors.toList());
		//return this.allMapPoints.stream().filter(point -> point.isCrossable()).toList();
	}
	
	public List<Point> getUnCrossablePoints() {
		return this.allMapPoints.stream().filter(point -> !point.isCrossable()).toList();
	}

}
