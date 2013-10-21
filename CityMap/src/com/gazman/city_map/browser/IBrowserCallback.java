package com.gazman.city_map.browser;

import java.util.ArrayList;

import com.gazman.city_map.city.City;

public interface IBrowserCallback {
	void onGettingCities(ArrayList<City> part1, ArrayList<City> part2);
}
