package net.theuniverscraft.Tower.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayersManager
{
    private List<TucPlayer> m_players;
    private static PlayersManager instance;
    
    public static PlayersManager getInstance() {
        if (PlayersManager.instance == null) {
            PlayersManager.instance = new PlayersManager();
        }
        return PlayersManager.instance;
    }
    
    private PlayersManager() {
        this.m_players = new ArrayList<TucPlayer>();
    }
    
    public TucPlayer getPlayer(final Player player) {
        for (final TucPlayer tplayer : this.m_players) {
            if (tplayer.getUUID().equals(player.getUniqueId())) {
                return tplayer;
            }
        }
        TucPlayer tplayer = new TucPlayer(player);
        this.m_players.add(tplayer);
        return tplayer;
    }
    
    public void deletePlayer(final Player player) {
        for (int i = 0; i < this.m_players.size(); ++i) {
            if (this.m_players.get(i).getUUID().equals(player.getUniqueId())) {
                this.m_players.remove(i);
                return;
            }
        }
    }
    
    public List<TucPlayer> getPlayersByTeam(final TeamManager.TucTeam team) {
        final List<TucPlayer> players = new ArrayList<TucPlayer>();
        for (final TucPlayer player : this.m_players) {
            if (player.haveTeam() && player.getTeam().getColor().equals(team.getColor())) {
                players.add(player);
            }
        }
        return players;
    }
    
    public List<TucPlayer> getPlayersWithoutTeam() {
        final List<TucPlayer> players = new ArrayList<TucPlayer>();
        for (final TucPlayer player : this.m_players) {
            if (!player.haveTeam()) {
                players.add(player);
            }
        }
        return players;
    }
    
    public List<TucPlayer> getPlayers() {
        return this.m_players;
    }
    
    public class TucPlayer
    {
		private boolean m_mortal;
        private Player m_player;
        private TeamManager.TucTeam m_team;
        
        private TucPlayer(final Player player) {
            this.m_player = player;
        }
        
        public boolean haveTeam() {
            return this.m_team != null;
        }
        
        public void setTeam(final TeamManager.TucTeam team) {
            (this.m_team = team).addPlayer(this.m_player);
        }
        
        public TeamManager.TucTeam getTeam() {
            return this.m_team;
        }
        
        public Player getPlayer() {
            return this.m_player;
        }
		public void setMortal(boolean mortal) { m_mortal = mortal; }
		public boolean isMortal() { return m_mortal; }
        
        public UUID getUUID() {
            return this.m_player.getUniqueId();
        }
    }
}
