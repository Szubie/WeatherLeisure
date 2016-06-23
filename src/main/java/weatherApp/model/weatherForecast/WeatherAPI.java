package weatherApp.model.weatherForecast;

import java.net.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.io.*;

public class WeatherAPI
{
	private ArrayList<Forecast> weatherForecastList;
	private boolean validLocation;

	private String theWeatherRSS;
	private String theCity;
	
	public class Forecast
	{
		String lowTemp;
		String highTemp;
		String text;
		String currentTemp;
		String code;
	}

	public WeatherAPI(String city)
	{
		weatherForecastList = new ArrayList<Forecast>();
		processWeatherRequest(city);
	}

	public WeatherAPI(){
		weatherForecastList = new ArrayList<Forecast>();
	}

	void processWeatherRequest(String city){
		weatherForecastList.clear();
		String parsedLocation = "";
		String[] processLocation = city.split(" ");
		if(processLocation.length==0){
			parsedLocation=city;
		}
		else{
			for(String s : processLocation){
				parsedLocation += s + "+";
			}
		}
		parsedLocation = parsedLocation.substring(0,parsedLocation.length()-1);
		theWeatherRSS = getWeatherAsRSS(parsedLocation);
		parseWeather(theWeatherRSS, city);
		isValidLocation(city, theWeatherRSS);
	}

	void parseWeather(String weatherHTML, String city)
	{
		int startIndex = 0;

		if (isValidLocation(city, weatherHTML)){
			validLocation = true;
		}
		else{
			validLocation = false;
		}


		while(true)
		{
			try{
				startIndex = weatherHTML.indexOf("<yweather:forecast", startIndex);
				// found a weather forecast
					int endIndex = weatherHTML.indexOf(">", startIndex);
					String weatherForecast = weatherHTML.substring(startIndex, endIndex+1);

					// get temp forecast				
					String lowString = getValueForKey(weatherForecast, "low");
					String highString = getValueForKey(weatherForecast, "high");
					String text = getValueForKey(weatherForecast, "text");
					String code = getValueForKey(weatherForecast, "code");

					Forecast fore = new Forecast();
					fore.lowTemp = lowString;
					fore.highTemp = highString;
					fore.text = text;
					fore.code = code;
					weatherForecastList.add(fore);
					
					// move to end of this forecast
					startIndex = endIndex-1;
			}
			catch(StringIndexOutOfBoundsException e){
				break;
			}
			
		}
		int currentStartIndex=0;
			currentStartIndex = weatherHTML.indexOf("<yweather:condition", currentStartIndex);
			if(currentStartIndex != -1){
				int currentEndIndex = weatherHTML.indexOf(">", currentStartIndex);
				String currentWeatherForecast = weatherHTML.substring(currentStartIndex, currentEndIndex+1);
				String currentString = getValueForKey(currentWeatherForecast, "temp");
				Forecast fore = new Forecast();
				fore.currentTemp = currentString;
				weatherForecastList.add(fore);

		}

	}

	String getValueForKey(String theString, String keyString)
	{
		int startIndex = theString.indexOf(keyString);
		startIndex = theString.indexOf("\"", startIndex);
		int endIndex = theString.indexOf("\"", startIndex+1);
		return theString.substring(startIndex+1, endIndex);
	}

	String getWeatherAsRSS(String city)
	{
		try{
			/*
			Adapted from: http://stackoverflow.com/questions/1381617/simplest-way-to-correctly-load-html-from-web-page-into-a-string-in-java
			Answer provided by: erickson
			*/

			//URL url = new URL("http://xml.weather.yahoo.com/forecastrss?w="+city+"&u=c");
			URL url = new URL("https://query.yahooapis.com/v1/public/yql?format=xml&q=select+title%2C+units.temperature%2C+item.forecast%2C+item.condition%0Afrom+weather.forecast%0Awhere+woeid+in+%28select+woeid+from+geo.places%281%29+where+placetype%3D%22town%22+and+text%3D%22"+city+"%22%29%0Aand+u+%3D+%27C%27%0Alimit+5%0A%7C%0Asort%28field%3D%22item.forecast.date%22%2C+descending%3D%22false%22%29%0A%3B");
			URLConnection con = url.openConnection();
			Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
			Matcher m = p.matcher(con.getContentType());
			/* If Content-Type doesn't match this pre-conception, choose default and 
			 * hope for the best. */
			String charset = m.matches() ? m.group(1) : "ISO-8859-1";
			Reader r = new InputStreamReader(con.getInputStream(), charset);
			StringBuilder buf = new StringBuilder();
			while (true) {
			  int ch = r.read();
			  if (ch < 0)
				break;
			  buf.append((char) ch);
			}
			return buf.toString();
		}
		catch(Exception e) {System.err.println("Weather API Exception: "+e);}
		return null;
	}

	private boolean isValidLocation(String cityName, String theWeatherRSS){
		int startIndex = 0;
		startIndex = theWeatherRSS.indexOf("<title>Yahoo! Weather - ", startIndex);
		startIndex = theWeatherRSS.indexOf(">", startIndex)+1;
		int endIndex = theWeatherRSS.indexOf("</title>", startIndex);
		String location = theWeatherRSS.substring(startIndex, endIndex);
		location = location.substring(17,location.length());
		String[] splitLocation = location.split(", ");
		if(cityName.split(", ").length>1){
			cityName = cityName.split(", ")[0];
		}
		if(splitLocation[0].equals(cityName)){
			return true;
		}
		else{
			return false;
		}
	}

	boolean foundRequestedCity(){
		return validLocation;
	}

	ArrayList<Forecast> getWeatherForecastList(){
		return weatherForecastList;
	}

	public static void main(String[] args){
		WeatherAPI weatherAPI = new WeatherAPI();
		weatherAPI.processWeatherRequest("London, UK");
		for(Forecast forecast : weatherAPI.weatherForecastList){
			System.out.println(forecast.code);
		}

	}
}
