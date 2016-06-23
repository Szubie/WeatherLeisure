package weatherApp.model.eventRecommender;

import weatherApp.model.settings.UserSettings;
import weatherApp.model.weatherForecast.WeatherForecast;

import java.util.Collections;
import java.util.ArrayList;

public class EventRecommender {

	private WeatherForecast weatherForecast;
	private UserSettings settings;

	//Number of suggestions per time in the day
	private int eventsPerRow;

	// ArrayLists of events to suggest, per weather type i.e. Dry weather and Wet weather
	private ArrayList<Event> dryWeather = new ArrayList<Event>();
	private ArrayList<Event> wetWeather = new ArrayList<Event>();


	public static void main(String[] args) {
		EventRecommender test = new EventRecommender(new WeatherForecast("London, UK"), new UserSettings(), 3);
		ArrayList<Event> testArray = test.suggestEvents();
		System.out.println(testArray.size());
	}


	public EventRecommender(WeatherForecast weatherForecast, UserSettings settings, int eventsPerRow) {
		this.weatherForecast = weatherForecast;
		this.eventsPerRow = eventsPerRow;
		this.settings = settings;
		createEvents();
	}


	//Model returns 9 events for the given day, 3 per time in the day
	public ArrayList<Event> suggestEvents() {

		//int dayNum = getDayInt(weekDay);
		//String[] weatherInDay = weatherForDay.get(dayNum);
		String[] weatherInDay = weatherForecast.getWeatherInTheDay();

		ArrayList<Event> events = new ArrayList<Event>();

		for (String dayWeather : weatherInDay) {
			int count = 0;

			ArrayList<Event> suitableEvents = getEventsForWeather(dayWeather);
			//Shuffle to randomise events, in order for greater variety.
			Collections.shuffle(suitableEvents);

			for (Event event : suitableEvents) {

				if (count < eventsPerRow) {

					if (matchRequirements(event)) {

						events.add(event);
						count++;
					}

				}


			}
		}
		//Should return 9 events for the day

		return events;

	}

	private ArrayList<Event> getEventsForWeather(String weather) {
		switch (weather) {
			case "Sun":
				return dryWeather;
			case "Rain":
				return wetWeather;
			case "Cloud":
				return wetWeather;
			case "PartCloud":
				return wetWeather;
			case "Snow":
				return wetWeather;
			case "Thunder":
				return wetWeather;
			case "Hail":
				return wetWeather;
			case "Fog":
				return wetWeather;
			default:
				return dryWeather;
		}
	}

	public void setEventsPerRow(int num) {
		this.eventsPerRow = num;
	}

	private Boolean matchRequirements(Event event) {

		if (settings.getSetting("Culture")) {
			if (event.isCulture()) {
				return true;
			}
		}
		if (settings.getSetting("Entertainment")) {
			if (event.isEntertainment()) {
				return true;
			}
		}
		if (settings.getSetting("Relaxation")) {
			if (event.isRelaxation()) {
				return true;
			}
		}
		if (settings.getSetting("Shopping")) {
			if (event.isShopping()) {
				return true;
			}
		}
		if (settings.getSetting("Sport")) {
			if (event.isSport()) {
				return true;
			}
		}
		if (settings.getSetting("Restaurants")) {
			if (event.isRestaurants()) {
				return true;
			}
		}
		if (settings.getSetting("Bars")) {
			if (event.isBars()) {
				return true;
			}
		}

		return false;
	}


	private void createEvents() {


		Event britishMuseum = new Event("British Museum", "britishMuseum.jpg");
		britishMuseum.culture();
		wetWeather.add(britishMuseum);

		Event westfield = new Event("Westfield Center", "westfield.jpg");
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

		Event radio = new Event("Radio Bar", "radioBar.jpg");
		radio.restaurants();
		radio.relaxation();
		radio.bars();
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
}