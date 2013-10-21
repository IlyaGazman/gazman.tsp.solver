package com.gazman.city_map;

import java.util.ArrayList;

import com.gazman.city_map.city.City;
import com.gazman.city_map.engines.map_engine.MapEngine;
import com.gazman.city_map.engines.next_engine.NextEngine;
import com.gazman.city_map.engines.streight_map.StreightMapEngine;
import com.gazman.city_map.file.CitiesFile;
import com.gazman.city_map.log.FileLog;
import com.gazman.city_map.out.Screen;
import com.gazman.city_map.test.compare.CompareTool;
import com.gazman.city_map.test.engine.EngineResoult;
import com.gazman.city_map.test.engine.EngineTest;
import com.gazman.city_map.test.engine.IResoultCallback;
import com.gazman.city_map.test.statistics.StatisticsTest;

/**
 * @author Ilya Gazman
 * 
 */
public class Root implements Runnable, IResoultCallback {

	// tests
	public static enum Test {
		COMPARE_TEST, 
		STATISTIC_TEST, 
		TIME_TEST
	}

	// Settings
	public static Test TEST = Test.STATISTIC_TEST;

	public static final int NUMBER_OF_CITIES = 19;
	public static final boolean SHOW_GUI = false;

	public static final boolean CIRCLE_TRIP = true;
	/**
	 * Math distance or random distance.
	 */
	public static final boolean MATH_DISTANCE = true;
	/** reduce performance for extra testing */
	public static final boolean DEBUG_TEST = false;

	// End Settings

	private Screen screen;
	private EngineTest engineTest;

	public Root() {
		switch (TEST) {
			case STATISTIC_TEST:
				performStatisticTest();
				break;
			case COMPARE_TEST:
				preformCompareTest();
				break;
			case TIME_TEST:
				PerformTimeTest();
				break;
		}
	}

	private void PerformTimeTest() {
		if (SHOW_GUI) {
			screen = new Screen();
			screen.createView();
			screen.setCallback(this);
		}
		else {
			run();
		}
	}

	private void preformCompareTest() {
		CompareTool compareTool = new CompareTool();
		compareTool.init(new MapEngine(), new NextEngine());
		compareTool.compare(10000, NUMBER_OF_CITIES);
		FileLog.instance.log("test completed susccesfully");
	}

	private void performStatisticTest() {
		StatisticsTest statisticsTest = new StatisticsTest();
		statisticsTest.init(new StreightMapEngine());
		statisticsTest.start(50, 30);
	}

	@Override
	public void run() {
		ArrayList<City> cities;
		if (SHOW_GUI) {
			cities = screen.readCities();
			printSource(cities);
		}
		else {
			engineTest = new EngineTest();
			Object citiesData = null;
//			 citiesData = new CitiesFile().readCities("files/map2.txt");
			// citiesData = new CitiesFile().readCities("files/map 7663.txt");
			// citiesData =
			// "12645,42973, 12421,42895, 12372,42711, 12300,42433, 12149,42477, 12058,42195, 11715,41836, 11511,42106, 11438,42057, 11003,42102, 11108,42373, 11155,42712, 11133,42885, 11183,42933, 11297,42853, 11310,42929, 11416,42983, 11423,43000, 11485,43187, 11461,43252, 11600,43150, 11595,43148, 11583,43150, 11569,43136, 11503,42855, 11522,42841, 11690,42686, 11770,42651, 11846,42660, 11822,42673, 11751,42814, 11785,42884, 11973,43026, 11963,43290, 12286,43355, 12386,43334, 12363,43189, 12355,43156";
			// citiesData =
			// "1,60, 1,82, 13,2, 16,37, 2,62, 2,93, 23,55, 27,86, 28,20, 31,9, 50,57, 54,26, 55,80, 61,45, 66,36, 70,20, 70,63, 72,32, 72,56, 78,64, 87,49, 89,64";
//			citiesData = "12,56, 12,74, 19,4, 28,9, 30,82, 32,29, 36,52, 44,37, 52,27, 52,73, 53,88, 54,84, 6,38, 63,97, 7,0, 72,1, 79,64, 83,96, 97,13";
			// engineTest.init(new SmallEngine(), citiesString);
//			 engineTest.init(new MapEngine(), citiesData);
			engineTest.init(new StreightMapEngine(), citiesData);
			engineTest.setCallback(this);
			cities = engineTest.getCities();
			printSource(cities);
			engineTest.testEngine();
		}
	}

	@Override
	public void onResoult(EngineResoult engineResoult) {
		engineResoult.printResoults();
	}

	private void printSource(ArrayList<City> cities) {
		FileLog.instance.log("Source(" + cities.size() + ")\t" + cities);
	}

}
