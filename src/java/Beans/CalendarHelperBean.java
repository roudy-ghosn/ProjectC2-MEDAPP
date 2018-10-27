/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import java.time.YearMonth;
import java.util.Calendar;
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
        }
        
        String finalHTML = "<ul class=\"weekdays\">\n" +
"                    <li>Sunday</li>\n" +
"                    <li>Monday</li>\n" +
"                    <li>Tuesday</li>\n" +
"                    <li>Wednesday</li>\n" +
"                    <li>Thursday</li>\n" +
"                    <li>Friday</li>\n" +
"                    <li>Saturday</li>\n" +
"                </ul>";
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, monthShift);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        
        
        finalHTML += "Count = " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);;
        
        finalHTML += "<ul class=\"days\">";
        
        int currentDate = calendar.get(Calendar.DATE);
        
        for(int i=1; i< dayOfWeek; i++) {
            finalHTML += "<li class=\"day other-month\">\n" +
"                    </li>";
        }
        
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        calendar.add(Calendar.MONTH, monthShift);

        int myMonth = calendar.get(Calendar.MONTH);
        while (myMonth == calendar.get(Calendar.MONTH)) {
          if(calendar.get(Calendar.DAY_OF_WEEK) == 1) {
              finalHTML += "<ul class=\"days\">";
          }
          finalHTML += "<li class=\"day\">\n" +
"                        <div class=\"date\">"+ calendar.get(Calendar.DAY_OF_MONTH)  +"</div>                      \n" +
"                    </li>";
          if(calendar.get(Calendar.DAY_OF_WEEK) == 7) {
              finalHTML += "</ul>";
          }
          calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return finalHTML;
    }
}

