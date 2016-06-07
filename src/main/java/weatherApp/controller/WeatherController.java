package weatherApp.controller;

import weatherApp.model.WeatherViewModel;
import weatherApp.view.WeatherView;

import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;


public class WeatherController{

	private ArrayList<WeatherView> viewList = new ArrayList<WeatherView>();
	private WeatherViewModel model;

	public WeatherController(WeatherView view, WeatherViewModel model){
		//this.weatherApp.view=weatherApp.view;
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
				view.proposalGrid.disSelectAll(view.proposalGrid.daysPanel);
				JButton but = (JButton) e.getSource();
				String weekDay = but.getText();
				//make a call to the Weather Model to set the week day accordingly
				model.getWeekDays().setWeekDay(weekDay);
				//but.setBackground(view.darkGrey);
				//but.setForeground(Color.WHITE);
				//but.setOpaque(true);
				view.proposalGrid.updateProposalGrid();
			}
		});
	}

	public void addSearchBarActionListener(final WeatherView view){
	    //Here is an action listener for the text box to update the location in the weatherApp.model
        view.weatherScreen.addSearchBarListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String text = view.weatherScreen.locationEntry.getText();
						String cityName = view.weatherScreen.locationEntry.getText();
						if (text.contains(",")) {
							String[] splitText = text.split(",");
							cityName = splitText[0];
						}
						if (model.getListOfCities().contains(cityName)) {
							model.getWeatherForecast().setLocation(text);
							view.weatherScreen.updateLocation();
						} else {
							JOptionPane.showMessageDialog(view.frame, "Sorry, that location either does not exist or is not supported at this time.");
						}
					}
				});
    }

    public void addWeatherButtonListener(final WeatherView view){
    	view.addWeatherButtonListener(
    		new ActionListener(){
	            public void actionPerformed(ActionEvent e){
		            view.hideSettings();
		            view.hideProposal();
		            view.showWeatherMain();
	        	}
	        });
	}

	public void addProposalButtonListener(final WeatherView view){
    	view.addProposalButtonListener(
    		new ActionListener(){
	            public void actionPerformed(ActionEvent e){
		            view.proposalGrid.updateProposalGrid();
		            view.hideSettings();
		            view.hideWeatherMain();
					//Set the current day button to be selected by default
					JRootPane rootPane = view.frame.getRootPane();
					rootPane.setDefaultButton(view.proposalGrid.today);
		            view.showProposal();
	        	}
	        });
	}

	public void addSettingsButtonListener(final WeatherView view){
		view.addSettingsButtonListener(
			new ActionListener(){
		        public void actionPerformed(ActionEvent e){
		            view.hideWeatherMain();
		            view.hideProposal();
		            view.showSettings();
		        }
	        });
	}

	public void addSettingsPageListeners(final WeatherView view){
		view.settingsScreen.addCultureBoxListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					model.getEventRecommender().setCulture(true);
					view.proposalGrid.updateProposalGrid();

				} else {
					model.getEventRecommender().setCulture(false);
					view.proposalGrid.updateProposalGrid();

				};
			}
		});

		view.settingsScreen.addEntertainmentBoxListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.getEventRecommender().setEntertainment(true);
					view.proposalGrid.updateProposalGrid();

				} else {
					model.getEventRecommender().setEntertainment(false);
					view.proposalGrid.updateProposalGrid();

				};
			}
		});

		view.settingsScreen.addRelaxationBoxListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.getEventRecommender().setRelaxation(true);
					view.proposalGrid.updateProposalGrid();
				} else {
					model.getEventRecommender().setRelaxation(false);
					view.proposalGrid.updateProposalGrid();
				};
			}
		});

		view.settingsScreen.addShoppingBoxListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.getEventRecommender().setShopping(true);
					view.proposalGrid.updateProposalGrid();
				} else {
					model.getEventRecommender().setShopping(false);
					view.proposalGrid.updateProposalGrid();
				};
			}
		});

		view.settingsScreen.addSportBoxListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.getEventRecommender().setSport(true);
					view.proposalGrid.updateProposalGrid();
				} else {
					model.getEventRecommender().setSport(false);
					view.proposalGrid.updateProposalGrid();
				};
			}
		});

		view.settingsScreen.addEatingBoxListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					model.getEventRecommender().setEating(true);
					view.proposalGrid.updateProposalGrid();
				} else {
					model.getEventRecommender().setEating(false);
					view.proposalGrid.updateProposalGrid();
				};
			}
		});

		view.settingsScreen.addDrinkingBoxListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					model.getEventRecommender().setDrinking(true);
					view.proposalGrid.updateProposalGrid();
				} else {
					model.getEventRecommender().setDrinking(false);
					view.proposalGrid.updateProposalGrid();
				};
			}
		});


	}


}