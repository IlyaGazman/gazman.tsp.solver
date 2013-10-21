package com.gazman.city_map.test.statistics;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ActionsCounter {
	public static final ActionsCounter instance = new ActionsCounter();
	
	private ArrayList<CounterData> counterDatas = new ArrayList<>();
	private HashMap<String, CounterData> hashMap = new HashMap<>();
	
	public void count(String key) {
		CounterData counterData = hashMap.get(key);
		if(counterData == null){
			counterData = new CounterData(key);
			counterDatas.add(counterData);
			hashMap.put(key, counterData);
		}
		
		counterData.count++;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		for(CounterData counterData : counterDatas){
			stringBuffer.append(NumberFormat.getInstance().format(counterData.count));
			stringBuffer.append(">\t");
			stringBuffer.append(counterData.key);
			stringBuffer.append("\n");
		}
		
		return stringBuffer.toString();
	}
	
	private class CounterData{
		public long count;
		public String key;
		
		public CounterData(String key) {
			this.key = key;
		}
	}
}
