package me.ibrt.universal.managers;

import me.ibrt.bungeecord.bungeemain;
import me.ibrt.bungeecord.constants.M;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    public static void loadStrings() {
        try {
            if (!bungeemain.getInstance().getDataFolder().exists()) {
                bungeemain.getInstance().getDataFolder().mkdir();
            }
            File file = new File(bungeemain.getInstance().getDataFolder().getPath(), "strings.yml");
            if (!file.exists()) {
                file.createNewFile();
                Configuration strings = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                strings.set(String.valueOf(0), "<!-- Informations -->\n" +
                        "<!-- Use %m for the main colour -->\n" +
                        "<!-- Use %i for the important colour -->\n" +
                        "<!-- Use %d for the default colour -->\n" +
                        "<!-- Use %b for the bracket colour -->\n" +
                        "<!-- Use %e for the error colour -->\n" +
                        "<!-- Thank you! -->");

                strings.set("colour_main", "&6");
                strings.set("colour_important", "&e");
                strings.set("colour_default", "&7");
                strings.set("colour_bracket", "&8");
                strings.set("colour_error", "&c");

                strings.set("prefix", "%b[%mServername%b] %d");
                strings.set("prefix_ban", "%b[%mBansystem%b] %d");
                strings.set("prefix_team", "%b[%m✚%b] %d");
                strings.set("prefix_report", "%b[%mReportsystem%b] %d");
                strings.set("prefix_pax", "%b[%mPAX%b] %d");

                strings.set("permission_ban_command", "ibertapi.ban.command");
                strings.set("permission_ban_notice", "ibertapi.ban.notice");
                strings.set("permission_ban_notbannable", "ibertapi.ban.notbannable");
                strings.set("permission_group_command", "ibertapi.pax.command");

                strings.set("error_noperm", "%eDir fehlen die nötigen Rechte!");

                strings.set("error_player_not_existing", "%eDieser Spieler hat den Server noch nie betreten!");
                strings.set("error_player_never_had_bans", "%eDieser Spieler hatte noch nie einen Ban!");
                strings.set("error_player_already_banned", "%eDieser Spieler hat bereits einen Ban!");

                strings.set("error_group_not_existing", "%eDiese Gruppe existiert nicht!");
                strings.set("error_group_already_existing", "%eDiese Gruppe existiert bereits!");
                strings.set("error_group_already_has_id", "%eDiese Gruppe hat die gewünschte id bereits!");

                strings.set("error_command_not_existing", "%eDieser Befehl existiert nicht!");
                strings.set("error_command_misspelled", "%eDu hast den Befehl falsch eingegeben!");

                strings.set("error_banreason_not_valid", "%eDies ist kein gültiger Bangrund!");

                strings.set("error_id_not_a_number", "%eDu darfst nur Zahlen verwenden!");
                strings.set("error_id_not_in_range", "%eDie Id darf nicht kleiner als 0 und größer als 30 sein!");

                ConfigurationProvider.getProvider(YamlConfiguration.class).save(strings, file);
                strings.getKeys().forEach(k -> {
                    M.strings.put(k, strings.get(k).toString().replace("%m", strings.getString("colour_main")).replace("%i", strings.getString("colour_important")).replace("%d", strings.getString("colour_default")).replace("%e", strings.getString("colour_error")).replace("%b", strings.getString("colour_bracket")).replace('&', ChatColor.COLOR_CHAR));
                });
                return;
            }
            Configuration strings = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            strings.getKeys().forEach(k -> {
                M.strings.put(k, strings.get(k).toString().replace("%m", strings.getString("colour_main")).replace("%i", strings.getString("colour_important")).replace("%d", strings.getString("colour_default")).replace("%e", strings.getString("colour_error")).replace("%b", strings.getString("colour_bracket")).replace('&', ChatColor.COLOR_CHAR));
            });
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
