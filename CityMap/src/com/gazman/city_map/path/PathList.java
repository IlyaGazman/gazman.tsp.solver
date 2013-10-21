package com.gazman.city_map.path;

import java.util.ArrayList;

import com.gazman.city_map.city.City;

/**
 * @author Ilya Gazman
 *
 */
public class PathList {

	private ArrayList<Path> pathes = new ArrayList<Path>();

	/**
	 * Add city to front of all the paths
	 * @param city
	 */
	public void addCityToFront(City city){
		for (Path path : pathes) {
			path.addCityToFront(city);
		}
	}
	
	public void addCity(City city){
		for (Path path : pathes) {
			path.addCity(city);
		}
	}

	/***
	 * Add a path if its shorter then the others paths
	 * 
	 * @param path
	 * @return if the new path been added
	 */
	public boolean addPath(Path path){
		invalidate(path);
		if (!isPathWarthy(path)) {
			return false;
		}
		if (path.getDistance() < getDistance()) {
			clear();
		}
		pathes.add(path);
		return true;
	}

	/**
	 * Remove all the paths
	 */
	public void clear(){
		pathes.clear();
	}

	@Override
	public PathList clone(){
		PathList pathList = new PathList();
		for (Path path : pathes) {
			pathList.pathes.add(path.clone());
		}
		return pathList;
	}

	public Path get(int index){
		return pathes.get(index);
	}

	/**
	 * 
	 * @param bestPathFound
	 * @return if the merge actually improved the paths
	 */
	public boolean margePathes(PathList bestPathFound){
		boolean isBetterPathFounded = false;
		for (int i = 0; i < bestPathFound.size(); i++) {
			if (addPath(bestPathFound.get(i))) {
				isBetterPathFounded = true;
			}
		}

		return isBetterPathFounded;
	}

	public void remove(int index){
		pathes.remove(index);
	}

	public int size(){
		return pathes.size();
	}

	@Override
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < size(); i++) {
			stringBuffer.append(i);
			stringBuffer.append(") ");
			stringBuffer.append(get(i).getDistance());
			stringBuffer.append(">\t");
			stringBuffer.append(get(i));
			if (size() - i > 1) {
				stringBuffer.append("\n");
			}
		}
		return stringBuffer.toString();
	}

	private double getDistance(){
		if (size() == 0) {
			return Integer.MAX_VALUE;
		}
		return get(0).getDistance();
	}

	private void invalidate(Path path){
		if (size() > 0 && pathes.get(0).size() != path.size()) {
			throw new IllegalArgumentException("Pathes do not mutch");
		}
	}

	private boolean isPathWarthy(Path path){
		invalidate(path);
		if (path.getDistance() == getDistance()) {
			for (int i = 0; i < pathes.size(); i++) {
				String localPathString = pathes.get(i).toString();
				String pathString = path.toString();
				if (localPathString.equals(pathString)) {
					return false;
				}
				String reversePath = reversePathString(pathes.get(i));
				if (reversePath.equals(pathString)) {
					return false;
				}
			}
			return true;
		}
		if (path.getDistance() < getDistance()) {
			return true;
		}
		return false;
	}

	private String reversePathString(Path path){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("[");
		for (int i = path.size() - 1; i >= 0; i--) {
			stringBuffer.append(path.get(i));
			if (i > 0) {
				stringBuffer.append(", ");
			}
		}
		stringBuffer.append("]");

		return stringBuffer.toString();
	}

}
