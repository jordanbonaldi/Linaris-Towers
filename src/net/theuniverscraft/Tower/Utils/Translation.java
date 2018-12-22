package net.theuniverscraft.Tower.Utils;

import net.theuniverscraft.Tower.Enum.TeamColor;
import net.theuniverscraft.Tower.Managers.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public abstract class Translation
{
    public static final String GAME_ALREADY_START;
    public static final String YOU_ARE_IN_TEAM;
    public static final String TEAM_ARE_FULL;
    public static final String GAME_FULL;
    public static final String THE_GAME_BEGIN_IN;
    public static final String THE_GAME_BEGIN_IN_ONE;
    public static final String THE_GAME_BEGIN;
    public static final String THE_GAME_IS_END;
    public static final String MOTD_BEGIN_IN;
    public static final String MOTD_GAME;
    public static final String MOTD_END_GAME;
    public static final String TEAM_IS_TOO_FULL;
    public static final String JOIN_MESSAGE;
    public static final String QUIT_MESSAGE;
    
    static {
        GAME_ALREADY_START = ChatColor.DARK_RED + "La partie est déj\u00e0 commencée !";
        YOU_ARE_IN_TEAM = "§6Vous avez rejoint la team §6<team> §7(§a<x>§7/§a<max>§7)";
        TEAM_ARE_FULL = "§6La team §b<team> est pleine §7(§a<x>§7/§a<max>§7)";
        GAME_FULL = ChatColor.RED + "La partie est pleine !";
        THE_GAME_BEGIN_IN = ChatColor.GOLD + "La partie commence dans " + ChatColor.AQUA + "<sec> secondes";
        THE_GAME_BEGIN_IN_ONE = ChatColor.GOLD + "La partie commence dans " + ChatColor.AQUA + "1 seconde";
        THE_GAME_BEGIN = "§aC'est parti :D ! Que le Meilleur gagne !";
        THE_GAME_IS_END = ChatColor.AQUA + "La partie est fini !";
        MOTD_BEGIN_IN = ChatColor.AQUA + "<sec> secondes";
        MOTD_GAME = "<RED>" + ChatColor.WHITE + " - <BLUE>";
        MOTD_END_GAME = ChatColor.RED + "Partie terminée";
        TEAM_IS_TOO_FULL = ChatColor.RED + "Cette team contient trop de joueurs par rapport \u00e0 l'autre team";
        JOIN_MESSAGE = ChatColor.YELLOW + "§6<player> §7a rejoint le jeu";
        QUIT_MESSAGE = ChatColor.YELLOW + "§6<player> §7a quitté le jeu";
    }
    
    public static void broadcastWinner(final TeamManager.TucTeam winner) {
        if (winner != null) {
            final TeamColor color = winner.getColor();
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§f§m-------------------------------");
            Bukkit.broadcastMessage(ChatColor.GOLD + "   L'equipe des " + color.getChatColor() + color.toLocal()+"s" + ChatColor.GOLD + " a gagné !");
            Bukkit.broadcastMessage("§f§m-------------------------------");
            Bukkit.broadcastMessage("");
        }
        else {
            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§f§m-------------------------------");
            Bukkit.broadcastMessage("   §7La partie est terminé ! &eRetour au hub :)");
            Bukkit.broadcastMessage("§f§m-------------------------------");
            Bukkit.broadcastMessage("");
        }
    }
}
