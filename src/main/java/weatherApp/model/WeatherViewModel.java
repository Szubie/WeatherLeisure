package weatherApp.model;

import weatherApp.model.eventRecommender.EventRecommender;
import weatherApp.model.settings.UserSettings;
import weatherApp.model.weatherForecast.ListOfCities;
import weatherApp.model.weatherForecast.WeatherForecast;
import weatherApp.model.weatherForecast.WeekDays;

public class WeatherViewModel {

	private WeatherForecast weatherForecast;
	private EventRecommender eventRecommender;
	private WeekDays weekDays;
	private ListOfCities listOfCities;
	private UserSettings settings;

	public static void main(String[] args) {
		WeatherViewModel test = new WeatherViewModel(3);
	}

	public WeatherViewModel(int numberOfRecommendations) {
		settings = new UserSettings();
		weatherForecast = new WeatherForecast(settings.getDefaultLocation());
		eventRecommender = new EventRecommender(weatherForecast, settings,  numberOfRecommendations);
		weekDays = new WeekDays();
		listOfCities = new ListOfCities();
	}

	public WeatherForecast getWeatherForecast() {
		return weatherForecast;
	}

	public EventRecommender getEventRecommender() {
		return eventRecommender;
	}

	public WeekDays getWeekDays() {
		return weekDays;
	}

	public ListOfCities getListOfCities() {
		return listOfCities;
	}

	public UserSettings getSettings(){
		return settings;
	}

}
