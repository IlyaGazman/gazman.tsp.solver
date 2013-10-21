package com.gazman.city_map.out;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.gazman.city_map.city.City;
import com.gazman.city_map.path.Path;

/**
 * @author Ilya Gazman
 *
 */
public class DrawResoult extends JPanel {

	private static final long serialVersionUID = 5488566189857782247L;
	private static final int SCALE_FACTOR = 6;
	private static final int PADDING = 10;
	private Path path;
	private double maxXsize = 0;
	private double maxYsize = 0;

	private double minXsize = Integer.MAX_VALUE;
	private double minYsize = Integer.MAX_VALUE;

	public DrawResoult(Path path){
		this.path = path;
		setSize(new Dimension(100 * SCALE_FACTOR + PADDING * 2, 100 * SCALE_FACTOR + PADDING * 2));
	}

	@Override
	public void paintComponent(Graphics g){

		super.paintComponent(g);
		doDrawing(g);
	}

	private int convertValue(double value, double maxSize, double minSize){
		value -= minSize;
		maxSize -= minSize;
		value = value / maxSize * 100;
		return (int) (value * SCALE_FACTOR + PADDING);
	}

	private int convertXValue(double value){
		return convertValue(value, maxXsize, minXsize);
	}

	private int convertYValue(double value){
		return convertValue(value, maxYsize, minYsize);
	}

	private void doDrawing(Graphics graphics){
		Graphics2D graphics2D = (Graphics2D) graphics;

		for (int i = 0; i < path.size(); i++) {
			updatedimantions(path.get(i));
		}

		int count = 0;
		for (int i = 0; i < path.size() - 1; i++) {

			City city = path.get(i);
			City city2 = path.get(i + 1);

			if (count < 1) {
				graphics2D.setColor(Color.green);
				count++;
			}
			else if (count < 2) {
				graphics2D.setColor(Color.orange);
				count++;
			}
			else {
				graphics2D.setColor(Color.blue);
			}

			drawLine(graphics2D, city, city2);

			graphics2D.setColor(Color.red);

			drawCity(graphics2D, city);
			drawCity(graphics2D, city2);
			drawCircle(graphics2D, city, 3);
			drawCircle(graphics2D, city2, 3);
		}
	}

	private void drawCircle(Graphics2D graphics2D, City city, int radius){
		int factor = -radius / 2;
		int convertXValue = convertXValue(city.x);
		int convertYValue = convertYValue(city.y);
		graphics2D.drawOval(convertXValue + factor, convertYValue + factor, radius, radius);
	}

	private void drawCity(Graphics2D graphics2D, City city){
		int x = convertXValue(city.x);
		int y = convertYValue(city.y);
		drawLine(graphics2D, x, y, x, y);
	}

	private void drawLine(Graphics2D graphics2D, City city, City city2){
		int x = convertXValue(city.x);
		int y = convertYValue(city.y);
		int x2 = convertXValue(city2.x);
		int y2 = convertYValue(city2.y);
		drawLine(graphics2D, x, y, x2, y2);
	}

	private void drawLine(Graphics2D graphics2D, int x, int y, int x2, int y2){
		graphics2D.drawLine(x, y, x2, y2);
	}

	private void updatedimantions(City city){
		if (city.x > maxXsize) {
			maxXsize = city.x;
		}

		if (city.y > maxYsize) {
			maxYsize = city.y;
		}

		if (city.x < minXsize) {
			minXsize = city.x;
		}

		if (city.y < minYsize) {
			minYsize = city.y;
		}
	}
}
