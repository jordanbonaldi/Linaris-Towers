package net.theuniverscraft.Tower.Timers;

import java.util.List;

import net.theuniverscraft.Tower.Tower;
import net.theuniverscraft.Tower.Enum.GameState;
import net.theuniverscraft.Tower.Listeners.JoinListener;
import net.theuniverscraft.Tower.Managers.PlayersManager;
import net.theuniverscraft.Tower.Managers.TeamManager;
import net.theuniverscraft.Tower.Utils.Translation;
import net.theuniverscraft.Tower.Utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class GameTimer implements Runnable
{
    private long m_time;
    
    public GameTimer() {
        this.m_time = Integer.MAX_VALUE;
    }
    
    @Override
    public void run() {
        final GameState state = Tower.getInstance().getGameState();
        if (state != GameState.BEFORE_GAME && state != GameState.END_GAME) {
            return;
        }
        --this.m_time;
        if (state == GameState.BEFORE_GAME) {
        	if(m_time <= 120L){
            for(Player player : Bukkit.getOnlinePlayers()){
                player.setLevel((int)this.m_time);
            }
            if (this.m_time == 0L) {
                if (Bukkit.getOnlinePlayers().size() <= 1) {
                    this.m_time = Integer.MAX_VALUE;
                	JoinListener.ok = false;
                    return;
                }
                Bukkit.broadcastMessage(Translation.THE_GAME_BEGIN);
            }
            else if (this.m_time == 1L) {
                Bukkit.broadcastMessage(Translation.THE_GAME_BEGIN_IN_ONE);
            }
            else if (this.m_time % 10L == 0L || this.m_time <= 5L) {
                Bukkit.broadcastMessage(Translation.THE_GAME_BEGIN_IN.replaceAll("<sec>", Long.toString(this.m_time)));
            }
            if (this.m_time == 2L) {
                if (Bukkit.getOnlinePlayers().size() <= 1) {
                	JoinListener.ok = false;
                    this.m_time = Integer.MAX_VALUE;
                    return;
                }
                final List<PlayersManager.TucPlayer> withoutTeam = PlayersManager.getInstance().getPlayersWithoutTeam();
                while (!TeamManager.getInstance().allTeamFull()) {
                    if (withoutTeam.isEmpty()) {
                        break;
                    }
                    final TeamManager.TucTeam team = TeamManager.getInstance().getTeamNotFull().get(0);
                    withoutTeam.get(0).setTeam(team);
                    withoutTeam.remove(0);
                }
            }
        	}
        }
        else if (state == GameState.END_GAME) {
            for(Player player : Bukkit.getOnlinePlayers()){
                player.setLevel((int)this.m_time);
            }
        }
        if (this.m_time <= 0L) {
            if (state == GameState.BEFORE_GAME) {
                this.m_time = Long.MAX_VALUE;
                Tower.getInstance().setGameState(GameState.GAME);
                Utils.kok = false;
            }
            else if (state == GameState.END_GAME) {
                Tower.getInstance().setGameState(GameState.RESET);
            }
        }
    }
    
    public void setTime(final long time) {
        this.m_time = time;
    }
    
    public long getTime() {
        return this.m_time;
    }
}
