package com.gazman.city_map.path;

import java.util.ArrayList;

import com.gazman.city_map.Root;
import com.gazman.city_map.city.City;
import com.gazman.city_map.city.CityManager;

/**
 * @author Ilya Gazman
 * 
 */
public class Path {

	protected ArrayList<City> cities = new ArrayList<City>();
	protected City lastCity;
	protected double distance;

	public Path(){}
	public Path(ArrayList<City> cities) {
		for(City city : cities){
			addCity(city);
		}
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}

	/**
	 * Add the city to the end of the path and updates the distance
	 * 
	 * @param city
	 */
	public void addCity(City city) {
		if(Root.DEBUG_TEST){
			for(City city2 : cities){
				if(city == city2 && city != cities.get(0)){
					throw new IllegalStateException("This is nor right...");
				}
			}
		}
		if (lastCity != null) {
			distance += CityManager.instance.calculateDistance(city, lastCity);
		}
		cities.add(city);
		lastCity = city;
	}

	/**
	 * Add the city to the beginning of the path and updates the distance
	 * 
	 * @param city
	 */
	public void addCityToFront(City city) {
		if (cities.size() == 0) {
			addCity(city);
		}
		else {
			distance += CityManager.instance.calculateDistance(city,
					cities.get(0));
			cities.add(0, city);
		}
	}

	public void removeLastCity() {
		City lastCityTmp = lastCity;
		cities.remove(cities.size() - 1);
		if (cities.size() > 0) {
			lastCity = cities.get(cities.size() - 1);
			distance -= CityManager.instance.calculateDistance(lastCityTmp,
					lastCity);
		}
		else {
			lastCity = null;
		}
	}

	@Override
	public Path clone() {
		Path path = new Path();
		path.init(this);
		return path;
	}

	public City get(int index) {
		return cities.get(index);
	}

	public double getDistance() {
		return distance;
	}
	
	public double getDistance(City city){
		if(lastCity == null){
			return 0;
		}
		return distance + CityManager.instance.calculateDistance(city, lastCity);
	}
	
	public double getDistance(PathList mappedPathList){
		if(lastCity == null || mappedPathList.size() == 0){
			return 0;
		}
		return distance + CityManager.instance.calculateDistance(mappedPathList.get(0).get(0), lastCity);
	}

	public int size() {
		return cities.size();
	}

	@Override
	public String toString() {
		return cities.toString();
	}

	@SuppressWarnings("unchecked")
	private void init(Path path) {
		cities = (ArrayList<City>) path.cities.clone();
		distance = path.distance;
		lastCity = path.lastCity;
	}

	public void addPath(Path path2, int startingIndex) {
		for (int i = startingIndex; i < path2.size(); i++) {
			addCity(path2.get(i));
		}
	}
}
