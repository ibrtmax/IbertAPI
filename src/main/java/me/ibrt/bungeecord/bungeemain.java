package me.ibrt.bungeecord;

import me.ibrt.universal.managers.FileManager;
import me.ibrt.bungeecord.listeners.Login;
import me.ibrt.bungeecord.listeners.PlayerDisconnect;
import com.google.gson.Gson;
import lombok.Getter;
import me.ibrt.universal.managers.BanManager;
import me.ibrt.universal.managers.GroupManager;
import me.ibrt.universal.managers.UserManager;
import me.ibrt.universal.mongo.MM_A;
import me.ibrt.universal.mongo.MM_S;
import me.ibrt.bungeecord.commands.BanCommand;
import me.ibrt.bungeecord.commands.PaxCommand;
import me.ibrt.bungeecord.constants.S;
import me.ibrt.bungeecord.listeners.PermissionCheck;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

public class bungeemain extends Plugin {

    @Getter public static bungeemain instance;

    @Getter private MM_A MM_A;
    @Getter private MM_S MM_S;
    @Getter private BanManager BanManager;
    @Getter private UserManager UserManager;
    @Getter private GroupManager GroupManager;
    @Getter private Gson gson;

    @Override
    public void onEnable() {
        instance = this;
        init();
        getProxy().getConsole().sendMessage(S.prefix + ChatColor.GREEN + "Aktiviert");
    }

    @Override
    public void onDisable() {
        getProxy().getConsole().sendMessage(S.prefix + ChatColor.RED + "Deaktiviert");
    }

    private void init() {
        MM_A = new MM_A("localhost", "IbertAPI", 27017);
        MM_S = new MM_S("localhost", "IbertAPI", 27017);
        MM_A.connectLocal();
        MM_S.connectLocal();

        FileManager.loadStrings();

        BanManager = new BanManager();
        UserManager = new UserManager();
        GroupManager = new GroupManager();

        this.gson = new Gson();

        registerCommands();
        registerListener();

        getGroupManager().registerGroups();
        getGroupManager().getGroups().forEach(group -> {
            group.getMembers().forEach(m -> {
                getGroupManager().registerMember(getUserManager().getUserUUID(m));
            });
        });
        getProxy().getPlayers().forEach(p -> {
            getUserManager().setupUser(p.getUniqueId().toString());
        });
    }

    private void registerCommands() {
        getProxy().getPluginManager().registerCommand(this, new BanCommand());
        getProxy().getPluginManager().registerCommand(this, new PaxCommand());
    }

    private void registerListener() {
        getProxy().getPluginManager().registerListener(this, new Login());
        getProxy().getPluginManager().registerListener(this, new PermissionCheck());
        getProxy().getPluginManager().registerListener(this, new PlayerDisconnect());
    }
}
