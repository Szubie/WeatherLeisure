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


public class WeatherView{

	int WIDTH = 320;
	int HEIGHT = 480;
	float fontSize = 15.0f;
	float imageLabelFloat = 10.0f;
	float settingsFont = 14.0f;
	int cityFont = 32;
	int weekDaysFont = 12;
	int screenLabelfont = 30;
	int highLowFont = 12;
	int weatherDescFont = 12;
	int weekWeatherFont = 12;
	int morningFont = 12;
	int afternoonFont = 12;
	int eveningFont = 12;


	WeatherViewModel model;
    WeatherController controller;


    //Create a new clour to use in the app
    Color niceBlue = new Color(102, 153, 255);
    public Color darkGrey = new Color(102, 102, 153);
	Color darkBlue = new Color(60, 110, 190);

    //The main application frame
    public JFrame weather;

    //Global JFrame elements,
    JLabel location;
    JPanel weatherDisplay;
    JPanel weekWeather;
    JPanel settingsParent;
    JPanel settingsPanel;
    JPanel locationPanel;
    public JTextField locationEntry;
    JLabel topBarLabel;
    JPanel topBar;
    JPanel bottomPanel;


    //Create an ImageIcon to display the current weather
    String imagesFolder = "images/";
    String smallImagesFolder = "images/small";


    String imageExtension;
    String weatherPic;
	JPanel highLowMain;
    JPanel weatherDescription;

    JPanel proposalGrid;
    public JPanel daysPanel;

	JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel timePanel = new JPanel();
	JPanel eventGrid = new JPanel(new GridLayout(3, 3, 7, 7));
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
        weather.add(settingsScreen());
        weather.add(locationEntry());
        weather.add(locationPanel());
        weather.add(proposalGrid());

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


    public JPanel proposalGrid(){
        proposalGrid = new JPanel();
        proposalGrid.setPreferredSize(new Dimension(WIDTH, 360));
        proposalGrid.setLayout(new BoxLayout(proposalGrid, BoxLayout.Y_AXIS));
        proposalGrid.setBackground(Color.WHITE);

        daysPanel = new JPanel(new FlowLayout());
        daysPanel.setBackground(Color.WHITE);

        //Monday selected by default
        JButton monday = new JButton(model.getWeekDays().getWeekDay());
        monday = formatButton(monday);
        monday.setBackground(darkGrey);
        monday.setForeground(Color.WHITE);
        monday.setOpaque(true);
        JButton tuesday = new JButton(model.getWeekDays().getFirstDay());
        tuesday = formatButton(tuesday);
        JButton wednesday = new JButton(model.getWeekDays().getSecondDay());
        wednesday = formatButton(wednesday);
        JButton thursday = new JButton(model.getWeekDays().getThirdDay());
        thursday = formatButton(thursday);
        JButton friday = new JButton(model.getWeekDays().getFourthDay());
        friday = formatButton(friday);


        //here we populate our days panel
        daysPanel.add(monday);
        daysPanel.add(tuesday);
        daysPanel.add(wednesday);
        daysPanel.add(thursday);

        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.setAlignmentX(Component.LEFT_ALIGNMENT);


		//Get the weather forecast break down for the current day (11am, 3pm, 7pm))
        String[] timeWeather = model.getWeatherForecast().getWeatherInTheDay();


        JLabel morning = createIconLabel("11AM", "verySmall"+timeWeather[0]+".png");
				morning.setFont(new Font("Century Gothic", Font.BOLD, morningFont));
        JLabel afternoon = createIconLabel("3PM", "verySmall"+timeWeather[1]+ ".png");
				afternoon.setFont(new Font("Century Gothic", Font.BOLD, afternoonFont));
        JLabel evening = createIconLabel("7PM","verySmall"+timeWeather[2]+".png" );
				evening.setFont(new Font("Century Gothic", Font.BOLD, eveningFont));


        evening.setAlignmentX(Component.LEFT_ALIGNMENT);
        afternoon.setAlignmentX(Component.LEFT_ALIGNMENT);
        morning.setAlignmentX(Component.LEFT_ALIGNMENT);

        timePanel.add(morning);
        timePanel.add(Box.createRigidArea(new Dimension(0,40)));
        timePanel.add(afternoon);
        timePanel.add(Box.createRigidArea(new Dimension(0,40)));
        timePanel.add(evening);
        timePanel.setBackground(Color.WHITE);
        midPanel.add(timePanel);

        //for now we will have an array with all the events in one place
        //String[] events = {"britishMuseum.jpg", "bigBen.jpg", "primeroseHill.jpg","britishMuseum.jpg", "bigBen.jpg", "primeroseHill.jpg","britishMuseum.jpg", "bigBen.jpg", "primeroseHill.jpg"};
        ArrayList<weatherApp.model.eventRecommender.Event> suggestEvents = model.getEventRecommender().suggestEvents();

        for(weatherApp.model.eventRecommender.Event ev : suggestEvents){
            String name = ev.getName();
            String path = ev.getPicPath();
            eventGrid.add(createIconLabel(name, path));

        }

        eventGrid.setVisible(true);
        eventGrid.setBackground(Color.WHITE);
        midPanel.add(eventGrid);

        proposalGrid.add(daysPanel);
        daysPanel.setBackground(Color.WHITE);
        proposalGrid.add(midPanel);
        midPanel.setBackground(Color.WHITE);
        proposalGrid.setVisible(false);

        return proposalGrid;

    }

    public void addDaySelectionButtonListeners(ActionListener listener){
        //ArrayList<JButton> buttons = new ArrayList<JButton>();
        for(int i=0; i<4; i++){
            JPanel dayPanel = (JPanel) proposalGrid.getComponents()[0];
            JButton button = (JButton) dayPanel.getComponents()[i];
            button.addActionListener(listener);
        }
    }


    public void updateTimePanel(){
        for(int i=0; i<3; i++){
            //timePanel add new Morning, Afternoon and Evening values
        }
    }


    public JButton formatButton(JButton button){
        button.setFont(new Font("Century Gothic",Font.BOLD, weekDaysFont));
        return button;
    }

    //
    public void disSelectAll(JPanel daysPanel){
        for (Component button : daysPanel.getComponents()){
            button.setBackground(Color.WHITE);
			button.setForeground(Color.BLACK);

        }
    }


	//Once the day button is pressed we update the proposal events
    public void updateProposalGrid(){

		//Get the weatehr forecast break down for the current day (11am, 3pm, 7pm))
        String[] timeWeather = model.getWeatherForecast().getWeatherInTheDay();


        JLabel morning = createIconLabel("11AM", "verySmall"+timeWeather[0]+".png");
        JLabel afternoon = createIconLabel("3PM", "verySmall"+timeWeather[1]+ ".png");
        JLabel evening = createIconLabel("7PM","verySmall"+timeWeather[2]+".png" );


        evening.setAlignmentX(Component.LEFT_ALIGNMENT);
        afternoon.setAlignmentX(Component.LEFT_ALIGNMENT);
        morning.setAlignmentX(Component.LEFT_ALIGNMENT);

		timePanel.removeAll();
        timePanel.add(morning);
        timePanel.add(Box.createRigidArea(new Dimension(0,40)));
        timePanel.add(afternoon);
        timePanel.add(Box.createRigidArea(new Dimension(0,40)));
        timePanel.add(evening);
        timePanel.setBackground(Color.WHITE);


		eventGrid.removeAll();
        //for now we will have an array with all the events in one place
        //String[] events = {"britishMuseum.jpg", "bigBen.jpg", "primeroseHill.jpg","britishMuseum.jpg", "bigBen.jpg", "primeroseHill.jpg","britishMuseum.jpg", "bigBen.jpg", "primeroseHill.jpg"};
        ArrayList<weatherApp.model.eventRecommender.Event> suggestEvents = model.getEventRecommender().suggestEvents();

        for(weatherApp.model.eventRecommender.Event ev : suggestEvents){
            String name = ev.getName();
            String path = ev.getPicPath();

            eventGrid.add(createIconLabel(name, path));

        }

		midPanel.revalidate();
        midPanel.repaint();

    }

    public JLabel createIcon(String picPath){
        String iconPath = imagesFolder + picPath;
        java.net.URL picURL = classLoader.getResource(iconPath);
        ImageIcon image = new ImageIcon(iconPath);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setVisible(true);
        return imageLabel;
    }

    public JLabel createIconLabel(String label, String picPath){
        String iconPath = imagesFolder + picPath;
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


    public void showProposal(){
        locationPanel.setVisible(true);
        proposalGrid.setVisible(true);
        daysPanel.setVisible(true);
        updateTopBar("PROPOSAL");
    }

    public void hideProposal(){
        locationPanel.setVisible(false);
        proposalGrid.setVisible(false);
        daysPanel.setVisible(false);

    }

    public void showSettings(){
        settingsParent.setVisible(true);
        settingsPanel.setVisible(true);
        updateTopBar("SETTINGS");


    }

    public void hideSettings(){
        settingsParent.setVisible(false);
        settingsPanel.setVisible(false);
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


    public JPanel settingsScreen(){

        JCheckBox defaultLocation = new JCheckBox("Default Location");
        defaultLocation.setSelected(true);
        defaultLocation.setFont(defaultLocation.getFont().deriveFont(settingsFont));
        defaultLocation.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.getEventRecommender().setDefaultLocation(true);
                } else {
                    model.getEventRecommender().setDefaultLocation(false);
                };
            }
        });

        defaultLocation.setAlignmentX(Component.LEFT_ALIGNMENT);


        JCheckBox culture = new JCheckBox("Culture");
        culture.setFont (culture.getFont().deriveFont(settingsFont));
        culture.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.getEventRecommender().setCulture(true);
                    updateProposalGrid();

                } else {
                    model.getEventRecommender().setCulture(false);
                    updateProposalGrid();

                };
            }
        });

        culture.setAlignmentX(Component.LEFT_ALIGNMENT);
        JCheckBox entertainment = new JCheckBox("Entertainment");
        entertainment.setFont (entertainment.getFont().deriveFont (settingsFont));

        entertainment.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.getEventRecommender().setEntertainment(true);
                    updateProposalGrid();

                } else {
                    model.getEventRecommender().setEntertainment(false);
                    updateProposalGrid();

                };
            }
        });


        JCheckBox relaxation = new JCheckBox("Relaxation");
        relaxation.setFont (relaxation.getFont().deriveFont (settingsFont));

        relaxation.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.getEventRecommender().setRelaxation(true);
                    updateProposalGrid();
                } else {
                    model.getEventRecommender().setRelaxation(false);
                    updateProposalGrid();
                };
            }
        });


        JCheckBox shopping = new JCheckBox("Shopping");
        shopping.setFont (shopping.getFont().deriveFont (settingsFont));

        shopping.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.getEventRecommender().setShopping(true);
                    updateProposalGrid();
                } else {
                    model.getEventRecommender().setShopping(false);
                    updateProposalGrid();
                };
            }
        });

        JCheckBox sport = new JCheckBox("Sport");
        sport.setFont (sport.getFont().deriveFont (settingsFont));
        sport.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.getEventRecommender().setSport(true);
                    updateProposalGrid();
                } else {
                    model.getEventRecommender().setSport(false);
                    updateProposalGrid();
                };
            }
        });


        JCheckBox eating = new JCheckBox("Restaurants");
        eating.setFont (eating.getFont().deriveFont (settingsFont));
        eating.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.getEventRecommender().setEating(true);
                    updateProposalGrid();
                } else {
                    model.getEventRecommender().setEating(false);
                    updateProposalGrid();
                };
            }
        });

		JCheckBox drinking = new JCheckBox("Bars");
		drinking.setFont (drinking.getFont().deriveFont (settingsFont));
		drinking.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange() == ItemEvent.SELECTED) {
								model.getEventRecommender().setDrinking(true);
								updateProposalGrid();
						} else {
								model.getEventRecommender().setDrinking(false);
								updateProposalGrid();
						};
				}
		});


        settingsParent = new JPanel(new FlowLayout(FlowLayout.CENTER));

        settingsPanel = new JPanel();
        settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBackground(Color.WHITE);
        settingsPanel.add(defaultLocation);
        settingsPanel.add(Box.createVerticalStrut(20));

        settingsPanel.add(culture);
        settingsPanel.add(entertainment);
        settingsPanel.add(relaxation);
        settingsPanel.add(shopping);
        settingsPanel.add(sport);
        settingsPanel.add(eating);
		settingsPanel.add(drinking);
	
        settingsPanel.setPreferredSize(new Dimension(WIDTH, 400));
        settingsPanel.setVisible(false);

        settingsParent.add(settingsPanel);
        settingsParent.setBackground(Color.WHITE);

        return settingsParent;
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
