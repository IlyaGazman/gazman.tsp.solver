package com.gazman.city_map.engines.map_engine;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import com.gazman.city_map.city.City;
import com.gazman.city_map.path.PathList;

/**
 * @author Ilya Gazman
 * 
 */
public class CityMap {
	public static int readCounter[] = new int[1000];
	public static int writeCounter[] = new int[1000];
	private HashMap<String, PathList> map = new HashMap<String, PathList>();

	public PathList get(ArrayList<City> cities, City finalDestination) {
		String citiesToString = citiesToString(cities, finalDestination);
		PathList pathList = map.get(citiesToString);
		if (pathList != null) {
			readCounter[cities.size()]++;
			return pathList.clone();
		}
		return null;
	}

	public void map(ArrayList<City> cities, PathList pathList,
			City finalDestination) {
		writeCounter[cities.size()]++;
		map.put(citiesToString(cities, finalDestination), pathList.clone());
//		mapInvert(cities, pathList, finalDestination);
	}

//	private void mapInvert(ArrayList<City> cities, PathList mapPathList,
//			City finalDestination) {
//		ArrayList<City> invertedCities = new ArrayList<>();
//		invertedCities.add(finalDestination);
//		for (int i = cities.size() - 1; i > 0; i--) {
//			invertedCities.add(cities.get(i));
//		}
//		finalDestination = cities.get(0);
//		PathList invertedPathList = new PathList();
//		for (int i = 0; i < mapPathList.size(); i++) {
//			Path path = mapPathList.get(i);
//			invertedPathList.addPath(invertPath(path));
//		}
//		
//		map.put(citiesToString(invertedCities, finalDestination), invertedPathList);
//	}
//
//	private Path invertPath(Path path) {
//		Path invertedPath = new Path();
//		for (int i = path.size() - 1; i >= 0; i--) {
//			invertedPath.addCity(path.get(i));
//		}
//		return invertedPath;
//	}

	private String citiesToString(ArrayList<City> cities, City finalDestination) {
		BigInteger key = new BigInteger("0");
		for (City city : cities) {
			key = key.or(city.mask);
		}

		return cities.get(0) + "|" + key + "|" + finalDestination;
	}

}
