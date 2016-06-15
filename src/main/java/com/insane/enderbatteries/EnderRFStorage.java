package com.insane.enderbatteries;

import java.util.HashSet;

import lombok.Getter;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.minecraft.nbt.NBTTagCompound;
import codechicken.enderstorage.api.AbstractEnderStorage;
import codechicken.enderstorage.api.EnderStorageManager;

public class EnderRFStorage extends AbstractEnderStorage {

	@Getter
	private HashSet<BlockPos> attachedTiles = new HashSet<BlockPos>();
	
	public EnderRFStorage(EnderStorageManager manager, String owner, int freq) {
		super(manager, owner, freq);
	}

	@Override
	public void loadFromTag(NBTTagCompound tag) {	
	}

	@Override
	public NBTTagCompound saveToTag() {
		NBTTagCompound tag = new NBTTagCompound();
		return tag;
	}

	@Override
	public String type() {
		return "RF";
	}
	
	public void addCoords(int x, int y, int z) {
		attachedTiles.add(new BlockPos(x ,y ,z));
	}
	
	public void removeCoords(int x, int y, int z) {
		attachedTiles.remove(new BlockPos(x, y, z));
	}

}
