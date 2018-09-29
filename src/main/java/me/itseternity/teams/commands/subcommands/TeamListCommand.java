package me.itseternity.teams.commands.subcommands;

import com.simplexitymc.command.api.ChildCommand;
import com.simplexitymc.command.api.Command;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.team.Team;
import me.itseternity.teams.team.TeamManager;
import me.itseternity.teams.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author ItsEternity
 */
public class TeamListCommand extends ChildCommand {

    private final TeamsPlugin plugin;

    public TeamListCommand(Command parent, TeamsPlugin plugin) {
        super(parent, "list");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String... args) {
        TeamManager manager = plugin.getTeamManager();
        Team team = manager.getTeam(player.getUniqueId());

        if (team == null) {
            Message.NOT_IN_TEAM.send(player);
            return;
        }

        List<UUID> members = new CopyOnWriteArrayList<>(team.getMembers());

        System.out.println(members);

        player.sendMessage(ChatColor.YELLOW + "Your team:");
        player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "  [ONLINE]");
        for (UUID member : members) {
            Player target = Bukkit.getPlayer(member);
            if (target != null) {
                System.out.println(target.getName() + " removed ");
                members.remove(member);
                player.sendMessage(ChatColor.YELLOW + "- " + ChatColor.GREEN + target.getName());
            }
        }

        if (!members.isEmpty()) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "  [OFFLINE]");
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                for (UUID offlineMember : members) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(offlineMember);

                    if (offlineMember != null) { //Just in case
                        player.sendMessage(ChatColor.YELLOW + "- " + ChatColor.RED + offlinePlayer.getName());
                    }
                }
            });
        }
    }
}
