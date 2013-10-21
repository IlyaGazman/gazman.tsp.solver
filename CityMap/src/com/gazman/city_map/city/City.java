package com.gazman.city_map.city;

import java.math.BigInteger;

/**
 * @author Ilya Gazman
 * 
 */
public class City {

	private static BigInteger _mask = new BigInteger("1");

	public int x, y;
	public final BigInteger mask;

	public City(int x, int y) {
		this();
		this.x = x;
		this.y = y;
	}

	City() {
		mask = _mask.shiftLeft(1);
		_mask = mask;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
}
