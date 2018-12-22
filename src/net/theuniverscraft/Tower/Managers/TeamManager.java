package net.theuniverscraft.Tower.Managers;

import net.theuniverscraft.Tower.Enum.*;
import net.theuniverscraft.Tower.*;
import net.theuniverscraft.Tower.Utils.*;
import java.io.*;
import org.bukkit.configuration.file.*;
import java.util.*;
import org.bukkit.scoreboard.*;
import org.bukkit.entity.*;
import org.bukkit.*;

public class TeamManager
{
    private List<TucTeam> m_teams;
    private static TeamManager instance;
    
    public static TeamManager getInstance() {
        if (TeamManager.instance == null) {
            TeamManager.instance = new TeamManager();
        }
        return TeamManager.instance;
    }
    
    private TeamManager() {
        this.m_teams = new ArrayList<TucTeam>();
        try {
            TeamColor[] values;
            for (int length = (values = TeamColor.values()).length, i = 0; i < length; ++i) {
                final TeamColor color = values[i];
                final File dirs = new File("plugins/" + Tower.getInstance().getName() + "/teams");
                dirs.mkdirs();
                final File teamFile = new File("plugins/" + Tower.getInstance().getName() + "/teams/" + color.toString() + ".yml");
                if (!teamFile.exists()) {
                    teamFile.createNewFile();
                }
                final FileConfiguration teamConfig = (FileConfiguration)YamlConfiguration.loadConfiguration(teamFile);
                final TucTeam team = new TucTeam(color, teamFile, teamConfig);
                team.setSpawnPlayer(Utils.getLocation(teamConfig.getConfigurationSection("spawn_player")));
                team.setTrap(Utils.getLocation(teamConfig.getConfigurationSection("p1")), Utils.getLocation(teamConfig.getConfigurationSection("p2")));
                this.m_teams.add(team);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean allTeamOk() {
        for (final TucTeam team : this.m_teams) {
            if (!team.isOk()) {
                return false;
            }
        }
        return true;
    }
    
    public TucTeam getTeam(final TeamColor color) {
        for (final TucTeam team : this.m_teams) {
            if (team.getColor().equals(color)) {
                return team;
            }
        }
        return null;
    }
    
    public List<TucTeam> getTeamNotFull() {
        final List<TucTeam> teams = new ArrayList<TucTeam>();
        for (final TucTeam team : this.m_teams) {
            if (!team.isFull()) {
                teams.add(team);
            }
        }
        Utils.sortList(teams, new Comparator<TucTeam>() {
            @Override
            public int compare(final TucTeam team1, final TucTeam team2) {
                return Integer.compare(team1.getPlayers().size(), team2.getPlayers().size());
            }
        });
        return teams;
    }
    
    public List<TucTeam> getTeamsIgnore(final TucTeam ignore) {
        final List<TucTeam> teams = new ArrayList<TucTeam>();
        for (final TucTeam team : this.m_teams) {
            if (!team.getColor().equals(ignore.getColor())) {
                teams.add(team);
            }
        }
        return teams;
    }
    
    public boolean allTeamHavePlayer() {
        for (final TucTeam team : this.m_teams) {
            if (team.getPlayers().size() == 0) {
                return false;
            }
        }
        return true;
    }
    
    public TucTeam firstTeamHavePlayer() {
        for (final TucTeam team : this.m_teams) {
            if (team.getPlayers().size() != 0) {
                return team;
            }
        }
        return null;
    }
    
    public boolean allTeamFull() {
        for (final TucTeam team : this.m_teams) {
            if (!team.isFull()) {
                return false;
            }
        }
        return true;
    }
    
    public List<TucTeam> getTeams() {
        return this.m_teams;
    }
    
    public class TucTeam
    {
        private TeamColor m_color;
        private File m_file;
        private FileConfiguration m_config;
        private Location m_spawnPlayer;
        private Location m_p1;
        private Location m_p2;
        private Team m_bukkitTeam;
        private int m_score;
        
        private TucTeam(final TeamColor color, final File file, final FileConfiguration config) {
            this.m_score = 0;
            this.m_color = color;
            this.m_file = file;
            this.m_config = config;
            if (TucScoreboardManager.getScoreboard().getTeam(this.m_color.toLocal()) != null) {
                this.m_bukkitTeam = TucScoreboardManager.getScoreboard().getTeam(this.m_color.toLocal());
            }
            else {
                this.m_bukkitTeam = TucScoreboardManager.getScoreboard().registerNewTeam(this.m_color.toLocal());
            }
            this.m_bukkitTeam.setAllowFriendlyFire(false);
            this.m_bukkitTeam.setDisplayName(this.m_color.getChatColor() + this.m_color.toLocal());
            this.m_bukkitTeam.setPrefix(ChatColor.RESET + "[" + this.m_color.getChatColor() + this.m_color.toLocal() + ChatColor.RESET + "] ");
        }
        
        public void tpToSpawn() {
            for (final PlayersManager.TucPlayer player : this.getPlayers()) {
                player.getPlayer().teleport(this.m_spawnPlayer);
                Utils.setInventory(player.getPlayer());
            }
        }
        
        public boolean isTrap(final Location loc) {
            return loc.getBlockX() >= this.m_p1.getBlockX() && loc.getBlockX() <= this.m_p2.getBlockX() && loc.getBlockY() >= this.m_p1.getBlockY() && loc.getBlockY() <= this.m_p2.getBlockY() && loc.getBlockZ() >= this.m_p1.getBlockZ() && loc.getBlockZ() <= this.m_p2.getBlockZ();
        }
        
        public boolean isOverTrap(final Location loc) {
            return loc.getBlockX() >= this.m_p1.getBlockX() && loc.getBlockX() <= this.m_p2.getBlockX() && loc.getBlockZ() >= this.m_p1.getBlockZ() && loc.getBlockZ() <= this.m_p2.getBlockZ();
        }
        
        public Location getSpawnPlayer() {
            return this.m_spawnPlayer;
        }
        
        public int getScore() {
            return this.m_score;
        }
        
        public TeamColor getColor() {
            return this.m_color;
        }
        
        public void addPoints(final Player player) {
            ++this.m_score;
            Utils.checkWin();
        }
        
        public void sendMessage(final String msg) {
            for (final PlayersManager.TucPlayer player : this.getPlayers()) {
                player.getPlayer().sendMessage(msg);
            }
        }
        
        public void setSpawnPlayer(final Location spawnPlayer) {
            this.m_spawnPlayer = spawnPlayer;
            Utils.setLocation(this.m_config.createSection("spawn_player"), this.m_spawnPlayer);
            this.save();
        }
        
        public void setTrap(final Location p1, final Location p2) {
            if (p1 == null || p2 == null) {
                return;
            }
            this.m_p1 = new Location(p1.getWorld(), (p1.getX() < p2.getX()) ? p1.getX() : p2.getX(), (p1.getY() < p2.getY()) ? p1.getY() : p2.getY(), (p1.getZ() < p2.getZ()) ? p1.getZ() : p2.getZ());
            this.m_p2 = new Location(p1.getWorld(), (p1.getX() > p2.getX()) ? p1.getX() : p2.getX(), (p1.getY() > p2.getY()) ? p1.getY() : p2.getY(), (p1.getZ() > p2.getZ()) ? p1.getZ() : p2.getZ());
            Utils.setLocation(this.m_config.createSection("p1"), this.m_p1);
            Utils.setLocation(this.m_config.createSection("p2"), this.m_p2);
            this.save();
        }
        
        public List<PlayersManager.TucPlayer> getPlayers() {
            return PlayersManager.getInstance().getPlayersByTeam(this);
        }
        
        public boolean isFull() {
            return this.getPlayers().size() >= 7;
        }
        
        public boolean isOk() {
            return this.m_spawnPlayer != null && this.m_p1 != null && this.m_p2 != null;
        }
        
        private void save() {
            try {
                this.m_config.save(this.m_file);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void kick(final String msg) {
            for (final PlayersManager.TucPlayer player : this.getPlayers()) {
                player.getPlayer().kickPlayer(msg);
            }
        }
        
        public void addPlayer(final Player player) {
            this.m_bukkitTeam.addPlayer((OfflinePlayer)player);
        }
        
        public String getNameColored() {
            return this.m_color.getChatColor() + this.m_color.toLocal();
        }
        
        public void removePlayer(final Player player) {
            this.m_bukkitTeam.removePlayer((OfflinePlayer)player);
        }
        
        @Override
        public boolean equals(final Object object) {
            if (!(object instanceof TucTeam)) {
                return false;
            }
            final TucTeam other = (TucTeam)object;
            return other.getColor().equals(this.m_color);
        }
    }
}
