package weatherApp.model.settings;

import java.util.ArrayList;
import java.util.prefs.Preferences;

/**
 * Created by Benjy on 19/06/2016.
 */
public class UserSettings {

	private Preferences prefs = Preferences.userNodeForPackage(UserSettings.class);
	private static final boolean DEFAULT_VALUE = true;

	//Here are the daytrip planning suggestions
	private ArrayList<Setting> settings = new ArrayList<Setting>();
	private Setting culture;
	private Setting entertainment;
	private Setting relaxation;
	private Setting shopping;
	private Setting sport;
	private Setting eating;
	private Setting drinking;

	private String defaultLocation;


	public UserSettings(){
		culture = buildSetting("Culture");
		entertainment = buildSetting("Entertainment");
		relaxation = buildSetting("Relaxation");
		shopping = buildSetting("Shopping");
		sport = buildSetting("Sport");
		eating = buildSetting("Restaurants");
		drinking = buildSetting("Bars");
		loadDefaultLocation();
	}

	private void loadDefaultLocation(){
		defaultLocation = prefs.get("Location", "London, UK");
	}

	private void saveDefaultLocation(String location){
		prefs.put("Location", location);
	}

	private Setting buildSetting(String name){

		Setting setting  = new Setting(name);
		assignSettingValue(setting, DEFAULT_VALUE);
		settings.add(setting);

		return setting;
	}

	private void assignSettingValue(Setting setting, boolean DEFAULT_VALUE){
		setting.setValue(prefs.getBoolean(setting.getName(), DEFAULT_VALUE));
	}

	//Set events tags methods
	public void setCulture(Boolean value) {
		culture.setValue(value);
		setSettingPref(culture, value);
	}

	public void setEntertainment(Boolean value) {
		entertainment.setValue(value);
		setSettingPref(entertainment, value);
	}

	public void setRelaxation(Boolean value) {
		relaxation.setValue(value);
		setSettingPref(relaxation, value);
	}

	public void setShopping(Boolean value) {
		shopping.setValue(value);
		setSettingPref(shopping, value);
	}

	public void setSport(Boolean value) {
		sport.setValue(value);
		setSettingPref(sport, value);
	}

	public void setEating(Boolean value) {
		eating.setValue(value);
		setSettingPref(eating, value);
	}

	public void setDrinking(Boolean value) {
		drinking.setValue(value);
		setSettingPref(drinking, value);
	}

	private void setSettingPref(Setting setting, Boolean value){
		prefs.putBoolean(setting.getName(), value);
	}

	public Boolean getSetting(String settingName){
		for(Setting setting : settings){
			if(setting.getName().equals(settingName)){
				return setting.getValue();
			}
		}
		return DEFAULT_VALUE;
	}

	public String getDefaultLocation(){
		return defaultLocation;
	}

	public void setDefaultLocation(String newLocation){
		defaultLocation = newLocation;
		saveDefaultLocation(newLocation);
	}
}
