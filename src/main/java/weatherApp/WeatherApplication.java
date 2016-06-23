package weatherApp;

import weatherApp.controller.WeatherController;
import weatherApp.model.WeatherModel;
import weatherApp.view.WeatherView;

import javax.swing.SwingUtilities;

public class WeatherApplication {
	public static void main(final String args[]) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				if (args.length > 0) {
					WeatherModel model = new WeatherModel(4);
					WeatherView view = new WeatherView(model, 1);
					WeatherController controller = new WeatherController(view, model);
				} else {
					WeatherModel model = new WeatherModel(3);
					WeatherView view = new WeatherView(model, 0);
					//Attach the weatherApp.controller to connect this weatherApp.view and the weatherApp.model
					WeatherController controller = new WeatherController(view, model);

					//WeatherView view2 = new WeatherView(model, 0);
					//controller.addView(view2);
				}

			}
		});
	}
}