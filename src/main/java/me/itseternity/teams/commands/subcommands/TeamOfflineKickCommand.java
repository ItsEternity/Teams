package me.itseternity.teams.commands.subcommands;

import com.evilmidget38.UUIDFetcher;
import com.simplexitymc.command.api.ChildCommand;
import com.simplexitymc.command.api.Command;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.team.Team;
import me.itseternity.teams.team.TeamManager;
import me.itseternity.teams.utils.Callback;
import me.itseternity.teams.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TeamOfflineKickCommand extends ChildCommand {

    private final TeamsPlugin plugin;

    public TeamOfflineKickCommand(Command parent, TeamsPlugin plugin) {
        super(parent, "offlinekick");
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

        if (team == null) {
            Message.NOT_IN_TEAM.send(player);
            return;
        }

        if (!player.getUniqueId().equals(team.getLeader())) {
            Message.NOT_LEADER.send(player);
            return;
        }

        if (args[0].length() > 16) {
            Message.NOT_VALID_NAME.send(player);
            return;
        }

        getUUID(args[0], uuid -> {
            if (uuid == null) {
                Message.FAILED_FIND_UUID.send(player, args[0]);
                return;
            }

            if (!team.getMembers().contains(uuid)) {
                Message.CANNOT_KICK.send(player);
                return;
            }

            manager.removeTeam(uuid);
            team.removeMember(uuid);
            for (UUID teamMember : team.getMembers()) {
                Player member = Bukkit.getPlayer(teamMember);
                if (member != null) {
                    Message.KICKED_FROM_TEAM_BROADCAST.send(member, args[0]);
                }
            }
        });
    }

    private void getUUID(String name, final Callback callback) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            UUID target = UUIDFetcher.getUUIDOf(name);

            Bukkit.getScheduler().runTask(plugin, () -> callback.onComplete(target));

        });

    }
}
