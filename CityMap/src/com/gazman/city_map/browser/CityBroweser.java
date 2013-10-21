package com.gazman.city_map.browser;

import java.util.ArrayList;

import com.gazman.city_map.city.City;

public class CityBroweser {
	private ArrayList<City> cities;
	private IBrowserCallback callback;

	public void init(ArrayList<City> cities, IBrowserCallback callback) {
		this.cities = cities;
		this.callback = callback;
	}

	public void browse(int count) {
		doBrowsing(new ArrayList<City>(), 0, count);
	}

	private void doBrowsing(ArrayList<City> citiesPart, int position, int count) {
		if(position == count){
			callback.onGettingCities(citiesPart, cities);
		}
		for (int i = position; i < cities.size(); i++) {
			City city = cities.remove(i);
			citiesPart.add(city);
			doBrowsing(citiesPart, position + 1, count);
			citiesPart.remove(citiesPart.size() - 1);
			cities.add(i, city);
			
//			City city = cities.get(i);
			ArrayList<City> arrayList = new ArrayList<City>();
			arrayList.add(city);
			
			
		}
	}
}
