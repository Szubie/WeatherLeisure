package weatherApp.model;

import weatherApp.model.eventRecommender.EventRecommender;
import weatherApp.model.weatherForecast.ListOfCities;
import weatherApp.model.weatherForecast.WeatherForecast;
import weatherApp.model.weatherForecast.WeekDays;

public class WeatherViewModel {

	private WeatherForecast weatherForecast;
	private EventRecommender eventRecommender;
	private WeekDays weekDays;
	private ListOfCities listOfCities;

	public static void main(String[] args){
		WeatherViewModel test = new WeatherViewModel(3);
	}

	public WeatherViewModel(int numberOfRecommendations){
		//Default location is London
		weatherForecast = new WeatherForecast("London, UK");
		eventRecommender = new EventRecommender(weatherForecast, numberOfRecommendations);
		weekDays = new WeekDays();
		listOfCities = new ListOfCities();
	}

	public WeatherForecast getWeatherForecast(){
		return weatherForecast;
	}

	public EventRecommender getEventRecommender(){
		return eventRecommender;
	}

	public WeekDays getWeekDays(){
		return weekDays;
	}

	public ListOfCities getListOfCities(){
		return listOfCities;
	}

}
