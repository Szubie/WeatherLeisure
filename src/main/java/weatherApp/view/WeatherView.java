package weatherApp.view;

import weatherApp.controller.WeatherController;
import weatherApp.model.WeatherViewModel;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;


public class WeatherView extends AppScreen{

	WeatherViewModel model;
    WeatherController controller;

    //The main application frame
    public JFrame weather;

    //Global JFrame elements,
    JLabel location;
    JPanel weatherDisplay;
    JPanel weekWeather;
    JPanel locationPanel;
    public JTextField locationEntry;
    JLabel topBarLabel;
    JPanel topBar;
    JPanel bottomPanel;


    String imageExtension;
    String weatherPic;
	JPanel highLowMain;
    JPanel weatherDescription;

    public ProposalGrid proposalGrid;
    public SettingsScreen settingsScreen;


    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();


	public WeatherView(WeatherViewModel model, int device){

        weather = new JFrame();
        weather.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        if(device == 1){
            //Here we start the program in the tablet mode
            imagesFolder = "tabletImages/";
            weather.setSize(1024,768);
            this.model = model;
            fontSize = 30.0f;
            imageLabelFloat = 15.0f;
            settingsFont = 20.0f;
            cityFont = 48;
			weekDaysFont = 22;
			screenLabelfont = 45;
			highLowFont = 20;
			weatherDescFont = 20;
			weekWeatherFont = 20;
			morningFont = 20;
			afternoonFont = 20;
			eveningFont = 20;

        }else{
            //Phone device screensize
            weather.setSize(WIDTH,HEIGHT);
            this.model = model;
        }

        weather.setBackground(Color.WHITE);
        weather.setLayout(new BoxLayout(weather.getContentPane(), BoxLayout.Y_AXIS));

        //Proceed with building the UI layout
        startWeatherScreen(weather);
        weather.setResizable(false);
        weather.setVisible(true);

	}


	public void startWeatherScreen(JFrame weather){
        //Add the components to our frame
        weather.add(topBar());
        settingsScreen = new SettingsScreen();
        weather.add(settingsScreen);
        weather.add(locationEntry());
        weather.add(locationPanel());
        proposalGrid = new ProposalGrid(model);
        weather.add(proposalGrid);

        weather.add(weatherDisplay());
        weather.add(weatherDescription());
        weather.add(highLowMain());
        weather.add(weekWeather());
        weather.add(bottomPanel());
	}


    public JPanel topBar(){
        //create the top panel telling us what screen we are in
        topBar = new JPanel();
        topBar.setPreferredSize(new Dimension(WIDTH, 80));
        topBar.setBackground(darkBlue);
        topBarLabel = new JLabel("WEATHER", JLabel.CENTER);
        topBarLabel.setForeground(Color.WHITE);
      	topBarLabel.setFont(new Font("Century Gothic", Font.BOLD, screenLabelfont));
        topBar.add(topBarLabel);
        return topBar;
    }


    public void updateTopBar(String text){
        topBarLabel.setText(text);
    }

    public JTextField locationEntry(){
        //Here we create a text field to take in the location for the forecast
		locationEntry = new JTextField("search bar", 21);
		locationEntry.setFont(new Font("Century Gothic", Font.ITALIC, 12));
        locationEntry.setBackground(darkBlue);
		locationEntry.setForeground(Color.WHITE);
        return locationEntry;
    }

    public void addSearchBarListener(ActionListener listener){
        locationEntry.addActionListener(listener);
    }

    public JPanel locationPanel(){
        //Add the text label telling us what location the weather is given for
        locationPanel = new JPanel();
        location = new JLabel(model.getWeatherForecast().getLocation()+", "+model.getWeatherForecast().getCurrentAverageTemp());
        location.setFont(new Font("Century Gothic",Font.BOLD, cityFont));
        locationPanel.add(location,BorderLayout.NORTH);
        locationPanel.setBackground(Color.WHITE);
        return locationPanel;
    }


    public JPanel weatherDisplay(){
        //Create an ImageIcon to display the current weather
        imageExtension = ".png";
        weatherPic = imagesFolder + model.getWeatherForecast().getTodayWeather() + imageExtension;
        java.net.URL picURL = classLoader.getResource(weatherPic);
        ImageIcon image = new ImageIcon(picURL);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setVisible(true);
        weatherDisplay = new JPanel();
        weatherDisplay.setBackground(Color.WHITE);
        weatherDisplay.add(imageLabel,BorderLayout.CENTER);
        weatherDisplay.setPreferredSize(new Dimension(WIDTH,360));
        return weatherDisplay;
    }

    public JPanel highLowMain(){
        highLowMain = new JPanel(new FlowLayout());
        highLowMain.setBackground(darkBlue);
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
        return highLowMain;
    }


    public JPanel weatherDescription(){
        weatherDescription = new JPanel(new FlowLayout());
        weatherDescription.setBackground(darkBlue);
        String value = model.getWeatherForecast().getCurrentWeatherDescription();
        //Here we would query weatherApp.model to retrieve the temperatures
        JLabel descriptionText = new JLabel(value);
        descriptionText.setFont(new Font("Century Gothic", Font.BOLD, weatherDescFont));
		descriptionText.setForeground(Color.WHITE);
        weatherDescription.add(descriptionText);

        return weatherDescription;

    }

    public JPanel weekWeather(){
        //create a panel to display weather for the week ahead
        weekWeather = new JPanel();
        weekWeather.setBackground(Color.WHITE);
        weekWeather.setPreferredSize(new Dimension(WIDTH, 100));
        weekWeather.setLayout(new FlowLayout());
        String upcomingWeather [] = new String[3];
        imageExtension = ".png";

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
        JLabel tomorrow4= createSmallIconLabel(model.getWeekDays().getFourthDay(), weatherPic);
				tomorrow4.setFont(new Font("Century Gothic", Font.BOLD, weekWeatherFont));

        weekWeather.add(tomorrow1);
        weekWeather.add(tomorrow2);
        weekWeather.add(tomorrow3);
        weekWeather.add(tomorrow4);

        return weekWeather;
    }

    public JLabel createSmallIconLabel(String label, String picPath){
        String iconPath = smallImagesFolder + picPath;
        java.net.URL picURL = classLoader.getResource(iconPath);
        ImageIcon image = new ImageIcon(picURL);
        JLabel imageLabel = new JLabel(label);
        imageLabel.setFont (imageLabel.getFont ().deriveFont (imageLabelFloat));

        imageLabel.setIcon(image);
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        imageLabel.setVerticalTextPosition(JLabel.BOTTOM);

        imageLabel.setVisible(true);
        return imageLabel;
    }

    public JPanel bottomPanel(){

        //create a bottom navigation panel with all the necessery buttons
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, 60));

        bottomPanel.setBackground(darkBlue);

        JButton weatherButton = new JButton("Weather");
        JButton proposalButton = new JButton("Proposal");
        JButton settingsButton = new JButton("Settings");
        java.net.URL buttonIcon;
        try{
            buttonIcon = classLoader.getResource("images/SettingsButton.png");
            settingsButton = new JButton("Settings",new ImageIcon(buttonIcon));
            settingsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            settingsButton.setHorizontalTextPosition(SwingConstants.CENTER);

            buttonIcon = classLoader.getResource("images/WeatherButton.png");
            weatherButton = new JButton("Weather",new ImageIcon(buttonIcon));
            weatherButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            weatherButton.setHorizontalTextPosition(SwingConstants.CENTER);

            buttonIcon = classLoader.getResource("images/ProposalButton.png");
            proposalButton = new JButton("Proposal",new ImageIcon(buttonIcon));
            proposalButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            proposalButton.setHorizontalTextPosition(SwingConstants.CENTER);

        }catch(Exception e){
            e.printStackTrace();
        }
        settingsButton.setBorder(BorderFactory.createEmptyBorder());
        weatherButton.setBorder(BorderFactory.createEmptyBorder());
        proposalButton.setBorder(BorderFactory.createEmptyBorder());

        settingsButton.setPreferredSize(new Dimension(80, 52));
        weatherButton.setPreferredSize(new Dimension(80, 52));
        proposalButton.setPreferredSize(new Dimension(80,52));

		settingsButton.setFont(new Font("Century Gothic", Font.BOLD, 12));
		settingsButton.setForeground(Color.WHITE);
		weatherButton.setFont(new Font("Century Gothic", Font.BOLD, 12));
		weatherButton.setForeground(Color.WHITE);
		proposalButton.setFont(new Font("Century Gothic", Font.BOLD, 12));
		proposalButton.setForeground(Color.WHITE);

        bottomPanel.add(weatherButton);
        bottomPanel.add(proposalButton);
        bottomPanel.add(settingsButton);

        return bottomPanel;
    }

    public void addWeatherButtonListener(ActionListener listener){
        JButton button = (JButton) bottomPanel.getComponents()[0];
        button.addActionListener(listener);
    }

    public void addProposalButtonListener(ActionListener listener){
        JButton button = (JButton) bottomPanel.getComponents()[1];
        button.addActionListener(listener);
    }

    public void addSettingsButtonListener(ActionListener listener){
        JButton button = (JButton) bottomPanel.getComponents()[2];
        button.addActionListener(listener);
    }

    public void startSettingsScreen(JFrame frame){
        frame.setVisible(true);
        JFrame settings = new JFrame();
        settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settings.setSize(WIDTH,HEIGHT);
        settings.setLayout(new BoxLayout(settings.getContentPane(), BoxLayout.Y_AXIS));

    }

    public void showProposal(){
        locationPanel.setVisible(true);
        proposalGrid.setVisible(true);
        proposalGrid.daysPanel.setVisible(true);
        updateTopBar("PROPOSAL");
    }

    public void hideProposal(){
        locationPanel.setVisible(false);
        proposalGrid.setVisible(false);
        proposalGrid.daysPanel.setVisible(false);

    }

    public void showSettings(){
        settingsScreen.setVisible(true);
        settingsScreen.settingsPanel.setVisible(true);
        updateTopBar("SETTINGS");


    }

    public void hideSettings(){
        settingsScreen.setVisible(false);
        settingsScreen.settingsPanel.setVisible(false);
		highLowMain.setVisible(false);


    }

    public void hideWeatherMain(){
        weatherDisplay.setVisible(false);
        weekWeather.setVisible(false);
        locationPanel.setVisible(false);
        locationEntry.setVisible(false);
		highLowMain.setVisible(false);
        weatherDescription.setVisible(false);
    }

    public void showWeatherMain(){
        weatherDisplay.setVisible(true);
        weekWeather.setVisible(true);
        locationPanel.setVisible(true);
        locationEntry.setVisible(true);
		highLowMain.setVisible(true);
        weatherDescription.setVisible(true);
        updateTopBar("WEATHER");

    }




    //Set the location to the text in the text area
    public void updateLocation(){
        location.setText(model.getWeatherForecast().getLocation() + ", "+model.getWeatherForecast().getCurrentAverageTemp());

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
        for(int i=0; i<weekWeatherComponents.length; i++){
            weatherPic = model.getWeatherForecast().getWeather(i+1) + imageExtension;
            JLabel weekLabel = (JLabel) weekWeatherComponents[i];
            java.net.URL picURL = classLoader.getResource(smallImagesFolder+weatherPic);
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
