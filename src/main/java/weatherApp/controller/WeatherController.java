package weatherApp.controller;

import weatherApp.model.WeatherViewModel;
import weatherApp.view.WeatherView;

import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Color;
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
	}

	public void addDaySelectionButtonListeners(final WeatherView view){
		view.addDaySelectionButtonListeners(new ActionListener(){
            public void actionPerformed(ActionEvent e){
	            view.disSelectAll(view.daysPanel);
	            JButton but = (JButton) e.getSource();
	            String weekDay = but.getText();
	            //make a call to the Weather Model to set the week day accordingly
	            model.getWeekDays().setWeekDay(weekDay);
	            but.setBackground(view.darkGrey);
				but.setForeground(Color.WHITE);
	            but.setOpaque(true);
	            view.updateProposalGrid();
        	}
		});
	}

	public void addSearchBarActionListener(final WeatherView view){
	    //Here is an action listener for the text box to update the location in the weatherApp.model
        view.addSearchBarListener(
	        new ActionListener(){
	            public void actionPerformed(ActionEvent e){
		            String text = view.locationEntry.getText();
		            String cityName = view.locationEntry.getText();
		            if(text.contains(",")){
		                String[] splitText = text.split(",");
		                cityName = splitText[0];
		            }
		            if(model.getListOfCities().contains(cityName)){
		                model.getWeatherForecast().setLocation(text);
		                view.updateLocation();
		            }
		            else{
		                JOptionPane.showMessageDialog(view.weather, "Sorry, that location either does not exist or is not supported at this time.");
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
		            view.updateProposalGrid();
		            view.hideSettings();
		            view.hideWeatherMain();
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
}