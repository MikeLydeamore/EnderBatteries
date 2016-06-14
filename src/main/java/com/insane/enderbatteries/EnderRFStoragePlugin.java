package com.insane.enderbatteries;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import codechicken.enderstorage.api.AbstractEnderStorage;
import codechicken.enderstorage.api.EnderStorageManager;
import codechicken.enderstorage.api.EnderStoragePlugin;
import codechicken.lib.config.ConfigTag;

public class EnderRFStoragePlugin implements EnderStoragePlugin {

	@Override
	public AbstractEnderStorage createEnderStorage(EnderStorageManager manager,
			String owner, int freq) {
		return new EnderRFStorage(manager, owner, freq);
	}

	@Override
	public String identifer() {
		return "RF";
	}

	@Override
	public void loadConfig(ConfigTag arg0) {
		
	}

	@Override
	public void sendClientInfo(EntityPlayer arg0,
			List<AbstractEnderStorage> arg1) {
		
	}

}
