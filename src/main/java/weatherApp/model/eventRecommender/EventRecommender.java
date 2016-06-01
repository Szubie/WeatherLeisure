package weatherApp.model.eventRecommender;

import weatherApp.model.weatherForecast.WeatherForecast;

import java.util.Random;
import java.util.Collections;
import java.util.ArrayList;


public class EventRecommender{

	private WeatherForecast weatherForecast;

	private Random rand = new Random();

	//Here are the daytrip planning suggestions
	private Boolean culture;
	private Boolean entertainment;
	private Boolean relaxation;
	private Boolean shopping;
	private Boolean sport;
	private Boolean eating;
	private Boolean drinking;

	//Currently doesn't do anything
	private Boolean defaultLocation;

	//True when user doesn't select any of the refining options
	private Boolean notSpecific;

	//Number of suggestions per time in the day
	private int eventsPerRow;

	// ArrayLists of events to suggest, per weather type i.e. Dry weather and Wet weather
	private ArrayList<Event> dryWeather = new ArrayList<Event>();
	private ArrayList<Event> wetWeather = new ArrayList<Event>();


	public static void main(String[] args){
		EventRecommender test = new EventRecommender(new WeatherForecast("London, UK"), 3);
		ArrayList<Event> testArray = test.suggestEvents();
		System.out.println(testArray.size());
	}


	public EventRecommender(WeatherForecast weatherForecast, int eventsPerRow){
		this.weatherForecast = weatherForecast;
		this.eventsPerRow = eventsPerRow;
		createEvents();
		culture = false;
		entertainment = false;
		relaxation = false;
		shopping = false;
		sport= false;
		eating = false;
		drinking = false;
	}


	//Model returns 9 events for the given day, 3 per time in the day
	public ArrayList<Event> suggestEvents(){

		//int dayNum = getDayInt(weekDay);
		//String[] weatherInDay = weatherForDay.get(dayNum);
		String[] weatherInDay = weatherForecast.getWeatherInTheDay();

		ArrayList<Event> events = new ArrayList<Event>();

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

	public void setEventsPerRow(int num){
		this.eventsPerRow=num;
	}

	private Boolean matchRequirements(Event event){

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
		if(drinking){
			if(event.isDrinking()){
				return true;
			}
		}
		
		return false;
	}


	private void createEvents(){


		Event britishMuseum = new Event("British Museum", "britishMuseum.jpg");
		britishMuseum.culture();
		wetWeather.add(britishMuseum);

		Event westfield =  new Event("Westfield Center", "westfield.jpg");
		westfield.shopping();
		wetWeather.add(westfield);

		Event nationalArtGallery = new Event("National Gallery", "nationalArtGallery.jpg");
		nationalArtGallery.culture();
		wetWeather.add(nationalArtGallery);

		Event criterion = new Event("Criterion Theatre", "criterion.jpg");
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
		dryWeather.add(primerose);

		Event hydePark = new Event("Hyde Park", "hydePark.jpg");
		hydePark.relaxation();
		dryWeather.add(hydePark);

		Event oxfordStreet = new Event("Oxford Street", "oxfordStreet.jpg");
		oxfordStreet.shopping();
		oxfordStreet.entertainment();
		dryWeather.add(oxfordStreet);


	}

	private Boolean notSpecific(){

		if(culture){ return false; }
		if(entertainment){ return false; }
		if(relaxation){ return false; }
		if(shopping){ return false; }
		if(sport){return false; }
		if(eating){ return false; }
		if(drinking){ return false; }
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
	}

	public void setEating(Boolean value){
		eating = !eating;
	}

	public void setDrinking(Boolean value){
		drinking = !drinking;
	}

	public void setDefaultLocation(Boolean value){
		defaultLocation = !defaultLocation;
	}
}