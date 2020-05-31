package me.ibrt.universal.managers;

import com.mongodb.client.model.Filters;
import me.ibrt.universal.objects.User;
import me.ibrt.bungeecord.bungeemain;
import me.ibrt.bungeecord.constants.M;
import org.bson.Document;

import java.util.function.Consumer;

public class UserManager {

    public boolean isUserExistingNAME(String name) {
        for (User user : M.users.values()) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return bungeemain.getInstance().getMM_S().getUsers().find(Filters.eq("name", name)).first() != null;
    }

    public boolean isUserExistingUUID(String uuid) {
        if (M.users.containsKey(uuid)) {
            return true;
        }
        return bungeemain.getInstance().getMM_S().getUsers().find(Filters.eq("uuid", uuid)).first() != null;
    }

    public void createUser(User user) {
        M.users.put(user.getUuid(), user);
        Document document = bungeemain.getInstance().getGson().fromJson(bungeemain.getInstance().getGson().toJson(user), Document.class);
        bungeemain.getInstance().getMM_S().getUsers().insertOne(document);
    }

    public User getUserUUID(String uuid) {
        if (M.users.containsKey(uuid)) {
            return M.users.get(uuid);
        }
        Document document = bungeemain.getInstance().getMM_S().getUsers().find(Filters.eq("uuid", uuid)).first();
        return bungeemain.getInstance().getGson().fromJson(document.toJson(), User.class);
    }

    public User getUserNAME(String name) {
        for (User user : M.users.values()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        Document document = bungeemain.getInstance().getMM_S().getUsers().find(Filters.eq("name", name)).first();
        return bungeemain.getInstance().getGson().fromJson(document.toJson(), User.class);
    }

    public void updateUser(String uuid, Consumer<User> update) {
        User user = getUserUUID(uuid);
        update.accept(user);
        M.users.put(user.getUuid(), user);
        Document document = bungeemain.getInstance().getGson().fromJson(bungeemain.getInstance().getGson().toJson(user), Document.class);
        bungeemain.getInstance().getMM_S().getUsers().replaceOne(Filters.eq("uuid", uuid), document);
    }

    //Setup

    public void setupUser(String uuid) {
        M.users.put(uuid, getUserUUID(uuid));
    }
}
