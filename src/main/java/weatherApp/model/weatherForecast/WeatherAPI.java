package weatherApp.model.weatherForecast;

import java.net.*;
import java.util.regex.*;
import java.util.ArrayList;
import java.io.*;

public class WeatherAPI
{
	static String theWeatherRSS;
	static String theCity;
	static ArrayList<Forecast> weatherForecastList;
	
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
		parseWeather(theWeatherRSS);
	}

	void parseWeather(String weatherHTML)
	{
		weatherForecastList = new ArrayList<Forecast>();
		int startIndex = 0;


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

	/*
	GET http://social.yahooapis.com/v1/user/abcdef123/profile?format=json   
	Authorization: OAuth   
	realm="yahooapis.com",  
	  oauth_consumer_key="dj0yJmk9aVVSTm1HaldkTDZKJmQ9WVdrOVMwOVhZakZOTkhNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0yMA--",  
	  oauth_nonce="24829.2331",  
	  oauth_signature_method="HMAC-SHA1",  
	  oauth_timestamp="1219450170",  
	  oauth_token="A%3DuqkiebGpiTJl7ThQxU.jDXXaETYyfEy3xAKPyoavokwOOcZcz8Xs_l1Nvnl._  	
	    KmCEVCeLkxxT1Y6BgRqf5f98sQWHklBM_anetveR7okK_M_5XEmQ1_1reo3UgKQULT_dQT8Gao3.  
	    Rrgz5rJxgmnYrhdWWdfgTdMQVzpbJT2aGkz59NTK1O8yXVE1EvZUCqju7WiFYu.WHNEw.9TWq3g--",  
	  oauth_version="1.0",  
	  oauth_signature="O2AQipLITO0aYHKZc9266RzC94%3D"  
	*/
}

/*
//Fill in your consumer Key & Secret from Yahoo's App & adjust location as needed. 
//This Key & Secret combination is invalid & won't work for you
String consumerKey = "dj0yJmk9aVVSTm1HaldkTDZKJmQ9WVdrOVMwOVhZakZOTkhNbWNHbzlNQS0tJnM9Y29uc3VtZXJzZWNyZXQmeD0yMA--";
String consumerSecret = "a4dd0bf004d6f6aed83c7b93513d161ec1b0fb4b";
String locationToQuery = "44418"; //Can be zip code or anything that works in the query select woeid from geo.places(1) where text=<Your Location>


var makeSignedRequest = function(ck,cs,loc) {

    String encodedurl = "https://query.yahooapis.com/v1/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22"+loc+"%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    var accessor = { consumerSecret: cs, tokenSecret: ""};          
    var message = { action: encodedurl, method: "GET", parameters: [["oauth_version","1.0"],["oauth_consumer_key",ck]]};

    OAuth.setTimestampAndNonce(message);
    OAuth.SignatureMethod.sign(message, accessor);

    ArrayList<String> parameterMap = OAuth.getParameterMap(message);
    String baseStr = OAuth.decodeForm(OAuth.SignatureMethod.getBaseString(message));           
    String theSig = "";

    if (parameterMap.parameters) {
        for (var item in parameterMap.parameters) {
            for (var subitem in parameterMap.parameters[item]) {
                if (parameterMap.parameters[item][subitem].equals("oauth_signature")){
                    theSig = parameterMap.parameters[item][1];                    
                    break;                      
                }
            }
        }
    }

    ArrayList<String[]> paramList = baseStr[2][0].split("&");
    paramList.add("oauth_signature="+ encodeURIComponent(theSig));
    Collections.sort(paramList, new Comparator<String[]>(){
    	@Override
    	public int compare(String[] a, String[] b){
	        if (a[0] < b[0]) return -1;
	        if (a[0] > b[0]) return 1;
	        if (a[1] < b[1]) return  -1;
	        if (a[1] > b[1]) return 1;
	        return 0;
	    })
	};

    String locString = "";
    for (String x : paramList) {
        locString += paramList[x] + "&";                
    }

    var finalStr = baseStr[1][0] + "?" + locString.slice(0,locString.length - 1);

    return finalStr;
};

//Use the encodedURL to make your request
var encodedURL = makeSignedRequest(consumerKey, consumerSecret, locationToQuery); 
*/