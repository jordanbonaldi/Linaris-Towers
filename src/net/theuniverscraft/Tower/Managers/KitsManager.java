package net.theuniverscraft.Tower.Managers;

import fr.icrotz.gameapi.modules.kits.KitsModuleManager;
import net.theuniverscraft.Tower.Kits.Kit1;
import net.theuniverscraft.Tower.Kits.Kit2;
import net.theuniverscraft.Tower.Kits.Kit3;
import net.theuniverscraft.Tower.Kits.Kit4;
import net.theuniverscraft.Tower.Kits.Kit5;
import net.theuniverscraft.Tower.Kits.Kit6;
import net.theuniverscraft.Tower.Kits.Kit7;
import net.theuniverscraft.Tower.Kits.Kit8;
import net.theuniverscraft.Tower.Kits.Kit9;


public class KitsManager extends KitsModuleManager{

	@Override
	public void inits() {
		register(new Kit1());
		register(new Kit2());
		register(new Kit3());
		register(new Kit4());
		register(new Kit5());
		register(new Kit6());
		register(new Kit7());
		register(new Kit8());
		register(new Kit9());



	}

}
