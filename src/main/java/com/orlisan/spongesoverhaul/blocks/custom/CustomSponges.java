package com.orlisan.spongesoverhaul.blocks.custom;

import com.mojang.serialization.MapCodec;
import com.orlisan.spongesoverhaul.blocks.blockEntities.CustomSpongeBlockEntity;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

public class CustomSponges extends Block implements EntityBlock{

    private static final Direction[] ALL_DIRECTIONS = Direction.values();

    private Block type = null;
    private TagKey<Block> types = null;
    private final boolean isATag;
    private final Item onOutput;
    private final Block WET_SPONGE;
    public CustomSponges(Properties properties, Block type, Item onOutput, Block wetSponge) {
        super(properties);
        isATag = false;
        this.type = type;
        this.onOutput = onOutput;
        this.WET_SPONGE = wetSponge;
    }
    public CustomSponges(Properties properties, TagKey<Block> types, Item onOutput, Block wetSponge) {
        super(properties);
        isATag = true;
        this.types = types;
        this.onOutput = onOutput;
        this.WET_SPONGE = wetSponge;

    }

    public Block getType() {return type;}
    public void setType(Block type) {this.type = type;}
    public TagKey<Block> getTypes() {return types;}
    public void setTypes(TagKey<Block> types) {types = types;}
    public boolean isATag() {return isATag;}
    public Item getOutput() {return onOutput;}
    public Block getWET_SPONGE(){return WET_SPONGE;};

    protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        if (!oldState.is(state.getBlock()) && !movedByPiston) {
            if(level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity blockEntity) blockEntity.startCooldown();
            ArrayList<CustomSpongeBlockEntity> blockEntities = new ArrayList<CustomSpongeBlockEntity>();
            blockEntities.add((CustomSpongeBlockEntity) level.getBlockEntity(pos));
            byte finalUnoX = 0;
            byte finalUnoY = 0;
            byte finalUnoZ = 0;
            int trovati = 0;
            byte uno = 1;
            for(int i = 0; i < 6; i++) {
                uno = (byte) -uno;
                if(i == 0 || i == 1) {
                    BlockPos newPos = new BlockPos(pos.getX() + uno, pos.getY(), pos.getZ());
                    if(level.getBlockEntity(newPos) instanceof CustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoX = uno;
                        trovati++;
                    }
                }else if(i == 2 || i == 3) {
                    BlockPos newPos = new BlockPos(pos.getX(), pos.getY() + uno, pos.getZ());
                    if(level.getBlockEntity(newPos) instanceof CustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoY = uno;
                        trovati++;
                    }
                }else {
                    BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + uno);
                    if(level.getBlockEntity(newPos) instanceof CustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoZ=uno;
                        trovati++;
                    }
                }

            }
            if(finalUnoZ != 0 && finalUnoY != 0 && finalUnoX != 0 ) {
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY() + finalUnoY, pos.getZ() + finalUnoZ)) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY(), pos.getZ() + finalUnoZ)) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + finalUnoY, pos.getZ() + finalUnoZ)) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY() + finalUnoY, pos.getZ())) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
            }
            if(trovati == 7) {
                for(CustomSpongeBlockEntity blockEntity: blockEntities) {
                    blockEntity.stopCooldown();
                    if(blockEntity.getBlockState().getBlock() instanceof CustomSponges sponge) {
                        level.setBlock(blockEntity.getBlockPos(), sponge.getWET_SPONGE().defaultBlockState(), 2);
                    }
                }
                blockEntities.getFirst().MAX_COUNT *= 8;
                blockEntities.getFirst().MAX_DEPTH *= 4;
                this.tryAbsorbWater(level, pos);
            }
        }
    }
    public static final int MAX_COOLDOWN = 40;
    protected void neighborChanged(final @NotNull BlockState state, final @NotNull Level level, final @NotNull BlockPos pos, final @NotNull Block block, final @Nullable Orientation orientation, final boolean movedByPiston) {
        if(level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity blockEntity) {
            if(blockEntity.isCooldownFinished()) this.tryAbsorbWater(level, pos);
        }
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (lvl, pos, st, be) -> ((CustomSpongeBlockEntity) be).tick(lvl, pos, st);
    }

    public void tryAbsorbWater(final Level level, final BlockPos pos) {
        if (this.removeWaterBreadthFirstSearch(level, pos)) {
            level.setBlock(pos, WET_SPONGE.defaultBlockState(), 2);
            level.playSound((Entity)null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

    }

    private boolean removeWaterBreadthFirstSearch(final Level level, final BlockPos startPos) {
        if(!(level.getBlockEntity(startPos) instanceof CustomSpongeBlockEntity customBlockEntity)) return false;
        return BlockPos.breadthFirstTraversal(startPos, customBlockEntity.MAX_DEPTH, customBlockEntity.MAX_COUNT, (pos, consumer) -> {
            for(Direction direction : ALL_DIRECTIONS) {
                consumer.accept(pos.relative(direction));
            }

        }, (pos) -> {
            if (pos.equals(startPos)) {
                return BlockPos.TraversalNodeStatus.ACCEPT;
            } else {
                BlockState state = level.getBlockState(pos);
                FluidState fluidState = level.getFluidState(pos);
                if (!fluidState.is(FluidTags.WATER)) {
                    return BlockPos.TraversalNodeStatus.SKIP;
                } else {
                    Block patt0$temp = state.getBlock();
                    if (patt0$temp instanceof BucketPickup bucketPickup) {
                        if (!bucketPickup.pickupBlock((LivingEntity)null, level, pos, state).isEmpty()) {
                            return BlockPos.TraversalNodeStatus.ACCEPT;
                        }
                    }
                    if(!isATag) {
                        if (state.is(type)) {
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        } else {
                            if (!state.is(Blocks.KELP) && !state.is(Blocks.KELP_PLANT) && !state.is(Blocks.SEAGRASS) && !state.is(Blocks.TALL_SEAGRASS)) {
                                return BlockPos.TraversalNodeStatus.SKIP;
                            }

                            BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                            dropResources(state, level, pos, blockEntity);
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }else {
                        if (state.is(types)) {
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        } else {
                            if (!state.is(Blocks.KELP) && !state.is(Blocks.KELP_PLANT) && !state.is(Blocks.SEAGRASS) && !state.is(Blocks.TALL_SEAGRASS)) {
                                return BlockPos.TraversalNodeStatus.SKIP;
                            }

                            BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                            dropResources(state, level, pos, blockEntity);
                            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }

                    return BlockPos.TraversalNodeStatus.ACCEPT;
                }
            }
        }) > 1;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos worldPosition, BlockState blockState) {
        return new CustomSpongeBlockEntity(worldPosition, blockState);
    }
}
