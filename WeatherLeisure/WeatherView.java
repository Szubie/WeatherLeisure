import java.util.*;


import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.event.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class WeatherView{

	int WIDTH = 320;
	int HEIGHT = 480;
    float fontSize = 15.0f;
    float imageLabelFloat = 10.0f;
    float settingsFont = 14.0f;
    int cityFont = 32;
    int weekDaysFont = 12;


	WeatherModel model;
    

    //Create a new clour to use in the app
    Color niceBlue = new Color(102, 153, 255);
    Color darkGrey = new Color(102, 102, 153);


    //The main application frame
    JFrame weather;

    //Global JFrame elements, 
    JLabel location;
    JPanel weatherDisplay;
    JPanel weekWeather;
    JPanel settingsParent;
    JPanel settingsPanel;
    JPanel locationPanel;
    JTextField locationEntry;
    JLabel topBarLabel;
    JPanel topBar;


    //Create an ImageIcon to display the current weather
    String imagesFolder = "images/";
    String smallImagesFolder = "images/small";


    String imageExtension;
    String weatherPic;
	JPanel highLowMain;
    JPanel weatherDescription;

    JPanel proposalGrid;
    JPanel daysPanel;
	
	JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	JPanel timePanel = new JPanel();
	JPanel  eventGrid = new JPanel(new GridLayout(3, 3, 7, 7));


	public WeatherView(int device){

        weather = new JFrame();

        weather.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Find the current date.
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd, MMMM, YYYY");
        Date date = new Date();
        String theDate = dateFormat.format(date);
        String theDay = dateFormat.format(date).substring(0,3);

        if(device == 1){
            //Here we start the program in the tablet mode
            imagesFolder = "tabletImages/";
            weather.setSize(1024,768);
            model = new WeatherModel(4);
            model.setWeekDay(theDay);
            fontSize = 30.0f;
            imageLabelFloat = 15.0f;
            settingsFont = 20.0f;
            cityFont = 48;
            weekDaysFont = 22;

        }else{
            //Phone device screensize
            weather.setSize(WIDTH,HEIGHT);
            model = new WeatherModel(3);
            model.setWeekDay(theDay);
        }



        weather.setBackground(Color.WHITE);
        weather.setLayout(new BoxLayout(weather.getContentPane(), BoxLayout.Y_AXIS));

        //Proceed with building the UI layout
        startWeatherScreen(weather);
        weather.setResizable(false);
        weather.setVisible(true);

	}


	private void startWeatherScreen(JFrame weather){

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
        topBar.setBackground(niceBlue);
        topBarLabel = new JLabel("Weather",
                    JLabel.CENTER);
        topBarLabel.setForeground(Color.WHITE);
        topBarLabel.setFont (topBarLabel.getFont ().deriveFont (fontSize));
        topBar.add(topBarLabel);
        return topBar;
    }


    public void updateTopBar(String text){
            topBarLabel.setText(text);
    }

    private JTextField locationEntry(){
        //Here we create a text field to take in the location for the forecast
        locationEntry = new JTextField(21);
        locationEntry.setBackground(Color.WHITE);


        //Here is an action listener for the text box to update the location in the model
        locationEntry.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent e){
            String text = locationEntry.getText();
            model.setLocation(text);
            updateLocation();

        }
        });

        return locationEntry;
    }

    public JPanel locationPanel(){
        
        //Add the text label telling us what location the weather is given for
        locationPanel = new JPanel();
        location = new JLabel(model.getLocation());
        location.setFont(new Font("Courier New",Font.BOLD, cityFont));
        //JLabel temp = new JLabel(model.getTodayTemperature());
        //temp.setFont(new Font("Courier New",Font.BOLD, cityFont));


        locationPanel.add(location,BorderLayout.NORTH);
        //locationPanel.add(temp,BorderLayout.CENTER);
        locationPanel.setBackground(Color.WHITE);

        return locationPanel;
    }


    private JPanel weatherDisplay(){
        //Create an ImageIcon to display the current weather
        imageExtension = ".png";
        weatherPic = imagesFolder + model.getTodayWeather() + imageExtension;
        ImageIcon image = new ImageIcon(weatherPic);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setVisible(true);

        weatherDisplay = new JPanel();
        weatherDisplay.setBackground(Color.WHITE);
        weatherDisplay.add(imageLabel,BorderLayout.CENTER);
        weatherDisplay.setPreferredSize(new Dimension(WIDTH,360));

        return weatherDisplay;

    }

    private JPanel highLowMain(){
        highLowMain = new JPanel(new FlowLayout());
        highLowMain.setBackground(Color.WHITE);
        String high = "High: ";
        String low = "Low: ";

        String highTemp = model.getCurrentHighTemperature();
        String lowTemp = model.getCurrentLowTemperature();

        String fullLabel = high + highTemp + " " + low + lowTemp;
        JLabel highLow = new JLabel(fullLabel);
        highLow.setFont(highLow.getFont ().deriveFont (settingsFont));
        highLowMain.add(highLow);

        return highLowMain;

    }


    private JPanel weatherDescription(){
        weatherDescription = new JPanel(new FlowLayout());
        weatherDescription.setBackground(Color.WHITE);
        String value = model.getCurrentWeatherDescription();
        //Here we would query model to retrieve the temperatures
        JLabel descriptionText = new JLabel(value);
        descriptionText.setFont(descriptionText.getFont ().deriveFont (fontSize));
        weatherDescription.add(descriptionText);

        return weatherDescription;

    }

    public JPanel weekWeather(){
        //create a panel to display weather for the week ahead
        weekWeather = new JPanel();
        weekWeather.setBackground(Color.WHITE);
        weekWeather.setPreferredSize(new Dimension(WIDTH, 100));
       // weekWeather.setMaximumSize( weekWeather.getPreferredSize());
        weekWeather.setLayout(new FlowLayout());

        String upcomingWeather [] = new String[3];
        //upcomingWeather = model.getUpcomingWeather();


        imageExtension = ".png";
        weatherPic = model.getWeather(0) + imageExtension;

        JLabel tomorrow1 = createSmallIconLabel("Tue", weatherPic);


        imageExtension = ".png";
        weatherPic = model.getWeather(1) + imageExtension;

        JLabel tomorrow2 = createSmallIconLabel("Wed", weatherPic);

        imageExtension = ".png";
        weatherPic = model.getWeather(2) + imageExtension;
        JLabel tomorrow3 = createSmallIconLabel("Thur", weatherPic);


        imageExtension = ".png";
        weatherPic = model.getWeather(3) + imageExtension;
        JLabel tomorrow4= createSmallIconLabel("Fri", weatherPic);

        weekWeather.add(tomorrow1);
        weekWeather.add(tomorrow2);
        weekWeather.add(tomorrow3);
        weekWeather.add(tomorrow4);

        return weekWeather;
    }

    public JPanel bottomPanel(){

        //create a bottom navigation panel with all the necessery buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(WIDTH, 60));

        bottomPanel.setBackground(niceBlue);

        JButton weatherButton = new JButton("Weather");
        JButton proposalButton = new JButton("Proposal");
        JButton settingsButton = new JButton("Settings");
        BufferedImage buttonIcon;
        try{
            buttonIcon = ImageIO.read(new File("images/settings.png"));
            settingsButton = new JButton("Settings",new ImageIcon(buttonIcon));
            settingsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            settingsButton.setHorizontalTextPosition(SwingConstants.CENTER);
            buttonIcon = ImageIO.read(new File("images/weather.png"));
            weatherButton = new JButton("Weather",new ImageIcon(buttonIcon));
            weatherButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            weatherButton.setHorizontalTextPosition(SwingConstants.CENTER);

            buttonIcon = ImageIO.read(new File("images/plan.png"));
            proposalButton = new JButton("Proposal",new ImageIcon(buttonIcon));
            proposalButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            proposalButton.setHorizontalTextPosition(SwingConstants.CENTER);

        }catch(Exception e){
            System.out.println(e);
        }
        settingsButton.setBorder(BorderFactory.createEmptyBorder());
        weatherButton.setBorder(BorderFactory.createEmptyBorder());
        proposalButton.setBorder(BorderFactory.createEmptyBorder());
		
		
		

        settingsButton.setPreferredSize(new Dimension(80, 52));
        weatherButton.setPreferredSize(new Dimension(80, 52));
        proposalButton.setPreferredSize(new Dimension(80,52));




        //Here we are going to add action listeners to our navigation buttons
        settingsButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            hideWeatherMain();
            hideProposal();
            showSettings();
        }
        });

        weatherButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            hideSettings();
            hideProposal();
            showWeatherMain();

        }
        });

        proposalButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            updateProposalGrid();
            hideSettings();
            hideWeatherMain();
            showProposal();


        }
        });


        bottomPanel.add(weatherButton);
        bottomPanel.add(proposalButton);
        bottomPanel.add(settingsButton);

        return bottomPanel;
    }

    public void startSettingsScreen(JFrame frame){
        frame.setVisible(true);
        JFrame settings = new JFrame();
        settings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settings.setSize(WIDTH,HEIGHT);
        settings.setLayout(new BoxLayout(settings.getContentPane(), BoxLayout.Y_AXIS));

    }


    private JPanel proposalGrid(){
        proposalGrid = new JPanel();
        proposalGrid.setPreferredSize(new Dimension(WIDTH, 360));
        proposalGrid.setLayout(new BoxLayout(proposalGrid, BoxLayout.Y_AXIS));
        proposalGrid.setBackground(Color.WHITE);

        daysPanel = new JPanel(new FlowLayout());
        daysPanel.setBackground(Color.WHITE);

        JButton monday = new JButton("Mon");
        monday = buttonFlat(monday);
        JButton tuesday = new JButton("Tue");
        tuesday = buttonFlat(tuesday);
        JButton wednesday = new JButton("Wed");
        wednesday = buttonFlat(wednesday);
        JButton thursday = new JButton("Thur");
        thursday = buttonFlat(thursday);
        JButton friday = new JButton("Fri");
        friday = buttonFlat(friday);
        //JButton saturday = new JButton("Sat");
        //saturday = buttonFlat(saturday);

        //here we populate our days panel
        //daysPanel.add(Box.createRigidArea(new Dimension(20,0)));
        daysPanel.add(monday);
        daysPanel.add(wednesday);
        daysPanel.add(thursday);
        daysPanel.add(friday);
        //daysPanel.add(saturday);

        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        /*
        JLabel morning = new JLabel("11AM");
        JLabel afternoon = new JLabel("3PM");
        JLabel evening = new JLabel("7PM");
        */

        //TODO 

		//Get the weatehr forecast break down for the current day (11am, 3pm, 7pm)) 
        String[] timeWeather = model.getWeatherInTheDay();
		
		
        JLabel morning = createIconLabel("11AM", "verySmall"+timeWeather[0]+".png");
        JLabel afternoon = createIconLabel("3PM", "verySmall"+timeWeather[1]+ ".png");
        JLabel evening = createIconLabel("7PM","verySmall"+timeWeather[2]+".png" );


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
        ArrayList<Event> suggestEvents = model.suggestEvents();

        for(Event ev : suggestEvents){
            String name = ev.name;
            String path = ev.picPath;
            eventGrid.add(createIconLabel(name, path));

        }

        /*
        for(int i =0; i<9; i++){
            String name = events[i].substring(0, events[i].length() - 4);

            eventGrid.add(createIconLabel(name,events[i]));


        }
        */

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

 
    private void updateTimePanel(){
        for(int i=0; i<3; i++){
            //timePanel add new Morning, Afternoon and Evening values
        }
    }
 

    private JButton buttonFlat(JButton button){

        button.setFont(new Font("Courier New",Font.BOLD, weekDaysFont));


        button.addActionListener(
        new ActionListener(){
            public void actionPerformed(ActionEvent e){

            disSelectAll(daysPanel);
            String text = locationEntry.getText();
            JButton but = (JButton)e.getSource();
            String weekDay = but.getText();
            //make a call to the Weather Model to set the week day accordingly
            model.setWeekDay(weekDay);
            but.setBackground(darkGrey);
			but.setForeground(Color.WHITE);
            //but.setFont(but.getFont().deriveFont(Font.BOLD));
            but.setOpaque(true);
            String day = but.getName();
            updateProposalGrid();

        }
        });

        return button;
    }

    //
    private void disSelectAll(JPanel daysPanel){
        for (Component button : daysPanel.getComponents()){
            button.setBackground(Color.WHITE);
			button.setForeground(Color.BLACK);

        }
    }


	//Once the day button is pressed we update the proposal events
    private void updateProposalGrid(){

		//Get the weatehr forecast break down for the current day (11am, 3pm, 7pm)) 
        String[] timeWeather = model.getWeatherInTheDay();
		
		
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
        ArrayList<Event> suggestEvents = model.suggestEvents();

        for(Event ev : suggestEvents){
            String name = ev.name;
            String path = ev.picPath;

            eventGrid.add(createIconLabel(name, path));

        }
		
		
		midPanel.revalidate();
        midPanel.repaint();
	
    }

    private JLabel createIcon(String picPath){
        //imagesFolder = "images/";
        //imageExtension = ".png";
        String iconPath = imagesFolder + picPath;
        ImageIcon image = new ImageIcon(iconPath);
        JLabel imageLabel = new JLabel(image);
        imageLabel.setVisible(true);
        return imageLabel;
    }

    private JLabel createIconLabel(String label, String picPath){
        //imagesFolder = "images/";
        //imageExtension = ".png";
        String iconPath = imagesFolder + picPath;
        ImageIcon image = new ImageIcon(iconPath);
        JLabel imageLabel = new JLabel(label);
        imageLabel.setFont (imageLabel.getFont ().deriveFont (imageLabelFloat));

        imageLabel.setIcon(image);
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        imageLabel.setVerticalTextPosition(JLabel.BOTTOM);

        imageLabel.setVisible(true);
        return imageLabel;
    }

    private JLabel createSmallIconLabel(String label, String picPath){
        //imagesFolder = "images/";
        //imageExtension = ".png"; 
        String iconPath = smallImagesFolder + picPath;
        ImageIcon image = new ImageIcon(iconPath);
        JLabel imageLabel = new JLabel(label);
        imageLabel.setFont (imageLabel.getFont ().deriveFont (imageLabelFloat));

        imageLabel.setIcon(image);
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        imageLabel.setVerticalTextPosition(JLabel.BOTTOM);

        imageLabel.setVisible(true);
        return imageLabel;
    }


    private void showProposal(){
        locationPanel.setVisible(true);
        proposalGrid.setVisible(true);
        daysPanel.setVisible(true);
        updateTopBar("Proposals");
    }

    private void hideProposal(){
        locationPanel.setVisible(false);
        proposalGrid.setVisible(false);
        daysPanel.setVisible(false);

    }

    private void showSettings(){
        settingsParent.setVisible(true);
        settingsPanel.setVisible(true);
        updateTopBar("Settings");


    }

    private void hideSettings(){
        settingsParent.setVisible(false);
        settingsPanel.setVisible(false);
		highLowMain.setVisible(false);


    }

    private void hideWeatherMain(){
        weatherDisplay.setVisible(false);
        weekWeather.setVisible(false);
        locationPanel.setVisible(false);
        locationEntry.setVisible(false);
		highLowMain.setVisible(false);
        weatherDescription.setVisible(false);
    }

    private void showWeatherMain(){
        weatherDisplay.setVisible(true);
        weekWeather.setVisible(true);
        locationPanel.setVisible(true);
        locationEntry.setVisible(true);
		highLowMain.setVisible(true);
        weatherDescription.setVisible(true);
        updateTopBar("Weather");

    }


    private JPanel settingsScreen(){

        JCheckBox defaultLocation = new JCheckBox("Default Location");
        defaultLocation.setSelected(true);
        defaultLocation.setFont(defaultLocation.getFont().deriveFont(settingsFont));
        defaultLocation.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.setDefaultLocation(true);
                } else {
                    model.setDefaultLocation(false);
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
                    System.out.println("Set culture to be true");              

                    model.setCulture(true);
                    updateProposalGrid();

                } else {
                    model.setCulture(false);
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
                    System.out.println("Set entertainment to be true");              
                    model.setEntertainment(true);
                    updateProposalGrid();

                } else {
                    model.setEntertainment(false);
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
                    System.out.println("Set relaxation to be true");              
                    model.setRelaxation(true);
                    updateProposalGrid();
                } else {
                    model.setRelaxation(false);
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
                    System.out.println("Set shopping to be true");              
                    model.setShopping(true);
                    updateProposalGrid();
                } else {
                    model.setShopping(false);
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
                System.out.println("Set sport to be true");              
                    model.setSport(true);
                    updateProposalGrid();
                } else {
                    model.setSport(false);
                    updateProposalGrid();
                };
            }
        });


        JCheckBox eating = new JCheckBox("Restuarants/Bars");
        eating.setFont (eating.getFont().deriveFont (settingsFont));
        eating.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    model.setEating(true);
                    updateProposalGrid();
                } else {
                    model.setEating(false);
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

        settingsPanel.setPreferredSize(new Dimension(WIDTH, 400));
        settingsPanel.setVisible(false);

        settingsParent.add(settingsPanel);
        settingsParent.setBackground(Color.WHITE);

        return settingsParent;
    }




    //Set the location to the text in the text area
    private void updateLocation(){
        location.setText(model.getLocation());
    }

    public static void main (String args[]) {

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                if(args.length>0){
                    new WeatherView(1);
                }else{
                    new WeatherView(0);
                }

            }
        });
    }

}