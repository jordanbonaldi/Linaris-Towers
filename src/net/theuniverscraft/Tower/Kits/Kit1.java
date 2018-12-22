package net.theuniverscraft.Tower.Kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.modules.kits.LevelInfo;
import fr.icrotz.gameapi.utils.ItemBuilder;

public class Kit1 extends Kit {

	@SuppressWarnings("deprecation")
	public Kit1() {
		super("kit1","Kit 1",new ItemStack(Material.WOOD_SWORD),"",true);
		LevelInfo level0 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Cuir", 
				"§7- Plastron en Cuir P1", "§7- Pantalon en Cuir P1", "§7- Bottes en Cuir","§7- Epée en bois","§7- Potion de soin (Buvable)", "", "§cA acheter au HUB" )) {
			@Override
			public void onGive(Player p) {
				
			}
		};
	
		LevelInfo level1 = new LevelInfo(Arrays.asList("§7Obtenez en début de partie :", "", "§7- Casque en Cuir", "§7- Plastron en Cuir P1", "§7- Pantalon en Cuir P1", "§7- Bottes en Cuir","§7- Epée en bois","§7- Potion de soin (Buvable)" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		level1.addItem(new ItemStack(298,1,(short) 0));
		level1.addItem(new ItemBuilder(new ItemStack(299,1,(short) 0)).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
		level1.addItem(new ItemBuilder(new ItemStack(300,1,(short) 0)).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
		level1.addItem(new ItemStack(301,1,(short) 0));

		level1.addItem(new ItemStack(268,1,(short) 0));
		
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
