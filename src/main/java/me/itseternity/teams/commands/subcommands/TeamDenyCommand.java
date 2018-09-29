package me.itseternity.teams.commands.subcommands;

import com.simplexitymc.command.api.ChildCommand;
import com.simplexitymc.command.api.Command;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.team.Team;
import me.itseternity.teams.team.TeamManager;
import me.itseternity.teams.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author ItsEternity
 */
public class TeamDenyCommand extends ChildCommand {

    private final TeamsPlugin plugin;

    public TeamDenyCommand(Command parent, TeamsPlugin plugin) {
        super(parent, "deny");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String... args) {

        System.out.println(Arrays.toString(args));
        if (args.length == 0) {
            Message.SPECIFY_TARGET.send(player);
            player.sendMessage(ChatColor.RED + "Please make sure you enter the name of a member in the team");
            return;
        }
        TeamManager manager = plugin.getTeamManager();

        if (manager.inTeam(player.getUniqueId())) {
            Message.IN_TEAM.send(player);
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            Message.NOT_ONLINE.send(player);
            return;
        }

        Team team = manager.getTeam(target.getUniqueId());

        if (team == null) {
            Message.TARGET_NOT_IN_TEAM.send(player);
            return;
        }

        if (team.hasInvite(player.getUniqueId())) {
            team.removeInvite(player.getUniqueId());
            Message.DENIED_INVITE.send(player, team.getName());

            for (UUID uuid : team.getMembers()) {
                Player member = Bukkit.getPlayer(uuid);
                if (member != null) {
                    Message.DENIED_INVITE_BROADCAST.send(member, player.getName());
                }
            }
        } else {
            Message.FAILED_TO_FIND.send(player, target.getName());
        }
    }
}
