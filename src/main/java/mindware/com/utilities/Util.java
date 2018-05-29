package mindware.com.utilities;

import com.vaadin.server.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {

     public StreamResource getStream(File inputfile) {

        StreamResource.StreamSource source = new StreamResource.StreamSource() {

            public InputStream getStream() {

                InputStream input=null;
                try
                {
                    input = new FileInputStream(inputfile);
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                return input;

            }
        };
        StreamResource resource = new StreamResource ( source, inputfile.getName());
        return resource;
    }

    public Date dateWithFixedDayAndIncMonth(int fixedDay, LocalDate date, int incMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(localDateToDate(date));
        calendar.add(Calendar.MONTH,incMonth);

        int year = calendar.get(calendar.YEAR) ;
        int month = calendar.get(calendar.MONTH);
        int day = fixedDay;

        calendar.set(year,month,day);
        return calendar.getTime();
    }

    public static int monthsBetweenIgnoreDays(LocalDate start, LocalDate end) {
        start = start.withDayOfMonth(1);
        end = end.withDayOfMonth(1);
        Period diff = Period.between(start, end);
        return diff.getMonths();
    }

    public Date localDateToDate(LocalDate localDate){
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return date;
    }

    public LocalDate dateToLocalDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public String localDateToString(LocalDate localDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(localDateToDate(localDate));
        Integer year = calendar.get(calendar.YEAR) ;
        Integer month = calendar.get(calendar.MONTH)+1;
        Integer day = calendar.get(calendar.DAY_OF_MONTH);
        String monthString;
        if (month<10) monthString = '0' + month.toString();
        else monthString = month.toString();

        return day.toString()+'-'+monthString+'-'+year.toString();

    }

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
