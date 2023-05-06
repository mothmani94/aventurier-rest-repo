package com.aventurier.app.backend.business.model;

import lombok.Data;

/***
 * 
 * @author motmani
 *
 */
@Data
public class Point {

	private int index;
	private int x;
	private int y;
	private boolean isCrossable;
	

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(int x, int y, boolean isCrossable) {
		this.x = x;
		this.y = y;
		this.isCrossable = isCrossable;
	}
}
