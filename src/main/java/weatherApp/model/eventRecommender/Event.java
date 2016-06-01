package weatherApp.model.eventRecommender;

public class Event{

	Boolean culture;
	Boolean entertainment;
	Boolean relaxation;
	Boolean shopping;
	Boolean sport;
	Boolean eating;
	Boolean drinking;

	String name;
	String picPath;

	public Event(String name, String path){
		this.name = name;
		this.picPath = path;
		culture = false;
		entertainment = false;
		relaxation = false;
		shopping = false;
		sport = false;
		eating = false;
		drinking = false;

	}

	public String getName(){
		return this.name;
	}

	public String getPicPath(){
		return this.picPath;
	}

	public void culture(){
		this.culture = true;
	}

	public void entertainment(){
		this.entertainment = true;
	}

	public void relaxation(){
		this.relaxation = true;
	}

	public void shopping(){
		this.shopping = true;
	}

	public void sport(){
		this.sport = true;
	}


	public void eating(){
		this.eating = true;
	}

	public void drinking(){
		this.drinking = true;
	}



	public Boolean isCulture(){
		return culture;
	}

	public Boolean isEntertainment(){
		return entertainment;
	}


	public Boolean isRelaxation(){
		return relaxation;
	}

	public Boolean isShopping(){
		return shopping;
	}

	public Boolean isSport(){
		return sport;
	}

	public Boolean isEating(){
		return eating;
	}

	public Boolean isDrinking(){
		return drinking;
	}


}
