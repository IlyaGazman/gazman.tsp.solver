package com.gazman.city_map.engines.streight_map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.gazman.city_map.city.City;
import com.gazman.city_map.distance.Distance;
import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.engines.map_engine.CityMap;
import com.gazman.city_map.engines.map_engine.MapEngine;
import com.gazman.city_map.engines.simple.SimpleEngine;
import com.gazman.city_map.log.FileLog;
import com.gazman.city_map.path.IntersactionPath;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;
import com.gazman.city_map.test.statistics.ActionsCounter;

public class StreightMapEngine implements IEngine {

	private ArrayList<City> cities;
	private City finalDestination;
	private PathList pathList;
	private IntersactionPath intersactionPath;
	private int minemumMapSize = 4;
	private int maximumMapSize = 5;
	private CityMap cityMap;
	private MapEngine mapEngine;
	private double[] distanceMap;

	@Override
	public void init() {
		cities = null;
		finalDestination = null;
		pathList = new PathList();
		intersactionPath = new IntersactionPath();
		cityMap = new CityMap();
		mapEngine = new MapEngine();
		distanceMap = null;
	}

	@Override
	public PathList findBestPathList(ArrayList<City> cities,
			City finalDestination) {

		cities.add(finalDestination);
		distanceMap = Distance.createDistanceMap(cities);
		cities.remove(cities.size() - 1);
		this.cities = cities;
		this.finalDestination = finalDestination;
		intersactionPath.addCity(cities.get(0));
		search();

		return pathList;
	}
	
	private int minCitiesSize = Integer.MAX_VALUE;

	private void search() {
		
		if(cities.size() < minCitiesSize){
			FileLog.instance.log("got cities " + cities.size());
			minCitiesSize = cities.size();
		}
		
		ActionsCounter.instance.count("Straight work");
		if (pathList.size() != 0) {
			if (pathList.get(0).getDistance() < intersactionPath.getDistance()
					+ distanceMap[cities.size()]) {
//				) {
				ActionsCounter.instance.count("To long");
				return;
			}
		}

		if (cities.size() == 1) {
			ActionsCounter.instance.count("Bingo");
			Path path = intersactionPath.clone();
			path.addCity(finalDestination);
			if (pathList.addPath(path)) {
				FileLog.instance.log(pathList);
			}
			return;
		}
		
		ArrayList<CityData> cityDatas = new ArrayList<>();

		for (int i = 1; i < cities.size(); i++) {

			City city = cities.get(i);

			if (intersactionPath.isIntersacts(city)) {
				continue;
			}

			if (intersactionPath.size() > minemumMapSize) {
				if (!validateWithMap(city)) {
					continue;
				}
			}

//			CityData cityData = new CityData();
//			cityData.city = city;
//			cityData.index = i;
//			cityData.distanceToCity = intersactionPath.getDistance(city);
//			cityDatas.add(cityData);

			 cities.remove(i);
			 intersactionPath.addCity(city);
			 search();
			 intersactionPath.removeLastCity();
			 cities.add(i, city);
		}

		Collections.sort(cityDatas, comparator);

//		for (CityData cityData : cityDatas) {
//			cities.remove(cityData.index);
//			intersactionPath.addCity(cityData.city);
//			search();
//			intersactionPath.removeLastCity();
//			cities.add(cityData.index, cityData.city);
//		}

	}

	private Comparator<CityData> comparator = new Comparator<StreightMapEngine.CityData>() {
		@Override
		public int compare(CityData o1, CityData o2) {
			return Double.compare(o1.distanceToCity, o2.distanceToCity);
		}
	};
	private SimpleEngine simpleEngine = new SimpleEngine();;

	private class CityData {
		double distanceToCity;
		City city;
		int index;
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

//		PathList mapPathList = simpleEngine.findBestPathList(mapCities, city);
		PathList mapPathList = cityMap.get(mapCities, city);
		if (mapPathList == null) {
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
	protected StreightMapEngine clone() {
		StreightMapEngine streightMapEngine = new StreightMapEngine();
		streightMapEngine.cityMap = cityMap;
		return streightMapEngine;
	}

}
