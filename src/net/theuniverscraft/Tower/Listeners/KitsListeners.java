package net.theuniverscraft.Tower.Listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.utils.ItemStackUtils;
import fr.icrotz.gameapi.utils.NBTItem;
import fr.icrotz.gameserver.BukkitAPI;
import fr.icrotz.gameserver.api.Games;
import fr.icrotz.gameserver.api.PlayerLocal;
import fr.icrotz.gameserver.api.SettingsManager;
import fr.icrotz.gameserver.utils.gui.GuiManager;
import net.theuniverscraft.Tower.Kits.KitsGui;
import net.theuniverscraft.Tower.Managers.KitsManager;


public class KitsListeners implements Listener {

	KitsManager manager;
	
	public KitsListeners(KitsManager manager) {
		this.manager = manager;
	}

	public static ItemStack getItem() {
		return ItemStackUtils.create(Material.NAME_TAG, (byte) 0, 1, "§6Choisir un kit §7(Clic-droit)", null);
	}

	@EventHandler
	public void Interact(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (!ItemStackUtils.isValid(p.getItemInHand()))
			return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if (p.getItemInHand().isSimilar(getItem()))
			GuiManager.openGui(new KitsGui(p));
	}

	@EventHandler
	public void onPlayer(PlayerDeathEvent e) {
		PlayerLocal pl = PlayerLocal.getPlayer(e.getEntity().getName());
		List<ItemStack> items = new ArrayList<ItemStack>();
		items.addAll(e.getDrops());
		for (ItemStack item : e.getDrops()) {
			if (new NBTItem(item).hasKey("kit")) {
				items.remove(item);
			}
		}
		e.getDrops().clear();
		for (ItemStack item : items) {
			e.getEntity().getLocation().getWorld().dropItem(e.getEntity().getLocation(), item);
		}
		if (!pl.contains("kit"))
			return;
		Kit kit = manager.getKitByID(pl.get("kit"));
		if (kit != null)
			kit.onDead(e.getEntity());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		PlayerLocal pl = PlayerLocal.getPlayer(p.getName());
		if (e.getPlayer().getGameMode() == GameMode.SPECTATOR)
			return;
		

		p.updateInventory();

	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (ItemStackUtils.isValid(e.getItemDrop().getItemStack()))
			return;
		if (new NBTItem(e.getItemDrop().getItemStack()).hasKey("kit")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		BukkitAPI.get().getTasksManager().addTask(() -> {

			if (SettingsManager.isSetting(e.getPlayer().getName(), Games.RUSH, "kit"))
				PlayerLocal.getPlayer(e.getPlayer().getName()).set("kit",
						SettingsManager.getSetting(e.getPlayer().getName(), Games.RUSH, "kit"));

			Player p = e.getPlayer();
			PlayerLocal pl = PlayerLocal.getPlayer(p.getName());
			if (pl.contains("kit")) {
				Kit kit = manager.getKitByID(pl.get("kit"));
				if (kit != null)
					p.sendMessage("§6Votre kit : §e" + kit.getName() + ".");
				else {
					pl.remove("kit");
					SettingsManager.removeSetting(p.getName(), Games.RUSH, "kit");
				}
			}
		});
	}
}

