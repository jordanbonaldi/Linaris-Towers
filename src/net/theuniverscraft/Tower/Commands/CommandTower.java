package net.theuniverscraft.Tower.Commands;

import org.bukkit.command.*;
import net.theuniverscraft.Tower.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;
import net.theuniverscraft.Tower.Enum.*;
import net.theuniverscraft.Tower.Managers.*;
import net.theuniverscraft.Tower.Timers.GameTimer;

import java.util.*;

public class CommandTower implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "Vous devez être op");
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("config")) {
                if (Tower.getInstance().getGameState() == GameState.BEFORE_GAME) {
                    sender.sendMessage(ChatColor.GREEN + "Jeu en config !");
                    for(Player aplayer : Bukkit.getOnlinePlayers()){

                        if (!aplayer.isOp()) {
                            aplayer.kickPlayer(ChatColor.RED + "Jeu en config !");
                        }
                    }
                    Tower.getInstance().setGameState(GameState.INIT_GAME);
                }
                else {
                    sender.sendMessage(ChatColor.RED + "Vous ne pouvez pas faire cela maintenant !");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("clear")) {
                for (final Entity entity : Bukkit.getWorlds().get(0).getEntities()) {
                    if (entity instanceof Item) {
                        entity.remove();
                    }
                    else {
                        if (!(entity instanceof Player)) {
                            continue;
                        }
                        final Player aplayer2 = (Player)entity;
                        aplayer2.setMaxHealth(20.0);
                        aplayer2.setHealth(20.0);
                        aplayer2.setFoodLevel(20);
                        final Collection<PotionEffect> effects = (Collection<PotionEffect>)aplayer2.getActivePotionEffects();
                        for (final PotionEffect effect : effects) {
                            aplayer2.removePotionEffect(effect.getType());
                        }
                    }
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("start")) {
            	Tower.getInstance().getTimer().setTime(10);
                return true;
            }
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Vous devez être un joueur");
            return true;
        }
        final Player player = (Player)sender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("setspawn") || args[0].equalsIgnoreCase("setlobby")) {
                Tower.getInstance().setLobby(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Ok !");
                return true;
            }
        }
        else if (args.length == 2) {
            if (!args[0].equalsIgnoreCase("configteam")) {
                if (!args[0].equalsIgnoreCase("createteam")) {
                    return false;
                }
            }
            try {
                final TeamColor color = TeamColor.valueOf(args[1]);
                CreateTeamManager.getInstance().createTeam(player, color);
            }
            catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.GRAY + "Couleurs disponnibles :");
                TeamColor[] values;
                for (int length2 = (values = TeamColor.values()).length, j = 0; j < length2; ++j) {
                    final TeamColor color2 = values[j];
                    player.sendMessage(ChatColor.GRAY + " - " + color2.getChatColor() + color2.toString());
                }
            }
            return true;
        }
        return false;
    }
}
