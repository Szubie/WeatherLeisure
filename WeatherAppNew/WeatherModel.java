import java.util.*;
import java.util.Random;


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

	WeatherAPI weatherAPI;

	Boolean defaultLocation;

	//Here are the daytrip planning suggestions
	Boolean culture;
	Boolean entertainment;
	Boolean relaxation;
	Boolean shopping;
	Boolean sport;
	Boolean eating;
	Boolean drinking;
	Boolean fashion;
	Boolean exhibitions;
	Boolean justopened;
	Boolean fitness;

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


	public WeatherModel(int numberOfEvents){
		location = "London";

		//Here is the Weather API that gets weather for london
		WeatherAPI weather = new WeatherAPI("2122265");

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
		drinking = false;
		fashion = false;
		exhibitions = false;
		justopened = false;
		fitness = false;
	}



	public String getLocation(){

		return location;
	}

	public void setLocation(String newLocation){
		location = newLocation;
	}


	private void getApiForecast(){

	}

	public void setWeekDay(String day){
		weekDay = day;
	}

	public String getTodayTemperature(){
		String temperature = "";
		temperature = temperatures.get(today).toString() + "\u00b0";
		return temperature;
	}

	public String getTodayWeather(){

		return weather.get(today);
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
				 randomNum =  rand.nextInt(4 - 1 + 1) + 1;
				 timeAndWeather[i] = getWeatherName(randomNum);
			}
			weatherForDay.add(j,timeAndWeather);
		}

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
		int count=0;

		for (String dayWeather : weatherInDay){

			for(Event event : getEventsForWeather(dayWeather)){

				if(count < eventsPerRow){

					if(matchRequirements(event) || notSpecific()){
						events.add(event);
						count++;
					}

				}


			}
			count = 0;
		}
		//Should return 9 events for the day
		return events;

	}


	public Boolean matchRequirements(Event event){

		Boolean result;
		result = culture == event.isCulture() && entertainment == event.isEntertainment() && relaxation == event.isRelaxation() && shopping == event.isShopping() && sport == event.isSport() && eating == event.isEating() && drinking == event.isDrinking() && fashion == event.isFashion() && exhibitions == event.isExhibitions() && justopened == event.isJustOpened() && fitness == event.isFitness();
		//result = entertainment == event.isEntertainment();
		//result = relaxation == event.isRelaxation();
		//result = shopping == event.isShopping();
		//result = sport == event.isSport();
		//result = eating == event.isEating();
		//result = drinking == event.isDrinking();
		//result = fashion == event.isFashion();
		//result = exhibitions == event.isExhibitions();
		//result = justopened == event.isJustOpened();
		//result = fitness == event.isFitness();

		return result;
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

		Event westfield =  new Event("Westfield Center", "westfield.jpg");
		westfield.shopping();
		westfield.fashion();
		wetWeather.add(westfield);

		Event nationalArtGallery = new Event("National Gallery", "nationalArtGallery.jpg");
		nationalArtGallery.culture();
		wetWeather.add(nationalArtGallery);

		Event criterion = new Event("Criterion theatre", "criterion.jpg");
		criterion.culture();
		criterion.entertainment();
		criterion.justopened();
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

		Event thames = new Event("Thames Cruise", "thamesRiverBoat.jpg");
		thames.relaxation();
		thames.entertainment();
		thames.culture();
		dryWeather.add(thames);

		Event radio = new Event("Radio Bar", "radiobar.jpg");
		radio.eating();
		radio.relaxation();
		radio.drinking();
		dryWeather.add(radio);

		Event primerose = new Event("Primrose Hill", "primeroseHill.jpg");
		primerose.relaxation();
		primerose.fitness();
		dryWeather.add(primerose);

		Event hydePark = new Event("Hyde Park", "hydePark.jpg");
		hydePark.relaxation();
		primerose.fitness();
		dryWeather.add(hydePark);

		Event oxfordStreet = new Event("Oxford Street", "oxfordStreet.jpg");
		oxfordStreet.shopping();
		oxfordStreet.entertainment();
		oxfordStreet.fashion();
		dryWeather.add(oxfordStreet);

	}

	public Boolean notSpecific(){

		if(culture){ return false; }
		if(entertainment){ return false; }
		if(relaxation){ return false; }
		if(shopping){ return false; }
		if(sport){ return false; }
		if(eating){ return false; }
		if(drinking){ return false; }
		if(fashion){ return false; }
		if(exhibitions){ return false; }
		if(justopened){ return false; }
		if(fitness){ return false; }
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

	public void setDrinking(Boolean value){
		drinking = !drinking;
	}

	public void setFashion(Boolean value){
		fashion = !fashion;
	}

	public void setExhibitions(Boolean value){
		exhibitions = !exhibitions;
	}

	public void setJustOpened(Boolean value){
		justopened = !justopened;
	}

	public void setFitness(Boolean value){
		fitness = !fitness;
	}

	public void setDefaultLocation(Boolean value){
		defaultLocation = !defaultLocation;
	}

}
