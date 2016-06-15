package com.insane.enderbatteries;

import java.util.HashSet;

import lombok.Getter;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.minecraft.nbt.NBTTagCompound;
import codechicken.enderstorage.api.AbstractEnderStorage;
import codechicken.enderstorage.api.EnderStorageManager;

public class EnderRFStorage extends AbstractEnderStorage {

	@Getter
	private HashSet<BlockPosDimension> attachedTiles = new HashSet<BlockPosDimension>();
	
	public EnderRFStorage(EnderStorageManager manager, String owner, int freq) {
		super(manager, owner, freq);
	}

	@Override
	public void loadFromTag(NBTTagCompound tag) {
		int total = tag.getInteger("totalTiles");
		for (int i = 0 ; i < total; i++) {
			NBTTagCompound posTag = (NBTTagCompound) tag.getTag(String.valueOf(i));
			BlockPosDimension pos = new BlockPosDimension(posTag);
			attachedTiles.add(pos);
		}
	}

	@Override
	public NBTTagCompound saveToTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("totalTiles", attachedTiles.size());
		int i = 0;
		for (BlockPosDimension pos : attachedTiles) {
			NBTTagCompound posTag = pos.saveToTag();
			tag.setTag(String.valueOf(i), posTag);
			i++;
		}
		return tag;
	}

	@Override
	public String type() {
		return "RF";
	}
	
	public void addCoords(int x, int y, int z, int dimension) {
		attachedTiles.add(new BlockPosDimension(x ,y ,z, dimension));
		this.setDirty();
	}
	
	public void removeCoords(int x, int y, int z, int dimension) {
		attachedTiles.remove(new BlockPosDimension(x, y, z, dimension));
		this.setDirty();
	}

}
