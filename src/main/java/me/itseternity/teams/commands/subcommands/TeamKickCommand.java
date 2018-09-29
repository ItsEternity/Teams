package me.itseternity.teams.commands.subcommands;

import com.simplexitymc.command.api.ChildCommand;
import com.simplexitymc.command.api.Command;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.team.Team;
import me.itseternity.teams.team.TeamManager;
import me.itseternity.teams.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author ItsEternity
 */
public class TeamKickCommand extends ChildCommand {

    private final TeamsPlugin plugin;

    public TeamKickCommand(Command parent, TeamsPlugin plugin) {
        super(parent, "kick");
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

        if (args.length < 1) {
            Message.SPECIFY_TARGET.send(player);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            Message.NOT_ONLINE.send(player);
            Message.OFFLINE_KICK.send(player);
            return;
        }

        if (manager.getTeam(target.getUniqueId()) != team) {
            Message.CANNOT_KICK.send(player);
            return;
        }

        manager.removeTeam(player.getUniqueId());
        team.removeMember(target.getUniqueId());
        Message.KICKED_FROM_TEAM.send(target);

        for (UUID uuid : team.getMembers()) {
            Player member = Bukkit.getPlayer(uuid);
            if (member != null) {
                Message.KICKED_FROM_TEAM_BROADCAST.send(member, target.getName());
            }
        }
        //TODO Send  message to the rest of the team player has left [Using team chat]
    }
}
