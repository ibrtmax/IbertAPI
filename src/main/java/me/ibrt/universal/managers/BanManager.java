package me.ibrt.universal.managers;

import com.google.common.collect.Lists;
import com.mongodb.Block;
import com.mongodb.client.model.Filters;
import me.ibrt.universal.enums.Bantype;
import me.ibrt.universal.objects.Ban;
import me.ibrt.bungeecord.bungeemain;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BanManager {

    //Ban Section

    public boolean isBanExisting(long bantime) {
        return bungeemain.getInstance().getMM_S().getBans().find(Filters.eq("bantime", bantime)).first() != null;
    }

    public void createBan(Ban ban) {
        Document document = bungeemain.getInstance().getGson().fromJson(bungeemain.getInstance().getGson().toJson(ban), Document.class);
        bungeemain.getInstance().getMM_S().getBans().insertOne(document);
    }

    public void deleteBan(long bantime) {
        bungeemain.getInstance().getMM_S().getBans().deleteOne(Filters.eq("bantime", bantime));
    }

    public Ban getBan(long bantime) {
        Document document = bungeemain.getInstance().getMM_S().getBans().find(Filters.eq("bantime", bantime)).first();
        return bungeemain.getInstance().getGson().fromJson(document.toJson(), Ban.class);
    }

    public void updateBan(long bantime, Consumer<Ban> update) {
        Ban ban = getBan(bantime);
        update.accept(ban);
        Document doc = bungeemain.getInstance().getGson().fromJson(bungeemain.getInstance().getGson().toJson(ban), Document.class);
        bungeemain.getInstance().getMM_S().getBans().replaceOne(Filters.eq("bantime", bantime), doc);
    }

    //User Section

    public boolean isUserBanned(String uuidbanned, Bantype type) {
        for (Ban ban : getUserActiveBans(uuidbanned)) {
            if (ban.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Ban getUserActiveBan(String uuidbanned, Bantype type) {
        for (Ban ban : getUserActiveBans(uuidbanned)) {
            if (ban.getType() == type) {
                return ban;
            }
        }
        return null;
    }

    public List<Ban> getUserActiveBans(String uuidbanned) {
        ArrayList<Ban> bans = Lists.newArrayList();
        Block<Document> block = (Document doc) -> {
            Ban ban = bungeemain.getInstance().getGson().fromJson(doc.toJson(), Ban.class);
            if (ban.isActive()) {
                bans.add(ban);
            }
        };
        bungeemain.getInstance().getMM_S().getBans().find(Filters.eq("uuidbanned", uuidbanned)).sort(Filters.eq("bantime", -1)).forEach(block);
        return bans;
    }

    public List<Ban> getUserBans(String uuidbanned) {
        ArrayList<Ban> bans = Lists.newArrayList();
        Block<Document> block = (Document doc) -> {
            Ban ban = bungeemain.getInstance().getGson().fromJson(doc.toJson(), Ban.class);
            bans.add(ban);
        };
        bungeemain.getInstance().getMM_S().getBans().find(Filters.eq("uuidbanned", uuidbanned)).sort(Filters.eq("bantime", -1)).forEach(block);
        return bans;
    }
}
