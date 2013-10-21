package com.gazman.city_map.engines.small_engine;


public class ShortestDistance {
	private double value = Integer.MAX_VALUE;

	public double getValue(){
		return value;
	}
	
	public void setValue(double value){
		this.value = value;
	}
	
	public void reset(){
		value = Integer.MAX_VALUE;
	}
}
