package com.insane.enderbatteries;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=EnderBatteries.MODID)
public class EnderBatteries {

	public static final String MODID = "enderbatteries";
	
	@Instance(MODID)
	public static EnderBatteries instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		
	}
	
}
