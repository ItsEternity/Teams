package me.itseternity.teams.api;

import com.google.common.base.Preconditions;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.team.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public class TeamsAPI {

    private final TeamsPlugin plugin;

    public TeamsAPI(TeamsPlugin plugin) {
        Preconditions.checkNotNull(plugin, "TeamsPlugin reference is null, is it loaded on the server?");
        this.plugin = plugin;
    }


    /**
     * Gets the {@link Team} the player is currently in.
     *
     * @param player The {@link UUID} of the player
     * @return a {@link Team} if in a team, otherwise an empty {@link Optional}
     */

    public Optional<Team> getTeam(UUID player) {
        Team team = plugin.getTeamManager().getTeam(player);

        if (team != null) {
            return Optional.of(team);
        }

        return Optional.empty();
    }

    /**
     * Adds a team into the cache
     *
     * @param leader a player uuid
     * @param team   the team to add
     */

    public void addTeam(UUID leader, Team team) {
        Preconditions.checkNotNull(team, "The team object is null");
        plugin.getTeamManager().addTeam(leader, team);
    }

    /**
     * Removes a team from the cache
     *
     * @param leader a player uuid
     * @return the team that was removed
     */

    public Team removeTeam(UUID leader) {
        return plugin.getTeamManager().removeTeam(leader);
    }


    /**
     * Gets all the teams
     *
     * @return Returns all the teams
     */

    public Collection<Team> getTeams() {
        return new ArrayList<>(plugin.getTeamManager().getTeams());
    }


}
