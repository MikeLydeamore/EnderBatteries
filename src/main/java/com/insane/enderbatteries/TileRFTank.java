package com.insane.enderbatteries;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.ForgeDirection;
import codechicken.enderstorage.EnderStorage;
import codechicken.enderstorage.api.AbstractEnderStorage;
import codechicken.enderstorage.api.EnderStorageManager;
import codechicken.enderstorage.common.TileFrequencyOwner;
import cofh.api.energy.IEnergyReceiver;

public class TileRFTank extends TileFrequencyOwner implements IEnergyReceiver {
	
	private boolean firstTick = false;
	private EnderRFStorage storage;
	private IEnergyReceiver[] receivers = new IEnergyReceiver[6];
	
	@Override
	public AbstractEnderStorage getStorage() {
		return storage;
	}

	@Override
	public void reloadStorage() {
		storage = (EnderRFStorage) EnderStorageManager.instance(worldObj.isRemote).getStorage(owner, freq, "RF");
		
	}
	
	public void retestForEnergyHandlers() {
		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity te = worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
			if (te != null && te instanceof IEnergyReceiver && !(te instanceof TileRFTank)) {
				receivers[dir.ordinal()] = (IEnergyReceiver) te;
			}
			else if (receivers[dir.ordinal()] != null) {
				receivers[dir.ordinal()] = null;
			}
		}
		this.markDirty();
	}
	
	private int pushEnergy(int maxReceive, boolean simulate) {
		// This looks over each IEnergyReceiver and pushes out as much energy as possible.
		// Returns the total spread across *all* attached IEnergyReceivers.
		int total = 0;
		for (int i = 0; i < 6; i++) {
			if (receivers[i] == null)
				continue;
			
			int specific = receivers[i].receiveEnergy(ForgeDirection.VALID_DIRECTIONS[i].getOpposite(), maxReceive, simulate);
			maxReceive -= specific;
			total += specific;
		}
		
		return total;
	}
	
	public void addCoords() {
		storage.addCoords(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
	}
	
	public void removeCoords() {
		storage.removeCoords(xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection arg0) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection arg0) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection arg0) {
		return 0;
	}

	@Override
	public int receiveEnergy(ForgeDirection dir, int maxReceive, boolean simulate) {
		// This looks over all the connected batteries and asks them how much to distribute.
		int total = 0;
		for (BlockPosDimension pos : storage.getAttachedTiles()) {
			World world = DimensionManager.getProvider(pos.getDimension()).worldObj;
			TileEntity te = world.getTileEntity(pos.getX(), pos.getY(), pos.getZ());
			if (!(te instanceof TileRFTank))
				continue;
			TileRFTank battery = (TileRFTank) te;
			int thisBattery = battery.pushEnergy(maxReceive, simulate);
			total += thisBattery;
			maxReceive -= thisBattery;
		}
		return total;
	}
	
	@Override
	public void updateEntity() {
		if (!firstTick) {
			retestForEnergyHandlers();
			firstTick = true;
		}
	}

	

}
