package net.theuniverscraft.Tower.Managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import fr.icrotz.gameapi.Game;
import fr.icrotz.gameapi.modules.scoreboard.ScoreBoardModule;
import fr.icrotz.gameapi.utils.RainbowEffect;
import fr.icrotz.gameapi.utils.ScoreboardSign;
import fr.icrotz.gameserver.BukkitAPI;
import fr.icrotz.gameserver.api.ServerInfo;
import net.theuniverscraft.Tower.Tower;
import net.theuniverscraft.Tower.Enum.GameState;
import net.theuniverscraft.Tower.Enum.TeamColor;
import net.theuniverscraft.Tower.Listeners.GameListener;
import net.theuniverscraft.Tower.Managers.TeamManager.TucTeam;
import net.theuniverscraft.Tower.Utils.Constantes;

public class ScoreboardManager extends ScoreBoardModule {

	RainbowEffect domainEffect;
	String domainDisplay;
	private int gm= 0;


	public ScoreboardManager(Game game) {
		super(game);

		domainEffect = new RainbowEffect("play.linaris.fr", "§e", "§c", 40);


	}

	@Override
	public void onUpdate(Player p) {
		List<Player> gmp = new ArrayList<Player>();
		gmp.clear();
		for(Player pp : Bukkit.getOnlinePlayers()){
			if(pp.getGameMode().equals(GameMode.SPECTATOR)){
				gmp.add(pp);
			}
		}
		gm = gmp.size();
		
		ScoreboardSign bar = ScoreboardSign.get(p);
		if (bar == null) {
			bar = new ScoreboardSign(p, "§eTowers");
			bar.create();
		}

		HashMap<Integer, String> lines = new HashMap<Integer, String>();

		if (Tower.getInstance().getGameState().equals(GameState.BEFORE_GAME)) {
			bar.setObjectiveName("§6Tower");
			lines.put(7, "§c");
			lines.put(6, "§fMode: §a4vs4");
			lines.put(5, "§f");
			lines.put(4, "§fJoueurs: §e" + (Bukkit.getOnlinePlayers().size() - gm));
			lines.put(3, "§e");
			lines.put(2, "§7Serveur: §f" + BukkitAPI.get().getServName());
			lines.put(1, domainDisplay);

		} else {
			if(p.getGameMode().equals(GameMode.SPECTATOR)){
				bar.setObjectiveName("§6Tower");
				lines.put(8, "§7");
				lines.put(5, "§cVous êtes");
				lines.put(4, "§cmort !");
				lines.put(3, "§d");
				lines.put(2, "§7Serveur: §f" + BukkitAPI.get().getServName());
				lines.put(1, domainDisplay);
			}else{
				if(Constantes.TEAM == 2){
					bar.setObjectiveName("§9" + TeamManager.getInstance().getTeam(TeamColor.BLUE).getScore() + "  §6Tower  §c"+ TeamManager.getInstance().getTeam(TeamColor.RED).getScore());
				}else{
					bar.setObjectiveName("§e"+ TeamManager.getInstance().getTeam(TeamColor.YELLOW).getScore()+"  §9" + TeamManager.getInstance().getTeam(TeamColor.BLUE).getScore() + "  §6Tower  §c"+ TeamManager.getInstance().getTeam(TeamColor.RED).getScore() + "  §a"+ TeamManager.getInstance().getTeam(TeamColor.GREEN).getScore());
				}
			lines.put(13, "§c");
			lines.put(12, "§fJoueurs: §a" + (Bukkit.getOnlinePlayers().size() - gm));
			lines.put(11, "§f");
			lines.put(10, "§fTeam: " + (Tower.getInstance().getGameState().equals(GameState.END_GAME) ? "§cFin du jeu" : PlayersManager.getInstance().getPlayer(p).getTeam().getNameColored().toString()));
			lines.put(9, "§e");
			lines.put(8, "§fVos Points: §e" + (Tower.getInstance().getGameState().equals(GameState.END_GAME) ? "§cFin du jeu" : scorepoint(PlayersManager.getInstance().getPlayer(p).getTeam())));
			lines.put(7, "§d");
			lines.put(6, "§fVos Kills: §a" + (Tower.getInstance().getGameState().equals(GameState.END_GAME) ? "§cFin du jeu" : GameListener.hkill.get(p) == null ? "0" : GameListener.hkill.get(p)));
			lines.put(5, "§d");
			lines.put(4, "§fVos Morts: §c" + (Tower.getInstance().getGameState().equals(GameState.END_GAME) ? "§cFin du jeu" : GameListener.hdeath.get(p) == null ? "0" : GameListener.hdeath.get(p)));
			lines.put(3, "§a");
			lines.put(2, "§7Serveur: §f" + BukkitAPI.get().getServName());
			lines.put(1, domainDisplay);
			}
			
		} 
		
		if (lines.isEmpty())
			return;
		for (int i = 1; i < 16; i++) {
			if (!lines.containsKey(i)) {
				if (bar.getLine(i) != null)
					bar.removeLine(i);
			} else {
				if (bar.getLine(i) == null)
					bar.setLine(i, lines.get(i));
				else if (!bar.getLine(i).equals(lines.get(i)))
					bar.setLine(i, lines.get(i));
			}
		}

	}
	private String scorepoint(TucTeam team){
		switch(team.getScore()){
		case 0:
			return "§c0§7/§b10";
		case 1:
			return "§c1§7/§b10";
		case 2:
			return "§c2§7/§b10";
		case 3:
			return "§c3§7/§b10";
		case 4:
			return "§64§7/§b10";
		case 5:
			return "§65§7/§b10";
		case 6:
			return "§e6§7/§b10";
		case 7:
			return "§e7§7/§b10";
		case 8:
			return "§e8§7/§b10";
		case 9:
			return "§a9§7/§b10";
		case 10:
			return "§aVictoire";
		}
		
		return null;
	}

	@Override
	public void onUpdate() {
		domainDisplay = domainEffect.next();
	}

}
