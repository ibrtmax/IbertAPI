package me.ibrt.bungeecord.listeners;

import me.ibrt.bungeecord.bungeemain;
import com.google.common.collect.Lists;
import me.ibrt.universal.objects.Group;
import me.ibrt.universal.objects.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

public class PermissionCheck implements Listener {

    @EventHandler
    public void onCheck(PermissionCheckEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();

        User user = bungeemain.getInstance().getUserManager().getUserUUID(p.getUniqueId().toString());
        Group group = bungeemain.getInstance().getGroupManager().getGroup(user.getGroup());

        if (group.getPermissions().contains(e.getPermission())) {
            e.setHasPermission(true);
        } else {
            for (String inheritants : getInheritance(Lists.newArrayList(), group.getName())) {
                Group group2 = bungeemain.getInstance().getGroupManager().getGroup(inheritants);
                if (group2.getPermissions().contains(e.getPermission())) {
                    e.setHasPermission(true);
                    break;
                }
                e.setHasPermission(false);
            }
        }
    }

    public List<String> getInheritance(List<String> list, String groupname) {
        Group group = bungeemain.getInstance().getGroupManager().getGroup(groupname);
        for (String inherit : group.getInheritance()) {
            if (!list.contains(inherit)) list.add(inherit);
            if (!bungeemain.getInstance().getGroupManager().getGroup(inherit).getInheritance().isEmpty()) {
                List<String> cList = getInheritance(Lists.newArrayList(), inherit);
                for (String subChild : cList) {
                    if (!list.contains(subChild)) list.add(subChild);
                }
            }
        }
        return list;
    }
}
