package com.gazman.city_map.test.compare;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.city.CityManager;
import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;

/**
 * @author Ilya Gazman
 *
 */
public class CompareTool {

	long lastUpdate = System.currentTimeMillis();
	private IEngine engine2;
	private IEngine engine1;

	@SuppressWarnings("unchecked")
	public void compare(int numberOfTestToPerfom, int numberOfCities){

		for (int i = 0; i < numberOfTestToPerfom; i++) {
			ArrayList<City> baseCities = CityManager.instance.generate(numberOfCities, 100);
			PathList bestPathList1 = engine1.findBestPathList((ArrayList<City>) baseCities.clone(), null);
			engine2.init();
			PathList bestPathList2 = engine2.findBestPathList((ArrayList<City>) baseCities.clone(), null);
			test(bestPathList1, bestPathList2);

			logTime(numberOfTestToPerfom, i);
		}
	}

	public void init(IEngine engine1, IEngine engine2){
		this.engine1 = engine1;
		this.engine2 = engine2;
	}

	private void logTime(int numberOfTestToPerfom, int i){
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastUpdate > 1000) {
			lastUpdate = currentTime;
			float percent = (float) i / numberOfTestToPerfom * 100;
			System.out.println("Finished " + String.format("%.2f", percent) + "%");
		}
	}

	private void test(PathList bestPathList1, PathList bestPathList2){
		for (int i = 0; i < bestPathList1.size(); i++) {
			boolean isEquals = false;
			Path path1 = bestPathList1.get(i);
			for (int j = 0; j < bestPathList1.size(); j++) {
				isEquals = false;
				Path path2 = bestPathList1.get(j);
				if (path1.toString().equals(path2.toString())) {
					isEquals = true;
					break;
				}
			}
			if (!isEquals) {
				throw new IllegalArgumentException("Pathes does not mutch\n" + bestPathList1 + "\n"
						+ bestPathList2);
			}
		}

	}
}
