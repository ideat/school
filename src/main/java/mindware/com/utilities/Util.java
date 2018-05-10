package mindware.com.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {
    public Date stringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH); //
        Date fecha = new Date();
        try {
            fecha = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fecha;
    }

    public LocalDate stringToLocalDate(String fecha, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        Date parsed = null;
        try {
            parsed = sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String h;
        h = DateFormat.getDateInstance().format(parsed);
        return LocalDate.parse(h, formatter);

    }

    public Integer getYearToday(){
        Date date = new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);

    }

    public Integer getYearDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

}
