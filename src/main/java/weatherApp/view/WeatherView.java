package weatherApp.view;

import weatherApp.model.WeatherViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.net.URL;


public class WeatherView extends AppScreen {

	WeatherViewModel model;

	//The main application frame
	public JFrame frame;

	//Global JFrame elements,
	JLabel topBarLabel;
	JPanel topBar;
	JPanel bottomPanel;

	public ProposalGrid proposalGrid;
	public SettingsScreen settingsScreen;
	public WeatherScreen weatherScreen;


	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();


	public WeatherView(WeatherViewModel model, int device) {

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (device == 1) {
			//Here we start the program in the tablet mode
			imagesFolder = "tabletImages/";
			frame.setSize(1024, 768);
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

		} else {
			//Phone device screensize
			frame.setSize(WIDTH, HEIGHT);
			this.model = model;
		}

		//frame.setBackground(Color.WHITE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		//Proceed with building the UI layout
		startWeatherScreen(frame);
		frame.setResizable(false);
		frame.setVisible(true);

	}


	public void startWeatherScreen(JFrame frame) {
		//Add the components to our frame
		frame.add(topBar());
		settingsScreen = new SettingsScreen();
		frame.add(settingsScreen);
		weatherScreen = new WeatherScreen(model);
		frame.add(weatherScreen);
		proposalGrid = new ProposalGrid(model);
		frame.add(proposalGrid);
		frame.add(bottomPanel());

	}


	public JPanel topBar() {
		//create the top panel telling us what screen we are in
		topBar = new JPanel();
		topBar.setPreferredSize(new Dimension(WIDTH, 80));
		//topBar.setBackground(darkBlue);
		topBarLabel = new JLabel("WEATHER", JLabel.CENTER);
		//topBarLabel.setForeground(Color.WHITE);
		topBarLabel.setFont(new Font("Century Gothic", Font.BOLD, screenLabelfont));
		topBar.add(topBarLabel);
		return topBar;
	}

	public void updateTopBar(String text) {
		topBarLabel.setText(text);
	}

	public JPanel bottomPanel() {

		//create a bottom navigation panel with all the necessary buttons
		bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(WIDTH, 60));

		//bottomPanel.setBackground(darkBlue);

		JButton weatherButton = new JButton("Weather");
		JButton proposalButton = new JButton("Proposal");
		JButton settingsButton = new JButton("Settings");
		java.net.URL buttonIcon;
		try {
			buttonIcon = classLoader.getResource("images/SettingsButton.png");
			settingsButton = new JButton("Settings", new ImageIcon(buttonIcon));
			settingsButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			settingsButton.setHorizontalTextPosition(SwingConstants.CENTER);
			settingsButton.setContentAreaFilled(false);

			buttonIcon = classLoader.getResource("images/WeatherButton.png");
			weatherButton = new JButton("Weather", new ImageIcon(buttonIcon));
			weatherButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			weatherButton.setHorizontalTextPosition(SwingConstants.CENTER);
			weatherButton.setContentAreaFilled(false);

			buttonIcon = classLoader.getResource("images/ProposalButton.png");
			proposalButton = new JButton("Proposal", new ImageIcon(buttonIcon));
			proposalButton.setVerticalTextPosition(SwingConstants.BOTTOM);
			proposalButton.setHorizontalTextPosition(SwingConstants.CENTER);
			proposalButton.setContentAreaFilled(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
		settingsButton.setBorder(BorderFactory.createEmptyBorder());
		weatherButton.setBorder(BorderFactory.createEmptyBorder());
		proposalButton.setBorder(BorderFactory.createEmptyBorder());

		settingsButton.setPreferredSize(new Dimension(80, 52));
		weatherButton.setPreferredSize(new Dimension(80, 52));
		proposalButton.setPreferredSize(new Dimension(80, 52));

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

	public void addWeatherButtonListener(ActionListener listener) {
		JButton button = (JButton) bottomPanel.getComponents()[0];
		button.addActionListener(listener);
	}

	public void addProposalButtonListener(ActionListener listener) {
		JButton button = (JButton) bottomPanel.getComponents()[1];
		button.addActionListener(listener);
	}

	public void addSettingsButtonListener(ActionListener listener) {
		JButton button = (JButton) bottomPanel.getComponents()[2];
		button.addActionListener(listener);
	}

	public void showProposal() {
		//Set the current day button to be selected by default
		JRootPane rootPane = frame.getRootPane();
		rootPane.setDefaultButton(proposalGrid.buttonList[0]);

		weatherScreen.locationPanel.setVisible(true);
		proposalGrid.setVisible(true);
		proposalGrid.daysPanel.setVisible(true);
		updateTopBar("PROPOSAL");
	}

	public void hideProposal() {
		weatherScreen.locationPanel.setVisible(false);
		proposalGrid.setVisible(false);
		proposalGrid.daysPanel.setVisible(false);

	}

	public void showSettings() {
		settingsScreen.setVisible(true);
		settingsScreen.settingsPanel.setVisible(true);
		updateTopBar("SETTINGS");


	}

	public void hideSettings() {
		settingsScreen.setVisible(false);
		settingsScreen.settingsPanel.setVisible(false);
		weatherScreen.highLowMain.setVisible(false);


	}

	public void hideWeatherMain() {
		weatherScreen.weatherDisplay.setVisible(false);
		weatherScreen.weekWeather.setVisible(false);
		weatherScreen.locationPanel.setVisible(false);
		weatherScreen.locationEntry.setVisible(false);
		weatherScreen.highLowMain.setVisible(false);
		weatherScreen.weatherDescription.setVisible(false);
	}

	public void showWeatherMain() {
		weatherScreen.weatherDisplay.setVisible(true);
		weatherScreen.weekWeather.setVisible(true);
		weatherScreen.locationPanel.setVisible(true);
		weatherScreen.locationEntry.setVisible(true);
		weatherScreen.highLowMain.setVisible(true);
		weatherScreen.weatherDescription.setVisible(true);
		updateTopBar("WEATHER");

	}

}
