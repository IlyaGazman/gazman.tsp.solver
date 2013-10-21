package com.gazman.city_map.engines.base_engine;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.path.PathList;

/**
 * @author Ilya Gazman
 *
 */
public interface IEngine {

	PathList findBestPathList(ArrayList<City> cities, City finalDestination);

	void init();

}