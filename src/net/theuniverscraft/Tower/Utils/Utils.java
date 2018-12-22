package net.theuniverscraft.Tower.Utils;

import java.util.Comparator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameserver.BukkitAPI;
import fr.icrotz.gameserver.api.PlayerData;
import fr.icrotz.gameserver.api.PlayerLocal;
import net.theuniverscraft.Tower.Tower;
import net.theuniverscraft.Tower.Enum.GameState;
import net.theuniverscraft.Tower.Enum.TeamColor;
import net.theuniverscraft.Tower.Managers.PlayersManager;
import net.theuniverscraft.Tower.Managers.TeamManager;
import net.theuniverscraft.Tower.Managers.TeamManager.TucTeam;

public abstract class Utils
{
	public static boolean kok = true;
    public static Location getLocation(final ConfigurationSection section) {
        try {
            final Location location = new Location(Bukkit.getWorld(section.getString("world")), section.getDouble("x"), section.getDouble("y"), section.getDouble("z"), (float)section.getDouble("yaw"), (float)section.getDouble("pitch"));
            return location;
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static void setLocation(final ConfigurationSection section, final Location loc) {
        try {
            if (loc == null) {
                return;
            }
            section.set("world", (Object)loc.getWorld().getName());
            section.set("x", (Object)loc.getX());
            section.set("y", (Object)loc.getY());
            section.set("z", (Object)loc.getZ());
            section.set("yaw", (Object)loc.getYaw());
            section.set("pitch", (Object)loc.getPitch());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static boolean plus1moins1equal(final int x, final int y) {
        return x - y >= -1 && x - y <= 1;
    }
    

    
    public static Object getMetadata(final Metadatable meta, final String key) {
        if (meta.hasMetadata(key)) {
            for (final MetadataValue value : meta.getMetadata(key)) {
                if (value.getOwningPlugin().getName().equalsIgnoreCase(Tower.getInstance().getName())) {
                    return value.value();
                }
            }
        }
        return null;
    }
    
    public static void setInventory(final Player player) {
        final PlayersManager.TucPlayer tplayer = PlayersManager.getInstance().getPlayer(player);
        final PlayerInventory inv = player.getInventory();
        final GameState state = Tower.getInstance().getGameState();
        if (state == GameState.BEFORE_GAME) {
            inv.clear();
            for (int i = 0; i < TeamColor.values().length; ++i) {
                final ItemStack is = new ItemStack(Material.WOOL, 1);
                is.setDurability((short)TeamColor.values()[i].getDyeColor().getData());
                inv.setItem(i, is);
            }
			tplayer.getPlayer().getInventory().setItem(6, net.theuniverscraft.Tower.Listeners.KitsListeners.getItem());
			ItemStack bedi = new ItemStack(Material.WOOD_DOOR);
			ItemMeta bedim = bedi.getItemMeta();
			bedim.setDisplayName("§6Retour au HUB");
			bedi.setItemMeta(bedim);
			
			tplayer.getPlayer().getInventory().setItem(8, bedi);
        }
        else if (state == GameState.GAME) {
            player.setHealth(20);
            player.setFoodLevel(20);
            inv.clear();
            final ItemStack[] armor = new ItemStack[4];
            for (int j = 0; j < 4; ++j) {
                ItemStack item = null;
                switch (j) {
                    case 0: {
                        item = new ItemStack(Material.LEATHER_BOOTS);
                        break;
                    }
                    case 1: {
                        item = new ItemStack(Material.LEATHER_LEGGINGS);
                        break;
                    }
                    case 2: {
                        item = new ItemStack(Material.LEATHER_CHESTPLATE);
                        break;
                    }
                    case 3: {
                        item = new ItemStack(Material.LEATHER_HELMET);
                        break;
                    }
                }
                if (item != null) {
                    final LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
                    meta.setColor(tplayer.getTeam().getColor().getDyeColor().getColor());
                    item.setItemMeta((ItemMeta)meta);
                }
                armor[j] = item;
            }
            inv.setArmorContents(armor);
            if(kok){
			PlayerLocal pl = PlayerLocal.getPlayer(player.getName());
			
			if (pl.contains("kit")) {
				Kit kit = Tower.kits.getKitByID(pl.get("kit"));
				if (kit != null)
					kit.giveKit(player);
			}
            }
		}
        else if (state == GameState.END_GAME) {
            player.setGameMode(GameMode.CREATIVE);
            inv.clear();
            inv.setItem(0, new ItemStack(Material.TNT, 1));
            inv.setItem(1, new ItemStack(Material.FLINT_AND_STEEL, 1));
        }
        player.updateInventory();
    }
    private static TucTeam getmorescore(){
    	if(TeamManager.getInstance().getTeam(TeamColor.RED).getScore() >= TeamManager.getInstance().getTeam(TeamColor.BLUE).getScore() 
    			&& TeamManager.getInstance().getTeam(TeamColor.RED).getScore() >= TeamManager.getInstance().getTeam(TeamColor.GREEN).getScore() 
    			&& (TeamManager.getInstance().getTeam(TeamColor.RED).getScore() >= TeamManager.getInstance().getTeam(TeamColor.YELLOW).getScore())){
    		return TeamManager.getInstance().getTeam(TeamColor.RED);
    	}else if(TeamManager.getInstance().getTeam(TeamColor.BLUE).getScore() >= TeamManager.getInstance().getTeam(TeamColor.RED).getScore() 
    			&& TeamManager.getInstance().getTeam(TeamColor.BLUE).getScore() >= TeamManager.getInstance().getTeam(TeamColor.GREEN).getScore() 
    			&& (TeamManager.getInstance().getTeam(TeamColor.BLUE).getScore() >= TeamManager.getInstance().getTeam(TeamColor.YELLOW).getScore())){
    		return TeamManager.getInstance().getTeam(TeamColor.BLUE);
    	}else if(TeamManager.getInstance().getTeam(TeamColor.YELLOW).getScore() >= TeamManager.getInstance().getTeam(TeamColor.RED).getScore() 
    			&& TeamManager.getInstance().getTeam(TeamColor.YELLOW).getScore() >= TeamManager.getInstance().getTeam(TeamColor.GREEN).getScore() 
    			&& (TeamManager.getInstance().getTeam(TeamColor.YELLOW).getScore() >= TeamManager.getInstance().getTeam(TeamColor.BLUE).getScore())){
    		return TeamManager.getInstance().getTeam(TeamColor.YELLOW);
    	}else if(TeamManager.getInstance().getTeam(TeamColor.GREEN).getScore() >= TeamManager.getInstance().getTeam(TeamColor.RED).getScore() 
    			&& TeamManager.getInstance().getTeam(TeamColor.GREEN).getScore() >= TeamManager.getInstance().getTeam(TeamColor.BLUE).getScore() 
    			&& (TeamManager.getInstance().getTeam(TeamColor.GREEN).getScore() >= TeamManager.getInstance().getTeam(TeamColor.YELLOW).getScore())){
    		return TeamManager.getInstance().getTeam(TeamColor.GREEN);
    	}
		return TeamManager.getInstance().getTeam(TeamColor.BLUE);
    }
    @SuppressWarnings("unchecked")
	public static void checkWin() {
        TeamManager.TucTeam winner = null;
        for (final TeamManager.TucTeam team : TeamManager.getInstance().getTeams()) {
            if (team.getScore() >= 10) {
                winner = team;
                break;
            }
        }
        if (winner == null && !TeamManager.getInstance().allTeamHavePlayer()) {
        	winner = getmorescore();
        }
        if (winner != null) {
        	 BukkitAPI api = BukkitAPI.get();
            for (final PlayersManager.TucPlayer tplayer : winner.getPlayers()) {
    			api.getTasksManager().addTask(() -> {
    				PlayerData data = api.getPlayerDataManager().getPlayerData(tplayer.getPlayer().getName());
    				data.creditCoins(8, "Win", true, null);
    				data.creditLegendaryCoins(4, "win", true, null);
    			});  
            }
            Translation.broadcastWinner(winner);
            Tower.getInstance().setGameState(GameState.END_GAME);
            for(Player player : Bukkit.getOnlinePlayers()){
                setInventory(player);
            }
        }
    }
    
    public static <T> void sortList(final List<T> list, final Comparator<T> c) {
        for (int i = 0; i < list.size() - 1; ++i) {
            final T obj1 = list.get(i);
            final T obj2 = list.get(i + 1);
            if (c.compare(obj1, obj2) > 0) {
                list.set(i, obj2);
                list.set(i + 1, obj1);
                i -= 2;
                if (i <= -2) {
                    ++i;
                }
            }
        }
    }
    
    public static void tpToLobby(final Player player) {
    	 ByteArrayDataOutput out = ByteStreams.newDataOutput();
         out.writeUTF("Connect");
         out.writeUTF("Hub1");
         player.sendPluginMessage(Tower.getInstance(), "BungeeCord", out.toByteArray());
    }
}
