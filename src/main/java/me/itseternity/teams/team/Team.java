package me.itseternity.teams.team;

import me.itseternity.teams.utils.Message;
import net.jodah.expiringmap.ExpirationListener;
import net.jodah.expiringmap.ExpiringMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ItsEternity
 */
public class Team {

    /**
     * List of members within the team
     **/
    private final Set<UUID> members = new HashSet<>();
    private final Map<UUID, UUID> invites = ExpiringMap.builder()
            .expiration(120, TimeUnit.SECONDS)
            .expirationListener((ExpirationListener<UUID, UUID>) (key, value) -> {
                Player player = Bukkit.getPlayer(key);
                Player target = Bukkit.getPlayer(value);

                if (player != null && target != null) {
                    Message.INVITE_EXPIRED.send(target, player.getName());
                    Message.YOUR_INVITE_EXPIRED.send(player, target.getName());
                }
            })
            .build();
    /**
     * UUID of the team's leader
     **/
    private UUID leader;
    /**
     * Name of the team's leader
     **/
    private String name;


    public Team(UUID leader, String name) {
        this.leader = leader;
        this.name = name;

        members.add(leader);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getLeader() {
        return this.leader;
    }

    public void setLeader(UUID leader) {
        this.leader = leader;
    }

    public Set<UUID> getMembers() {
        return Collections.unmodifiableSet(members);
    }

    public void addMember(UUID member) {
        this.members.add(member);
    }

    public void removeMember(UUID member) {
        this.members.remove(member);
    }

    public boolean hasInvite(UUID player) {
        return invites.get(player) != null;
    }

    public void addInvite(UUID player, UUID inviter) {
        invites.put(player, inviter);
    }

    public void removeInvite(UUID player) {
        invites.remove(player);
    }

}
