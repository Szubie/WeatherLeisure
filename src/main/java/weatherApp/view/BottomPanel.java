package weatherApp.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by Benjy on 13/06/2016.
 */
public class BottomPanel extends AppScreen {

	JButton weatherButton;
	JButton proposalButton;
	JButton settingsButton;

	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();


	public BottomPanel() {


		//create a bottom navigation panel with all the necessary buttons
		this.setPreferredSize(new Dimension(WIDTH, 60));

		this.setBackground(darkBlue);




		settingsButton = buildIconButton("Settings");
		weatherButton = buildIconButton("Weather");
		proposalButton = buildIconButton("Activities");

		this.add(weatherButton);
		this.add(proposalButton);
		this.add(settingsButton);

		this.setVisible(true);
	}

	public JButton buildIconButton(String text){
		String path = "images/" + text + "Button.png";
		JButton button;
		try {
			java.net.URL buttonIcon = classLoader.getResource(path);
			button = new JButton(text, new ImageIcon(buttonIcon));
		}
		catch(NullPointerException e){
			e.printStackTrace();
			//Make the button without an icon
			button = new JButton(text);
		}
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setContentAreaFilled(false);
		button.setFocusable(false);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setPreferredSize(new Dimension(80, 52));
		button.setFont(new Font("Century Gothic", Font.BOLD, 12));
		button.setForeground(Color.WHITE);
		return button;
	}

	public void addWeatherButtonListener(ActionListener listener) {
		weatherButton.addActionListener(listener);
	}

	public void addProposalButtonListener(ActionListener listener) {
		proposalButton.addActionListener(listener);
	}

	public void addSettingsButtonListener(ActionListener listener) {
		settingsButton.addActionListener(listener);
	}
}
