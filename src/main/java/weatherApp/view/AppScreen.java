package weatherApp.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Benjy on 07/06/2016.
 */

//Problem: lack of multiple inheritance. Can't implement this and also JPanel? Although, actually, you can, if this extends JPanel?

    //But now that I think about it, is it necessary?

    //Is it better to have a specialised Colours class to refer to? Less memory taken up I suppose, SOLID.

public abstract class AppScreen extends JPanel {
    int WIDTH = 320;
    int HEIGHT = 480;
    float fontSize = 15.0f;
    float imageLabelFloat = 10.0f;
    float settingsFont = 14.0f;
    int cityFont = 32;
    int weekDaysFont = 12;
    int screenLabelfont = 30;
    int highLowFont = 12;
    int weatherDescFont = 12;
    int weekWeatherFont = 12;
    int morningFont = 12;
    int afternoonFont = 12;
    int eveningFont = 12;

    //Create a new clour to use in the app
    Color niceBlue = new Color(102, 153, 255);
    public Color darkGrey = new Color(102, 102, 153);
    Color darkBlue = new Color(60, 110, 190);

    //Create an ImageIcon to display the current weather
    String imagesFolder = "images/";
    String smallImagesFolder = "images/small";
}
