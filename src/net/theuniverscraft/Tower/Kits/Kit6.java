package net.theuniverscraft.Tower.Kits;

import java.util.Arrays;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.icrotz.gameapi.modules.kits.Kit;
import fr.icrotz.gameapi.modules.kits.LevelInfo;
import fr.icrotz.gameapi.utils.ItemBuilder;

public class Kit6 extends Kit {

	@SuppressWarnings("deprecation")
	public Kit6() {
		super("kit6","Kit 6",new ItemBuilder(new ItemStack(267,1,(short) 0)).addEnchantment(Enchantment.DAMAGE_ALL, 1).build(),"",true);
		LevelInfo level1 = new LevelInfo(Arrays.asList("�7Obtenez en d�but de partie :", "", "�7- Casque en Fer", "�7- Plastron en Fer", 
				"�7- Pantalon en Fer", "�7- Bottes en Fer","�7- Ep�e en Fer T1","�7- Pomme dor�e","�7- Potion de soin (Jetable)","", "�cA acheter au HUB" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		LevelInfo level0 = new LevelInfo(Arrays.asList("�7Obtenez en d�but de partie :", "", "�7- Casque en Fer", "�7- Plastron en Fer", "�7- Pantalon en Fer", "�7- Bottes en Fer","�7- Ep�e en Fer T1","�7- Pomme dor�e","�7- Potion de soin (Jetable)" )) {
			
			@Override
			public void onGive(Player p) {
				
			}
		};
		level0.addItem(new ItemStack(306,1,(short) 0));
		level0.addItem(new ItemStack(307,1,(short) 0));
		level0.addItem(new ItemStack(308,1,(short) 0));
		level0.addItem(new ItemStack(309,1,(short) 0));

		level0.addItem(new ItemBuilder(new ItemStack(267,1,(short) 0)).addEnchantment(Enchantment.DAMAGE_ALL, 1).build());
		
		level0.addItem(new ItemStack(322,1,(short) 0));
		
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
