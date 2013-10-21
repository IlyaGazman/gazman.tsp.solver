package com.gazman.city_map.engines.streight_map;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.engines.map_engine.CityMap;
import com.gazman.city_map.engines.map_engine.MapEngine;
import com.gazman.city_map.engines.simple.SimpleEngine;
import com.gazman.city_map.log.FileLog;
import com.gazman.city_map.path.IntersactionPath;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;
import com.gazman.city_map.test.statistics.ActionsCounter;

public class CopyOfStreightMapEngine implements IEngine {

	private ArrayList<City> cities;
	private City finalDestination;
	private PathList pathList = new PathList();
	private IntersactionPath intersactionPath = new IntersactionPath();
	private int minemumMapSize = 4;
	private int maximumMapSize = 5;
	private CityMap cityMap;
	private static SimpleEngine simpleEngine = new SimpleEngine();
	private MapEngine mapEngine = new MapEngine();
	
	private boolean reset = false;

	@Override
	public void init() {
		cityMap = new CityMap();
	}

	@Override
	public PathList findBestPathList(ArrayList<City> cities,
			City finalDestination) {

		if(cities.size() <= 4){
			return simpleEngine.findBestPathList(cities, finalDestination);
		}
		
		this.cities = cities;
		this.finalDestination = finalDestination;
		do{
			intersactionPath.addCity(cities.get(0));
			reset = false;
			search();
			intersactionPath = new IntersactionPath();
		}while(reset);

		return pathList;
	}

	
	private void search() {
		
		if(pathList.size() != 0){
			if(pathList.get(0).getDistance() < intersactionPath.getDistance()){
				return;
			}
		}
		
		if (cities.size() == 1) {
			ActionsCounter.instance.count("Streight");
			Path path = intersactionPath.clone();
			path.addCity(finalDestination);
			if (pathList.addPath(path)){
				FileLog.instance.log(pathList);
				reset = true;
				cities = (ArrayList<City>) pathList.get(0).getCities().clone();
				cities.remove(cities.size() - 1);
			}
			return;
		}

		for (int i = 1; i < cities.size(); i++) {
			
			City city = cities.get(i);
			
			if(intersactionPath.isIntersacts(city)){
				return;
			}
			
			if (intersactionPath.size() > minemumMapSize) {
				if (!validateWithMap(city)) {
					continue;
				}
			}
			
			cities.remove(i);
			intersactionPath.addCity(city);
			search();
			if(reset){
				return;
			}
			intersactionPath.removeLastCity();
			cities.add(i, city);
		}
	}

	private boolean validateWithMap(City city) {
		ArrayList<City> mapCities;
		if (intersactionPath.size() > maximumMapSize) {
			mapCities = new ArrayList<>();
			for (int i = 0; i < maximumMapSize; i++) {
				int index = intersactionPath.size() - maximumMapSize + i;
				mapCities.add(intersactionPath.get(index));
			}
		}
		else {
			mapCities = intersactionPath.getCities();
		}

		PathList mapPathList = cityMap.get(mapCities, city);
		if (mapPathList == null) {
//			StreightMapEngine engine = clone();
//			mapPathList = engine.findBestPathList(mapCities, city);
//			cityMap.map(mapCities, mapPathList, city);
//			MapEngine mapEngine = new MapEngine();
			mapEngine.init();
			mapEngine.cityMap = cityMap;
			mapPathList = mapEngine.findBestPathList(mapCities, city);
		}
		for (int k = 0; k < mapPathList.size(); k++) {
			Path path = mapPathList.get(k);
			boolean foundMistake = false;
			for (int j = 0; j < path.size() - 1; j++) {
				if (path.get(j) != mapCities.get(j)) {
					foundMistake = true;
					break;
				}
			}
			if (!foundMistake) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected CopyOfStreightMapEngine clone() {
		CopyOfStreightMapEngine streightMapEngine = new CopyOfStreightMapEngine();
		streightMapEngine.cityMap = cityMap;
		return streightMapEngine;
	}

}
