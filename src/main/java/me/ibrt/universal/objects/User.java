package me.ibrt.universal.objects;

import lombok.Getter;

@Getter
public class User {

    private String uuid;
    private String name;
    private String group;
    private long createDate;
    private long lastLogin;

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setUUID(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public User setGroup(String group) {
        this.group = group;
        return this;
    }

    public User setCreateDate(long createDate) {
        this.createDate = createDate;
        return this;
    }

    public User setLastLogin(long lastLogin) {
        this.lastLogin = lastLogin;
        return this;
    }
}
