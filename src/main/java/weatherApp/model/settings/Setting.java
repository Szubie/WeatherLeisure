package weatherApp.model.settings;

/**
 * Created by Benjy on 19/06/2016.
 */
public class Setting {
	private String name;
	private Boolean value;

	public Setting(String name, Boolean value){
		this.name = name;
		this.value = value;
	}

	public Setting(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public Boolean getValue(){
		return value;
	}

	public void setValue(Boolean newValue){
		value = newValue;
	}
}
