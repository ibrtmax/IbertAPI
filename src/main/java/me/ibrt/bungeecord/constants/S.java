package me.ibrt.bungeecord.constants;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class S {

    public static String c_main = M.strings.get("colour_main");
    public static String c_imp = M.strings.get("colour_important");
    public static String c_def = M.strings.get("colour_default");
    public static String c_err = M.strings.get("colour_error");
    public static String c_bra = M.strings.get("colour_bracket");

    public static String prefix = M.strings.get("prefix");
    public static String prefix_ban = M.strings.get("prefix_ban");
    public static String prefix_team = M.strings.get("prefix_team");
    public static String prefix_report = M.strings.get("prefix_report");
    public static String prefix_pax = M.strings.get("prefix_pax");

    public static String permission_ban_command = M.strings.get("permission_ban_command");
    public static String permission_ban_notice = M.strings.get("permission_ban_notice");
    public static String permission_ban_notbannable = M.strings.get("permission_ban_notbannable");
    public static String permission_group_command = M.strings.get("permission_group_command");


    public static String error_noperm = prefix + M.strings.get("error_noperm");

    public static String error_player_not_existing = prefix + M.strings.get("error_player_not_existing");
    public static String error_player_never_had_bans = prefix + M.strings.get("error_player_never_had_bans");
    public static String error_player_already_banned = prefix + M.strings.get("error_player_already_banned");

    public static String error_group_not_existing = prefix + M.strings.get("error_group_not_existing");
    public static String error_group_already_existing = prefix + M.strings.get("error_group_already_existing");
    public static String error_group_already_has_id = prefix + M.strings.get("error_group_already_has_id");

    public static String error_command_not_existing = prefix + M.strings.get("error_command_not_existing");
    public static String error_command_misspelled = prefix + M.strings.get("error_command_misspelled");

    public static String error_banreason_not_valid = prefix + M.strings.get("error_banreason_not_valid");

    public static String error_id_not_a_number = prefix + M.strings.get("error_id_not_a_number");
    public static String error_id_not_in_range = prefix + M.strings.get("error_id_not_in_range");

    public static void send(CommandSender sender, String string) {
        sender.sendMessage(TextComponent.fromLegacyText(string));
    }
}
