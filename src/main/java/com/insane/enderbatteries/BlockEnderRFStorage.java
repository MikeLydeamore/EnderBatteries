package com.insane.enderbatteries;

import codechicken.enderstorage.common.TileFrequencyOwner;
import codechicken.lib.raytracer.RayTracer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockEnderRFStorage extends BlockContainer {

	protected BlockEnderRFStorage() {
		super(Material.rock);
		setHardness(15F);
		setResistance(100F);
		setStepSound(soundTypeStone);
		setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileRFTank();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return true;

		TileFrequencyOwner tile = (TileFrequencyOwner) world.getTileEntity(x, y, z);
		MovingObjectPosition hit = RayTracer.retraceBlock(world, player, x, y, z);

		return tile.activate(player, hit.subHit);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileRFTank te = (TileRFTank) world.getTileEntity(x, y, z);
		if (te != null)
			te.retestForEnergyHandlers();
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileRFTank te = (TileRFTank) world.getTileEntity(x, y, z);
		if (te != null)	{
			te.addCoords();
		}
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileRFTank te = (TileRFTank) world.getTileEntity(x, y, z);
		if (te != null) {
			te.removeCoords();
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

}
