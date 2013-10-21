package com.gazman.city_map.path;

import java.awt.geom.Line2D;
import java.util.ArrayList;

import com.gazman.city_map.city.City;

public class IntersactionPath extends Path {
	private ArrayList<Line> lines = new ArrayList<Line>();

	@Override
	public void addCity(City city) {
		if(lastCity != null){
			Line line = new Line(lastCity, city);
			lines.add(line);
		}
		super.addCity(city);
	}
	
	public boolean isIntersacts(City city){
		for(int i = 0; i < lines.size() - 1; i++){
			Line line = lines.get(i);
			if (Line2D.linesIntersect(city.x, city.y, lastCity.x, lastCity.y, line.a.x, line.a.y, line.b.x, line.b.y)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void removeLastCity() {
		super.removeLastCity();
		if (cities.size() > 0) {
			if (lines.size() > 0) {
				lines.remove(lines.size() - 1);
			}
		}
	}

	public ArrayList<City> getCities() {
		return cities;
	}
}
