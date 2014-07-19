package DrummerMC.Extra_Stuff.Parts;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;
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
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import DrummerMC.Extra_Stuff.Extra_Stuff;
import DrummerMC.Extra_Stuff.Api.Grid.IEnergyGrid;
import DrummerMC.Extra_Stuff.Api.Grid.IEnergyStorageGrid;
import DrummerMC.Extra_Stuff.Api.Util.EnergyType;
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

@Optional.InterfaceList(value = { @Interface(iface = "appeng.api.parts.IPart", modid = "appliedenergistics2"),
		@Interface(iface = "appeng.api.networking.IGridHost", modid = "appliedenergistics2")})
public class PartBase implements IPart, IGridHost{
	

	IGridNode node = null;
	
	public TileEntity tile;
	public ForgeDirection side = ForgeDirection.UNKNOWN;
	public IPartHost host;
	public PartGridBlock block;
	
	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public ItemStack getItemStack(PartItemStack type) {
		return new ItemStack(Extra_Stuff.partItem);
	}
	
	@Optional.Method(modid = "appliedenergistics2")
	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventory(IPartRenderHelper rh, RenderBlocks renderer) {
		rh.setTexture(Blocks.stone.getIcon(0, 0));
		rh.setBounds(2, 2, 14, 14, 14, 16);
		rh.renderInventoryBox(renderer);

	}
	
	@Optional.Method(modid = "appliedenergistics2")
	@SideOnly(Side.CLIENT)
	@Override
	public void renderStatic(int x, int y, int z, IPartRenderHelper rh,
			RenderBlocks renderer) {
		rh.setTexture(Blocks.stone.getIcon(0, 0));
		rh.setBounds(2, 2, 14, 14, 14, 16);
		rh.renderBlock(x, y, z, renderer);

	}
	
	@Optional.Method(modid = "appliedenergistics2")
	@SideOnly(Side.CLIENT)
	@Override
	public void renderDynamic(double x, double y, double z,
			IPartRenderHelper rh, RenderBlocks renderer) {

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean requireDynamicRender() {
		// TODO Auto-generated method stub
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean canConnectRedstone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void writeToNBT(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void readFromNBT(NBTTagCompound data) {
		// TODO Auto-generated method stub

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public int getLightLevel() {
		return 5;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean isLadder(EntityLivingBase entity) {
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void onNeighborChanged() {
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public int isProvidingStrongPower() {
		return 0;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public int isProvidingWeakPower() {
		return 0;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void writeToStream(ByteBuf data) throws IOException {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		ByteBufUtils.writeTag(data, tag);

	}

	@Optional.Method(modid = "appliedenergistics2")
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

	@Optional.Method(modid = "appliedenergistics2")
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

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void onEntityCollision(Entity entity) {

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void removeFromWorld() {
		if(this.getGridNode() != null)
			this.getGridNode().destroy();
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void addToWorld() {
		this.block = new PartGridBlock(this);

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IGridNode getExternalFacingNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void setPartHostInfo(ForgeDirection side, IPartHost host, TileEntity tile) {
		this.host = host;
		this.tile = tile;
		this.side = side;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void getBoxes(IPartCollsionHelper bch) {
		bch.addBox(2, 2, 14, 14, 14, 16);

	}

	@Optional.Method(modid = "appliedenergistics2")
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

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean onShiftActivate(EntityPlayer player, Vec3 pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void getDrops(List<ItemStack> drops, boolean wrenched) {
		// TODO Auto-generated method stub

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public int cableConnectionRenderTo() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random r) {
		// TODO Auto-generated method stub

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void onPlacement(EntityPlayer player, ItemStack held,
			ForgeDirection side) {
		// TODO Auto-generated method stub

	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean canBePlacedOn(BusSupport what) {
		if(what == BusSupport.DENSE_CABLE)
			return false;
		return true;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		return this.getGridNode();
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public AECableType getCableConnectionType(ForgeDirection dir) {
		return AECableType.SMART;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public void securityBreak() {
		
	}

	@Optional.Method(modid = "appliedenergistics2")
	public DimensionalCoord getLocation() {
		return new DimensionalCoord(tile);
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public IIcon getBreakingTexture() {
		// TODO Auto-generated method stub
		return null;
	}

}