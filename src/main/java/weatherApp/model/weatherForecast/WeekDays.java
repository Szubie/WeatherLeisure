package weatherApp.model.weatherForecast;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WeekDays{

	private String[] daysOfWeek = new String[5];
	private String weekDay;
	private String firstDay;
	private String secondDay;
	private String thirdDay;
	private String fourthDay;

	//Numeric representation of today's day of the week
	private int today;

	public static void main(String[] args){
		WeekDays test = new WeekDays();
		for(String day: test.daysOfWeek){
			System.out.println(day);
		}
	}

	public WeekDays(){
		setWeekDays();
	}


	private void setWeekDays(){
		DateFormat dateFormat = new SimpleDateFormat("EEE, dd, MMMM, YYYY");
        Date date = new Date();
        String theDate = dateFormat.format(date);
        String theDay = dateFormat.format(date).substring(0,3);
        setWeekDay(theDay);
        daysOfWeek[0] = theDay;
        Calendar c = Calendar.getInstance();

        for(int i=1; i<daysOfWeek.length; i++){
        	try{
	        	c.setTime(dateFormat.parse(theDate));
	        }
	        catch(Exception e){e.printStackTrace();}
	        c.add(Calendar.DATE, 1);
	        theDate = dateFormat.format(c.getTime());
	        daysOfWeek[i] = theDate.substring(0,3);       	
        }
	}

	public String[] getWeekDays(){
		return daysOfWeek;
	}

	public void setWeekDay(String day){
		weekDay = day;
	}

	public String getWeekDay(){
		return daysOfWeek[0];
	}

	public String getFirstDay(){
		return daysOfWeek[1];
	}
	
	public String getSecondDay(){
		return daysOfWeek[2];
	}
	
	public String getThirdDay(){
		return daysOfWeek[3];
	}
	
	public String getFourthDay(){
		return daysOfWeek[4];
	}

}