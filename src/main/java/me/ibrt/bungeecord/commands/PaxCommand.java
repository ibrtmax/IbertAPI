package me.ibrt.bungeecord.commands;

import me.ibrt.universal.objects.TextBuilder;
import me.ibrt.bungeecord.bungeemain;
import me.ibrt.bungeecord.constants.M;
import me.ibrt.bungeecord.constants.S;
import com.google.common.collect.Lists;
import me.ibrt.universal.objects.Group;
import me.ibrt.universal.objects.User;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;
import java.util.function.Consumer;

public class PaxCommand extends Command implements TabExecutor {


    public PaxCommand() {
        super("pax");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (!p.hasPermission(S.permission_group_command)) {
                S.send(sender, S.error_noperm);
                return;
            }
        }
        if (args.length == 0) {
            S.send(sender, S.c_bra + "▌▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌");
            S.send(sender, "  " + S.prefix_pax + "Liste der Befehle");
            S.send(sender, " §7- /pax user [name]");
            S.send(sender, " §7- /pax user [name] set group [group]");
            S.send(sender, " §7- /pax groups");
            S.send(sender, " §7- /pax group [name]");
            S.send(sender, " §7- /pax group [name] create");
            S.send(sender, " §7- /pax group [name] delete");
            S.send(sender, S.c_bra + "▌▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌");
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("user")) {
                S.send(sender, S.c_bra + "▌▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌");
                S.send(sender, "  " + S.prefix_pax + "Liste der Befehle");
                S.send(sender, " §7- /pax user [name]");
                S.send(sender, " §7- /pax user [name] set group [group]");
                S.send(sender, S.c_bra + "▌▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌");
            } else if (args[0].equalsIgnoreCase("group")) {
                S.send(sender, S.c_bra + "▌▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌");
                S.send(sender, "  " + S.prefix_pax + "Liste der Befehle");
                S.send(sender, " §7- /pax groups");
                S.send(sender, " §7- /pax group [name]");
                S.send(sender, " §7- /pax group [name] create");
                S.send(sender, " §7- /pax group [name] delete");
                S.send(sender, S.c_bra + "▌▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌");
            } else if (args[0].equalsIgnoreCase("groups")) {
                S.send(sender, S.c_bra + "▌▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌");
                S.send(sender, "  " + S.prefix_pax + "Liste der Gruppen");
                bungeemain.getInstance().getGroupManager().getGroups().forEach(group -> sender.sendMessage(new TextBuilder(S.c_bra + "- " + group.getColour() + group.getName() + S.c_bra + " (" + S.c_def + group.getId() + S.c_bra + ")").addHover(S.c_def + ChatColor.ITALIC + "Klicke hier für mehr Informationen zu " + S.c_bra + ChatColor.ITALIC + "» " + group.getColour() + ChatColor.ITALIC + group.getName(), HoverEvent.Action.SHOW_TEXT).addClick("/pax group " + group.getName(), ClickEvent.Action.RUN_COMMAND).build()));
                S.send(sender, S.c_bra + "▌▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌");
            } else if (args[0].equalsIgnoreCase("reload")) {
                bungeemain.getInstance().getGroupManager().registerGroups();
                M.users.clear();
                for (ProxiedPlayer pl : ProxyServer.getInstance().getPlayers()) {
                    if (!bungeemain.getInstance().getUserManager().isUserExistingUUID(pl.getUniqueId().toString())) {
                        bungeemain.getInstance().getUserManager().createUser(new User().setName(pl.getName()).setUUID(pl.getUniqueId().toString()).setCreateDate(System.currentTimeMillis()).setLastLogin(System.currentTimeMillis()).setGroup(bungeemain.getInstance().getGroupManager().getGroup(0).getName()));
                        continue;
                    }
                    bungeemain.getInstance().getUserManager().updateUser(pl.getUniqueId().toString(), user -> {
                        if (!bungeemain.getInstance().getGroupManager().isGroupExisting(user.getGroup())) {
                            user.setGroup(bungeemain.getInstance().getGroupManager().getGroup(0).getName());
                        }
                        bungeemain.getInstance().getGroupManager().registerMember(user);
                        bungeemain.getInstance().getUserManager().setupUser(user.getUuid());
                    });
                }
                S.send(sender, S.prefix_pax + "Das Rangsystem wurde aktualisiert!");
            } else {
                S.send(sender, S.error_command_misspelled);
            }
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("user")) {
                if (!bungeemain.getInstance().getUserManager().isUserExistingNAME(args[1])) {
                    S.send(sender, S.error_player_not_existing);
                    return;
                }
                Group group = bungeemain.getInstance().getGroupManager().getGroup(bungeemain.getInstance().getUserManager().getUserNAME(args[1]).getGroup());
                S.send(sender, S.prefix_pax + "Der Spieler " + ChatColor.ITALIC + args[1] + S.c_def + " hat den Rang " + group.getColour() + group.getName() + S.c_def + "!");
            } else if (args[0].equalsIgnoreCase("group")) {
                if (!bungeemain.getInstance().getGroupManager().isGroupExisting(args[1])) {
                    S.send(sender, S.error_group_not_existing);
                    return;
                }
                Group group = bungeemain.getInstance().getGroupManager().getGroup(args[1]);
                S.send(sender, S.c_bra + "▌▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▌");
                S.send(sender, "  " + S.prefix_pax + "Informationen zu " + S.c_bra + "» " + group.getColour() + group.getName());
                S.send(sender, S.c_bra + "- " + S.c_def + "Prefix " + S.c_bra + "» " + group.getPrefix());
                S.send(sender, S.c_bra + "- " + S.c_def + "Rang-ID " + S.c_bra + "» " + S.c_def + group.getId());
                if (group.getInheritance().size() != 0) {
                    S.send(sender, S.c_bra + "- " + S.c_def + "Erbt von" + S.c_bra + ":");
                    for (String inh : group.getInheritance()) {
                        if (bungeemain.getInstance().getGroupManager().isGroupExisting(inh)) {
                            Group inher = bungeemain.getInstance().getGroupManager().getGroup(inh);
                            sender.sendMessage(new TextBuilder(S.c_bra + "   └" + inher.getColour() + ChatColor.ITALIC + inher.getName()).addHover(S.c_def + ChatColor.ITALIC + "Klicke hier für mehr Informationen zu " + S.c_bra + ChatColor.ITALIC + "» " + inher.getColour() + ChatColor.ITALIC + inher.getName(), HoverEvent.Action.SHOW_TEXT).addClick("/pax group " + inher.getName(), ClickEvent.Action.RUN_COMMAND).build());
                            continue;
                        } else {
                            S.send(sender, S.c_bra + "   └" + S.c_def + ChatColor.ITALIC + inh + S.c_bra + " (" + S.c_err + "✖" + S.c_bra + ")");
                        }
                    }
                }
                if (group.getPermissions().size() != 0) {
                    S.send(sender, S.c_bra + "- " + S.c_def + "Rechte" + S.c_bra + ":");
                    group.getPermissions().forEach(perm -> S.send(sender, S.c_bra + "   └" + S.c_def + ChatColor.ITALIC + perm));
                }
                if (group.getMembers().size() != 0) {
                    S.send(sender, S.c_bra + "- " + S.c_def + "Mitglieder" + S.c_bra + ":");
                    group.getMembers().forEach(mem -> S.send(sender, S.c_bra + "   └" + S.c_def + ChatColor.ITALIC + bungeemain.getInstance().getUserManager().getUserUUID(mem).getName()));
                }
                S.send(sender, S.c_bra + "▌▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▌");
            } else {
                S.send(sender, S.error_command_misspelled);
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("group")) {
                if (args[2].equalsIgnoreCase("create")) {
                    if (bungeemain.getInstance().getGroupManager().isGroupExisting(args[1])) {
                        S.send(sender, S.error_group_already_existing);
                        return;
                    }
                    int i = 0;
                    while (bungeemain.getInstance().getGroupManager().isGroupExisting(i)) {
                        i++;
                    }
                    Group group = new Group().setName(args[1]).setColour(S.c_def).setId(i).setPrefix(S.c_bra + "[" + S.c_def + args[1] + S.c_bra + "] " + S.c_def).setPermissions(Lists.newArrayList()).setInheritance(Lists.newArrayList()).setMembers(Lists.newArrayList());
                    bungeemain.getInstance().getGroupManager().createGroup(group);
                    M.groups.put(group.getName(), group);
                    S.send(sender, S.prefix_pax + "Die Gruppe " + ChatColor.ITALIC + args[1] + S.c_def + " wurde erstellt!");
                } else if (args[2].equalsIgnoreCase("delete")) {
                    if (!bungeemain.getInstance().getGroupManager().isGroupExisting(args[1])) {
                        S.send(sender, S.error_group_not_existing);
                        return;
                    }
                    bungeemain.getInstance().getGroupManager().deleteGroup(args[1]);
                    M.groups.remove(args[1]);
                    S.send(sender, S.prefix_pax + "Die Gruppe " + ChatColor.ITALIC + args[1] + S.c_def + " wurde entfernt!");
                } else {
                    S.send(sender, S.error_command_misspelled);
                }
            } else {
                S.send(sender, S.error_command_misspelled);
            }
        } else if (args.length == 5) {
            if(args[0].equalsIgnoreCase("group")) {
                if(bungeemain.getInstance().getGroupManager().isGroupExisting(args[1])) {
                    if(args[2].equalsIgnoreCase("set")) {
                        if(args[3].equalsIgnoreCase("id")) {
                            try {
                                if(Integer.parseInt(args[4]) < 0 || Integer.parseInt(args[4]) > 30) {
                                    S.send(sender, S.error_id_not_in_range);
                                    return;
                                }
                                if(bungeemain.getInstance().getGroupManager().getGroup(args[1]).getId() == Integer.parseInt(args[4])) {
                                    S.send(sender, S.error_group_already_has_id);
                                    return;
                                }
                                Group group = bungeemain.getInstance().getGroupManager().getGroup(args[1]);
                                if(bungeemain.getInstance().getGroupManager().isGroupExisting(Integer.parseInt(args[4]))) {
                                    Group group2 = bungeemain.getInstance().getGroupManager().getGroup(Integer.parseInt(args[4]));
                                    bungeemain.getInstance().getGroupManager().updateGroup(group2.getName(), g2 -> {
                                        g2.setId(group.getId());
                                    });
                                }
                                bungeemain.getInstance().getGroupManager().updateGroup(args[1], g -> {
                                    g.setId(Integer.parseInt(args[4]));
                                });
                                S.send(sender, S.prefix_pax + "Die Gruppe " + group.getColour() + group.getName() + S.c_def + " hat nun die id " + S.c_imp + group.getId() + S.c_def + "!");
                            } catch (NumberFormatException e) {
                                S.send(sender, S.error_id_not_a_number);
                            }
                        } else {
                            S.send(sender, S.error_command_misspelled);
                        }
                    } else {
                        S.send(sender, S.error_command_misspelled);
                    }
                } else {
                    S.send(sender, S.error_group_not_existing);
                }
            } else {
                S.send(sender, S.error_command_misspelled);
            }
        } else {
            S.send(sender, S.error_command_misspelled);
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        return Lists.newArrayList(Arrays.asList("user", "group"));
    }
}
