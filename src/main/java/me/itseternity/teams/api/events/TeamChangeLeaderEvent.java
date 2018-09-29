package me.itseternity.teams.api.events;

import me.itseternity.teams.api.TeamEvent;
import me.itseternity.teams.team.Team;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

/**
 * @author ItsEternity
 */
public class TeamChangeLeaderEvent extends TeamEvent implements Cancellable {

    /**
     * The team that changed leaders
     */
    private final Team team;
    /**
     * The player that used to be the leader
     */
    private final Player oldLeader;
    /**
     * The player that is the new leader
     */
    private final Player newLeader;
    private boolean cancelled;

    public TeamChangeLeaderEvent(Player oldLeader, Player newLeader, Team team) {
        this.oldLeader = oldLeader;
        this.newLeader = newLeader;
        this.team = team;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public Team getTeam() {
        return team;
    }

    public Player getOldLeader() {
        return oldLeader;
    }

    public Player getNewLeader() {
        return newLeader;
    }
}
