package net.theuniverscraft.Tower.Managers;

import java.util.*;
import net.theuniverscraft.Tower.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import net.theuniverscraft.Tower.Enum.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;
import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.event.block.*;

public class CreateTeamManager implements Listener
{
    private List<TeamCreator> m_clanCreators;
    private static CreateTeamManager instance;
    
    public static CreateTeamManager getInstance() {
        if (CreateTeamManager.instance == null) {
            CreateTeamManager.instance = new CreateTeamManager();
        }
        return CreateTeamManager.instance;
    }
    
    private CreateTeamManager() {
        this.m_clanCreators = new LinkedList<TeamCreator>();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)Tower.getInstance());
    }
    
    public void createTeam(final Player player, final TeamColor color) {
        this.m_clanCreators.add(new TeamCreator(player, color));
    }
    
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        for (int i = 0; i < this.m_clanCreators.size(); ++i) {
            this.m_clanCreators.get(i).onPlayerInteract(event);
            if (this.m_clanCreators.get(i).isFinish()) {
                this.m_clanCreators.remove(i);
                --i;
            }
        }
    }
    
    public enum CreationEtapes
    {
        SPAWN_PLAYER("SPAWN_PLAYER", 0, ChatColor.GREEN + "Définissez le spawn des joueurs (fa\u00eete un clique droit dans l'air)"), 
        P1("P1", 1, ChatColor.GREEN + "Définissez le premier coins du trou (clique droit sur le bloc)"), 
        P2("P2", 2, ChatColor.GREEN + "Définissez le deuxieme coins du trou (clique droit sur le bloc)"), 
        CREATION_END("CREATION_END", 3, ChatColor.DARK_GREEN + "La team est créé");
        
        private String m_msg;
        
        private CreationEtapes(final String s, final int n, final String msg) {
            this.m_msg = msg;
        }
        
        public String getMessage() {
            return this.m_msg;
        }
    }
    
    public class TeamCreator
    {
        private Player m_player;
        private CreationEtapes m_etape;
        private TeamColor m_color;
        private Location spawn_player;
        private Location p1;
        private Location p2;
        
        private TeamCreator(final Player player, final TeamColor color) {
            this.m_player = player;
            this.m_color = color;
            this.setEtape(CreationEtapes.SPAWN_PLAYER);
            this.m_player.setItemInHand(new ItemStack(Material.STICK, 1));
        }
        
        public void onPlayerInteract(final PlayerInteractEvent event) {
            if (!this.m_player.getUniqueId().equals(event.getPlayer().getUniqueId())) {
                return;
            }
            if (this.m_etape == CreationEtapes.SPAWN_PLAYER) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    this.spawn_player = event.getPlayer().getLocation();
                    this.m_player.setItemInHand(new ItemStack(Material.STONE));
                    this.setEtape(CreationEtapes.P1);
                }
            }
            else if (this.m_etape == CreationEtapes.P1) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    this.p1 = event.getClickedBlock().getLocation();
                    event.setCancelled(true);
                    this.setEtape(CreationEtapes.P2);
                }
            }
            else if (this.m_etape == CreationEtapes.P2 && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                this.p2 = event.getClickedBlock().getLocation();
                event.setCancelled(true);
                this.setEtape(CreationEtapes.CREATION_END);
            }
        }
        
        public boolean isFinish() {
            return this.m_etape == CreationEtapes.CREATION_END;
        }
        
        private void setEtape(final CreationEtapes etape) {
            this.m_etape = etape;
            this.m_player.sendMessage(this.m_etape.getMessage());
            if (this.isFinish()) {
                final TeamManager.TucTeam team = TeamManager.getInstance().getTeam(this.m_color);
                team.setSpawnPlayer(this.spawn_player);
                team.setTrap(this.p1, this.p2);
            }
        }
    }
}
