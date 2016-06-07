package weatherApp.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

/**
 * Created by Benjy on 07/06/2016.
 */
public class SettingsScreen extends AppScreen{

    public JPanel settingsPanel;

    public JCheckBox culture;
    public JCheckBox entertainment;
    public JCheckBox relaxation;
    public JCheckBox shopping;
    public JCheckBox sport;
    public JCheckBox eating;
    public JCheckBox drinking;

    public SettingsScreen(){

        JCheckBox defaultLocation = new JCheckBox("Default Location");
        defaultLocation.setSelected(true);
        defaultLocation.setFont(defaultLocation.getFont().deriveFont(settingsFont));
        defaultLocation.setAlignmentX(Component.LEFT_ALIGNMENT);


        culture = new JCheckBox("Culture");
        culture.setSelected(true);
        culture.setFont (culture.getFont().deriveFont(settingsFont));
        culture.setAlignmentX(Component.LEFT_ALIGNMENT);

        entertainment = new JCheckBox("Entertainment");
        entertainment.setSelected(true);
        entertainment.setFont (entertainment.getFont().deriveFont (settingsFont));

        relaxation = new JCheckBox("Relaxation");
        relaxation.setSelected(true);
        relaxation.setFont (relaxation.getFont().deriveFont (settingsFont));

        shopping = new JCheckBox("Shopping");
        shopping.setSelected(true);
        shopping.setFont (shopping.getFont().deriveFont (settingsFont));

        sport = new JCheckBox("Sport");
        sport.setSelected(true);
        sport.setFont (sport.getFont().deriveFont (settingsFont));

        eating = new JCheckBox("Restaurants");
        eating.setSelected(true);
        eating.setFont (eating.getFont().deriveFont (settingsFont));

        drinking = new JCheckBox("Bars");
        drinking.setSelected(true);
        drinking.setFont (drinking.getFont().deriveFont (settingsFont));


        this.setLayout(new FlowLayout(FlowLayout.CENTER));

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
        settingsPanel.add(drinking);

        settingsPanel.setPreferredSize(new Dimension(WIDTH, 400));
        settingsPanel.setVisible(false);

        this.add(settingsPanel);
        this.setBackground(Color.WHITE);
    }

    public void addCultureBoxListener(ItemListener listener){
        culture.addItemListener(listener);
    }
    public void addEntertainmentBoxListener(ItemListener listener){
        entertainment.addItemListener(listener);
    }
    public void addRelaxationBoxListener(ItemListener listener){
        relaxation.addItemListener(listener);
    }
    public void addShoppingBoxListener(ItemListener listener){
        shopping.addItemListener(listener);
    }
    public void addSportBoxListener(ItemListener listener){
        sport.addItemListener(listener);
    }
    public void addEatingBoxListener(ItemListener listener){
        eating.addItemListener(listener);
    }
    public void addDrinkingBoxListener(ItemListener listener){
        drinking.addItemListener(listener);
    }
}
