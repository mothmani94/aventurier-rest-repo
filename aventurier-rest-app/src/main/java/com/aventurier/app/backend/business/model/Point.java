package com.aventurier.app.backend.business.model;

/***
 * 
 * @author motmani
 *
 */

public class Point {

	private int index;
	private int x;
	private int y;
	private boolean isCrossable = false;
	
	public Point( int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point( int x, int y, boolean isCrossable) {
		this.x = x;
		this.y = y;
		this.isCrossable = isCrossable;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isCrossable() {
		return isCrossable;
	}
	public void setCrossable(boolean isCrossable) {
		this.isCrossable = isCrossable;
	}
	
	
}
