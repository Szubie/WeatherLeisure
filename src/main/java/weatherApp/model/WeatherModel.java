package weatherApp.model;

import weatherApp.model.eventRecommender.EventRecommender;
import weatherApp.model.settings.UserSettings;
import weatherApp.model.weatherForecast.WeatherForecast;
import weatherApp.model.weatherForecast.WeekDays;

public class WeatherModel {

	private WeatherForecast weatherForecast;
	private EventRecommender eventRecommender;
	private WeekDays weekDays;
	//private ListOfCities listOfCities;
	private UserSettings settings;

	public static void main(String[] args) {
		WeatherModel test = new WeatherModel(3);
	}

	public WeatherModel(int numberOfRecommendations) {
		settings = new UserSettings();
		weatherForecast = new WeatherForecast(settings.getDefaultLocation());
		eventRecommender = new EventRecommender(weatherForecast, settings,  numberOfRecommendations);
		weekDays = new WeekDays();
		//listOfCities = new ListOfCities();
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

	//public ListOfCities getListOfCities() {
	//	return listOfCities;
	//}

	public UserSettings getSettings(){
		return settings;
	}

}
