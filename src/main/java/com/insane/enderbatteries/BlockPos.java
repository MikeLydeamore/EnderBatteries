package com.insane.enderbatteries;

import net.minecraft.nbt.NBTTagCompound;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor @EqualsAndHashCode
public class BlockPos {
	
	@Getter
	private int x, y, z;
	
	public NBTTagCompound saveToTag() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		
		return tag;
	}
	
	public BlockPos(NBTTagCompound tag) {
		x = tag.getInteger("x");
		y = tag.getInteger("y");
		z = tag.getInteger("z");
	}

}
