package me.itseternity.teams.commands.subcommands;

import com.simplexitymc.command.api.ChildCommand;
import com.simplexitymc.command.api.Command;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.team.Team;
import me.itseternity.teams.team.TeamManager;
import me.itseternity.teams.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author ItsEternity
 */
public class TeamDisbandCommand extends ChildCommand {

    private final TeamsPlugin plugin;
    private final HashMap<UUID, Long> confirm = new HashMap<>();

    public TeamDisbandCommand(Command parent, TeamsPlugin plugin) {
        super(parent, "disband");
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

        if (!team.getLeader().equals(player.getUniqueId())) {
            Message.NOT_LEADER.send(player);
            return;
        }

        if (args.length == 0 && !confirm.containsKey(player.getUniqueId())) {
            Message.TEAM_DISBAND_CHECK.send(player);
            confirm.put(player.getUniqueId(), System.currentTimeMillis());
        } else {
            if (args[0].equalsIgnoreCase("confirm")) {
                if (System.currentTimeMillis() - confirm.get(player.getUniqueId()) / 1000 >= 30) {

                    manager.removeTeam(player.getUniqueId());
                    for (UUID uuid : team.getMembers()) {
                        Player member = Bukkit.getPlayer(uuid);
                        if (member != null) {
                            Message.TEAM_DISBAND.send(member, player.getName());
                        }
                    }

                } else {
                    Message.DISBAND_EXPIRED.send(player);
                }

                confirm.remove(player.getUniqueId());
            }
        }
    }
}
