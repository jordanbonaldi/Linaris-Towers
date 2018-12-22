package net.theuniverscraft.Tower.Listeners;

import net.theuniverscraft.Tower.Tower;
import net.theuniverscraft.Tower.Enum.GameState;
import net.theuniverscraft.Tower.Managers.PlayersManager;
import net.theuniverscraft.Tower.Managers.TeamManager;
import net.theuniverscraft.Tower.Utils.Utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.icrotz.gameserver.BukkitAPI;
import fr.icrotz.gameserver.api.PlayerData;
import fr.icrotz.gameserver.api.PlayerLocal;
import fr.icrotz.gameserver.api.PlayerLocalManager;
import fr.icrotz.gameserver.events.ReturnToLobbyEvent;

public class GameListener implements Listener
{
	public static HashMap<Player, Integer> hkill = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> hdeath = new HashMap<Player, Integer>();

	
	HashMap<Player, Boolean> hattack = new HashMap<Player, Boolean>();
	
    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        if (Tower.getInstance().getGameState() != GameState.GAME) {
            return;
        }
        final Player player = event.getPlayer();
        final PlayersManager.TucPlayer tplayer = PlayersManager.getInstance().getPlayer(player);
        event.setRespawnLocation(tplayer.getTeam().getSpawnPlayer());
        Utils.setInventory(player);
        hattack.put(player, true);
        
		Bukkit.getScheduler().scheduleSyncDelayedTask(Tower.getInstance(), new Runnable() {
			@Override
			public void run() {                
				
				event.getPlayer().sendMessage("§cTu peux maintenant prende des dégâts");
				hattack.put(player, false);
			}
		}, 160L);
        
        
    }
    
	@EventHandler
	public void changeweather(WeatherChangeEvent e){
		e.setCancelled(true);
	}
    
    @EventHandler
    public void onattacj(EntityDamageByEntityEvent e){
    	if(hattack.get(e.getEntity()) != null && hattack.get(e.getEntity()) == true){
    		e.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (Tower.getInstance().getGameState() != GameState.GAME) {
            return;
        }
        for (final TeamManager.TucTeam team : TeamManager.getInstance().getTeams()) {
            if (team.isOverTrap(event.getBlock().getLocation())) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        if (Tower.getInstance().getGameState() != GameState.GAME) {
            return;
        }
        for (final TeamManager.TucTeam team : TeamManager.getInstance().getTeams()) {
            if (team.isOverTrap(event.getBlock().getLocation())) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        if (Tower.getInstance().getGameState() != GameState.GAME) {
            return;
        }
        final Player player = event.getPlayer();
        final PlayersManager.TucPlayer tplayer = PlayersManager.getInstance().getPlayer(player);
        for (final TeamManager.TucTeam team : TeamManager.getInstance().getTeamsIgnore(tplayer.getTeam())) {
            if (!team.isTrap(event.getFrom()) && team.isTrap(event.getTo())) {
                tplayer.getTeam().addPoints(player);
                Bukkit.broadcastMessage(tplayer.getTeam().getColor().getChatColor() + ""+ tplayer.getPlayer().getName() + "§b a marqué un point chez les §f: " + team.getNameColored());
                player.teleport(tplayer.getTeam().getSpawnPlayer());
            }
        }
    }
	@EventHandler
	public void onPlayerQuit(ReturnToLobbyEvent e) {
		 Player player = e.getTarget();
		 if (Tower.getInstance().getGameState().equals(GameState.BEFORE_GAME)) return;
		 PlayerLocal pl = PlayerLocalManager.get().getPlayerLocal(player.getName());
		 player.sendMessage("§6-----------------------------------------------------");
		 player.sendMessage("§6Fin de partie sur §b" + Bukkit.getServerName());
		 player.sendMessage("§7Gain total de §eCoins §7sur la partie : §e" + String.format("%.2f", pl.getGainedEC()));
		 player.sendMessage("§7Gain total de §bCrédits §7sur la partie : §e" + String.format("%.2f", pl.getGainedLC()));
		 player.sendMessage("§6-----------------------------------------------------");
	}
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        if (Tower.getInstance().getGameState() != GameState.GAME) {
            return;
        }
        Utils.checkWin();
    }
    
    @EventHandler
    public void onPlayerDeath(final PlayerDeathEvent event) {
		BukkitAPI api = BukkitAPI.get();
        final Player dead = event.getEntity();
        if (dead.getKiller() != null) {
            int kill = hkill.get(dead.getKiller()) == null ? 0 : hkill.get(dead.getKiller());
        	hkill.put(dead.getKiller(), kill + 1);
        	api.getTasksManager().addTask(() -> {
				PlayerData data = api.getPlayerDataManager().getPlayerData(dead.getKiller().getName());
				data.creditCoins(2, "Kill", true, null);
			}); 
        }
        int death = hdeath.get(dead) == null ? 0 : hdeath.get(dead);
        hdeath.put(dead, death + 1);
        event.setDeathMessage(" " + ChatColor.YELLOW + dead.getName() + ChatColor.GRAY + " " + ChatColor.GRAY + (dead.getKiller() == null ? "a succombé." : new StringBuilder("a été tué par ").append(ChatColor.YELLOW).append(dead.getKiller().getName()).toString()));
        Utils.checkWin();
    }
}
