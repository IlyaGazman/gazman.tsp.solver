package com.gazman.city_map.path;

import com.gazman.city_map.city.City;

public class Line {
	public final City b;
	public final City a;

	public Line(City a, City b){
		this.a = a;
		this.b = b;
	}
}
