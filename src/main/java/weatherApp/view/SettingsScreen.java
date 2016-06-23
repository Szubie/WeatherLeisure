package weatherApp.view;

import weatherApp.model.WeatherModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Benjy on 07/06/2016.
 */
public class SettingsScreen extends AppScreen implements Observer{
	public WeatherModel model;

	public JPanel settingsPanel;

	public JLabel locationLabel;
	public JTextField locationInput;

	public ArrayList<JCheckBox> settingsCheckBoxList = new ArrayList<JCheckBox>();

	public SettingsScreen(WeatherModel model) {
		this.model = model;
		model.getSettings().addObserver(this);

		locationLabel = new JLabel("Default Location:");
		locationLabel.setFont(locationLabel.getFont().deriveFont(settingsFont));
		locationLabel.setBackground(Color.WHITE);

		locationInput = new JTextField();
		locationInput.setFont(locationInput.getFont().deriveFont(settingsFont));
		locationInput.setBackground(Color.WHITE);
		locationInput.setMaximumSize(new Dimension(WIDTH, 23));

		buildSettingsCheckbox("Culture");
		buildSettingsCheckbox("Entertainment");
		buildSettingsCheckbox("Relaxation");
		buildSettingsCheckbox("Shopping");
		buildSettingsCheckbox("Sport");
		buildSettingsCheckbox("Restaurants");
		buildSettingsCheckbox("Bars");


		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		settingsPanel = new JPanel();
		settingsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
		settingsPanel.setBackground(Color.WHITE);
		settingsPanel.add(locationLabel);
		settingsPanel.add(locationInput);
		settingsPanel.add(Box.createVerticalStrut(20));

		for(JCheckBox checkBox : settingsCheckBoxList){
			settingsPanel.add(checkBox);
		}

		settingsPanel.setPreferredSize(new Dimension(WIDTH, 400));
		settingsPanel.setVisible(false);

		this.add(settingsPanel);
		this.setBackground(Color.WHITE);
	}

	public void addLocationInputListener(ActionListener listener){
		locationInput.addActionListener(listener);
	}

	private void buildSettingsCheckbox(String title){
		JCheckBox checkBox = new JCheckBox(title);
		if(model.getSettings().getSetting(title)){
			checkBox.setSelected(true);
		}
		checkBox.setFont(checkBox.getFont().deriveFont(settingsFont));
		checkBox.setBackground(Color.WHITE);
		settingsCheckBoxList.add(checkBox);
	}

	public void addCheckBoxListeners(ItemListener listener){
		for(JCheckBox checkBox : settingsCheckBoxList){
			checkBox.addItemListener(listener);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o == model.getSettings()){
			for(JCheckBox checkBox : settingsCheckBoxList){
				String setting = checkBox.getText();
				if(model.getSettings().getSetting(setting) == true){
					checkBox.setSelected(true);
				}
				else{
					checkBox.setSelected(false);
				}
			}
		}
	}
}
