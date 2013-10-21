package com.gazman.city_map.engines.order_engine;

import java.util.ArrayList;
import java.util.HashMap;

import com.gazman.city_map.city.City;
import com.gazman.city_map.engines.base_engine.BaseEngine;
import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.engines.small_engine.SmallEngine;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;

public class OrderEngine implements IEngine {

	private static final Object OBJECT = new Object();
	private HashMap<String, Object> pathListHash;
	private int startingBuffer;
	private BaseEngine engine;
	private boolean hasFinalDestination;

	public OrderEngine(){
		this(3);
	}

	public OrderEngine(int startingBuffer){
		this.startingBuffer = startingBuffer;
	}

	@Override
	public void init(){
		pathListHash = new HashMap<String, Object>();
		engine = new SmallEngine();
	}

	@Override
	public PathList findBestPathList(ArrayList<City> sourceCities, City finalDestination){
		hasFinalDestination = finalDestination != null;
		ArrayList<City> buffer = createBuffer(sourceCities);

		PathList bestPathList = new SmallEngine().findBestPathList(buffer, finalDestination);
		for (int i = startingBuffer; i < sourceCities.size(); i++) {
			PathList bestPathListTmp = null;
			for (int j = 0; j < bestPathList.size(); j++) {
				ArrayList<City> cities = extractCities(bestPathList, j);
				City extraCity = sourceCities.get(i);
				for (int k = 1; k < cities.size(); k++) {
					extraCity = swapCities(cities, extraCity, k);

					PathList extendedPathList = findExtendedPath(cities, extraCity, finalDestination);
					if (bestPathListTmp == null) {
						bestPathListTmp = extendedPathList;
					}
					else {
						bestPathListTmp.margePathes(extendedPathList);
					}
					ArrayList<City> cities2 = extractCities(bestPathListTmp, 0);

					PathList betterPathList = findBetterPathList(cities2, finalDestination);
					if (betterPathList != null) {
						bestPathListTmp.margePathes(betterPathList);
					}
					extraCity = swapCities(cities, extraCity, k);
				}
			}
			bestPathList = bestPathListTmp;
		}

		return bestPathList;
	}

	private ArrayList<City> extractCities(PathList bestPathListTmp, int index){
		@SuppressWarnings("unchecked")
		ArrayList<City> cities = (ArrayList<City>) bestPathListTmp.get(index).getCities().clone();
		if (hasFinalDestination) {
			cities.remove(cities.size() - 1);
		}
		return cities;
	}

	private PathList findPathList(ArrayList<City> cities, City finalDestination){
		ArrayList<City> buffer = createBuffer(cities);

		PathList bestPathList = engine.clone().findBestPathList(buffer, finalDestination);
		if (bestPathList == null) {
			return null;
		}
		for (int i = startingBuffer; i < cities.size(); i++) {
			PathList bestPathListTmp = new PathList();
			for (int j = 0; j < bestPathList.size(); j++) {
				ArrayList<City> cities2 = extractCities(bestPathList, j);
				PathList extendedPathList = findExtendedPath(cities2, cities.get(i), finalDestination);
				for (int k = 0; k < extendedPathList.size(); k++) {
					bestPathListTmp.addPath(extendedPathList.get(k));
				}
			}
			bestPathList = bestPathListTmp;
		}

		return bestPathList;
	}

	public PathList findBetterPathList(ArrayList<City> cities, City finalDestination){
		PathList pathList = new PathList();
		PathList bestPathList = pathList;
		Object oldValue = null;
		while (oldValue == null && pathList != null) {
			if (pathList.size() > 0) {
				cities = extractCities(pathList, 0);
			}
			pathList = findPathList(cities, finalDestination);
			if (pathList == null) {
				return null;
			}

			bestPathList.margePathes(pathList);
			for (int i = 0; i < pathList.size(); i++) {
				String citiesString = pathList.get(i).getCities().toString();
				oldValue = pathListHash.put(citiesString, OBJECT);
			}
		}

		return bestPathList;
	}

	private City swapCities(ArrayList<City> cities, City extraCity, int index){
		City tmpCity = cities.get(index);
		cities.set(index, extraCity);
		extraCity = tmpCity;
		return extraCity;
	}

	private ArrayList<City> createBuffer(ArrayList<City> cities){
		ArrayList<City> buffer = new ArrayList<City>();

		for (int i = 0; i < startingBuffer; i++) {
			buffer.add(cities.get(i));
		}
		return buffer;
	}

	private PathList findExtendedPath(ArrayList<City> cities, City extraCity, City finalDestination){
		PathList bestPathList = new PathList();
		for (int i = cities.size(); i >= 1; i--) {
			Path startingPath = createStartingPath(cities, i);
			if (getRemindCitiesCount(cities, i) <= 1) {
				startingPath.addCity(extraCity);
				for (int j = i; j < cities.size(); j++) {
					startingPath.addCity(cities.get(j));
				}
				if(finalDestination != null){
					startingPath.addCity(finalDestination);
				}
				bestPathList.addPath(startingPath);
			}
			else {
				ArrayList<City> remindCities = getRemindCities(cities, extraCity, i);

				PathList bestPathListFound = engine.clone().findBestPathList(remindCities, finalDestination);
				if (bestPathListFound != null) {
					addStartingPathToPathList(startingPath, bestPathListFound);
					bestPathList.margePathes(bestPathListFound);
				}
			}
		}
		return bestPathList;
	}

	private int getRemindCitiesCount(ArrayList<City> cities, int startingIndex){
		return cities.size() - startingIndex;
	}

	private ArrayList<City> getRemindCities(ArrayList<City> cities, City firstCity, int startingIndex){
		ArrayList<City> remindCities = new ArrayList<City>();
		remindCities.add(firstCity);
		for (int i = startingIndex; i < cities.size(); i++) {
			remindCities.add(cities.get(i));
		}
		return remindCities;
	}

	private void addStartingPathToPathList(Path startingPath, PathList bestPathListFound){
		for (int i = startingPath.getCities().size(); i > 0; i--) {
			bestPathListFound.addCityToFront(startingPath.getCities().get(i - 1));
		}
	}

	private Path createStartingPath(ArrayList<City> cities, int count){
		Path path = new Path();
		for (int i = 0; i < count; i++) {
			path.addCity(cities.get(i));
		}
		return path;
	}
}
