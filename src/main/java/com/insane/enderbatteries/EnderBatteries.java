package com.insane.enderbatteries;

import net.minecraftforge.common.MinecraftForge;
import codechicken.enderstorage.api.EnderStorageManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=EnderBatteries.MODID)
public class EnderBatteries {

	public static final String MODID = "enderbatteries";
	
	@Instance(MODID)
	public static EnderBatteries instance;
	
	public BlockEnderRFStorage blockEnderRFStorage;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		blockEnderRFStorage = new BlockEnderRFStorage();
		blockEnderRFStorage.setBlockName("enderBattery");
		GameRegistry.registerBlock(blockEnderRFStorage, "enderBattery");
		//Don't know why this is here but EnderChests does it...
		MinecraftForge.EVENT_BUS.register(blockEnderRFStorage); 
		
		GameRegistry.registerTileEntity(TileRFTank.class, "Ender Battery");
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		EnderStorageManager.registerPlugin(new EnderRFStoragePlugin());
	}
	
}
