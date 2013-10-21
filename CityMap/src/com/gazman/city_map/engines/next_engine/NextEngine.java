package com.gazman.city_map.engines.next_engine;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.engines.map_engine.CityMap;
import com.gazman.city_map.engines.simple.SimpleEngine;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;
import com.gazman.city_map.test.statistics.ActionsCounter;

public class NextEngine implements IEngine {

	private ArrayList<City> cities;
	private PathList pathList = new PathList();
	private CityMap cityMap = new CityMap();
	private City finalDestination;
	private Combination combination1 = new Combination();
	private Combination combination2 = new Combination();
	private SimpleEngine simpleEngine = new SimpleEngine();

	@Override
	public void init() {}

	@Override
	public PathList findBestPathList(ArrayList<City> cities,
			City finalDestination) {
		this.cities = cities;
		this.finalDestination = finalDestination;
		if (cities.size() >= 5) {
			search();
		}
		else {
			return simpleEngine.findBestPathList(cities, finalDestination);
		}
		return pathList;
	}

	private void search() {

		for (int i = 1; i < cities.size(); i++) {
			City commonCity1 = cities.remove(i);
			for (int j = 1; j < cities.size(); j++) {
				City commonCity2 = cities.remove(j);

				messureCombinations(commonCity1, commonCity2);

				cities.add(j, commonCity2);
			}
			cities.add(i, commonCity1);
		}
	}

	private void messureCombinations(City commonCity1, City commonCity2) {
		int n = cities.size() - 1;
		int r = n / 3;
		combination1.init(n, r);

		while (combination1.hasNext()) {
			int[] resoult = combination1.next();

			ActionsCounter.instance.count("NextEngine");

			ArrayList<City> part1 = new ArrayList<City>();
			ArrayList<City> part2And3 = new ArrayList<City>();

			part1.add(cities.get(0));

			for (int citiesIndex = 1, resoultIndex = 0; citiesIndex < cities
					.size(); citiesIndex++) {
				City city = cities.get(citiesIndex);
				if (resoultIndex < resoult.length
						&& resoult[resoultIndex] == citiesIndex - 1) {
					resoultIndex++;
					part1.add(city);
				}
				else {
					part2And3.add(city);
				}
			}

			int n2 = part2And3.size() - 1;
			int r2 = n2 / 2;
			combination2.init(n2, r2);
			PathList bestPathList1 = search(part1, commonCity1);

			while (combination2.hasNext()) {
				ArrayList<City> part2 = new ArrayList<City>();
				ArrayList<City> part3 = new ArrayList<City>();
				part2.add(commonCity1);
				part3.add(commonCity2);

				int[] resoult2 = combination2.next();

				for (int citiesIndex = 0, resoultIndex = 0; citiesIndex < part2And3
						.size(); citiesIndex++) {
					City city = part2And3.get(citiesIndex);
					if (resoultIndex < resoult2.length
							&& resoult2[resoultIndex] == citiesIndex) {
						resoultIndex++;
						part2.add(city);
					}
					else {
						part3.add(city);
					}
				}
				

				PathList bestPathList2 = search(part2, commonCity2);
				PathList bestPathList3 = search(part3, finalDestination);

				if (bestPathList1.size() == 1 && bestPathList2.size() == 1
						&& bestPathList3.size() == 1) {
					Path path1 = bestPathList1.get(0).clone();
					Path path2 = bestPathList2.get(0);
					Path path3 = bestPathList3.get(0);
					path1.addPath(path2, 1);
					path1.addPath(path3, 1);
					pathList.addPath(path1);
				}
				else {
					for (int i = 0; i < bestPathList1.size(); i++) {
						Path combinedPath = bestPathList1.get(i).clone();
						for (int j = 0; j < bestPathList2.size(); j++) {
							Path path2 = bestPathList2.get(j);
							Path combinedPathClone = combinedPath.clone();
							combinedPathClone.addPath(path2, 1);
							for (int k = 0; k < bestPathList3.size(); k++) {
								Path path3 = bestPathList3.get(k);
								Path combinedPathClone2 = combinedPathClone
										.clone();
								combinedPathClone2.addPath(path3, 1);
								pathList.addPath(combinedPathClone2);
							}
						}
					}
				}
			}

		}
	}

//	private void applyResoult(ArrayList<City> sourceCities, int[] resoult, ArrayList<City> part1,
//			ArrayList<City> part2And3) {
//		for (int citiesIndex = 1, resoultIndex = 0; citiesIndex < sourceCities
//				.size(); citiesIndex++) {
//			City city = sourceCities.get(citiesIndex);
//			if (resoultIndex < resoult.length
//					&& resoult[resoultIndex] == citiesIndex - 1) {
//				resoultIndex++;
//				part1.add(city);
//			}
//			else {
//				part2And3.add(city);
//			}
//		}
//	}

	private PathList search(ArrayList<City> cities, City finalDestination) {
		boolean isMapAlloud = cities.size() >= 3;
		PathList pathList = null;
		if (isMapAlloud) {
			pathList = cityMap.get(cities, finalDestination);
		}
		if (pathList == null) {
			NextEngine nextEngine = clone();
			pathList = nextEngine.findBestPathList(cities, finalDestination);
			if (isMapAlloud) {
				cityMap.map(cities, pathList, finalDestination);
			}
		}
		return pathList;
	}

	// private void mapInvert(ArrayList<City> cities, PathList mapPathList,
	// City finalDestination) {
	// ArrayList<City> invertedCities = new ArrayList<>();
	// invertedCities.add(finalDestination);
	// for (int i = cities.size() - 1; i > 0; i--) {
	// invertedCities.add(cities.get(i));
	// }
	// finalDestination = cities.get(0);
	// PathList invertedPathList = new PathList();
	// for (int i = 0; i < mapPathList.size(); i++) {
	// Path path = mapPathList.get(i);
	// invertedPathList.addPath(invertPath(path));
	// }
	// cityMap.map(invertedCities, invertedPathList, finalDestination);
	// }
	//
	// private Path invertPath(Path path) {
	// Path invertedPath = new Path();
	// for (int i = path.size() - 1; i >= 0; i--) {
	// invertedPath.addCity(path.get(i));
	// }
	// return invertedPath;
	// }

	@Override
	public NextEngine clone() {
		NextEngine nextEngine = new NextEngine();
		nextEngine.cityMap = cityMap;
		return nextEngine;
	}

}
