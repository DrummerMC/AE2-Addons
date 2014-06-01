package DrummerMC.AE2_Addons.Parts;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.AE2_Addons.AE2_Addons;
import DrummerMC.AE2_Addons.Api.Grid.IEnergyGrid;
import DrummerMC.AE2_Addons.Api.Grid.IEnergyStorageGrid;
import DrummerMC.AE2_Addons.Api.Util.EnergyType;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.parts.BusSupport;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartCollsionHelper;
import appeng.api.parts.IPartHost;
import appeng.api.parts.IPartRenderHelper;
import appeng.api.parts.PartItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;

public class PartBase implements IPart, IGridHost{
	

	IGridNode node = null;
	
	public TileEntity tile;
	public ForgeDirection side = ForgeDirection.UNKNOWN;
	public IPartHost host;
	public PartGridBlock block;
	
	@Override
	public ItemStack getItemStack(PartItemStack type) {
		return new ItemStack(AE2_Addons.partItem);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventory(IPartRenderHelper rh, RenderBlocks renderer) {
		rh.setTexture(Blocks.stone.getIcon(0, 0));
		rh.setBounds(2, 2, 14, 14, 14, 16);
		rh.renderInventoryBox(renderer);

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderStatic(int x, int y, int z, IPartRenderHelper rh,
			RenderBlocks renderer) {
		rh.setTexture(Blocks.stone.getIcon(0, 0));
		rh.setBounds(2, 2, 14, 14, 14, 16);
		rh.renderBlock(x, y, z, renderer);

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderDynamic(double x, double y, double z,
			IPartRenderHelper rh, RenderBlocks renderer) {

	}

	@Override
	public boolean requireDynamicRender() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canConnectRedstone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void readFromNBT(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getLightLevel() {
		return 5;
	}

	@Override
	public boolean isLadder(EntityLivingBase entity) {
		return false;
	}

	@Override
	public void onNeighborChanged() {
		// TODO Auto-generated method stub

	}

	@Override
	public int isProvidingStrongPower() {
		return 0;
	}

	@Override
	public int isProvidingWeakPower() {
		return 0;
	}

	@Override
	public void writeToStream(ByteBuf data) throws IOException {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		ByteBufUtils.writeTag(data, tag);

	}

	@Override
	public boolean readFromStream(ByteBuf data) throws IOException {
		try{
			this.readFromNBT(ByteBufUtils.readTag(data));
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public IGridNode getGridNode() {
		if(this.node == null){
			try{
				this.node = AEApi.instance().createGridNode(block);
				this.node.updateState();
			}catch(Exception e){
				return null;
			}
		}
		return node;
	}

	@Override
	public void onEntityCollision(Entity entity) {

	}

	@Override
	public void removeFromWorld() {

	}

	@Override
	public void addToWorld() {
		this.block = new PartGridBlock(this);

	}

	@Override
	public IGridNode getExternalFacingNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPartHostInfo(ForgeDirection side, IPartHost host, TileEntity tile) {
		this.host = host;
		this.tile = tile;
		this.side = side;
	}

	@Override
	public void getBoxes(IPartCollsionHelper bch) {
		bch.addBox(2, 2, 14, 14, 14, 16);

	}

	@Override
	public boolean onActivate(EntityPlayer player, Vec3 pos) {
		if(player.worldObj.isRemote)
			return false;
		IEnergyStorageGrid g = this.getGridNode().getGrid().getCache(IEnergyStorageGrid.class);
		String energy = ""+g.getEnergy(EnergyType.MJ, "default");
		player.addChatMessage(new ChatComponentText(energy));
		System.out.println(energy);
		return false;
	}

	@Override
	public boolean onShiftActivate(EntityPlayer player, Vec3 pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void getDrops(List<ItemStack> drops, boolean wrenched) {
		// TODO Auto-generated method stub

	}

	@Override
	public int cableConnectionRenderTo() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlacement(EntityPlayer player, ItemStack held,
			ForgeDirection side) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean canBePlacedOn(BusSupport what) {
		if(what == BusSupport.DENSE_CABLE)
			return false;
		return true;
	}

	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		return this.getGridNode();
	}

	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.SMART;
	}

	@Override
	public void securityBreak() {
		
	}

	public DimensionalCoord getLocation() {
		return new DimensionalCoord(tile);
	}

}