package net.theuniverscraft.Tower.Kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.modules.kits.LevelInfo;
import fr.icrotz.gameapi.utils.ItemBuilder;

public class Kit9 extends Kit {

	@SuppressWarnings("deprecation")
	public Kit9() {
		super("kit9","Kit 9",new ItemStack(Material.GOLDEN_APPLE),"",true);
		LevelInfo level1 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Diamants", "§7- Plastron en Diamants", 
				"§7- Pantalon en Diamants", "§7- Bottes en Diamants","§7- Epée en Diamants T1","§7- 2 Pommes dorées","§7- 2 Potion de soin II (Jetable)" ,"", "§cA acheter au HUB")) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		LevelInfo level0 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Diamants", "§7- Plastron en Diamants", "§7- Pantalon en Diamants", "§7- Bottes en Diamants","§7- Epée en Diamants T1","§7- 2 Pommes dorées","§7- 2 Potion de soin II (Jetable)" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		level0.addItem(new ItemStack(310,1,(short) 0));
		level0.addItem(new ItemStack(311,1,(short) 0));
		level0.addItem(new ItemStack(312,1,(short) 0));
		level0.addItem(new ItemStack(313,1,(short) 0));

		level0.addItem(new ItemBuilder(new ItemStack(276,1,(short) 0)).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
		
		level0.addItem(new ItemStack(322,3,(short) 0));
		
		level0.addItem(new ItemStack(373,2,(short) 16421));


		
		
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
