package com.insane.enderbatteries;

import codechicken.enderstorage.EnderStorage;
import codechicken.enderstorage.common.TileFrequencyOwner;
import codechicken.lib.raytracer.RayTracer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnderRFStorage extends BlockContainer {

	private IIcon[] icons = new IIcon[4];

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
		if (world.isRemote) {	
			return true;
		}

		TileFrequencyOwner tile = (TileFrequencyOwner) world.getTileEntity(x, y, z);
		MovingObjectPosition hit = RayTracer.retraceBlock(world, player, x, y, z);

		ItemStack item = player.inventory.getCurrentItem();
		if (item != null && item.getItem() == EnderStorage.getPersonalItem().getItem()) {
			if (tile.owner.equals("global")) {
				TileRFTank te = (TileRFTank) tile;
				te.removeCoords();
				te.setOwner(player.getCommandSenderName());
				te.addCoords();
				if (!player.capabilities.isCreativeMode)
					item.stackSize--;
				return true;
			}
		}
		return tile.activate(player, hit.subHit);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		TileRFTank te = (TileRFTank) world.getTileEntity(x, y, z);
		if (te != null) {
			te.retestForEnergyHandlers();
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileRFTank te = (TileRFTank) world.getTileEntity(x, y, z);
		if (te != null)	{
			te.addCoords();
			te.retestForEnergyHandlers();
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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icons[0] = register.registerIcon("enderbatteries:batteryBottom");
		icons[1] = register.registerIcon("enderbatteries:batteryTop");
		icons[2] = register.registerIcon("enderbatteries:batterySide");
		icons[3] = register.registerIcon("enderbatteries:batteryTopPersonal");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icons[side < 2 ? side : 2];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		if (side != 1)
			return getIcon(side, 0); //Metadata is irrelevant here.
		TileRFTank te = (TileRFTank) world.getTileEntity(x, y, z);
		if (te != null) {
			if (!te.owner.equals("global")) {
				return icons[3];
			}
		}
		return icons[1];
	}

}
