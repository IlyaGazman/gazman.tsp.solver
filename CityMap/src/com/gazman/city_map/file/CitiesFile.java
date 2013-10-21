package com.gazman.city_map.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.gazman.city_map.city.City;

public class CitiesFile {
	public ArrayList<City> readCities(String fileName){
		ArrayList<City> cities = new ArrayList<City>();
		File file = new File(fileName);
		try {
			BufferedReader bufferedReader = new BufferedReader(new java.io.FileReader(file));
			String line = bufferedReader.readLine();;
			do {
				City city = readCity(line);
				cities.add(0 ,city);
				line = bufferedReader.readLine();
			} while (line != null);
			bufferedReader.close();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cities;
	}

	private City readCity(String line){
		String[] cityData = line.split(" ");
		double x = Double.parseDouble(cityData[1]);
		double y = Double.parseDouble(cityData[2]);
		return new City((int)x, (int)y);
	}
}
