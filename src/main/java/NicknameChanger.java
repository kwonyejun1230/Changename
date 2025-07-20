package me.yj;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

public class NicknameChanger extends JavaPlugin implements Listener {

    private final HashMap<UUID, String> nicknames = new HashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("NicknameChanger 플러그인이 활성화되었습니다.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("setnick")) {
            if (args.length == 0) {
                player.sendMessage("사용법: /setnick <닉네임>");
                return true;
            }

            String nick = String.join(" ", args);
            nicknames.put(player.getUniqueId(), nick);
            player.sendMessage("§a닉네임이 §f" + nick + "§a(으)로 변경되었습니다.");
            return true;

        } else if (cmd.getName().equalsIgnoreCase("resetnick")) {
            nicknames.remove(player.getUniqueId());
            player.sendMessage("§c닉네임이 초기화되었습니다.");
            return true;
        }

        return false;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String nickname = nicknames.getOrDefault(player.getUniqueId(), player.getName());
        event.setFormat("§7[" + nickname + "] §f" + event.getMessage());
    }
}
