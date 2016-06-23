package weatherApp.view;

import weatherApp.model.WeatherModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Benjy on 07/06/2016.
 */
public class ProposalGrid extends AppScreen implements Observer {
	private WeatherModel model;

	JPanel locationPanel;
	JPanel daysPanel;

	private JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	private JPanel timePanel = new JPanel();
	private JPanel eventGrid = new JPanel(new GridLayout(3, 3, 7, 7));

	JButton[] buttonList = new JButton[5];

	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

	public ProposalGrid(WeatherModel model) {
		this.model = model;
		model.getWeatherForecast().addObserver(this);

		this.setPreferredSize(new Dimension(WIDTH, 360));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		locationPanel = locationPanel();
		this.add(locationPanel);

		daysPanel = new JPanel(new FlowLayout());
		//daysPanel.setBackground(Color.WHITE);
		daysPanel.setOpaque(false);

		for(int i=0; i<buttonList.length; i++){
			JButton dayButton = new JButton(model.getWeekDays().getDay(i));
			dayButton = formatButton(dayButton);
			buttonList[i] = dayButton;
			daysPanel.add(dayButton);
		}

		timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
		timePanel.setAlignmentX(Component.LEFT_ALIGNMENT);


		//Get the weather forecast break down for the current day (11am, 3pm, 7pm))
		String[] timeWeather = model.getWeatherForecast().getWeatherInTheDay();


		JLabel morning = createIconLabel("11AM", "small" + timeWeather[0] + ".png");
		morning.setFont(new Font("Century Gothic", Font.BOLD, morningFont));
		JLabel afternoon = createIconLabel("3PM", "small" + timeWeather[1] + ".png");
		afternoon.setFont(new Font("Century Gothic", Font.BOLD, afternoonFont));
		JLabel evening = createIconLabel("7PM", "small" + timeWeather[2] + ".png");
		evening.setFont(new Font("Century Gothic", Font.BOLD, eveningFont));


		evening.setAlignmentX(Component.LEFT_ALIGNMENT);
		afternoon.setAlignmentX(Component.LEFT_ALIGNMENT);
		morning.setAlignmentX(Component.LEFT_ALIGNMENT);

		timePanel.add(morning);
		timePanel.add(Box.createRigidArea(new Dimension(0, 40)));
		timePanel.add(afternoon);
		timePanel.add(Box.createRigidArea(new Dimension(0, 40)));
		timePanel.add(evening);
		//timePanel.setBackground(Color.LIGHT_GRAY);
		timePanel.setOpaque(false);
		midPanel.add(timePanel);

		ArrayList<weatherApp.model.eventRecommender.Event> suggestEvents = model.getEventRecommender().suggestEvents();

		for (weatherApp.model.eventRecommender.Event ev : suggestEvents) {
			String name = ev.getName();
			String path = ev.getPicPath();
			eventGrid.add(createIconLabel(name, path));

		}

		eventGrid.setVisible(true);
		//eventGrid.setBackground(Color.WHITE);
		eventGrid.setOpaque(false);
		midPanel.add(eventGrid);

		this.add(daysPanel);
		this.add(midPanel);
		//midPanel.setBackground(Color.WHITE);
		midPanel.setBackground(new Color(200,200,200));
		//midPanel.setOpaque(false);
		//this.setBackground(new Color(100,100,100,50));
		this.setVisible(false);

	}

	private JPanel locationPanel() {
		//Add the text label telling us what location the weather is given for
		locationPanel = new JPanel();
		JLabel location = new JLabel(model.getWeatherForecast().getLocation());
		location.setFont(new Font("Century Gothic", Font.BOLD, cityFont));
		location.setForeground(Color.WHITE);
		locationPanel.add(location);
		locationPanel.setOpaque(true);
		locationPanel.setBackground(darkBlue);
		return locationPanel;
	}

	public void addDaySelectionButtonListeners(ActionListener listener) {
		for (int i = 0; i < buttonList.length; i++) {
			JPanel dayPanel = (JPanel) this.getComponents()[1];
			JButton button = (JButton) dayPanel.getComponents()[i];
			button.addActionListener(listener);
		}
	}


	public void updateTimePanel() {
		for (int i = 0; i < 3; i++) {
			//timePanel add new Morning, Afternoon and Evening values
		}
	}


	private JButton formatButton(JButton button) {
		button.setFont(new Font("Century Gothic", Font.BOLD, weekDaysFont));
		return button;
	}


	//Once the day button is pressed we update the proposal events
	public void updateProposalGrid() {

		//Get the weather forecast break down for the current day (11am, 3pm, 7pm))
		String[] timeWeather = model.getWeatherForecast().getWeatherInTheDay();


		JLabel morning = createIconLabel("11AM", "small" + timeWeather[0] + ".png");
		JLabel afternoon = createIconLabel("3PM", "small" + timeWeather[1] + ".png");
		JLabel evening = createIconLabel("7PM", "small" + timeWeather[2] + ".png");


		evening.setAlignmentX(Component.LEFT_ALIGNMENT);
		afternoon.setAlignmentX(Component.LEFT_ALIGNMENT);
		morning.setAlignmentX(Component.LEFT_ALIGNMENT);

		timePanel.removeAll();
		timePanel.add(morning);
		timePanel.add(Box.createRigidArea(new Dimension(0, 40)));
		timePanel.add(afternoon);
		timePanel.add(Box.createRigidArea(new Dimension(0, 40)));
		timePanel.add(evening);


		eventGrid.removeAll();
		ArrayList<weatherApp.model.eventRecommender.Event> suggestEvents = model.getEventRecommender().suggestEvents();

		for (weatherApp.model.eventRecommender.Event ev : suggestEvents) {
			String name = ev.getName();
			String path = ev.getPicPath();

			eventGrid.add(createIconLabel(name, path));

		}

		midPanel.revalidate();
		midPanel.repaint();

	}

	private void updateProposalLocation(){
		JLabel locationLabel = (JLabel) locationPanel.getComponent(0);
		locationLabel.setText(model.getWeatherForecast().getLocation());
	}

	private JLabel createIconLabel(String label, String picPath) {
		String iconPath = imagesFolder + picPath;
		java.net.URL picURL = classLoader.getResource(iconPath);
		ImageIcon image = new ImageIcon(picURL);
		JLabel imageLabel = new JLabel(label);
		imageLabel.setFont(imageLabel.getFont().deriveFont(imageLabelFloat));

		imageLabel.setIcon(image);
		imageLabel.setHorizontalTextPosition(JLabel.CENTER);
		imageLabel.setVerticalTextPosition(JLabel.BOTTOM);

		imageLabel.setVisible(true);
		return imageLabel;
	}


	@Override
	public void update(Observable o, Object arg) {
		if(o == model.getWeatherForecast()){
			updateProposalLocation();
		}
	}
}
