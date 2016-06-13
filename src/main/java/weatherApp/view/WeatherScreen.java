package weatherApp.view;

import weatherApp.model.WeatherViewModel;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.net.URL;


/**
 * Created by Benjy on 07/06/2016.
 */
public class WeatherScreen extends AppScreen {

	WeatherViewModel model;
	JPanel weatherDisplay;
	JPanel weekWeather;
	JLabel location;
	JPanel locationPanel;

	//public JTextField locationEntry;
	public JComboBox<String> locationEntry;
	JPanel highLowMain;
	JPanel weatherDescription;


	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	private Image backgroundImage;

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
	}


	public WeatherScreen(WeatherViewModel model) {
		this.model = model;
		String currentWeather = model.getWeatherForecast().getTodayWeather().toLowerCase();
		String filePath = "weatherImages/"+currentWeather+"Background.gif";

		URL url = null;
		try{
			url = classLoader.getResource(filePath);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Image image = new ImageIcon(url).getImage();
		this.backgroundImage = image;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.add(locationEntry());
		this.add(locationPanel());
		this.add(weatherDisplay());
		this.add(weatherDescription());
		this.add(highLowMain());
		this.add(weekWeather());
	}

	public JPanel locationPanel() {
		//Add the text label telling us what location the weather is given for
		locationPanel = new JPanel();
		location = new JLabel(model.getWeatherForecast().getLocation() + ", " + model.getWeatherForecast().getCurrentAverageTemp());
		location.setFont(new Font("Century Gothic", Font.BOLD, cityFont));
		location.setForeground(Color.WHITE);
		locationPanel.add(location, BorderLayout.NORTH);
		locationPanel.setOpaque(false);
		return locationPanel;
	}

	public JComboBox<String> locationEntry(){
		locationEntry = new JComboBox<String>(model.getListOfCities().getArrayOfCities());
		locationEntry.setSelectedItem("Search bar");
		locationEntry.putClientProperty("JComboBox.isTableCellEditor", true);
		return locationEntry;
	}

	public void addSearchBarListener(PopupMenuListener listener) {
		locationEntry.addPopupMenuListener(listener);
	}

	public JPanel weatherDisplay() {
		//Create an ImageIcon to display the current weather
		String imageExtension = ".png";
		String weatherPic = imagesFolder + model.getWeatherForecast().getTodayWeather() + imageExtension;
		java.net.URL picURL = classLoader.getResource(weatherPic);
		ImageIcon image = new ImageIcon(picURL);
		JLabel imageLabel = new JLabel(image);
		imageLabel.setVisible(true);
		weatherDisplay = new JPanel();
		weatherDisplay.setOpaque(false);
		weatherDisplay.add(imageLabel, BorderLayout.CENTER);
		weatherDisplay.setPreferredSize(new Dimension(WIDTH, 360));
		return weatherDisplay;
	}

	public JPanel highLowMain() {
		highLowMain = new JPanel(new FlowLayout());
		String high = "High: ";
		String low = "Low: ";
		String highTemp = model.getWeatherForecast().getCurrentHighTemperature();
		String lowTemp = model.getWeatherForecast().getCurrentLowTemperature();
		String fullLabel = high + highTemp + " " + low + lowTemp;
		//Here we query weatherApp.model to retrieve the temperatures
		JLabel highLow = new JLabel(fullLabel);
		highLow.setFont(new Font("Century Gothic", Font.BOLD, highLowFont));
		highLow.setForeground(Color.WHITE);
		highLowMain.add(highLow);
		highLowMain.setOpaque(false);
		return highLowMain;
	}


	public JPanel weatherDescription() {
		weatherDescription = new JPanel(new FlowLayout());
		String value = model.getWeatherForecast().getCurrentWeatherDescription();
		//Here we would query weatherApp.model to retrieve the temperatures
		JLabel descriptionText = new JLabel(value);
		descriptionText.setFont(new Font("Century Gothic", Font.BOLD, weatherDescFont));
		descriptionText.setForeground(Color.WHITE);
		weatherDescription.add(descriptionText);
		weatherDescription.setOpaque(false);

		return weatherDescription;

	}

	public JPanel weekWeather() {
		//create a panel to display weather for the week ahead
		weekWeather = new JPanel();
		weekWeather.setPreferredSize(new Dimension(WIDTH, 100));
		weekWeather.setLayout(new FlowLayout());
		String upcomingWeather[] = new String[3];
		String imageExtension = ".png";

		String weatherPic = model.getWeatherForecast().getWeather(1) + imageExtension;

		for(int i=0; i<4; i++){
			JLabel dayWeatherPic = createSmallIconLabel(model.getWeekDays().getDay(i), weatherPic);
			dayWeatherPic.setFont(new Font("Century Gothic", Font.BOLD, weekWeatherFont));
			dayWeatherPic.setForeground(Color.WHITE);
			weekWeather.add(dayWeatherPic);
		}

		weekWeather.setOpaque(false);

		return weekWeather;
	}

	public JLabel createSmallIconLabel(String label, String picPath) {
		String iconPath = smallImagesFolder + picPath;
		java.net.URL picURL = classLoader.getResource(iconPath);
		ImageIcon image = new ImageIcon(picURL);
		JLabel imageLabel = new JLabel(label);
		imageLabel.setFont(imageLabel.getFont().deriveFont(imageLabelFloat));

		imageLabel.setIcon(image);
		imageLabel.setHorizontalTextPosition(JLabel.CENTER);
		imageLabel.setVerticalTextPosition(JLabel.BOTTOM);

		imageLabel.setVisible(true);
		return imageLabel;
	}

	//Set the location to the text in the text area
	public void updateLocation() {
		String imageExtension = ".png";

		location.setText(model.getWeatherForecast().getLocation() + ", " + model.getWeatherForecast().getCurrentAverageTemp());

		String high = "High: ";
		String low = "Low: ";

		String highTemp = model.getWeatherForecast().getCurrentHighTemperature();
		String lowTemp = model.getWeatherForecast().getCurrentLowTemperature();

		String fullLabel = high + highTemp + " " + low + lowTemp;
		//Here we query weatherApp.model to retrieve the temperatures
		Component[] tempComponents = highLowMain.getComponents();
		JLabel highLow = (JLabel) tempComponents[0];
		highLow.setText(fullLabel);
		highLowMain.revalidate();

		String value = model.getWeatherForecast().getCurrentWeatherDescription();
		Component[] weatherComponents = weatherDescription.getComponents();
		JLabel textBox = (JLabel) weatherComponents[0];
		textBox.setText(value);

		Component[] weekWeatherComponents = weekWeather.getComponents();
		for (int i = 0; i < weekWeatherComponents.length; i++) {
			String weatherPic = model.getWeatherForecast().getWeather(i + 1) + imageExtension;
			JLabel weekLabel = (JLabel) weekWeatherComponents[i];
			java.net.URL picURL = classLoader.getResource(smallImagesFolder + weatherPic);
			weekLabel.setIcon(new ImageIcon(picURL));
		}
		weekWeather.revalidate();

		String weatherPic = imagesFolder + model.getWeatherForecast().getTodayWeather() + imageExtension;
		java.net.URL picURL = classLoader.getResource(weatherPic);
		ImageIcon image = new ImageIcon(picURL);
		Component[] weatherDisplayComponents = weatherDisplay.getComponents();
		JLabel imageLabel = (JLabel) weatherDisplayComponents[0];
		imageLabel.setIcon(image);
		weatherDisplay.revalidate();

	}

}
