package com.gazman.city_map.distance;

import java.util.ArrayList;
import java.util.Collections;

import com.gazman.city_map.city.City;
import com.gazman.city_map.city.CityManager;

public class Distance {

	public static double[] createDistanceMap(ArrayList<City> cities){
		ArrayList<Double> distances = new ArrayList<Double>();
		for(int i = 0; i < cities.size(); i++){
			for(int j = i + 1; j < cities.size(); j++){
				double distance = CityManager.instance.calculateDistance(cities.get(i), cities.get(j));
				distances.add(distance);
			}
		}
		
		Collections.sort(distances);
		double[] shortestDistances = new double[cities.size()];
		shortestDistances[0] = distances.get(0);
		for(int i = 1; i < shortestDistances.length; i++){
			shortestDistances[i] = shortestDistances[i - 1] + distances.get(i);
		}
		
		return shortestDistances;
	}
}
