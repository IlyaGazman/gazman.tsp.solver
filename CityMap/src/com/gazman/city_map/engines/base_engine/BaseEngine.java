package com.gazman.city_map.engines.base_engine;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;
import com.gazman.city_map.test.statistics.ActionsCounter;
import com.gazman.city_map.utils.ClassConstractor;

/**
 * @author Ilya Gazman
 *
 */
public class BaseEngine implements IEngine {

	private PathList finalPathList = new PathList();
	private City firstCity;
	private ArrayList<City> cities;
	private City finalDestination;

	public BaseEngine(){
		ActionsCounter.instance.count(getClass().getSimpleName());
	}

	@Override
	public BaseEngine clone(){
		BaseEngine baseEngine = ClassConstractor.crateNewItem(getClass());
		return baseEngine;
	}

	@Override
	public PathList findBestPathList(ArrayList<City> cities, City finalDestination){
		this.cities = cities;
		this.finalDestination = finalDestination;

		firstCity = cities.remove(0);

		for (int i = 0; i < cities.size(); i++) {
			swapCities(0, i);

			if (cities.size() == 2) {
				Path path = createSimplePath();
				finalPathList.addPath(path);
			}
			else {
				BaseEngine baseEngine = clone();
				PathList pathList = baseEngine.findBestPathList(cities, finalDestination);
				if (pathList != null) {
					pathList.addCityToFront(firstCity);
					finalPathList.margePathes(pathList);
				}
			}
			swapCities(0, i);
		}

		cities.add(0, firstCity);

		return finalPathList.size() > 0 ? finalPathList : null;
	}

	@Override
	public void init(){
		finalPathList.clear();
		firstCity = null;
		cities = null;
		finalDestination = null;
	}

	private Path createSimplePath(){
		Path path = new Path();
		path.addCity(firstCity);
		path.addCity(cities.get(0));
		path.addCity(cities.get(1));
		if (finalDestination != null) {
			path.addCity(finalDestination);
		}
		return path;
	}

	private void swapCities(int index1, int index2){
		City city = cities.get(index1);
		cities.set(index1, cities.get(index2));
		cities.set(index2, city);
	}
}
