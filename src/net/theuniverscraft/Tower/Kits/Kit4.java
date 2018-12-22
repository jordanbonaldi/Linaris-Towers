package net.theuniverscraft.Tower.Kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.modules.kits.LevelInfo;
import fr.icrotz.gameapi.utils.ItemBuilder;

public class Kit4 extends Kit {

	@SuppressWarnings("deprecation")
	public Kit4() {
		super("kit4","Kit 4",new ItemStack(Material.IRON_CHESTPLATE),"",true);
		LevelInfo level1 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", 
				"§7- Casque en Mailles", "§7- Plastron en Fer", "§7- Pantalon en Mailles", "§7- Bottes en Mailles","§7- Epée en pierre T1","§7- Potion de soin (Buvable)","", "§cA acheter au HUB" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		LevelInfo level0 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Mailles", "§7- Plastron en Fer", "§7- Pantalon en Mailles", "§7- Bottes en Mailles","§7- Epée en pierre T1","§7- Potion de soin (Buvable)" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		level0.addItem(new ItemStack(314,1,(short) 0));
		level0.addItem(new ItemStack(307,1,(short) 0));
		level0.addItem(new ItemStack(316,1,(short) 0));
		level0.addItem(new ItemStack(317,1,(short) 0));

		level0.addItem(new ItemBuilder(new ItemStack(272,1,(short) 0)).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
		
		level0.addItem(new ItemStack(373,1,(short) 8261));


		
		
	
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
