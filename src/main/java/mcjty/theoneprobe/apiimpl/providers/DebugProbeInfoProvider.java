package mcjty.theoneprobe.apiimpl.providers;

import mcjty.theoneprobe.config.ConfigSetup;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.LayoutStyle;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static mcjty.theoneprobe.api.TextStyleClass.INFO;
import static mcjty.theoneprobe.api.TextStyleClass.LABEL;

public class DebugProbeInfoProvider implements IProbeInfoProvider {

    @Override
    public String getID() {
        return TheOneProbe.MODID + ":debug";
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        if (mode == ProbeMode.DEBUG && ConfigSetup.showDebugInfo) {
            Block block = blockState.getBlock();
            BlockPos pos = data.getPos();
            showDebugInfo(probeInfo, world, blockState, pos, block, data.getSideHit());
        }
    }

    private void showDebugInfo(IProbeInfo probeInfo, World world, IBlockState blockState, BlockPos pos, Block block, EnumFacing side) {
        String simpleName = block.getClass().getSimpleName();
        IProbeInfo vertical = probeInfo.vertical(new LayoutStyle().borderColor(0xffff4444).spacing(2))
                .text(LABEL + I18n.format("top.Reg_Name") + ": " + INFO + block.getRegistryName().toString())
                .text(LABEL + I18n.format("top.Unlocname") + ": " + INFO + block.getTranslationKey())
                .text(LABEL + I18n.format("top.Meta") + ": " + INFO + blockState.getBlock().getMetaFromState(blockState))
                .text(LABEL + I18n.format("top.Class") + ": " + INFO + simpleName)
                .text(LABEL + I18n.format("top.Hardness") + ": " + INFO + block.getBlockHardness(blockState, world, pos))
                .text(LABEL + I18n.format("top.Power_W") + ": " + INFO + block.getWeakPower(blockState, world, pos, side.getOpposite())
                        + LABEL + ", S: " + INFO + block.getStrongPower(blockState, world, pos, side.getOpposite()))
                .text(LABEL + I18n.format("top.Light") + ": " + INFO + block.getLightValue(blockState, world, pos));
        TileEntity te = world.getTileEntity(pos);
        if (te != null) {
            vertical.text(LABEL + I18n.format("top.TE") + ": " + INFO + te.getClass().getSimpleName());
        }
    }
}
