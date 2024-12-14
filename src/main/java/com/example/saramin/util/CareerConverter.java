package com.example.saramin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CareerConverter {

    public static Integer extractMinCareer(String career) {
        if (career == null || career.isEmpty()) {
            return null;
        }

        String regex = "(신입|경력무관|경력\\s?(\\d{1,2})년|\\d{1,2}\\s?~\\s?\\d{1,2}년)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(career);

        if (matcher.find()) {
            String match = matcher.group(0);
            if (match.startsWith("신입") || match.startsWith("경력무관")) {
                return 0;
            } else if (match.contains("~")) {
                return Integer.parseInt(match.split("~")[0].trim().replaceAll("\\D", ""));
            } else {
                return Integer.parseInt(match.replaceAll("\\D", ""));
            }
        }

        return null;
    }

    public static Integer extractMaxCareer(String career) {
        if (career == null || career.isEmpty()) {
            return null;
        }

        String regex = "(신입|경력무관|경력\\s?(\\d{1,2})년|\\d{1,2}\\s?~\\s?(\\d{1,2})년)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(career);

        if (matcher.find()) {
            String match = matcher.group(0);
            if (match.startsWith("신입") || match.startsWith("경력무관")) {
                return 999; // "신입" 또는 "경력무관"인 경우 최대 경력 999
            } else if (match.startsWith("경력") && match.contains("년")) {
                return 999;  // "경력 n년"인 경우 최대 경력을 999로 설정
            } else if (match.contains("~")) {
                String[] parts = match.split("~");
                return Integer.parseInt(parts[1].trim().replaceAll("\\D", ""));
            } else {
                return Integer.parseInt(match.replaceAll("\\D", ""));
            }
        }

        return null;
    }
}
