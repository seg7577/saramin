package com.example.saramin.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateParser {

    public static Date parsePostDate(String postDateText) {
        try {
            int daysAgo = Integer.parseInt(postDateText.replace("일 전 등록", "").trim());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -daysAgo);
            return calendar.getTime();
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in post date text: " + postDateText);
            return null;
        }
    }

    public static Date parseDeadlineDate(String deadlineText) {
        try {
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            if (deadlineText.startsWith("D-")) {
                int daysLeft = Integer.parseInt(deadlineText.replace("D-", "").trim());
                calendar.add(Calendar.DAY_OF_YEAR, daysLeft);
                return calendar.getTime();
            } else {
                String[] parts = deadlineText.split("~");
                if (parts.length > 1) {
                    String datePart = parts[1].trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("M.d(E)");
                    Date parsedDate = sdf.parse(datePart);

                    Calendar deadlineCalendar = Calendar.getInstance();
                    deadlineCalendar.setTime(parsedDate);

                    int deadlineMonth = deadlineCalendar.get(Calendar.MONTH) + 1;
                    int deadlineDay = deadlineCalendar.get(Calendar.DAY_OF_MONTH);

                    if (deadlineMonth < currentMonth || (deadlineMonth == currentMonth && deadlineDay < currentDay)) {
                        deadlineCalendar.set(Calendar.YEAR, currentYear + 1);
                    } else {
                        deadlineCalendar.set(Calendar.YEAR, currentYear);
                    }

                    return deadlineCalendar.getTime();
                } else {
                    System.err.println("Invalid deadline text format: " + deadlineText);
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format in deadline text: " + deadlineText);
            return null;
        } catch (ParseException e) {
            System.err.println("Error parsing deadline date: " + e.getMessage());
            return null;
        }
    }
}