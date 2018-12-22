package net.theuniverscraft.Tower;

import net.theuniverscraft.Tower.Commands.CommandTower;
import net.theuniverscraft.Tower.Enum.GameState;
import net.theuniverscraft.Tower.Listeners.BasicListener;
import net.theuniverscraft.Tower.Listeners.ChooseTeamListener;
import net.theuniverscraft.Tower.Listeners.GameListener;
import net.theuniverscraft.Tower.Listeners.GeneralListener;
import net.theuniverscraft.Tower.Listeners.JoinListener;
import net.theuniverscraft.Tower.Listeners.KitsListeners;
import net.theuniverscraft.Tower.Managers.KitsManager;
import net.theuniverscraft.Tower.Managers.ScoreboardManager;
import net.theuniverscraft.Tower.Managers.TeamManager;
import net.theuniverscraft.Tower.Timers.GameTimer;
import net.theuniverscraft.Tower.Utils.Translation;
import net.theuniverscraft.Tower.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.icrotz.gameserver.BukkitAPI;
import fr.icrotz.gameserver.api.ServerInfo;
import fr.icrotz.gameserver.utils.PlayerUtils;
import fr.icrotz.gameserver.utils.tasksmanager.TaskManager;


public class Tower extends JavaPlugin
{
    private Location m_lobby;
    private GameState m_gameState;
    private GameTimer m_timer;
    private static Tower instance;
    ServerInfo infos;
    public static KitsManager kits;
    public static KitsListeners km;

    public Tower() {
        this.m_gameState = GameState.BEFORE_GAME;
        this.m_timer = new GameTimer();
    }

    public static Tower getInstance() {
        return Tower.instance;
    }
    public void onLoad(){
        instance = this;
       
		infos = BukkitAPI.get().getServerInfos();
		infos.setGameName("Towers");
		infos.setMapName("4vs4");
    }
    public void onEnable() {
    	
        
        this.kits = new KitsManager();
    	this.kits.inits();
    	getServer().getPluginManager().registerEvents(new KitsListeners(kits), this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.m_lobby = Utils.getLocation(this.getConfig().getConfigurationSection("lobby"));
        if (this.m_lobby == null) {
            this.m_lobby = Bukkit.getWorlds().get(0).getSpawnLocation();
        }
        this.getCommand("tower").setExecutor((CommandExecutor)new CommandTower());
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents((Listener)new JoinListener(), (Plugin)this);
        pm.registerEvents((Listener)new ChooseTeamListener(), (Plugin)this);
        pm.registerEvents((Listener)new GameListener(), (Plugin)this);
        pm.registerEvents((Listener)new BasicListener(), (Plugin)this);
        pm.registerEvents((Listener)new GeneralListener(), (Plugin)this);
        this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)this.m_timer, 20L, 20L);
        for (final TeamManager.TucTeam team : TeamManager.getInstance().getTeams()) {
            if (!team.isOk()) {
                this.m_gameState = GameState.INIT_GAME;
                break;
            }
        }
        ScoreboardManager scoreboardManager = new ScoreboardManager(null);

		TaskManager.scheduleSyncRepeatingTask("Scoreboard", () -> {

			scoreboardManager.update();

		} , 0, 1);
		
		BukkitAPI.get().getTasksManager().addTask(() -> {
			infos.setCanJoin(true, false);
			infos.setCanSee(true, true);
		});
        
    }
    public void onDisable(){
		BukkitAPI.get().getTasksManager().addTask(() -> {
			infos.setCanJoin(false, false);
			infos.setCanSee(false, false);
		});
    }
    public void setLobby(final Location lobby) {
        this.m_lobby = lobby;
        this.m_lobby.getWorld().setSpawnLocation(this.m_lobby.getBlockX(), this.m_lobby.getBlockY(), this.m_lobby.getBlockZ());
        Utils.setLocation(this.getConfig().createSection("lobby"), this.m_lobby);
        this.saveConfig();
    }
    
    public boolean setGameState(final GameState state) {
        if (this.m_gameState == state) {
            return false;
        }
        this.m_gameState = state;
        if (this.m_gameState == GameState.GAME) {
			infos.setCanJoin(false, false);
			infos.setCanSee(false, true);
        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set 6000");
        	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gamerule doDaylightCycle false");
            for (final TeamManager.TucTeam team : TeamManager.getInstance().getTeams()) {
                team.tpToSpawn();
            }
        }
        else if (this.m_gameState == GameState.END_GAME) {
            this.m_timer.setTime(60L);
        }
        else if (this.m_gameState == GameState.RESET) {
        	for(Player player : Bukkit.getOnlinePlayers()){
                player.sendMessage(Translation.THE_GAME_IS_END);
                PlayerUtils.returnToHub(player);
            }
            new java.util.Timer().schedule( 
                    new java.util.TimerTask() {
                 @Override
                 public void run() {
                     Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wr reset");
                     Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "stop");
                 }
             }, 
             1500 
     );
        }
        return true;
    }
    
    public GameState getGameState() {
        return this.m_gameState;
    }
    
    public Location getLobby() {
        return this.m_lobby;
    }
    
    public GameTimer getTimer() {
        return this.m_timer;
    }
}
