package me.ibrt.bungeecord.commands;

import me.ibrt.bungeecord.bungeemain;
import me.ibrt.bungeecord.constants.S;
import com.google.common.collect.Lists;
import me.ibrt.universal.enums.Banreason;
import me.ibrt.universal.enums.Bantype;
import me.ibrt.universal.methods.Time;
import me.ibrt.universal.objects.Ban;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;
import java.util.List;

@Deprecated
public class BanCommand extends Command implements TabExecutor {


    public BanCommand() {
        super("ban");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (!p.hasPermission(S.permission_ban_command)) {
                p.sendMessage(S.error_noperm);
                return;
            }
        }

        if (args.length == 0) {
            S.send(sender, S.c_bra + "▌▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌");
            S.send(sender, "  " + S.prefix_ban + "Liste der Befehle");
            S.send(sender, " §7- /ban [name]");
            S.send(sender, " §7- /ban [name] remove");
            S.send(sender, " §7- /ban [name] shorten");
            S.send(sender, " §7- /ban [name] [reason]");
            S.send(sender, S.c_bra + "▌▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌");

        } else if (args.length == 1) {
            if (bungeemain.getInstance().getBanManager().getUserBans(bungeemain.getInstance().getUserManager().getUserNAME(args[0]).getUuid()).size() == 0) {
                S.send(sender, S.error_player_never_had_bans);
                return;
            }
            S.send(sender, S.c_bra + "▌▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌");
            S.send(sender, "  " + S.prefix_ban + "Liste der Bans von " + S.c_bra + " » " + bungeemain.getInstance().getGroupManager().getGroup(bungeemain.getInstance().getUserManager().getUserNAME(args[0]).getGroup()).getColour() + args[0]);
            bungeemain.getInstance().getBanManager().getUserBans(bungeemain.getInstance().getUserManager().getUserNAME(args[0]).getUuid()).forEach(ban -> {
                if (ban.isActive()) {
                    S.send(sender, S.c_bra + " - " + ChatColor.GREEN + ban.getReason().toString() + S.c_bra + " [" + S.c_def + Time.getTimeToString(ban.getRemainingTime(), false) + S.c_bra + S.c_def + " verbleibend" + S.c_bra + "]");
                } else {
                    S.send(sender, S.c_bra + " - " + S.c_def + ban.getReason().toString() + S.c_bra + " [" + S.c_def + "vor " + Time.getTimeToString(System.currentTimeMillis() - ban.getBantime(), false) + S.c_bra + "]");
                }
            });
            S.send(sender, S.c_bra + "▌▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌");
        } else if (args.length == 2) {
            if (!bungeemain.getInstance().getUserManager().isUserExistingNAME(args[0])) {
                S.send(sender, S.error_player_not_existing);
                return;
            }
            if (args[1].equalsIgnoreCase("remove")) {

            } else if (Banreason.isBanreasonExisting(args[1])) {
                Ban ban = new Ban().setBantime(System.currentTimeMillis()).setReason(Banreason.convertReason(args[1])).setUuidbanned(bungeemain.getInstance().getUserManager().getUserNAME(args[0]).getUuid());
                if(bungeemain.getInstance().getBanManager().isUserBanned(bungeemain.getInstance().getUserManager().getUserNAME(args[0]).getUuid(), ban.getType())) {
                    sender.sendMessage(S.error_player_already_banned);
                    return;
                }
                if(ban.getReason().toString().endsWith("SHORTEN")) {
                    S.send(sender, S.error_banreason_not_valid);
                    return;
                }
                if (ban.getReason() == Banreason.DISRESPECT) {
                    ban.setType(Bantype.CHATBAN);
                } else {
                    ban.setType(Bantype.LOGINBAN);
                }
                if(sender instanceof ProxiedPlayer) {
                    ban.setUuidbannedby(((ProxiedPlayer) sender).getUniqueId().toString());
                } else {
                    ban.setUuidbannedby(sender.getName());
                }

                bungeemain.getInstance().getBanManager().createBan(ban);

                if(ProxyServer.getInstance().getPlayer(args[0]) != null) {
                    if(ban.getType() == Bantype.LOGINBAN) {
                        if (ban.getReason() == Banreason.HACKING_PERM) {
                            ProxyServer.getInstance().getPlayer(args[0]).disconnect(TextComponent.fromLegacyText(S.c_main + ChatColor.BOLD + "BANSYSTEM\n\n" + S.c_err + "Du bist von dem Netzwerk " + S.c_imp + Banreason.getTimeString(ban.getReason()) + S.c_err + " gebannt!\n" + S.c_main + "Grund " + S.c_bra + "» " + S.c_err + ban.getReason().toString()));
                        } else {
                            ProxyServer.getInstance().getPlayer(args[0]).disconnect(TextComponent.fromLegacyText(S.c_main + ChatColor.BOLD + "BANSYSTEM\n\n" +S.c_err + "Du bist von dem Netzwerk für " + S.c_imp + Banreason.getTimeString(ban.getReason()) + S.c_err + " gebannt!\n" + S.c_main + "Grund " + S.c_bra + "» " + S.c_err + ban.getReason().toString() + "\n\n" + S.c_main + "Verbleibende Zeit " + S.c_bra + "» " + Time.getTimeToString(ban.getRemainingTime(), true)));
                        }
                    } else {
                        S.send(ProxyServer.getInstance().getPlayer(args[0]), S.c_bra + "▌▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌");
                        S.send(ProxyServer.getInstance().getPlayer(args[0]), "  " + S.prefix_ban + S.c_err + " Du hast einen " + S.c_imp + ban.getType() + S.c_err + " erhalten!");
                        S.send(ProxyServer.getInstance().getPlayer(args[0]), S.c_err + " Grund" + S.c_bra + " » " + S.c_imp + ban.getReason());
                        S.send(ProxyServer.getInstance().getPlayer(args[0]), S.c_err + " Verbleibende Zeit" + S.c_bra + " » " + S.c_imp + Time.getTimeToString(ban.getRemainingTime(), false));
                        S.send(ProxyServer.getInstance().getPlayer(args[0]), S.c_bra + "▌▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌");
                    }
                }
                S.send(sender, S.prefix_ban + "Der Spieler " + args[0] + " wurde aus dem Grund " + S.c_imp + ban.getReason() + S.c_def + " gebannt!");
            } else {
                S.send(sender, S.error_banreason_not_valid);
            }
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> list = Lists.newArrayList();

        if (sender instanceof ProxiedPlayer) {
            if(!sender.hasPermission(S.permission_ban_command)) {
                return list;
            }
        }

        if (args.length == 1) {
            List<String> list1 = Lists.newArrayList();
            ProxyServer.getInstance().getPlayers().forEach(p -> list1.add(p.getName()));
            list1.forEach(v -> {
                if (v.toLowerCase().startsWith(args[0].toLowerCase())) {
                    list.add(v);
                }
            });
        } else if (args.length == 2) {
            List<String> list1 = Lists.newArrayList();
            list1.addAll(Arrays.asList("info", "DISRESPECT", "HACKING_TEMP", "HACKING_PERM"));
            list1.forEach(v -> {
                if (v.toLowerCase().startsWith(args[1].toLowerCase())) {
                    list.add(v);
                }
            });
        }
        return list;
    }
}
