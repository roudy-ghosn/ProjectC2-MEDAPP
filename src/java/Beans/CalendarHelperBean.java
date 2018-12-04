/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import BusinessObjects.Appointment;
import CommonUtils.QueryUtils;
import CommonUtils.SessionUtils;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author eph
 */
@ManagedBean
@SessionScoped
public class CalendarHelperBean {
    private String test = "This is from the bean!";
    int x = 2;

    public int getX() {
        return x;
    }
    
    public String getTest() {
        YearMonth yearMonthObject = YearMonth.now();
        int daysInMonth = yearMonthObject.lengthOfMonth();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        return "This month has " + calendar.get(Calendar.DAY_OF_WEEK);
    }
    public String getHtmlContent() {
        return "<h3>This is a header from java</h3>";
    }
    public String getCalendar() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String id = request.getParameter("id");
        int monthShift = 0;
        if(id != null) {
            monthShift = Integer.parseInt(id);
//            if(monthShift <= 0) {
//                monthShift += 12;
//            }
//            if(monthShift > 12) {
//                monthShift -= 12;
//            }
        }
        
        
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, monthShift);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int myMonth = calendar.get(Calendar.MONTH);
        
        String finalHTML = "";
        
        finalHTML += "<h3>" + new DateFormatSymbols().getMonths()[myMonth] + " " + calendar.get(Calendar.YEAR) + "</h3>";
        
        finalHTML += "<ul class=\"weekdays\">\n" +
"                    <li>Sunday</li>\n" +
"                    <li>Monday</li>\n" +
"                    <li>Tuesday</li>\n" +
"                    <li>Wednesday</li>\n" +
"                    <li>Thursday</li>\n" +
"                    <li>Friday</li>\n" +
"                    <li>Saturday</li>\n" +
"                </ul>";
        
        finalHTML += "<ul class=\"days\">";
        
        int currentDate = calendar.get(Calendar.DATE);
        
        for(int i=1; i< dayOfWeek; i++) {
            finalHTML += "<li class=\"day other-month\">\n" +
"                    </li>";
        }
        
        List<Appointment> appointments = new ArrayList<>();
        String role = SessionUtils.getRole();
        String userID = SessionUtils.getLoggedPersonId();
        if(role.equals("Doctor")) {
            appointments = QueryUtils.getAllAppointments(userID);
        }

        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
//        calendar.add(Calendar.MONTH, 1);
        LocalDateTime dateTime = LocalDateTime.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));
        String thisMonthString = format.format(dateTime);
        thisMonthString = thisMonthString.substring(0, 7);
//        calendar.add(Calendar.MONTH, -1);
        
        ArrayList<Appointment> appoToRemove = new ArrayList<>();
        for(Iterator<Appointment> it = appointments.iterator(); it.hasNext();) {
            Appointment appo = it.next();
            Date datee = appo.getDate();
            LocalDateTime ldt = LocalDateTime.ofInstant(new java.util.Date(datee.getTime()).toInstant(), ZoneId.systemDefault());
            String appDate = format.format(ldt);
            if(!appDate.contains(thisMonthString)) {
                appoToRemove.add(appo);
            }
        }
        appointments.removeAll(appoToRemove);
        
        while (myMonth == calendar.get(Calendar.MONTH)) {
            
            
            
            
          if(calendar.get(Calendar.DAY_OF_WEEK) == 1) {
              finalHTML += "<ul class=\"days\">";
          }
          finalHTML += "<li class=\"day\">\n" +
"                        <div class=\"date\">"+ calendar.get(Calendar.DAY_OF_MONTH)  +"</div>                      \n";
          
          
          LocalDateTime date1 = LocalDateTime.ofInstant(calendar.getTime().toInstant(), ZoneId.systemDefault());
            for(Iterator<Appointment> it = appointments.iterator(); it.hasNext();) {
                Appointment appo = it.next();
                LocalDateTime date2 = LocalDateTime.ofInstant(new java.util.Date(appo.getDate().getTime()).toInstant(), ZoneId.systemDefault());
                if(format.format(date1).equals(format.format(date2))) {
                    finalHTML += "<div class=\"event\">\n";
                    if(appo.getNotes() != null) {
                        finalHTML +=  "                            <div class=\"event-desc\">\n" +
"                                "+ appo.getNotes() +"\n" +
"                            </div>\n";
                    }
                    if(appo.getTime() != null) {
                        finalHTML += "<div class=\"event-time\">\n" +
                                appo.getTime() +
"                            </div>\n";
                    }
                    finalHTML +=  "                        </div> ";
                }
            }
            
         finalHTML += "</li>";
          if(calendar.get(Calendar.DAY_OF_WEEK) == 7) {
              finalHTML += "</ul>";
          }
          calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return finalHTML;
    }
}

