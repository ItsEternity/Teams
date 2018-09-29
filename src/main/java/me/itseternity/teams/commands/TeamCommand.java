package me.itseternity.teams.commands;

import com.simplexitymc.command.api.Command;
import com.simplexitymc.command.api.CommandHandler;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.commands.subcommands.TeamAcceptCommand;
import me.itseternity.teams.commands.subcommands.TeamCreateCommand;
import me.itseternity.teams.commands.subcommands.TeamDenyCommand;
import me.itseternity.teams.commands.subcommands.TeamDisbandCommand;
import me.itseternity.teams.commands.subcommands.TeamInviteCommand;
import me.itseternity.teams.commands.subcommands.TeamKickCommand;
import me.itseternity.teams.commands.subcommands.TeamListCommand;
import me.itseternity.teams.commands.subcommands.TeamOfflineKickCommand;
import me.itseternity.teams.commands.subcommands.TeamSetLeaderCommand;
import me.itseternity.teams.utils.Message;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

/**
 * @author ItsEternity
 */
public class TeamCommand extends Command {

    public TeamCommand(CommandHandler handler, TeamsPlugin plugin) {
        super(handler, null, "team", "t", "party");

        Stream.of(
                new TeamCreateCommand(this, plugin),
                new TeamInviteCommand(this, plugin),
                new TeamAcceptCommand(this, plugin),
                new TeamDenyCommand(this, plugin),
                new TeamKickCommand(this, plugin),
                new TeamOfflineKickCommand(this, plugin),
                new TeamDisbandCommand(this, plugin),
                new TeamSetLeaderCommand(this, plugin),
                new TeamListCommand(this, plugin)
        ).forEach(this::addChild);

    }

    @Override
    public void execute(Player player, String... args) {
        if (args.length >= 1) {
            Message.TEAM_HELP.send(player);
            return;
        }

        attemptChildCommand(player, args);
    }
}
