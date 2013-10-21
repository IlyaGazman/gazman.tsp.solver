package com.gazman.city_map.engines.simple;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;
import com.gazman.city_map.test.statistics.ActionsCounter;

public class SimpleEngine implements IEngine {

	private PathList pathList;
	private ArrayList<City> cities;
	private City finalDestination;

	@Override
	public void init() {}
	
	@Override
	public PathList findBestPathList(ArrayList<City> cities,
			City finalDestination) {
		ActionsCounter.instance.count("SimpleEngine");
		this.cities = cities;
		this.finalDestination = finalDestination;
		pathList = new PathList();

		
		switch(cities.size()){
			case 1:
				addPath();
				break;
			case 2:
				addPath(1);
				break;
			case 3:
				addPath(1, 2);
				addPath(2, 1);
				break;
			case 4:
				addPath(1, 2, 3);
				addPath(1, 3, 2);
				addPath(2, 3, 1);
				addPath(2, 1, 3);
				addPath(3, 1, 2);
				addPath(3, 2, 1);
				break;
			default:
				throw new IllegalStateException("Can't deal with " + cities.size());
		}
		
		return pathList;
	}

	private void addPath(int... indexes) {
		Path path = new Path();
		path.addCity(cities.get(0));
		for (int i = 0; i < indexes.length; i++) {
			path.addCity(cities.get(indexes[i]));
		}
		path.addCity(finalDestination);
		pathList.addPath(path);
	}
}
