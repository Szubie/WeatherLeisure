package weatherApp.view;

import weatherApp.model.WeatherViewModel;

import javax.swing.*;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
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
	String weatherPic;
	JPanel highLowMain;
	JPanel weatherDescription;


	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	private Image backgroundImage;
	private ImageIcon imageIcon;

	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),this);
	}




	public WeatherScreen(WeatherViewModel model) {
		this.model = model;
		/*URL url = null;
		try{
			url = classLoader.getResource("weatherImages/rainBackground.gif");
		}
		catch(Exception e){}
		Image image = new ImageIcon(url).getImage();
		this.backgroundImage = image;*/

		/*URL url = null;
		try{
			url = new URL("http://i.4cdn.org/wsg/1464081561854.gif");
		}
		catch(Exception e){}
		Image image = new ImageIcon(url).getImage();
		this.backgroundImage = image;*/

		/*ImageIcon imageIcon = new ImageIcon(WeatherScreen.this.getClass().getResource("/weatherImages/rainBackground.gif"));
		Image image = imageIcon.getImage();
		this.backgroundImage = image;*/

		URL url = null;
		try{
			url = classLoader.getResource("weatherImages/rainBackground.gif");
		}
		catch(Exception e){}
		Image image = new ImageIcon(url).getImage();
		this.backgroundImage = image;

		/*InputStream in = classLoader.getResourceAsStream("weatherImages/rainBackground.gif");
		Image image = null;
		try {
			image = Toolkit.getDefaultToolkit().createImage(org.apache.commons.io.IOUtils.toByteArray(in));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.backgroundImage = image;*/

		/*URL url = null;
		try{
			url = classLoader.getResource("weatherImages/rain.gif");
		}
		catch(Exception e){}
		ImageIcon imageIcon = new ImageIcon(url);
		JLabel iconLabel = new JLabel();
		iconLabel.setIcon(imageIcon);
		imageIcon.setImageObserver(iconLabel);
		//this.add(iconLabel); //
		*/

		//this.setBackground(Color.WHITE);
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
		locationPanel.add(location, BorderLayout.NORTH);
		//locationPanel.setBackground(Color.WHITE);
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
		weatherPic = imagesFolder + model.getWeatherForecast().getTodayWeather() + imageExtension;
		java.net.URL picURL = classLoader.getResource(weatherPic);
		ImageIcon image = new ImageIcon(picURL);
		JLabel imageLabel = new JLabel(image);
		imageLabel.setVisible(true);
		weatherDisplay = new JPanel();
		//weatherDisplay.setBackground(Color.WHITE);
		weatherDisplay.setOpaque(false);
		weatherDisplay.add(imageLabel, BorderLayout.CENTER);
		weatherDisplay.setPreferredSize(new Dimension(WIDTH, 360));
		return weatherDisplay;
	}

	public JPanel highLowMain() {
		highLowMain = new JPanel(new FlowLayout());
		//highLowMain.setBackground(darkBlue);
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
		//weatherDescription.setBackground(darkBlue);
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
		//weekWeather.setBackground(Color.WHITE);
		weekWeather.setPreferredSize(new Dimension(WIDTH, 100));
		weekWeather.setLayout(new FlowLayout());
		String upcomingWeather[] = new String[3];
		String imageExtension = ".png";

		weatherPic = model.getWeatherForecast().getWeather(1) + imageExtension;

		JLabel tomorrow1 = createSmallIconLabel(model.getWeekDays().getFirstDay(), weatherPic);
		tomorrow1.setFont(new Font("Century Gothic", Font.BOLD, weekWeatherFont));

		imageExtension = ".png";
		weatherPic = model.getWeatherForecast().getWeather(2) + imageExtension;

		JLabel tomorrow2 = createSmallIconLabel(model.getWeekDays().getSecondDay(), weatherPic);
		tomorrow2.setFont(new Font("Century Gothic", Font.BOLD, weekWeatherFont));

		imageExtension = ".png";
		weatherPic = model.getWeatherForecast().getWeather(3) + imageExtension;
		JLabel tomorrow3 = createSmallIconLabel(model.getWeekDays().getThirdDay(), weatherPic);
		tomorrow3.setFont(new Font("Century Gothic", Font.BOLD, weekWeatherFont));

		imageExtension = ".png";
		weatherPic = model.getWeatherForecast().getWeather(4) + imageExtension;
		JLabel tomorrow4 = createSmallIconLabel(model.getWeekDays().getFourthDay(), weatherPic);
		tomorrow4.setFont(new Font("Century Gothic", Font.BOLD, weekWeatherFont));

		weekWeather.add(tomorrow1);
		weekWeather.add(tomorrow2);
		weekWeather.add(tomorrow3);
		weekWeather.add(tomorrow4);

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
			weatherPic = model.getWeatherForecast().getWeather(i + 1) + imageExtension;
			JLabel weekLabel = (JLabel) weekWeatherComponents[i];
			java.net.URL picURL = classLoader.getResource(smallImagesFolder + weatherPic);
			weekLabel.setIcon(new ImageIcon(picURL));
		}
		weekWeather.revalidate();

		weatherPic = imagesFolder + model.getWeatherForecast().getTodayWeather() + imageExtension;
		java.net.URL picURL = classLoader.getResource(weatherPic);
		ImageIcon image = new ImageIcon(picURL);
		Component[] weatherDisplayComponents = weatherDisplay.getComponents();
		JLabel imageLabel = (JLabel) weatherDisplayComponents[0];
		imageLabel.setIcon(image);
		weatherDisplay.revalidate();

	}

}
