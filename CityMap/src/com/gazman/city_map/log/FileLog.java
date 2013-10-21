package com.gazman.city_map.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.gazman.city_map.out.OutputView;

/**
 * @author Ilya Gazman
 *
 */
public class FileLog {

	public static boolean disable = false;
	private OutputView outputView;

	public static final FileLog instance = new FileLog();

	private FileLog(){}
	
	private long startingTime = System.currentTimeMillis();

	public void log(Object data){
		if(disable){
			return;
		}
		long timePass = System.currentTimeMillis() - startingTime;
		String dataString = timePass + ">\t" + data; 
		System.out.println(dataString.toString());
		if (outputView != null) {
			outputView.print(dataString.toString());
		}
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("out.txt", true)));
			out.println(dataString.toString());
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setOutputView(OutputView outputView){
		this.outputView = outputView;
	}
}
