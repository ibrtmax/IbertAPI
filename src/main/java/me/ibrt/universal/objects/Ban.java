package me.ibrt.universal.objects;

import lombok.Getter;
import me.ibrt.universal.enums.Banreason;
import me.ibrt.universal.enums.Bantype;

public class Ban {

    @Getter private long bantime;
    @Getter private Bantype type;
    @Getter private Banreason reason;
    @Getter private String uuidbanned;
    @Getter private String uuidbannedby;

    public Ban setBantime(Long bantime) {
        this.bantime = bantime;
        return this;
    }

    public Ban setType(Bantype type) {
        this.type = type;
        return this;
    }

    public Ban setReason(Banreason reason) {
        this.reason = reason;
        return this;
    }

    public Ban setUuidbanned(String uuidbanned) {
        this.uuidbanned = uuidbanned;
        return this;
    }

    public Ban setUuidbannedby(String uuidbannedby) {
        this.uuidbannedby = uuidbannedby;
        return this;
    }

    public boolean isActive() {
        if(getReason() == Banreason.HACKING_PERM) {
            return true;
        }
        return getRemainingTime() > 0;
    }

    public long getRemainingTime() {
        if(getReason() == Banreason.HACKING_PERM) {
            return -1;
        }
        return (Banreason.getTime(reason) + Long.valueOf(bantime)) - System.currentTimeMillis();
    }
}
