package net.theuniverscraft.Tower.Listeners;

import org.bukkit.event.player.*;

import net.theuniverscraft.Tower.*;
import org.bukkit.event.block.*;
import org.bukkit.*;
import net.theuniverscraft.Tower.Enum.*;
import net.theuniverscraft.Tower.Managers.*;
import net.theuniverscraft.Tower.Utils.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;

import fr.icrotz.gameserver.utils.PlayerUtils;

import java.util.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;

public class ChooseTeamListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if (Tower.getInstance().getGameState() != GameState.BEFORE_GAME) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_AIR) {
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }
        }
        try {
            final Player player = event.getPlayer();
            final PlayersManager.TucPlayer tplayer = PlayersManager.getInstance().getPlayer(player);
            final ItemStack is = player.getItemInHand();
            if (is.getType() == Material.WOOL) {
                final DyeColor dyeColor = DyeColor.getByData((byte)is.getDurability());
                final TeamColor color = TeamColor.getByeDyeColor(dyeColor);
                final TeamManager.TucTeam team = TeamManager.getInstance().getTeam(color);
                final List<TeamManager.TucTeam> teams = TeamManager.getInstance().getTeamNotFull();
                if (teams.size() >= 2) {
                    int team1Nb = teams.get(0).getPlayers().size();
                    int team2Nb = teams.get(1).getPlayers().size();
                    if (tplayer.haveTeam()) {
                        if (tplayer.getTeam().equals(teams.get(0))) {
                            --team1Nb;
                        }
                        else if (tplayer.getTeam().equals(teams.get(1))) {
                            --team2Nb;
                        }
                    }
                    final int compare = Integer.compare(team1Nb, team2Nb);
                    if (compare != 0 && ((compare == 1 && team.equals(teams.get(0))) || (compare == -1 && team.equals(teams.get(1))))) {
                        player.sendMessage(Translation.TEAM_IS_TOO_FULL);
                        return;
                    }
                }
                if (team.isFull()) {
                    player.sendMessage(Translation.TEAM_ARE_FULL.replaceAll("<team>", color.getChatColor() + color.toLocal()).replaceAll("<x>", Integer.toString(team.getPlayers().size())).replaceAll("<max>", Integer.toString(7)));
                }
                else {
                    tplayer.setTeam(team);
                    player.sendMessage(Translation.YOU_ARE_IN_TEAM.replaceAll("<team>", color.getChatColor() + color.toLocal()).replaceAll("<x>", Integer.toString(team.getPlayers().size())).replaceAll("<max>", Integer.toString(7)));
                }
                event.setCancelled(true);
                player.updateInventory();
                return;
            }else if(is.getType() == Material.WOODEN_DOOR || is.getType() == Material.WOOD_DOOR){
            	if(Tower.getInstance().getGameState().equals(GameState.BEFORE_GAME))
    			PlayerUtils.returnToHub(event.getPlayer());
            }

        }
        catch (Exception ex) {}
    }
    

}
