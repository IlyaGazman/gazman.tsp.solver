package com.gazman.city_map.city;

import java.math.BigInteger;

/**
 * @author Ilya Gazman
 * 
 */
public class City {

	private static long _mask = 1;
//	private static BigInteger _mask = new BigInteger("1");
	private static int count = 0;

	public int x, y;
	public final long mask;
//	public final BigInteger mask;

	public City(int x, int y) {
		this();
		this.x = x;
		this.y = y;
	}

	City() {
//		mask = _mask.shiftLeft(1);
		mask = _mask << count;
		count++;
//		_mask = mask;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}
}
