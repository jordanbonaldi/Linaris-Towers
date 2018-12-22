package net.theuniverscraft.Tower.Managers;

import org.bukkit.*;
import java.util.*;
import org.bukkit.scoreboard.*;

public abstract class TucScoreboardManager
{
    private static Scoreboard m_scoreboard;
    private static Objective m_objective;
    
    public static Scoreboard getScoreboard() {
        init();
        return TucScoreboardManager.m_scoreboard;
    }
    
    public static void update() {
        init();
        for (final TeamManager.TucTeam team : TeamManager.getInstance().getTeams()) {
            final Score teamScore = TucScoreboardManager.m_objective.getScore(Bukkit.getOfflinePlayer(String.valueOf(team.getNameColored()) + ChatColor.WHITE + ":"));
            teamScore.setScore(team.getScore());
        }
    }
    
    private static void init() {
        if (TucScoreboardManager.m_scoreboard == null) {
            TucScoreboardManager.m_scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }
    }
}
