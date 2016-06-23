package weatherApp.model.eventRecommender;

public class Event {

	private Boolean culture;
	private Boolean entertainment;
	private Boolean relaxation;
	private Boolean shopping;
	private Boolean sport;
	private Boolean restaurants;
	private Boolean bars;

	private String name;
	private String picPath;

	public Event(String name, String path) {
		this.name = name;
		this.picPath = path;
		culture = false;
		entertainment = false;
		relaxation = false;
		shopping = false;
		sport = false;
		restaurants = false;
		bars = false;

	}

	public String getName() {
		return this.name;
	}

	public String getPicPath() {
		return this.picPath;
	}

	public void culture() {
		this.culture = true;
	}

	public void entertainment() {
		this.entertainment = true;
	}

	public void relaxation() {
		this.relaxation = true;
	}

	public void shopping() {
		this.shopping = true;
	}

	public void sport() {
		this.sport = true;
	}


	public void restaurants() {
		this.restaurants = true;
	}

	public void bars() {
		this.bars = true;
	}


	public Boolean isCulture() {
		return culture;
	}

	public Boolean isEntertainment() {
		return entertainment;
	}


	public Boolean isRelaxation() {
		return relaxation;
	}

	public Boolean isShopping() {
		return shopping;
	}

	public Boolean isSport() {
		return sport;
	}

	public Boolean isRestaurants() {
		return restaurants;
	}

	public Boolean isBars() {
		return bars;
	}


}
