package weatherApp.view;

import weatherApp.model.WeatherModel;

import javax.swing.*;


public class WeatherView extends AppScreen {

	WeatherModel model;

	//The main application frame
	public JFrame frame;

	//Global JFrame elements,
	public BottomPanel bottomPanel;
	public ProposalGrid proposalGrid;
	public SettingsScreen settingsScreen;
	public WeatherScreen weatherScreen;

	public WeatherView(WeatherModel model, int device) {

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


	private void startWeatherScreen(JFrame frame) {
		//Add the components to our frame
		weatherScreen = new WeatherScreen(model);
		frame.add(weatherScreen);
		proposalGrid = new ProposalGrid(model);
		frame.add(proposalGrid);
		settingsScreen = new SettingsScreen(model);
		frame.add(settingsScreen);
		bottomPanel = new BottomPanel();
		frame.add(bottomPanel);
		hideProposal();
		hideSettings();
		showWeatherMain();
	}


	public void showProposal() {
		//Set the current day button to be selected by default
		JRootPane rootPane = frame.getRootPane();
		rootPane.setDefaultButton(proposalGrid.buttonList[0]);
		proposalGrid.locationPanel.setVisible(true);
		proposalGrid.setVisible(true);
		proposalGrid.daysPanel.setVisible(true);
	}

	public void hideProposal() {
		proposalGrid.locationPanel.setVisible(false);
		proposalGrid.setVisible(false);
		proposalGrid.daysPanel.setVisible(false);

	}

	public void showSettings() {
		settingsScreen.setVisible(true);
		settingsScreen.settingsPanel.setVisible(true);
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
		weatherScreen.currentTemperature.setVisible(false);
		weatherScreen.filler.setVisible(false);
		weatherScreen.locationEntryPanel.setVisible(false);
	}

	public void showWeatherMain() {
		weatherScreen.weatherDisplay.setVisible(true);
		weatherScreen.weekWeather.setVisible(true);
		weatherScreen.locationPanel.setVisible(true);
		weatherScreen.locationEntry.setVisible(true);
		weatherScreen.highLowMain.setVisible(true);
		weatherScreen.weatherDescription.setVisible(true);
		weatherScreen.currentTemperature.setVisible(true);
		weatherScreen.filler.setVisible(true);
		weatherScreen.locationEntryPanel.setVisible(true);
	}


}
