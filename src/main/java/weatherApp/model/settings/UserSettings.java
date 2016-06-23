package weatherApp.model.settings;

import java.util.ArrayList;
import java.util.Observable;
import java.util.prefs.Preferences;

/**
 * Created by Benjy on 19/06/2016.
 */
public class UserSettings extends Observable {

	private ArrayList<Setting> settings = new ArrayList<Setting>();
	private String defaultLocation;

	private Preferences prefs = Preferences.userNodeForPackage(UserSettings.class);
	private static final boolean DEFAULT_VALUE = true;


	public UserSettings(){
		buildSetting("Culture");
		buildSetting("Entertainment");
		buildSetting("Relaxation");
		buildSetting("Shopping");
		buildSetting("Sport");
		buildSetting("Restaurants");
		buildSetting("Bars");
		loadDefaultLocation();
	}

	private void loadDefaultLocation(){
		defaultLocation = prefs.get("Location", "London, UK");
	}

	private void saveDefaultLocation(String location){
		prefs.put("Location", location);
	}

	private void buildSetting(String name){
		Setting setting  = new Setting(name);
		assignSettingValue(setting, DEFAULT_VALUE);
		settings.add(setting);
	}

	private void assignSettingValue(Setting setting, boolean DEFAULT_VALUE){
		setting.setValue(prefs.getBoolean(setting.getName(), DEFAULT_VALUE));
	}

	public Boolean getSetting(String settingName){
		for(Setting setting : settings){
			if(setting.getName().equals(settingName)){
				return setting.getValue();
			}
		}
		return DEFAULT_VALUE;
	}

	//Set events tags methods
	public void setSetting(String settingName){
		for(Setting setting : settings){
			if(setting.getName().equals(settingName)){
				boolean newValue = !setting.getValue();
				setting.setValue(newValue);
				setSettingPref(setting,newValue);
			}
		}
	}

	private void setSettingPref(Setting setting, Boolean value){
		prefs.putBoolean(setting.getName(), value);
	}

	public String getDefaultLocation(){
		return defaultLocation;
	}

	public void setDefaultLocation(String newLocation){
		defaultLocation = newLocation;
		saveDefaultLocation(newLocation);
	}
}
