package com.gazman.city_map.engines.next_engine;

/**
@author Ilya Gazman: The algorithm is from Applied Combinatorics, by Alan Tucker, based on code from koders.com, with some improvments. 
*/
public class Combination {
	private int n, r;
	private int[] index;
	private boolean hasNext;
	private int[] result;

	public void init(int n, int r){
		this.n = n;
		this.r = r;
		index = new int[r];
		for (int i = 0; i < r; i++)
			index[i] = i;
		result = new int[r];
		hasNext = true;
	}

	public boolean hasNext() {
		return hasNext;
	}

	private void moveIndex() {
		int i = rightmostIndexBelowMax();
		if (i >= 0) {
			index[i] = index[i] + 1;
			for (int j = i + 1; j < r; j++)
				index[j] = index[j - 1] + 1;
		}
		else
			hasNext = false;
	}

	public int[] next() {
		if (!hasNext)
			return null;
		
		for (int i = 0; i < r; i++)
			result[i] = index[i];
		moveIndex();
		return result;
	}

	/**
	 * @return return int,the index which can be bumped up.
	 */
	private int rightmostIndexBelowMax() {
		for (int i = r - 1; i >= 0; i--)
			if (index[i] < n - r + i)
				return i;
		return -1;
	}
}
