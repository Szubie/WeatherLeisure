package weatherApp.model.weatherForecast;

import java.util.ArrayList;
import java.util.Random;


public class WeatherForecast{
	
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


	public static void main(String[] args){
		WeatherForecast test = new WeatherForecast("London, UK");
		System.out.println(test.location);
		System.out.println(test.dayTemperatures[0]);
		System.out.println(test.currentWeatherDescription);
	}

	public WeatherForecast(String location){
		this.location = location;
		try{
			getAPIdata(location);
		}
		catch(IndexOutOfBoundsException e){
			getAPIdata(location);
		}
		generateWeatherInTheDays();
	}

	public void getAPIdata(String location) throws IndexOutOfBoundsException{
		try{
			weatherAPI = new WeatherAPI(location);
		}
		catch(NullPointerException e){
			System.out.println("Yahoo weatherAPI has changed. Data is unavailable until program updated.");
			System.exit(1);
		}
		currentHighTemp=weatherAPI.weatherForecastList.get(0).highTemp;
		currentLowTemp=weatherAPI.weatherForecastList.get(0).lowTemp;
		currentWeatherDescription=weatherAPI.weatherForecastList.get(0).text;
		currentAverageTemp = weatherAPI.weatherForecastList.get(weatherAPI.weatherForecastList.size()-1).currentTemp;
		dayTemperatures[0] = currentAverageTemp;

		for(int i=0; i<weatherCodes.length; i++){
			weatherCodes[i] = weatherAPI.weatherForecastList.get(i).code;

		}

		for(int j=1; j<dayTemperatures.length; j++){
			dayTemperatures[j] = WeatherAPI.weatherForecastList.get(j).highTemp;
		}

		generateWeatherDescriptions();
	
	}

	private void updateAPIdata(String location){
		weatherAPI.weatherForecastList.clear();
		try{
			weatherAPI = new WeatherAPI(location);
		}
		catch(NullPointerException e){
			System.out.println("Yahoo weatherAPI has changed. Data is unavailable until program updated.");
			System.exit(1);
		}
		try{
			getAPIdata(location);
		}
		catch(IndexOutOfBoundsException e){
			getAPIdata(location);
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

	private String processCode(String stringCode){
		int code = Integer.parseInt(stringCode);
		String result="";
		if(code<=18 || code==35 || code>=37 && code<=43 || code>=45 &&code<=47){
			result="Rain";
		}
		else if(code>=19 && code<=26){
			result="Cloud";
		}
		else if( code>=27 && code<=30 || code==44){
			result="PartCloud";
		}
		else if(code>=31 && code<= 34 || code==36){
			result="Sun";
		}
		return result;
	}

	//Below are the stterer accessed via the setting page
	// Setting the main preferences to base the day planning suggestions on
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
		updateAPIdata(location);
	}
}