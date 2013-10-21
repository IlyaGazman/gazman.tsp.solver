package com.gazman.city_map.test.engine;

import java.util.ArrayList;

import com.gazman.city_map.Root;
import com.gazman.city_map.city.City;
import com.gazman.city_map.city.CityManager;
import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.path.PathList;

public class EngineTest {
	
	private IResoultCallback callback;
	private ArrayList<City> cities;
	private IEngine engine;

	public void setCallback(IResoultCallback callback) {
		this.callback = callback;
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}
	
	public void init(IEngine engine){
		init(engine, null);
	}
	
	@SuppressWarnings("unchecked")
	public void init(IEngine engine, Object citiesData){
		this.engine = engine;
		if(citiesData != null){
			if(citiesData instanceof String){
				cities = CityManager.instance.generate((String) citiesData);
			}
			else{
				cities = (ArrayList<City>) citiesData;
			}
		}
		else{
			cities = CityManager.instance.generate(Root.NUMBER_OF_CITIES, 100);
		}
	}
	
	public void testEngine(City finalDestination){
		testEngine(false, finalDestination);
	}
	
	public void testEngine(){
		testEngine(false, null);
	}
	
	public void testEngine(boolean runOberThread, final City finalDestination){
		Runnable task = new Runnable() {

			@Override
			public void run(){
				long startingTimeMillis = System.currentTimeMillis();
				engine.init();
				City currentFinalDestination = finalDestination;
				if (currentFinalDestination == null && Root.CIRCLE_TRIP) {
					currentFinalDestination = cities.get(0);
				}
				PathList patheList = engine.findBestPathList(cities, currentFinalDestination);
				long timePath = System.currentTimeMillis() - startingTimeMillis;
				callback.onResoult(new EngineResoult(engine, patheList, timePath));
			}
		};
		if (runOberThread) {
			new Thread(task).start();
		}
		else {
			task.run();
		}
	}
}
