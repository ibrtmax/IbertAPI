package me.ibrt.universal.enums;

public enum Banreason {

    DISRESPECT, DISRESPECT_SHORTEN, HACKING_TEMP, HACKING_TEMP_SHORTEN, HACKING_PERM, HACKING_PERM_SHORTEN;

    public static boolean isBanreasonExisting(String reason) {
        for(Banreason reasons : Banreason.values()) {
            if(reasons.toString().equals(reason)) {
                return true;
            }
        }
        return false;
    }

    public static Banreason convertReason(String reason) {
        for(Banreason reasons : Banreason.values()) {
            if(reasons.toString().equals(reason)) {
                return reasons;
            }
        }
        return null;
    }

    public static long getTime(Banreason banreason) {
        if(banreason == Banreason.DISRESPECT) {
            return 24L*60L*60L*1000L;
        }

        if(banreason == Banreason.DISRESPECT_SHORTEN) {
            return 6L*60L*60L*1000L;
        }

        if(banreason == Banreason.HACKING_TEMP) {
            //return 30L*24L*60L*60L*1000L;
            return 10000L;
        }

        if(banreason == Banreason.HACKING_TEMP_SHORTEN) {
            return 7L*24L*60L*60L*1000L;
        }

        if(banreason == Banreason.HACKING_PERM) {
            return -1L;
        }

        if(banreason == Banreason.HACKING_PERM_SHORTEN) {
            return 14L*24L*60L*60L*1000L;
        }

        return 0L;
    }

    public static String getTimeString(Banreason banreason) {
        if(banreason == Banreason.DISRESPECT) {
            return "einen Tag";
        }
        if(banreason == Banreason.HACKING_TEMP) {
            return "30 Tage";
        }

        if(banreason == Banreason.HACKING_PERM) {
            return "PERMANENT";
        }

        if(banreason == Banreason.DISRESPECT_SHORTEN) {
            return "6 Stunden";
        }
        if(banreason == Banreason.HACKING_TEMP_SHORTEN) {
            return "7 Tage";
        }

        if(banreason == Banreason.HACKING_PERM_SHORTEN) {
            return "14 Tage";
        }

        return "";
    }
}
