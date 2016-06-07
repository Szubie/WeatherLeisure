package weatherApp.view;

import weatherApp.model.WeatherViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Benjy on 07/06/2016.
 */
public class ProposalGrid extends AppScreen {
    private WeatherViewModel model;

    public JPanel daysPanel;

    JPanel midPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel timePanel = new JPanel();
    JPanel eventGrid = new JPanel(new GridLayout(3, 3, 7, 7));

    public JButton today;
    JButton firstDay;
    JButton secondDay;
    JButton thirdDay;
    JButton fourthDay;

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    public ProposalGrid(WeatherViewModel model){
        this.model = model;
        this.setPreferredSize(new Dimension(WIDTH, 360));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);

        daysPanel = new JPanel(new FlowLayout());
        daysPanel.setBackground(Color.WHITE);

        //Today selected by default through controller
        today = new JButton(model.getWeekDays().getWeekDay());
        today = formatButton(today);
        firstDay = new JButton(model.getWeekDays().getFirstDay());
        firstDay = formatButton(firstDay);
        secondDay = new JButton(model.getWeekDays().getSecondDay());
        secondDay = formatButton(secondDay);
        thirdDay = new JButton(model.getWeekDays().getThirdDay());
        thirdDay = formatButton(thirdDay);
        fourthDay = new JButton(model.getWeekDays().getFourthDay());
        fourthDay = formatButton(fourthDay);


        //Populate the days panel
        daysPanel.add(today);
        daysPanel.add(firstDay);
        daysPanel.add(secondDay);
        daysPanel.add(thirdDay);
        daysPanel.add(fourthDay);

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

        ArrayList<weatherApp.model.eventRecommender.Event> suggestEvents = model.getEventRecommender().suggestEvents();

        for(weatherApp.model.eventRecommender.Event ev : suggestEvents){
            String name = ev.getName();
            String path = ev.getPicPath();
            eventGrid.add(createIconLabel(name, path));

        }

        eventGrid.setVisible(true);
        eventGrid.setBackground(Color.WHITE);
        midPanel.add(eventGrid);

        this.add(daysPanel);
        daysPanel.setBackground(Color.WHITE);
        this.add(midPanel);
        midPanel.setBackground(Color.WHITE);
        this.setVisible(false);

    }

    public void addDaySelectionButtonListeners(ActionListener listener){
        for(int i=0; i<4; i++){
            JPanel dayPanel = (JPanel) this.getComponents()[0];
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

        //Get the weather forecast break down for the current day (11am, 3pm, 7pm))
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


}
