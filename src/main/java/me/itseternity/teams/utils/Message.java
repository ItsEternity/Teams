package me.itseternity.teams.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author ItsEternity
 */
public enum Message {

    //TODO Provide a way to edit these messages

    TEAM_HELP("&e&lTeam Help\n" +
            "&e/team create &b- Creates your own team\n" +
            "&e/team invite <player> &b- Invites a player to your team\n" +
            "&e/team accept <player> &b- Accepts an invite from a player\n" +
            "&e/team deny <player> &b- Denies an invite from a player\n" +
            "&e/team list &b- Lists all the players in your team\n" +
            "&e/team setleader <player> &b- Sets a new leader for the team (leader only command)\n" +
            "&e/team disband &b- Disbands the team (leader only command)\n"),

    TEAM_CREATED("&eYou have created your team! &eUse &b/team invite <player> &eto invite other players!"),
    TEAM_DISBAND_CHECK("&eAre you sure you want to &ldisband &eyour team? &b/team disband confirm &eto disband your team"),
    TEAM_DISBAND("&eThe team has been disbanded =("),
    OFFLINE_KICK("&eUse &b/team offlinekick <name> &eto kick a player who is offline!"),

    JOINED_TEAM("&eYou have joined &b%s's &eteam!"),
    JOINED_TEAM_BROADCAST("&b%s &ehas joined the team!"),

    DENIED_INVITE("&eYou have denied &b%s's &einvite!"),
    DENIED_INVITE_BROADCAST("&b%s &ehas denied the invite to the team =("),

    KICKED_FROM_TEAM("&eYou have been kicked from the team!"),
    KICKED_FROM_TEAM_BROADCAST("&b%s &ehas been kicked from the team!"),
    CANNOT_KICK("&eYou cannot kick this player"),

    IN_TEAM("&cYou are already in a team! Leave the team, /team leave"),
    TARGET_IN_TEAM("&cThis player is already in a team!"),
    NOT_IN_TEAM("&cYou are not in a team!"),
    TARGET_NOT_IN_TEAM("&cThat player is not in a team"),

    SET_LEADER("&b%s &ehas been set as the new leader"),
    NOT_LEADER("&cYou are not the leader of the team!"),
    CANNOT_SET_LEADER("&cThis player is not in your team so cannot be set as the leader"),

    INVITE_SENT("&eYou have sent an invite to &b%s"),
    INVITE_EXPIRED("&eYour invite to &b%s &ehas expired"),
    YOUR_INVITE_EXPIRED("&e%s's invite to their party has expired"),
    TARGET_HAS_INVITE("&cThis player already has a pendi1ng invite"),
    CANNOT_INVITE_SELF("&cYou cannot invite yourself!"),

    DISBAND_EXPIRED("&eYou took too long to disband the team, use /team disband again!"),

    FAILED_TO_FIND("&cCould not find %s's team invite"),
    FAILED_FIND_UUID("&cCould not find %s's UUID"),
    SPECIFY_TARGET("&cYou must specify a player!"),
    NOT_ONLINE("&cThis player is not online!"),
    NOT_VALID_NAME("&cThis is a not a valid name!");

    private final String message;


    Message(String message) {
        this.message = colourize(message);
    }

    private String colourize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public void send(Player player) {
        player.sendMessage(message);
    }

    public void send(Player player, Object... replacements) {
        player.sendMessage(String.format(message, replacements));
    }

    public void broadcast() {
        Bukkit.broadcastMessage(message);
    }

    public void broadcast(Object... replacements) {
        Bukkit.broadcastMessage(String.format(message, replacements));
    }
}
