package me.itseternity.teams;

import com.simplexitymc.command.api.CommandHandler;
import me.itseternity.teams.commands.TeamCommand;
import me.itseternity.teams.team.TeamManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class TeamsPlugin extends JavaPlugin {

    //TODO Add JSON messaging
    //TODO Persistent storage

    private TeamManager teamManager;
    private CommandHandler commandHandler;

    @Override
    public void onEnable() {
        commandHandler = new CommandHandler(this);
        commandHandler.addCommand(new TeamCommand(commandHandler, this));

        teamManager = new TeamManager();
    }

    @Override
    public void onDisable() {
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }
}
