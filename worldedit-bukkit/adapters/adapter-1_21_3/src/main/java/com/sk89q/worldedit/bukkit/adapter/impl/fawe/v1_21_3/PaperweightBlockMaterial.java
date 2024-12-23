package com.sk89q.worldedit.bukkit.adapter.impl.fawe.v1_21_3;

import com.fastasyncworldedit.core.nbt.FaweCompoundTag;
import com.sk89q.worldedit.world.registry.BlockMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import org.bukkit.craftbukkit.block.data.CraftBlockData;

import javax.annotation.Nullable;

public class PaperweightBlockMaterial implements BlockMaterial {

    private final Block block;
    private final BlockState blockState;
    private final CraftBlockData craftBlockData;
    private final org.bukkit.Material craftMaterial;
    private final int opacity;
    private final FaweCompoundTag tile;

    public PaperweightBlockMaterial(Block block) {
        this(block, block.defaultBlockState());
    }

    public PaperweightBlockMaterial(Block block, BlockState blockState) {
        this.block = block;
        this.blockState = blockState;
        this.craftBlockData = CraftBlockData.fromData(blockState);
        this.craftMaterial = craftBlockData.getMaterial();
        opacity = blockState.getLightBlock();
        BlockEntity tileEntity = !(block instanceof EntityBlock) ? null : ((EntityBlock) block).newBlockEntity(
                BlockPos.ZERO,
                blockState
        );
        tile = tileEntity == null
                ? null
                : PaperweightGetBlocks.NMS_TO_TILE.apply(tileEntity);
    }

    public Block getBlock() {
        return block;
    }

    public BlockState getState() {
        return blockState;
    }

    public CraftBlockData getCraftBlockData() {
        return craftBlockData;
    }

    @Override
    public boolean isAir() {
        return blockState.isAir();
    }

    @Override
    public boolean isFullCube() {
        return Block.isShapeFullBlock(blockState.getShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO));
    }

    @Override
    public boolean isOpaque() {
        return blockState.canOcclude();
    }

    @Override
    public boolean isPowerSource() {
        return blockState.isSignalSource();
    }

    @Override
    public boolean isLiquid() {
        return !blockState.getFluidState().is(Fluids.EMPTY);
    }

    @Override
    public boolean isSolid() {
        return blockState.isSolidRender();
    }

    @Override
    public float getHardness() {
        return craftBlockData.getState().destroySpeed;
    }

    @Override
    public float getResistance() {
        return block.getExplosionResistance();
    }

    @Override
    public float getSlipperiness() {
        return block.getFriction();
    }

    @Override
    public int getLightValue() {
        return blockState.getLightEmission();
    }

    @Override
    public int getLightOpacity() {
        return opacity;
    }

    @Override
    public boolean isFragileWhenPushed() {
        return blockState.getPistonPushReaction() == PushReaction.DESTROY;
    }

    @Override
    public boolean isUnpushable() {
        return blockState.getPistonPushReaction() == PushReaction.BLOCK;
    }

    @Override
    public boolean isTicksRandomly() {
        return blockState.isRandomlyTicking();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isMovementBlocker() {
        return blockState.blocksMotion();
    }

    @Override
    public boolean isBurnable() {
        return craftMaterial.isBurnable();
    }

    @Override
    public boolean isToolRequired() {
        // Removed in 1.16.1, this is not present in higher versions
        return false;
    }

    @Override
    public boolean isReplacedDuringPlacement() {
        return blockState.canBeReplaced();
    }

    @Override
    public boolean isTranslucent() {
        return !blockState.canOcclude();
    }

    @Override
    public boolean hasContainer() {
        return block instanceof EntityBlock;
    }

    @Override
    public boolean isTile() {
        return block instanceof EntityBlock;
    }

    @Override
    public @Nullable FaweCompoundTag defaultTile() {
        return tile;
    }

    @Override
    public int getMapColor() {
        // rgb field
        return block.defaultMapColor().col;
    }

}
