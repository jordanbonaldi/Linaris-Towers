package net.theuniverscraft.Tower.Listeners;

import net.theuniverscraft.Tower.Tower;
import net.theuniverscraft.Tower.Enum.GameState;
import net.theuniverscraft.Tower.Managers.PlayersManager;
import net.theuniverscraft.Tower.Managers.TeamManager;
import net.theuniverscraft.Tower.Utils.Translation;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class GeneralListener implements Listener
{
    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        

			event.setFormat("§7" + player.getName()+" §f: "+ event.getMessage());	
		
		
        final Player player1 = event.getPlayer();
        final PlayersManager.TucPlayer tplayer = PlayersManager.getInstance().getPlayer(player1);
        if(tplayer.haveTeam()){


			event.setFormat("[" + tplayer.getTeam().getNameColored() + ChatColor.RESET + "] " + player1.getName()+" §f: "+ event.getMessage());	
		}
		


		
        
    }
    
    @EventHandler
    public void interact(EntityDamageEvent e){
    	if(Tower.getInstance().getGameState() == GameState.END_GAME){
    			e.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void PlayerMove(PlayerMoveEvent e){
    	if(Tower.getInstance().getGameState() == GameState.END_GAME){
    		if(e.getPlayer().getLocation().getBlockY() <= 10){
    			e.getPlayer().getLocation().setY(200);
    		}
    	}
    }
    
    @EventHandler
    public void onServerPing(final ServerListPingEvent event) {
        String motd = null;
        final GameState state = Tower.getInstance().getGameState();
        if (state == GameState.BEFORE_GAME) {
            motd = Translation.MOTD_BEGIN_IN.replaceAll("<sec>", Long.toString(Tower.getInstance().getTimer().getTime()));
        }
        else if (state == GameState.GAME) {
            motd = Translation.MOTD_GAME;
            for (final TeamManager.TucTeam team : TeamManager.getInstance().getTeams()) {
                motd = motd.replaceAll("<" + team.getColor().toString() + ">", team.getColor().getChatColor() + Integer.toString(team.getScore()));
            }
        }
        else if (state == GameState.END_GAME || state == GameState.RESET) {
            motd = Translation.MOTD_END_GAME;
        }
        if (motd != null) {
            event.setMotd(motd);
        }
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
    	if(Tower.getInstance().getGameState().equals(GameState.BEFORE_GAME)){
        event.setJoinMessage(Translation.JOIN_MESSAGE.replaceAll("<player>", event.getPlayer().getName()));
    	}else {
            event.setJoinMessage(null);
    	}
    }
    
    @EventHandler
    public void onPlayerKick(final PlayerKickEvent event) {
    	if(Tower.getInstance().getGameState().equals(GameState.BEFORE_GAME)){
        event.setLeaveMessage(Translation.QUIT_MESSAGE.replaceAll("<player>", event.getPlayer().getName()));
    	}else {
            event.setLeaveMessage(null);
    	}
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(final PlayerQuitEvent event) {
    	if(Tower.getInstance().getGameState().equals(GameState.BEFORE_GAME)){
        event.setQuitMessage(Translation.QUIT_MESSAGE.replaceAll("<player>", event.getPlayer().getName()));
    	}else {
            event.setQuitMessage(null);
    	}
        PlayersManager.getInstance().deletePlayer(event.getPlayer());
    }
}
