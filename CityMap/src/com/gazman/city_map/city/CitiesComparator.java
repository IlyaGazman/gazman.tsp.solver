package com.gazman.city_map.city;

import java.util.Comparator;

/**
 * @author Ilya Gazman
 *
 */
public class CitiesComparator implements Comparator<City> {

	@Override
	public int compare(City city1, City city2){
		return city1.toString().compareTo(city2.toString());
	}
}
