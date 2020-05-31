package me.ibrt.universal.managers;

import com.google.common.collect.Lists;
import com.mongodb.Block;
import com.mongodb.client.model.Filters;
import me.ibrt.universal.objects.Group;
import me.ibrt.universal.objects.User;
import me.ibrt.bungeecord.bungeemain;
import me.ibrt.bungeecord.constants.M;
import me.ibrt.bungeecord.constants.S;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class GroupManager {

    // Hashmap

    public boolean isGroupExisting(String groupname) {
        return M.groups.containsKey(groupname);
    }

    public boolean isGroupExisting(Integer id) {
        for (Group group : M.groups.values()) {
            if (group.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void createGroup(Group group) {
        Document document = bungeemain.getInstance().getGson().fromJson(bungeemain.getInstance().getGson().toJson(group), Document.class);
        bungeemain.getInstance().getMM_S().getGroups().insertOne(document);
    }

    public void deleteGroup(String groupname) {
        bungeemain.getInstance().getMM_S().getGroups().deleteMany(Filters.eq("name", groupname));
    }

    public void deleteGroup(Integer id) {
        bungeemain.getInstance().getMM_S().getGroups().deleteMany(Filters.eq("id", id));
    }

    public Group getGroup(String groupname) {
        if (M.groups.containsKey(groupname)) {
            return M.groups.get(groupname);
        }
        Document document = bungeemain.getInstance().getMM_S().getGroups().find(Filters.eq("name", groupname)).first();
        return bungeemain.getInstance().getGson().fromJson(document.toJson(), Group.class);
    }

    public Group getGroup(Integer id) {
        for (Group groups : M.groups.values()) {
            if(groups.getId() == id) {
                return groups;
            }
        }
        Document document = bungeemain.getInstance().getMM_S().getGroups().find(Filters.eq("id", id)).first();
        return bungeemain.getInstance().getGson().fromJson(document.toJson(), Group.class);
    }

    public List<Group> getGroups() {
        ArrayList<Group> list = Lists.newArrayList();
        list.addAll(M.groups.values());
        Collections.sort(list, (group1, group2) -> {
            if (group1.getId() > group2.getId()) {
                return -1;
            } else {
                return 1;
            }
        });
        return list;
    }

    public List<Group> getMongoGroups() {
        ArrayList<Group> groups = Lists.newArrayList();
        Block<Document> block = (Document doc) -> {
            Group group = bungeemain.getInstance().getGson().fromJson(doc.toJson(), Group.class);
            groups.add(group);
        };
        bungeemain.getInstance().getMM_S().getGroups().find().sort(Filters.eq("id", -1)).forEach(block);
        return groups;
    }

    public void updateGroup(String groupname, Consumer<Group> update) {
        Group group = getGroup(groupname);
        update.accept(group);
        M.groups.put(group.getName(), group);
        Document document = bungeemain.getInstance().getGson().fromJson(bungeemain.getInstance().getGson().toJson(group), Document.class);
        bungeemain.getInstance().getMM_S().getGroups().replaceOne(Filters.eq("name", groupname), document);
    }

    //Setup

    public void registerGroups() {
        M.groups.clear();
        getMongoGroups().forEach(group -> {
            M.groups.put(group.getName(), group);
        });
        setupStandardGroup();
    }

    public void setupStandardGroup() {
        if(!isGroupExisting(0)) {
            createGroup(new Group().setId(0).setName("Standard").setColour("§7").setPrefix(S.c_bra + "[" + S.c_def + "Standard" + S.c_bra + "] " + S.c_def).setPermissions(Lists.newArrayList()).setMembers(Lists.newArrayList()).setInheritance(Lists.newArrayList()));
        }
    }

    public void registerMember(User user) {
        for(Group group : getGroups()) {
            if (group.getId() <= 0) {
                continue;
            }
            if(group.getName().equals(user.getGroup())) {
                if(group.getMembers().contains(user.getUuid())) {
                    continue;
                }
                updateGroup(group.getName(), (Consumer<Group>) group1 -> group1.addMember(user.getUuid()));
            } else {
                if(!group.getMembers().contains(user.getUuid())) {
                    continue;
                }
                updateGroup(group.getName(), (Consumer<Group>) group1 -> group1.removeMember(user.getUuid()));
            }
        }
    }
}
