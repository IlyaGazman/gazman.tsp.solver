package com.gazman.city_map.utils;

/**
 * @author Ilya Gazman
 *
 */
public class ClassConstractor {

	/**
	 * Create itemClass with default contractor
	 * @return new instance of itemClass or null if fails
	 */
	public static <T> T crateNewItem(Class<T> itemClass){
		try {
			return itemClass.newInstance();

		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}
}
