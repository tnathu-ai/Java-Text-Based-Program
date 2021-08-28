package Covid19;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

class TimeRelatedFunctions {
    public static String displayStartEndDate(String chosenDate, int dayAway, int DataChoice) {
        //Specifying date format
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Calendar c = new GregorianCalendar();
        try {
            Date date = sdf.parse(chosenDate);
            //Setting the date to the given date
            c.setTime(date);
        } catch (ParseException e) {
            System.out.println("Invalid input!");
            e.printStackTrace();
        }
        //Number of Days to add
        if (DataChoice == 1 || DataChoice == 2) {
            c.add(Calendar.DAY_OF_MONTH, dayAway);
        } else {
            c.add(Calendar.DAY_OF_MONTH, -dayAway);
        }

        //Day after adding the days to the input date
        String endDate = sdf.format(c.getTime());
        //Displaying the new Date after addition of Days
        System.out.println("Date after Addition: " + endDate);
        return endDate;
    }

    public static ArrayList<Date> getDatesBetween(String startDate, String endDate, int DataChoice) throws ParseException {
        //convert String to Date
        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
        Date sDate = sdf.parse(startDate);
        Date eDate = sdf.parse(endDate);

        ArrayList<Date> datesInRange = new ArrayList<>();
        Calendar c = new GregorianCalendar();
        c.setTime(sDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(eDate);

        if (DataChoice == 1 || DataChoice == 2) {
            while (c.before((endCalendar))) {
                Date result = c.getTime();
                datesInRange.add(result);
                c.add(Calendar.DATE, 1);
            }
        } else if (DataChoice == 3) {
            while (endCalendar.before((c))) {
                Date result = endCalendar.getTime();
                datesInRange.add(result);
                endCalendar.add(Calendar.DATE, 1);
            }
        }
        return datesInRange;
    }

    public static ArrayList<String> convertDateToString(ArrayList<Date> ArrList) {
        DateFormat df = new SimpleDateFormat("M/d/yyyy");
        ArrayList<String> dateToStrArr = new ArrayList<String>();

        for (int i = 0; i < ArrList.size(); i++) {
            Date DateInArr = ArrList.get(i);
            String dateToString = df.format(DateInArr);
            dateToStrArr.add(dateToString);
        }
        return dateToStrArr;
    }

    // Function to print difference in time chosenDate and endDate
    static int calculateDayAway(String chosenDate, String endDate) {
        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        // Try Block
        long dayAway = 0;
        try {
            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(chosenDate);
            Date d2 = sdf.parse(endDate);

            // Calucalte time difference
            // in time and days
            long timeDifference
                    = d2.getTime() - d1.getTime();

            dayAway = (timeDifference
                    / (1000 * 60 * 60 * 24))
                    % 365;

            // Print the date difference in days
            System.out.print("Difference " + "between two dates is: ");
            System.out.println(dayAway + " days, ");
        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) dayAway;
    }
}