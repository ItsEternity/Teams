package me.itseternity.teams.commands.subcommands;

import com.simplexitymc.command.api.ChildCommand;
import com.simplexitymc.command.api.Command;
import me.itseternity.teams.TeamsPlugin;
import me.itseternity.teams.api.events.TeamCreateEvent;
import me.itseternity.teams.team.Team;
import me.itseternity.teams.team.TeamManager;
import me.itseternity.teams.utils.Message;
import me.itseternity.teams.utils.MessageUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author ItsEternity
 */
public class TeamInviteCommand extends ChildCommand {

    private final TeamsPlugin plugin;

    public TeamInviteCommand(Command parent, TeamsPlugin plugin) {
        super(parent, "invite");
        this.plugin = plugin;
    }

    @Override
    public void execute(Player player, String... args) {
        if (args.length == 0) {
            Message.SPECIFY_TARGET.send(player);
            return;
        }

        TeamManager manager = plugin.getTeamManager();
        Player target = Bukkit.getPlayer(args[0]);


        if (target == null) {
            Message.NOT_ONLINE.send(player);
            return;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            Message.CANNOT_INVITE_SELF.send(player);
            return;
        }

        if (manager.inTeam(target.getUniqueId())) {
            Message.TARGET_IN_TEAM.send(player);
            return;
        }

        Team team;

        if (!manager.inTeam(player.getUniqueId())) {
            team = new Team(player.getUniqueId(), player.getName());
            manager.addTeam(player.getUniqueId(), team);
            Message.TEAM_CREATED.send(player);

            Bukkit.getPluginManager().callEvent(new TeamCreateEvent(player, team));
        } else {
            team = manager.getTeam(player.getUniqueId());
        }

        if (team.hasInvite(target.getUniqueId())) {
            Message.TARGET_HAS_INVITE.send(player);
            return;
        }

        team.addInvite(target.getUniqueId(), player.getUniqueId());

        TextComponent inviteMessage = new TextComponent(ChatColor.AQUA + player.getName() + ChatColor.YELLOW + " sent you a team invite!");
        TextComponent accept = MessageUtil.createCommandMessage(
                ChatColor.GREEN + ChatColor.BOLD.toString() + "ACCEPT", "team accept " + player.getName(),
                ChatColor.GREEN + "Accept the invite");

        TextComponent deny = MessageUtil.createCommandMessage(
                ChatColor.RED + ChatColor.BOLD.toString() + "DENY", "team deny " + player.getName(),
                ChatColor.RED + "Deny the invite");

        inviteMessage.addExtra(ChatColor.RESET + "  ");
        inviteMessage.addExtra(accept);
        inviteMessage.addExtra(ChatColor.RESET + "  ");
        inviteMessage.addExtra(deny);

        target.spigot().sendMessage(inviteMessage);
        Message.INVITE_SENT.send(player, target.getName());
    }
}
