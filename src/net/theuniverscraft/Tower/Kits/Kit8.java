package net.theuniverscraft.Tower.Kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.modules.kits.LevelInfo;
import fr.icrotz.gameapi.utils.ItemBuilder;

public class Kit8 extends Kit {

	@SuppressWarnings("deprecation")
	public Kit8() {
		super("kit8","Kit 8",new ItemStack(Material.DIAMOND_SWORD),"",true);
		LevelInfo level1 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Fer", "§7- Plastron en Diamants", 
				"§7- Pantalon en Diamants", "§7- Bottes en Fer","§7- Epée en Diamants","§7- 2 Pommes dorées","§7- Potion de soin II (Jetable)" ,"", "§cA acheter au HUB")) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		LevelInfo level0 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Fer", "§7- Plastron en Diamants", "§7- Pantalon en Diamants", "§7- Bottes en Fer","§7- Epée en Diamants","§7- 2 Pommes dorées","§7- Potion de soin II (Jetable)" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		level0.addItem(new ItemStack(306,1,(short) 0));
		level0.addItem(new ItemStack(311,1,(short) 0));
		level0.addItem(new ItemStack(312,1,(short) 0));
		level0.addItem(new ItemStack(309,1,(short) 0));

		level0.addItem(new ItemBuilder(new ItemStack(276,1,(short) 0)).build());
		
		level0.addItem(new ItemStack(322,2,(short) 0));
		
		level0.addItem(new ItemStack(373,1,(short) 16421));


		
		
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
