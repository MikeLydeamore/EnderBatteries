package com.insane.enderbatteries;

import net.minecraft.nbt.NBTTagCompound;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor @EqualsAndHashCode
public class BlockPosDimension {
	
	@Getter
	private int x, y, z, dimension;
	
	public NBTTagCompound saveToTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		tag.setInteger("dimension", dimension);
		
		return tag;
	}
	
	public BlockPosDimension(NBTTagCompound tag) {
		x = tag.getInteger("x");
		y = tag.getInteger("y");
		z = tag.getInteger("z");
		dimension = tag.getInteger("dimension");
	}

}
