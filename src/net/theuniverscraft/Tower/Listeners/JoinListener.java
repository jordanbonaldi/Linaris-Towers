package net.theuniverscraft.Tower.Listeners;

import net.theuniverscraft.Tower.Tower;
import net.theuniverscraft.Tower.Enum.GameState;
import net.theuniverscraft.Tower.Enum.TeamColor;
import net.theuniverscraft.Tower.Managers.PlayersManager;
import net.theuniverscraft.Tower.Managers.TucScoreboardManager;
import net.theuniverscraft.Tower.Utils.Translation;
import net.theuniverscraft.Tower.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.icrotz.gameapi.utils.ScoreboardSign;

public class JoinListener implements Listener
{
	public static boolean ok = false;
    @EventHandler
    public void onPlayerLogin(final PlayerLoginEvent event) {
        final GameState state = Tower.getInstance().getGameState();
        if (state != GameState.BEFORE_GAME) {
               event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Translation.GAME_ALREADY_START);

        }
        else if(Bukkit.getOnlinePlayers().size() >= 16){
        	if(ok == false){
        	Tower.getInstance().getTimer().setTime(120);
        		ok = true;
        	}
        }
        else if(Bukkit.getOnlinePlayers().size() == Bukkit.getMaxPlayers() -1){
        	Tower.getInstance().getTimer().setTime(10);
        }
        else if (Bukkit.getOnlinePlayers().size() >= 7 * TeamColor.values().length) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Translation.GAME_FULL);
        }
        if (state == GameState.INIT_GAME && event.getPlayer().isOp()) {
            event.allow();
        }
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        PlayersManager.getInstance().getPlayer(player);
        player.teleport(Tower.getInstance().getLobby());
        player.setScoreboard(TucScoreboardManager.getScoreboard());
        Utils.setInventory(player);
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
		ScoreboardSign bar = ScoreboardSign.get(event.getPlayer());
		if (bar != null) {
			bar.destroy();
		}
    	PlayersManager.getInstance().deletePlayer(event.getPlayer());
    }
    
    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        if (Tower.getInstance().getGameState() != GameState.GAME) {
            event.setRespawnLocation(Tower.getInstance().getLobby());
        }
    }
}
