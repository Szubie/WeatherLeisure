import java.util.*;
import java.util.Random;
import java.util.Collections;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class WeatherModel {

	int SUGGEST_NUMBER = 3;

	Random rand = new Random();

	String location;
	ArrayList<Integer> temperatures = new ArrayList<Integer>();
	ArrayList<String> weather = new ArrayList<String>();

	//Numeric representation of today's day of the week
	int today;
	//String representation
	String todayString;

	String currentHighTemp;
	String currentLowTemp;
	String currentWeatherDescription;

	WeatherAPI weatherAPI;

	Boolean defaultLocation;

	//Here are the daytrip planning suggestions
	Boolean culture;
	Boolean entertainment;
	Boolean relaxation;
	Boolean shopping;
	Boolean sport;
	Boolean eating;

	//True when user doesn't select any of the refining options
	Boolean notSpecific;

	//Number of suggestions per time in the day
	int eventsPerRow;

	// ArrayLists of events to suggest, per weather type i.e. Dry weather and Wet weather
	ArrayList<Event> dryWeather = new ArrayList<Event>();
	ArrayList<Event> wetWeather = new ArrayList<Event>();

	String weekDay;
    //MAY BE A TEMP SOLUTION TODO
    String[] dayWeathers;
	
	ArrayList<String []> weatherForDay = new ArrayList<String[]>();
	ArrayList<String> dayOverview = new ArrayList<String>();
	

	Date date;
    String theDate;
    String theDay;

	public WeatherModel(int numberOfEvents){
		location = "London";

		//Find the current date.
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd, MMMM, YYYY");
        date = new Date();
        theDate = dateFormat.format(date);
        theDay = dateFormat.format(date).substring(0,3);
        setWeekDay(theDay);


		//Here is the Weather API that gets weather for london
		WeatherAPI weather = new WeatherAPI("44418");
		currentHighTemp=weatherAPI.weatherForecastList.get(0).highTemp;
		currentLowTemp=weatherAPI.weatherForecastList.get(0).lowTemp;
		currentWeatherDescription=weatherAPI.weatherForecastList.get(0).text;

		getTestData();
		weatherAPI = new WeatherAPI(location);
		generateWeatherInTheDays();

		eventsPerRow = numberOfEvents;
		
		//generateWeatherOVerview();
		createEvents();
		defaultLocation = true;
		culture = false;
		entertainment = false;
		relaxation = false;
		shopping = false;
		sport= false;
		eating = false;
	}



	public String getLocation(){
		return location;
	}

	public void setLocation(String newLocation){
		location = newLocation;
	}

	public String getDate(){
		return theDate;
	}

	public String getDay(){
		return theDay;
	}


	private void getApiForecast(){

	}

	public void setWeekDay(String day){
		weekDay = day;
	}

	public String getCurrentHighTemperature(){
		String temperature = "";
		temperature+=currentHighTemp+"\u00b0";
		return temperature;
	}

	public String getCurrentLowTemperature(){
		String temperature = "";
		temperature+=currentLowTemp+"\u00b0";
		return temperature;		
	}	

	public String getTodayWeather(){
		return weather.get(today);
	}

	public String getCurrentWeatherDescription(){
		return currentWeatherDescription;
	}

	//Below are the stterer accessed via the setting page
	// Setting the main preferences to base the day planning suggestions on
	public String getWeather(int day){
		return weather.get(day);
	}

	private void getTestData(){
		today = 0;
		temperatures.add(23);
		temperatures.add(22);
		temperatures.add(27);
		weather.add("Rain");
		weather.add("Sun");
		weather.add("Cloud");
		weather.add("PartCloud");

	}

	public void generateWeatherInTheDays(){
		
		int randomNum;

		for(int j=0; j<4; j++){
		String[] timeAndWeather = new String[3];

			for(int i=0; i<3; i++){		 
				 randomNum =  rand.nextInt(4) + 1;
				 timeAndWeather[i] = getWeatherName(randomNum);
			}
			weatherForDay.add(j,timeAndWeather);
		}
		
/*
		// test.
		String[] arrayHighTemp = new String[3];

		for(int i = 0; i < weatherAPI.weatherForecastList.size(); i++){
			String dailyHighTemperature = weatherAPI.weatherForecastList.get(i).highTemp;
			arrayHighTemp[i] = dailyHighTemperature;
			weatherForDay.add(i, arrayHighTemp);
		}*/
	}
	
	public String[] getWeatherInTheDay(){
		
		return weatherForDay.get(getDayInt(weekDay));
	}

	//Model returns 9 events for the given day, 3 per time in the day	
	public ArrayList<Event> suggestEvents(){

		ArrayList<Event> events = new ArrayList<Event>();
		int dayNum = getDayInt(weekDay);
		//String[] weatherInDay = dayWeatherForecast[dayNum];	
		String[] weatherInDay = weatherForDay.get(dayNum);


		for (String dayWeather : weatherInDay){
			int count=0;

			ArrayList<Event> suitableEvents = getEventsForWeather(dayWeather);
			//Shuffle to randomise events, in order for greater variety.
			Collections.shuffle(suitableEvents);

			for(Event event : suitableEvents){
				
				if(count < eventsPerRow){

					if(matchRequirements(event) || notSpecific()){

						events.add(event);
						count++;	
					}

				}


			}
		}
		//Should return 9 events for the day

		return events;

	}	


	public Boolean matchRequirements(Event event){

		if(culture){
			if(event.isCulture()){
				return true;
			}
		}
		if(entertainment){
			if(event.isEntertainment()){
				return true;
			}
		}
		if(relaxation){
			if(event.isRelaxation()){
				return true;
			}
		}
		if(shopping){
			if(event.isShopping()){
				return true;
			}
		}
		if(sport){
			if(event.isSport()){
				return true;
			}
		}
		if(eating){
			if(event.isEating()){
				return true;
			}
		}
		return false;
	}


	private ArrayList<Event> getEventsForWeather(String weather){
		  switch (weather) {
		            case "Sun":  return dryWeather;
		            case "Rain":  return wetWeather;
		            case "Cloud":  return wetWeather;
		            case "PartCloud":  return wetWeather;
		            case "Snow":  return wetWeather;
		            case "Thunder":  return wetWeather;
		            case "Hail":  return wetWeather;
		           	case "Fog":  return wetWeather;
		            default: return dryWeather;
		        }
	}


	public void getDayVal(String day){
	}




	public String getDayVal(int dayNum){
		  int dayOfWeek = dayNum;
		  if(dayNum>7){
		  	dayOfWeek = dayNum - 7;
		  }

		  switch (dayOfWeek) {
		            case 1:  return "Mon";
		            case 2:  return "Tue";
		            case 3:  return "Wed";
		            case 4:  return "Thur";
		            case 5:  return "Fri";
		            case 6:  return "Sat";
		            case 7:  return "Sun";
		            default: return "Null";
		        }
	}
	
	
	
	public int getDayInt(String dayName){
		  switch (dayName) {
		            case "Mon":  return 0;
		            case "Tue":  return 1;
		            case "Wed":  return 2;
		            case "Thur":  return 3;
					default: return 0;
		        }
	}



	public String getWeatherName(int weatherCode){

		  switch (weatherCode) {
		            case 1:  return "Sun";
		            case 2:  return "Rain";
		            case 3:  return "Cloud";
		            case 4:  return "PartCloud";
		            default: return "Null";
		        }
	}


	public String[] getNextThreeDays(){

		String[] weekDays = new String[3];
		for(int i=0; i<3; i++){
			weekDays[i] = getDayVal(today+i);
		}

		return weekDays;
	}


	private void createEvents(){


		Event britishMuseum = new Event("British Museum", "britishMuseum.jpg");
		britishMuseum.culture();
		wetWeather.add(britishMuseum);

		Event westfield =  new Event("Westfield", "westfield.jpg");
		westfield.shopping();
		wetWeather.add(westfield);

		Event nationalArtGallery = new Event("National ", "nationalArtGallery.jpg");
		nationalArtGallery.culture();
		wetWeather.add(nationalArtGallery);

		Event criterion = new Event("Criterion theatre", "criterion.jpg");
		criterion.culture();
		criterion.entertainment();
		wetWeather.add(criterion);

		Event vue = new Event("Vue Cinema", "vueCinema.jpg");
		vue.culture();
		vue.entertainment();
		wetWeather.add(vue);

		Event cuttySark = new Event("Cutty Sarl", "cuttySark.jpg");
		cuttySark.culture(); 
		wetWeather.add(cuttySark);

		Event chelseaGame = new Event("Chelsea Game", "chelsea.jpg");
		chelseaGame.sport();
		wetWeather.add(chelseaGame);
		dryWeather.add(chelseaGame);


		Event thames = new Event("Thames ", "thamesRiverBoat.jpg");
		thames.relaxation();
		thames.entertainment();
		thames.culture();
		dryWeather.add(thames);

		Event radio = new Event("Radio ", "radiobar.jpg");
		radio.eating();
		radio.relaxation();
		dryWeather.add(radio);

		Event primerose = new Event("Primerose", "primeroseHill.jpg");
		primerose.relaxation();
		dryWeather.add(primerose);

		Event hydePark = new Event("Hyde Park", "hydePark.jpg");
		hydePark.relaxation(); 
		dryWeather.add(hydePark);

		Event oxfordStreet = new Event("Oxford Street", "oxfordStreet.jpg");
		oxfordStreet.shopping();
		oxfordStreet.entertainment(); 
		dryWeather.add(oxfordStreet);


	}

	public Boolean notSpecific(){

		if(culture){ return false; }
		if(entertainment){ return false; }
		if(relaxation){ return false; }
		if(shopping){ return false; }
		if(sport){return false; }
		if(eating){ return false; } 
		return true;
	}

	//Set events tags methods
	public void setCulture(Boolean value){
		culture = !culture;
	}

	public void setEntertainment(Boolean value){
		entertainment = !entertainment;
	}

	public void setRelaxation(Boolean value){
		relaxation = !relaxation;
	}

	public void setShopping(Boolean value){
		shopping = !shopping;
	}

	public void setSport(Boolean value){

		sport = !sport;
		if(sport){
			System.out.println("Sport has become true");
		}
	}

	public void setEating(Boolean value){
		eating = !eating;
	}

	public void setDefaultLocation(Boolean value){
		defaultLocation = !defaultLocation;
	}

}