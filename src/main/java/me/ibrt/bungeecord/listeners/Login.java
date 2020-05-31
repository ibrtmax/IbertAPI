package me.ibrt.bungeecord.listeners;

import me.ibrt.universal.enums.Banreason;
import me.ibrt.universal.enums.Bantype;
import me.ibrt.universal.methods.Time;
import me.ibrt.universal.objects.Ban;
import me.ibrt.universal.objects.User;
import me.ibrt.bungeecord.bungeemain;
import me.ibrt.bungeecord.constants.S;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Login implements Listener {

    @EventHandler
    public void onLogin(LoginEvent e) {

        bungeemain.getInstance().getGroupManager().setupStandardGroup();

        if (bungeemain.getInstance().getUserManager().isUserExistingUUID(e.getConnection().getUniqueId().toString())) {

            // BAN CHECK
            if (bungeemain.getInstance().getBanManager().isUserBanned(e.getConnection().getUniqueId().toString(), Bantype.LOGINBAN)) {
                e.setCancelled(true);
                Ban ban = bungeemain.getInstance().getBanManager().getUserActiveBan(e.getConnection().getUniqueId().toString(), Bantype.LOGINBAN);
                if (ban.getRemainingTime() == -1) {
                    e.setCancelReason(TextComponent.fromLegacyText(S.c_main + ChatColor.BOLD + "BANSYSTEM\n\n" + S.c_err + "Du bist von dem Netzwerk " + S.c_imp + Banreason.getTimeString(ban.getReason()) + S.c_err + " gebannt!\n" + S.c_main + "Grund " + S.c_bra + "» " + S.c_err + ban.getReason().toString()));
                } else {
                    e.setCancelReason(TextComponent.fromLegacyText(S.c_main + ChatColor.BOLD + "BANSYSTEM\n\n" + S.c_err + "Du bist von dem Netzwerk für " + S.c_imp + Banreason.getTimeString(ban.getReason()) + S.c_err + " gebannt!\n" + S.c_main + "Grund " + S.c_bra + "» " + S.c_err + ban.getReason().toString() + "\n\n" + S.c_main + "Verbleibende Zeit " + S.c_bra + "» " + Time.getTimeToString(ban.getRemainingTime(), true)));
                }
                return;
            }

            // USER ERSTELLEN ODER UPDATEN
            bungeemain.getInstance().getUserManager().updateUser(e.getConnection().getUniqueId().toString(), user -> {
                user.setName(e.getConnection().getName()).setLastLogin(System.currentTimeMillis());
                if (!bungeemain.getInstance().getGroupManager().isGroupExisting(user.getGroup())) {
                    user.setGroup(bungeemain.getInstance().getGroupManager().getGroup(0).getName());
                }
                bungeemain.getInstance().getGroupManager().registerMember(user);
            });

            bungeemain.getInstance().getUserManager().setupUser(e.getConnection().getUniqueId().toString());
        } else {
            bungeemain.getInstance().getUserManager().createUser(new User().setName(e.getConnection().getName()).setUUID(e.getConnection().getUniqueId().toString()).setCreateDate(System.currentTimeMillis()).setLastLogin(System.currentTimeMillis()).setGroup(bungeemain.getInstance().getGroupManager().getGroup(0).getName()));
        }
    }
}
