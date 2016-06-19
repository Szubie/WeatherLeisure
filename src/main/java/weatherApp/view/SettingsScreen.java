package weatherApp.view;

import weatherApp.model.WeatherViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * Created by Benjy on 07/06/2016.
 */
public class SettingsScreen extends AppScreen {
	public WeatherViewModel model;

	public JPanel settingsPanel;

	public JLabel locationLabel;
	public JTextField locationInput;
	public JCheckBox culture;
	public JCheckBox entertainment;
	public JCheckBox relaxation;
	public JCheckBox shopping;
	public JCheckBox sport;
	public JCheckBox eating;
	public JCheckBox drinking;

	public SettingsScreen(WeatherViewModel model) {
		this.model = model;
		locationLabel = new JLabel("Default Location:");
		locationLabel.setFont(locationLabel.getFont().deriveFont(settingsFont));
		locationLabel.setBackground(Color.WHITE);

		locationInput = new JTextField();
		locationInput.setFont(locationInput.getFont().deriveFont(settingsFont));
		locationInput.setBackground(Color.WHITE);
		locationInput.setMaximumSize(new Dimension(WIDTH, 23));

		culture = buildSettingsCheckbox("Culture");
		entertainment = buildSettingsCheckbox("Entertainment");
		relaxation = buildSettingsCheckbox("Relaxation");
		shopping = buildSettingsCheckbox("Shopping");
		sport = buildSettingsCheckbox("Sport");
		eating = buildSettingsCheckbox("Restaurants");
		drinking = buildSettingsCheckbox("Bars");


		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		settingsPanel = new JPanel();
		settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
		settingsPanel.setBackground(Color.WHITE);
		settingsPanel.add(locationLabel);
		settingsPanel.add(locationInput);
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

		this.add(settingsPanel);
		this.setBackground(Color.WHITE);
	}

	public void addLocationInputListener(ActionListener listener){
		locationInput.addActionListener(listener);
	}

	private JCheckBox buildSettingsCheckbox(String title){
		JCheckBox checkBox = new JCheckBox(title);
		if(model.getSettings().getSetting(title)){
			checkBox.setSelected(true);
		}
		checkBox.setFont(checkBox.getFont().deriveFont(settingsFont));
		checkBox.setBackground(Color.WHITE);
		return checkBox;
	}

	void updateSettingsLocationLabel(){
		locationInput.setText(model.getWeatherForecast().getLocation());
	}

	public void addCultureBoxListener(ItemListener listener) {
		culture.addItemListener(listener);
	}

	public void addEntertainmentBoxListener(ItemListener listener) {
		entertainment.addItemListener(listener);
	}

	public void addRelaxationBoxListener(ItemListener listener) {
		relaxation.addItemListener(listener);
	}

	public void addShoppingBoxListener(ItemListener listener) {
		shopping.addItemListener(listener);
	}

	public void addSportBoxListener(ItemListener listener) {
		sport.addItemListener(listener);
	}

	public void addEatingBoxListener(ItemListener listener) {
		eating.addItemListener(listener);
	}

	public void addDrinkingBoxListener(ItemListener listener) {
		drinking.addItemListener(listener);
	}
}
