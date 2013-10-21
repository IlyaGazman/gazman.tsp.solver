package com.gazman.city_map.engines.small_engine;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.engines.map_engine.MapEngine;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;

public class SmallEngine extends MapEngine {

	private ShortestDistance shortestDistance;

	private Path controllPath;

	@Override
	public PathList findBestPathList(ArrayList<City> cities, City finalDestination){
		PathList bestPath;
		if (getControllPath().getDistance() > getShortestDistance().getValue()) {
			return null;
		}
		if (cities.size() > 3) {
			getControllPath().addCity(cities.get(0));
			bestPath = super.findBestPathList(cities, finalDestination);
			getControllPath().removeLastCity();
		}
		else {
			bestPath = super.findBestPathList(cities, finalDestination);
			for (int i = 0; i < bestPath.size(); i++) {
				getControllPath().addCity(bestPath.get(i).get(0));
				getControllPath().addCity(bestPath.get(i).get(1));
				getControllPath().addCity(bestPath.get(i).get(2));
				if(finalDestination != null){
					getControllPath().addCity(finalDestination);
				}
				if (getControllPath().getDistance() <= getShortestDistance().getValue()) {
					shortestDistance.setValue(getControllPath().getDistance());
				}
				getControllPath().removeLastCity();
				getControllPath().removeLastCity();
				getControllPath().removeLastCity();
				if(finalDestination != null){
					getControllPath().removeLastCity();
				}
			}
		}

		return bestPath;
	}
	
	
	@Override
	public SmallEngine clone(){
		SmallEngine clone = (SmallEngine) super.clone();
		clone.controllPath = getControllPath();
		clone.shortestDistance = getShortestDistance();
		return clone;
	}


	private Path getControllPath(){
		if(controllPath == null){
			controllPath = new Path();
		}
		return controllPath;
	}

	private ShortestDistance getShortestDistance(){
		if(shortestDistance == null){
			shortestDistance = new ShortestDistance();
		}
		return shortestDistance;
	}
	
	public void setShortestDistance(double shortestDistance){
		getShortestDistance().setValue(shortestDistance);
	}
	
	@Override
	public void init(){
		super.init();
		getShortestDistance().reset();
	}
}
