package net.theuniverscraft.Tower.Kits;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.utils.ItemStackUtils;
import fr.icrotz.gameserver.BukkitAPI;
import fr.icrotz.gameserver.api.Games;
import fr.icrotz.gameserver.api.PlayerLocal;
import fr.icrotz.gameserver.api.SettingsManager;
import fr.icrotz.gameserver.utils.gui.GuiScreen;
import net.theuniverscraft.Tower.Tower;

public class KitsGui extends GuiScreen {

	public KitsGui(Player p) {
		super("", 1, p, false);
		PlayerLocal pl = PlayerLocal.getPlayer(getPlayer().getName());
		if (pl.contains("kit")) {
			Kit kit = Tower.kits.getKitByID(pl.get("kit"));
			if (kit != null)
				setName("Sélection: §e" + kit.getName());
			else {
				setName("Sélection: §eChoisir");
				pl.remove("kit");
				BukkitAPI.get().getTasksManager().addTask(() -> {
					SettingsManager.removeSetting(p.getName(), Games.TOWERS, "kit");
				});
			}
		} else {
			setName("Sélection: §eChoisir");
		}

		build();
	}

	@Override
	public void drawScreen() {

		buildKits(getPlayer());

	}

	public void buildKits(Player player) {
		List<Kit> kits = Tower.kits.getKits();
		for (Kit kit : kits)
			addItem(kit.getItemUI(player));
	}

	@Override
	public void onOpen() {
	}

	@Override
	public void onClick(ItemStack item, InventoryClickEvent event) {
		if (!ItemStackUtils.isValid(item))
			return;
		event.setCancelled(true);

		Kit kit = Tower.kits.getKit(item, getPlayer());

		if (!kit.haveKit(getPlayer()))
			return;

		PlayerLocal pl = PlayerLocal.getPlayer(getPlayer().getName());
		pl.set("kit", kit.getUuid());

		BukkitAPI.get().getTasksManager().addTask(() -> {

			SettingsManager.setSetting(getPlayer().getName(), Games.TOWERS, "kit", kit.getUuid());

		});

		getPlayer().sendMessage("§6Votre kit : §e" + kit.getName() + ".");

		close();
	}

	@Override
	public void onClose() {
	}

}
