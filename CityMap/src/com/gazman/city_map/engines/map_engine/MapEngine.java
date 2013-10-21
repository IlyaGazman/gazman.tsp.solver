package com.gazman.city_map.engines.map_engine;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.engines.base_engine.BaseEngine;
import com.gazman.city_map.path.PathList;

/**
 * @author Ilya Gazman
 *
 */
public class MapEngine extends BaseEngine {

	public CityMap cityMap = new CityMap();

	@Override
	public MapEngine clone(){
		MapEngine mapEngine = (MapEngine) super.clone();
		mapEngine.cityMap = cityMap;
		return mapEngine;
	}

	@Override
	public PathList findBestPathList(ArrayList<City> cities, City finalDestination){
		PathList pathList = getCityMap().get(cities, finalDestination);
		if (pathList != null) {
			return pathList;
		}
		pathList = super.findBestPathList(cities, finalDestination);
		if (pathList != null && cities.size() > 2) {
			getCityMap().map(cities, pathList, finalDestination);
		}
		return pathList;
	}

	private CityMap getCityMap(){
		if (cityMap == null) {
			cityMap = new CityMap();
		}
		return cityMap;
	}

}
