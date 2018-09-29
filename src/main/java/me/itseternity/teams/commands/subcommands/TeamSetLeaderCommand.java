package me.itseternity.teams.commands.subcommands;

import com.simplexitymc.command.api.ChildCommand;
import com.simplexitymc.command.api.Command;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.api.events.TeamChangeLeaderEvent;
import me.itseternity.teams.team.Team;
import me.itseternity.teams.team.TeamManager;
import me.itseternity.teams.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author ItsEternity
 */
public class TeamSetLeaderCommand extends ChildCommand {

    private final TeamsPlugin plugin;

    public TeamSetLeaderCommand(Command parent, TeamsPlugin plugin) {
        super(parent, "setleader");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String... args) {

        if (args.length == 0) {
            Message.SPECIFY_TARGET.send(player);
            return;
        }

        TeamManager manager = plugin.getTeamManager();
        Team team = manager.getTeam(player.getUniqueId());

        if (!team.getLeader().equals(player.getUniqueId())) {
            Message.NOT_LEADER.send(player);
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            Message.NOT_ONLINE.send(player);
            return;
        }

        if (!team.getMembers().contains(target.getUniqueId())) {
            Message.CANNOT_SET_LEADER.send(player);
            return;
        }

        team.setLeader(target.getUniqueId());
        team.setName(target.getName());

        for (UUID uuid : team.getMembers()) {
            Player member = Bukkit.getPlayer(uuid);
            if (member != null) {
                Message.SET_LEADER.send(member, target.getName());
            }
        }

        Bukkit.getPluginManager().callEvent(new TeamChangeLeaderEvent(player, target, team));
    }
}
