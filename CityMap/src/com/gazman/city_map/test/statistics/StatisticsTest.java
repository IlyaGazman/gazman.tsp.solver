package com.gazman.city_map.test.statistics;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.city.CityManager;
import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.log.FileLog;

public class StatisticsTest {

	private static final int MINEMUM_CITIES = 6;
	private IEngine engine;

	public void init(IEngine engine) {
		this.engine = engine;
	}

	public double[] start(int numberOfTests, int maxCitySize) {
		double resoults[] = new double[maxCitySize - MINEMUM_CITIES];
		for (int i = MINEMUM_CITIES; i < maxCitySize; i++) {
			resoults[i - MINEMUM_CITIES] = performTest(i, numberOfTests);
			FileLog.instance.log("Finished(" + i + ")\t"
					+ resoults[i - MINEMUM_CITIES]);
		}

		return resoults;
	}

	private double performTest(int count, int numberOfTests) {
		long totalTime = 0;
		for (int i = 0; i < numberOfTests; i++) {
			ArrayList<City> cities = CityManager.instance.generate(count, 100);
			long startingTime = System.currentTimeMillis();
			FileLog.disable = true;
			engine.init();
			engine.findBestPathList(cities, cities.get(0));
			FileLog.disable = false;
			long timePass = System.currentTimeMillis() - startingTime;
			totalTime += timePass;
		}
		return totalTime / (double) numberOfTests;
	}
}
