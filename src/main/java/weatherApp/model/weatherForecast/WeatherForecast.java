package weatherApp.model.weatherForecast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;


public class WeatherForecast extends Observable implements Runnable{
	
	private String location;
	private WeatherAPI weatherAPI;
	
	private String currentHighTemp;
	private String currentLowTemp;
	private String currentWeatherDescription;
	private String currentAverageTemp;

	private ArrayList<String> weatherDescriptions = new ArrayList<String>();
	private String[] dayTemperatures = new String[5];
	private String[] weatherCodes = new String[5];

	//Temporarily randomly generated dummy data
	private ArrayList<String []> weatherForDay = new ArrayList<String[]>();

	private boolean validLocation;
	private boolean previousAttemptToAccessAPI = false;


	public WeatherForecast(String location){
		this.location = location;
		weatherAPI = new WeatherAPI();
		try{
			getAPIdata(location, weatherAPI);
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("API list out of bounds exception");
			getAPIdata(location, weatherAPI);
		}
		generateWeatherInTheDays();
	}

	private void getAPIdata(String location, WeatherAPI weatherAPI) throws IndexOutOfBoundsException{
		try{
			weatherAPI.processWeatherRequest(location);
		}
		catch(NullPointerException e){
			if(!previousAttemptToAccessAPI){
				System.out.println("Retrying Yahoo Weather API...");
				previousAttemptToAccessAPI = true;
				getAPIdata(location, weatherAPI);
			}
			else{
				System.out.println("Yahoo weatherAPI may have changed. Could not access data.");
				System.exit(1);
			}
		}
		catch(IndexOutOfBoundsException e){
			System.out.println("Out of bounds exception");
			getAPIdata(location, weatherAPI);
		}
		if(weatherAPI.foundRequestedCity()) {
			validLocation = true;
			currentHighTemp = weatherAPI.weatherForecastList.get(0).highTemp;
			currentLowTemp = weatherAPI.weatherForecastList.get(0).lowTemp;
			currentWeatherDescription = weatherAPI.weatherForecastList.get(0).text;
			currentAverageTemp = weatherAPI.weatherForecastList.get(weatherAPI.weatherForecastList.size() - 1).currentTemp;
			dayTemperatures[0] = currentAverageTemp;

			for (int i = 0; i < weatherCodes.length; i++) {
				weatherCodes[i] = weatherAPI.weatherForecastList.get(i).code;

			}

			for (int j = 1; j < dayTemperatures.length; j++) {
				dayTemperatures[j] = weatherAPI.weatherForecastList.get(j).highTemp;
			}

			generateWeatherDescriptions();
			setChanged();
			notifyObservers();
		}
		else{
			validLocation = false;
		}
	
	}

	public String getCurrentHighTemperature(){
		String temperature = "";
		temperature+=currentHighTemp+"\u00b0"+"C";
		return temperature;
	}

	public String getCurrentLowTemperature(){
		String temperature = "";
		temperature+=currentLowTemp+"\u00b0"+"C";
		return temperature;
	}

	public String getTodayWeather(){
		return weatherDescriptions.get(0);
	}

	public String getCurrentWeatherDescription(){
		return currentWeatherDescription;
	}

	public String getCurrentAverageTemp(){
		String avgTemp = "";
		avgTemp+= currentAverageTemp+"\u00b0"+"C";
		return avgTemp;
	}

	private String processCode(String stringCode) {
		int code = Integer.parseInt(stringCode);
		String result = "";
		if (code >= 5 && code <= 12 || code >= 39 && code <= 40 || code == 18) {
			result = "Rain";
		} else if (code >= 19 && code <= 22 || code == 26) {
			result = "Cloud";
		} else if (code >= 27 && code <= 30 || code == 44) {
			result = "PartCloud";
		} else if (code >= 31 && code <= 34 || code == 36) {
			result = "Sun";
		} else if (code <= 2 || code >= 23 && code <= 25) {
			result = "Wind";
		} else if (code >= 3 && code <= 4 || code >= 37 && code <= 38 || code == 47 || code == 45) {
			result = "Lightning";
		} else if (code >= 13 && code <= 17 || code == 35 || code >= 41 && code <= 43 || code == 46) {
			result = "Snow";
		}
		return result;
	}

	public String getWeather(int day){
		return weatherDescriptions.get(day);
	}

	private void generateWeatherDescriptions(){
		weatherDescriptions.clear();
		for(int i=0; i<weatherCodes.length; i++){
			weatherDescriptions.add(processCode(weatherCodes[i]));
		}
	}

	private void generateWeatherInTheDays(){
		Random rand = new Random();
		int randomNum;

		for(int j=0; j<4; j++){
		String[] timeAndWeather = new String[3];

			for(int i=0; i<3; i++){
				 randomNum =  rand.nextInt(4) + 1;
				 timeAndWeather[i] = getWeatherName(randomNum);
			}
			weatherForDay.add(j,timeAndWeather);
		}

	}

	public String[] getWeatherInTheDay(){

		//return weatherForDay.get(getDayInt(weekDay));
		return weatherForDay.get(0);
	}

	private String getWeatherName(int weatherCode){

	  switch (weatherCode) {
	            case 1:  return "Sun";
	            case 2:  return "Rain";
	            case 3:  return "Cloud";
	            case 4:  return "PartCloud";
	            default: return "Null";
	        }
	}

	public String getLocation(){
		return location;
	}

	public void setLocation(String location){
		this.location = location;
		//getAPIdata(location, weatherAPI);
		new Thread(this).start();
	}

	public boolean isValidLocation(){
		return validLocation;
	}


	public static void main(String[] args){
		WeatherForecast test = new WeatherForecast("London, UK");
		System.out.println(test.location);
		System.out.println(test.dayTemperatures[0]);
		System.out.println(test.currentWeatherDescription);
	}

	@Override
	public void run() {
		getAPIdata(location, weatherAPI);
	}
}