package com.gazman.city_map.city;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

import com.gazman.city_map.Root;

/**
 * @author Ilya Gazman
 *
 */
public class CityManager {

	public static final CityManager instance = new CityManager();
	private static final Random RANDOM = new Random();
	private HashMap<String, Double> map = new HashMap<String, Double>();
	private ArrayList<City> cities;

	private CityManager(){}

	public ArrayList<City> generate(int count, int maxValue){
		Hashtable<String, Boolean> citiesMap = new Hashtable<String, Boolean>();
		cities = new ArrayList<City>();
		for (int i = 0; i < count; i++) {
			City city = genarateCity(maxValue);
			if (citiesMap.put(city.toString(), true) != null) {
				i--;
			}
			else {
				cities.add(city);
			}
		}

		Collections.sort(cities, new CitiesComparator());

		genarateDistances();

		return cities;
	}

	public ArrayList<City> generate(String data){

		Hashtable<String, Boolean> citiesMap = new Hashtable<String, Boolean>();
		data = data.replaceAll(" ", "");
		String[] citiesData = data.split(",");

		cities = new ArrayList<City>();
		for (int i = 0; i < citiesData.length; i += 2) {
			City city = new City(Integer.parseInt(citiesData[i]), Integer.parseInt(citiesData[i + 1]));
			if (citiesMap.put(city.toString(), true) != null) {
				throw new IllegalArgumentException("Bed data");
			}
			cities.add(city);
		}

		genarateDistances();

		return cities;
	}

	private City genarateCity(int maxValue){
		City city = new City();
		city.x = RANDOM.nextInt(maxValue);
		city.y = RANDOM.nextInt(maxValue);

		return city;
	}

	private void genarateDistances(){
		map.clear();
		for (int i = 0; i < cities.size(); i++) {
			for (int j = 0; j < cities.size(); j++) {
				if (j != i) {
					City city1 = cities.get(j);
					City city2 = cities.get(i);
					String citiesKey = getCitiesKey(city1, city2);
					double distance = calculateDistance(city1, city2);
					map.put(citiesKey, distance);
					// FileLog.instance.log(city1 + "\t->\t" + city2 + "\t=\t" +
					// genarateDistance);
				}
			}
		}
	}

	private String getCitiesKey(City city1, City city2){
		return "" + city1 + "|" + city2;
	}
	
	public double calculateDistance(City city1, City city2){
		if(Root.MATH_DISTANCE){
			return mathOption(city1, city2);
		}
		return randomDistance();
	}

	private double mathOption(City city1, City city2){
		double powerX = Math.pow(city1.x - city2.x, 2);
		double powerY = Math.pow(city1.y - city2.y, 2);
		return Math.sqrt(powerX + powerY);
	}
	
	private double randomDistance(){
		return new Double(RANDOM.nextInt(1000) + 1);
	}

}
