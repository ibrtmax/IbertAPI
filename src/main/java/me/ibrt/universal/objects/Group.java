package me.ibrt.universal.objects;

import lombok.Getter;

import java.util.List;

@Getter
public class Group {

    private int id;
    private String name;
    private String colour;
    private String prefix;
    private List<String> permissions;
    private List<String> members;
    private List<String> inheritance;

    public Group setId(int id) {
        this.id = id;
        return this;
    }

    public Group setName(String name) {
        this.name = name;
        return this;
    }

    public Group setColour(String colour) {
        this.colour = colour;
        return this;
    }

    public Group setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public Group setPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public Group setMembers(List<String> members) {
        this.members = members;
        return this;
    }

    public Group setInheritance(List<String> inheritance) {
        this.inheritance = inheritance;
        return this;
    }

    public Group addPermission(String permission) {
        this.permissions.add(permission);
        return this;
    }

    public Group addMember(String member) {
        this.members.add(member);
        return this;
    }

    public Group addInheritance(String inherit) {
        this.inheritance.add(inherit);
        return this;
    }

    public Group removePermission(String permission) {
        this.permissions.remove(permission);
        return this;
    }

    public Group removeMember(String member) {
        this.members.remove(member);
        return this;
    }

    public Group removeInheritance(String inheritance) {
        this.inheritance.remove(inheritance);
        return this;
    }
}
