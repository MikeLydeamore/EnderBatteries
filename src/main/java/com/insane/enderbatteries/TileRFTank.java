package com.insane.enderbatteries;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import codechicken.enderstorage.api.AbstractEnderStorage;
import codechicken.enderstorage.api.EnderStorageManager;
import codechicken.enderstorage.common.TileFrequencyOwner;
import codechicken.lib.packet.PacketCustom;
import codechicken.lib.raytracer.RayTracer;

public class TileRFTank extends TileFrequencyOwner {

	private EnderRFStorage storage;

	@Override
	public AbstractEnderStorage getStorage() {
		return storage;
	}

	@Override
	public void reloadStorage() {
		storage = (EnderRFStorage) EnderStorageManager.instance(worldObj.isRemote).getStorage(owner, freq, "RF");
	}

	@Override
	public boolean activate(EntityPlayer player, int subHit) {
		storage.increaseNumClicks();
		player.addChatMessage(new ChatComponentText(String.valueOf(storage.getNumClicks())));
		return true;
	}

	

}
