package com.insane.enderbatteries;

import net.minecraft.nbt.NBTTagCompound;
import codechicken.enderstorage.api.AbstractEnderStorage;
import codechicken.enderstorage.api.EnderStorageManager;

public class EnderRFStorage extends AbstractEnderStorage {

	private int numClicks;
	
	public EnderRFStorage(EnderStorageManager manager, String owner, int freq) {
		super(manager, owner, freq);
	}

	@Override
	public void loadFromTag(NBTTagCompound tag) {
		numClicks = tag.getInteger("numClicks");		
	}

	@Override
	public NBTTagCompound saveToTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("numClicks", numClicks);
		return tag;
	}

	@Override
	public String type() {
		return "RF";
	}
	
	public void increaseNumClicks() {
		numClicks++;
		setDirty();
	}
	
	public int getNumClicks() {
		return numClicks;
	}

}
