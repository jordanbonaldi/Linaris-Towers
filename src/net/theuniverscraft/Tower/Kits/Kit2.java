package net.theuniverscraft.Tower.Kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.modules.kits.LevelInfo;
import fr.icrotz.gameapi.utils.ItemBuilder;

public class Kit2 extends Kit {

	@SuppressWarnings("deprecation")
	public Kit2() {
		super("kit2","Kit 2",new ItemStack(Material.GOLD_SWORD),"",true);
		LevelInfo level0 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Or", "§7- Plastron en Mailles", 
				"§7- Pantalon en Mailles", "§7- Bottes en Or","§7- Epée en Or T1","§7- Potion de soin (Buvable)","","§cA Acheter au HUB" )) {
			@Override
			public void onGive(Player p) {
				
			}
		};
		LevelInfo level1 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Or", "§7- Plastron en Mailles", "§7- Pantalon en Mailles", "§7- Bottes en Or","§7- Epée en Or T1","§7- Potion de soin (Buvable)" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		level1.addItem(new ItemStack(314,1,(short) 0));
		level1.addItem(new ItemStack(303,1,(short) 0));
		level1.addItem(new ItemStack(304,1,(short) 0));
		level1.addItem(new ItemStack(317,1,(short) 0));

		level1.addItem(new ItemBuilder(new ItemStack(283,1,(short) 0)).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
		
		level1.addItem(new ItemStack(373,1,(short) 8261));


		
		
	
		addLevelInfo(level0);
		addLevelInfo(level1);

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
