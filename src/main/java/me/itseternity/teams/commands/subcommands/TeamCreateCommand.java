package me.itseternity.teams.commands.subcommands;

import com.simplexitymc.command.api.ChildCommand;
import com.simplexitymc.command.api.Command;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.api.events.TeamCreateEvent;
import me.itseternity.teams.team.Team;
import me.itseternity.teams.team.TeamManager;
import me.itseternity.teams.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author ItsEternity
 */
public class TeamCreateCommand extends ChildCommand {

    private final TeamsPlugin plugin;

    public TeamCreateCommand(Command parent, TeamsPlugin plugin) {
        super(parent, "create");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String... args) {
        TeamManager manager = plugin.getTeamManager();

        if (manager.inTeam(player.getUniqueId())) {
            Message.IN_TEAM.send(player);
            return;
        }

        Team team = new Team(player.getUniqueId(), player.getName());
        manager.addTeam(player.getUniqueId(), team);
        Message.TEAM_CREATED.send(player);

        Bukkit.getPluginManager().callEvent(new TeamCreateEvent(player, team));

    }
}
