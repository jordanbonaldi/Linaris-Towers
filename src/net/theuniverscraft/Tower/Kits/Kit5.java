package net.theuniverscraft.Tower.Kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.modules.kits.LevelInfo;

public class Kit5 extends Kit {

	@SuppressWarnings("deprecation")
	public Kit5() {
		super("kit5","Kit 5",new ItemStack(Material.IRON_SWORD),"",true);
		LevelInfo level1 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Mailles", "§7- Plastron en Fer", "§7- Pantalon en Fer",
				"§7- Bottes en Fer","§7- Epée en Fer","§7- Potion de soin (Jetable)" ,"", "§cA acheter au HUB")) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		LevelInfo level0 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Mailles", "§7- Plastron en Fer", "§7- Pantalon en Fer", "§7- Bottes en Fer","§7- Epée en Fer","§7- Potion de soin (Jetable)" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		level0.addItem(new ItemStack(314,1,(short) 0));
		level0.addItem(new ItemStack(307,1,(short) 0));
		level0.addItem(new ItemStack(308,1,(short) 0));
		level0.addItem(new ItemStack(309,1,(short) 0));

		level0.addItem(new ItemStack(267,1,(short) 0));
		
		level0.addItem(new ItemStack(373,1,(short) 16453));


		
		
		addLevelInfo(level1);
		addLevelInfo(level0);
	}

	@Override
	public void onFirstGive(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGive(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDead(Player p) {
		// TODO Auto-generated method stub

	}

}
