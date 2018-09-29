package me.itseternity.teams.team;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author ItsEternity
 */
public class TeamManager {
    private final Map<UUID, Team> teams = new HashMap<>();

    public Team getTeam(UUID player) {
        return teams.get(player);
    }

    public void addTeam(UUID player, Team team) {
        teams.put(player, team);
    }

    public Team removeTeam(UUID player) {
        return teams.remove(player);
    }

    public boolean inTeam(UUID player) {
        return getTeam(player) != null;
    }

    public Collection<Team> getTeams() {
        return teams.values();
    }
}
