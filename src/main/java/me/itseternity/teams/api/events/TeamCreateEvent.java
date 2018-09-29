package me.itseternity.teams.api.events;

import me.itseternity.teams.api.TeamEvent;
import me.itseternity.teams.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * @author ItsEternity
 */
public class TeamCreateEvent extends TeamEvent implements Cancellable {

    /**
     * The team that was created
     */
    private final Team team;
    /**
     * The player that created the team
     */
    private final Player player;
    private boolean cancelled;

    public TeamCreateEvent(Player player, Team team) {
        this.player = player;
        this.team = team;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
