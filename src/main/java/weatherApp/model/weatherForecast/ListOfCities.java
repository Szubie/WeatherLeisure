package weatherApp.model.weatherForecast;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

import java.util.Arrays;
import java.util.HashSet;
import java.io.*;

public class ListOfCities{
	public static HashSet<String> cityList = new HashSet<String>();
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	public ListOfCities(){
		InputStream input = classLoader.getResourceAsStream("citiesInWorldFinal.txt");
		try{
			LineIterator iterator = IOUtils.lineIterator(input, "UTF-8");
			while(iterator.hasNext()){
				String line = iterator.nextLine();
				cityList.add(line);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Couldn't find the file with the list of cities.");
		}
		finally {
			IOUtils.closeQuietly(input);
		}
	}

	public boolean contains(String city){
		return cityList.contains(city);
	}

	public String[] getArrayOfCities(){
		String[] cities =  cityList.toArray(new String[cityList.size()]);
		Arrays.sort(cities);
		return cities;
	}

	public static void main(String[] args){
		ListOfCities test = new ListOfCities();
		for(String string : test.cityList){
			System.out.println(string);
		}

		//System.out.println("Testing equality:");
		//System.out.println("Zymohiria".equals(test.cityList.get(test.cityList.size()-1)));
		//System.out.println(cityList.contains("Zymohiria"));

	}
}