package com.gazman.city_map.out;

import java.awt.ScrollPane;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.gazman.city_map.city.City;
import com.gazman.city_map.city.CityManager;
import com.gazman.city_map.log.FileLog;
import com.gazman.city_map.path.Path;
import com.gazman.city_map.path.PathList;

public class Screen {
	private OutputView outputView;
	
	private void showResoult(Path path) {
		JFrame jFrame = new JFrame("City Map");
		jFrame.setSize(800, 800);
		DrawResoult drawResoult = new DrawResoult(path);
		jFrame.add(drawResoult);
		jFrame.setVisible(true);
	}

	public void createView() {
		JFrame jFrame = new JFrame("City Map");

		jFrame.setSize(800, 600);
		outputView = new OutputView();
		FileLog.instance.setOutputView(outputView);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.add(outputView);
		jFrame.add(scrollPane);
		jFrame.setVisible(true);
	}

	public void setCallback(Runnable runnable) {
		outputView.startCallback = runnable;
	}

	public ArrayList<City> readCities() {
		ArrayList<City> cities = outputView.citiesList;
		if (cities == null) {
			if (outputView.cities != null) {
				cities = CityManager.instance.generate(outputView.cities);
			}
			else {
				cities = CityManager.instance.generate(outputView.numberOfCities, 100);
			}
		}
		
		return cities;
	}

	public void showResoults(PathList pathList) {
		for (int i = 0; i < pathList.size(); i++) {
			showResoult(pathList.get(i));
		}
	}
}
