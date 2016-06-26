package weatherApp.controller;

import weatherApp.model.WeatherModel;
import weatherApp.view.WeatherView;

import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


public class WeatherController{

	private ArrayList<WeatherView> viewList = new ArrayList<WeatherView>();
	private WeatherModel model;

	public WeatherController(WeatherView view, WeatherModel model){
		this.model=model;
		addActionListeners(view);
		viewList.add(view);
	}

	public void addView(WeatherView view){
		addActionListeners(view);
		viewList.add(view);
	}

	public void addActionListeners(WeatherView view){
		addSearchBarActionListener(view);
		addWeatherButtonListener(view);
		addProposalButtonListener(view);
		addSettingsButtonListener(view);
		addDaySelectionButtonListeners(view);
		addSettingsPageListeners(view);
	}

	public void addDaySelectionButtonListeners(final WeatherView view){
		view.proposalGrid.addDaySelectionButtonListeners(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton but = (JButton) e.getSource();
				String weekDay = but.getText();
				//make a call to the Weather Model to set the week day accordingly
				model.getWeekDays().setWeekDay(weekDay);
				view.proposalGrid.updateProposalGrid();
			}
		});
	}


	public void addSearchBarActionListener(final WeatherView view){
		view.weatherScreen.addSearchBarListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						JTextField searchBar = (JTextField) e.getSource();
						String text = searchBar.getText();
						model.getWeatherForecast().setLocation(text);
					}
				}
		);
	}

    public void addWeatherButtonListener(final WeatherView view){
    	view.bottomPanel.addWeatherButtonListener(
    		new ActionListener(){
	            public void actionPerformed(ActionEvent e){
					for(WeatherView view : viewList) {
						view.hideSettings();
						view.hideProposal();
						view.showWeatherMain();
					}
	        	}
	        });
	}

	public void addProposalButtonListener(final WeatherView view){
    	view.bottomPanel.addProposalButtonListener(
    		new ActionListener(){
	            public void actionPerformed(ActionEvent e){
					for(WeatherView view : viewList) {
						view.proposalGrid.updateProposalGrid();
						view.hideSettings();
						view.hideWeatherMain();
						view.showProposal();
					}
	        	}
	        });
	}

	public void addSettingsButtonListener(final WeatherView view){
		view.bottomPanel.addSettingsButtonListener(
			new ActionListener(){
		        public void actionPerformed(ActionEvent e){
					for(WeatherView view : viewList) {
						view.hideWeatherMain();
						view.hideProposal();
						view.showSettings();
					}
		        }
	        });
	}

	public void addSettingsPageListeners(final WeatherView view){
		addSettingsLocationInputListener(view);
		addCheckBoxListeners(view);
	}

	private void addSettingsLocationInputListener(final WeatherView view){
		view.settingsScreen.addLocationInputListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JTextField textField = (JTextField) e.getSource();
				String location = textField.getText();
				model.getWeatherForecast().setLocation(location);
				if(model.getWeatherForecast().isValidLocation()){
					model.getSettings().setDefaultLocation(location);
				}
/*				else{
					JOptionPane.showMessageDialog(view.frame, "Sorry, couldn't find that location!");
				}*/
			}
		});
	}

	private void addCheckBoxListeners(final WeatherView view){
		view.settingsScreen.addCheckBoxListeners(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JCheckBox checkBox = (JCheckBox) e.getSource();
				model.getSettings().setSetting(checkBox.getText());
			}
		});
	}

}