public class Event{
	
	Boolean culture;
	Boolean entertainment;
	Boolean relaxation;
	Boolean shopping;
	Boolean sport;
	Boolean eating;

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


}