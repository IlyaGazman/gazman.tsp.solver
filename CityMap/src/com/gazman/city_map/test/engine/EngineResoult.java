package com.gazman.city_map.test.engine;

import java.text.NumberFormat;

import com.gazman.city_map.engines.base_engine.IEngine;
import com.gazman.city_map.engines.map_engine.CityMap;
import com.gazman.city_map.log.FileLog;
import com.gazman.city_map.path.PathList;
import com.gazman.city_map.test.statistics.ActionsCounter;

public class EngineResoult {
	private PathList pathList;
	private long timePath;
	private IEngine engine;

	public EngineResoult(IEngine engine, PathList pathList, long timePath) {
		super();
		this.engine = engine;
		this.pathList = pathList;
		this.timePath = timePath;
	}

	public void printResoults(){
		FileLog.instance.log("Finish " + engine.getClass().getSimpleName() + " test after " + timePath
				+ " millis");
		if (pathList.size() == 0) {
			FileLog.instance.log("Not found");
		}
		else {
			FileLog.instance.log("Actions:\n" + ActionsCounter.instance);
			for (int i = 0; i < CityMap.readCounter.length; i++) {
				if (CityMap.writeCounter[i] > 0) {
					FileLog.instance.log("Map(" + i + ")\tWrite\t " + format(CityMap.writeCounter[i])
							+ " \tRead\t " + format(CityMap.readCounter[i]));
				}
			}

			FileLog.instance.log("\n" + pathList);
		}
	}

	private String format(int value) {
		return NumberFormat.getInstance().format(value);
	}
}
