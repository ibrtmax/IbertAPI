package me.ibrt.universal.methods;

import me.ibrt.bungeecord.constants.S;

public class Time {

    public static String getTimeToString(long timetoconvert, boolean ausgeschrieben) {
        long seconds = 0L;
        long minutes = 0L;
        long hours = 0L;
        long days = 0L;
        long years = 0L;

        while (timetoconvert >= 1000L) {
            timetoconvert -= 1000L;
            seconds += 1L;
        }
        while (seconds >= 60L) {
            seconds -= 60L;
            minutes += 1L;
        }
        while (minutes >= 60L) {
            minutes -= 60L;
            hours += 1L;
        }
        while (hours >= 24L) {
            hours -= 24L;
            days += 1L;
        }
        while (days >= 365L) {
            days -= 365L;
            years += 1L;
        }

        String time = S.c_imp;

        if(ausgeschrieben) {
            if (years != 0L) {
                time = time + years + (years != 1 ? " Jahre" : " Jahr") + S.c_bra + ", " + S.c_imp + days+ (days != 1 ? " Tage" : " Tag");
            } else if (days != 0L) {
                time = time + days + (days != 1 ? " Tage" : " Tag") + S.c_bra + ", " + S.c_imp + hours + (hours != 1 ? " Stunden" : " Stunde");
            } else if (hours != 0L) {
                time = time + hours + (hours != 1 ? " Stunden" : " Stunde") + S.c_bra + ", " + S.c_imp + minutes + (minutes != 1 ? " Minuten" : " Minute");
            } else if (minutes != 0L) {
                time = time + minutes + (minutes != 1 ? " Minuten" : " Minute") + S.c_bra + ", " + S.c_imp + seconds + (seconds != 1 ? " Sekunden" : " Sekunde");
            } else if (seconds != 0L) {
                time = time + S.c_imp + seconds + (seconds != 1 ? " Sekunden" : " Sekunde");
            } else {
                time = time + S.c_imp + "0 Sekunden";
            }
        } else {
            if (years != 0L) {
                time = time + years + "y" + S.c_bra + ", " + S.c_imp + days + "d";
            } else if (days != 0L) {
                time = time + days + "d" + S.c_bra + ", " + S.c_imp + hours + "h";
            } else if (hours != 0L) {
                time = time + hours + "h" + S.c_bra + ", " + S.c_imp + minutes + "m";
            } else if (minutes != 0L) {
                time = time + minutes + "m" + S.c_bra + ", " + S.c_imp + seconds + "s";
            } else if (seconds != 0L) {
                time = time + S.c_imp + seconds + "s";
            } else {
                time = time + S.c_imp + "0s";
            }
        }
        return time;
    }
}
