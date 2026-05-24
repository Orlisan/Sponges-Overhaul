package com.orlisan.spongesoverhaul.blocks.custom;

import com.orlisan.spongesoverhaul.blocks.blockEntities.CustomSpongeBlockEntity;
import com.orlisan.spongesoverhaul.blocks.blockEntities.SimpleCustomSpongeBlockEntity;
import com.orlisan.spongesoverhaul.blocks.blockEntities.SpongeBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;


import java.util.ArrayList;

import static com.orlisan.spongesoverhaul.SpongesOverhaul.LOGGER;

public class SimpleCustomSponges extends CustomSponges{
    public SimpleCustomSponges(Properties properties, Class<?> fluidClass, Item onOutput, Block wetSponge, int... configurazioni) {
        super(properties, fluidClass, onOutput, wetSponge, configurazioni);
    }

    public SimpleCustomSponges(Properties properties, Class<?> fluidClass, Item onOutput, Block wetSponge) {
        super(properties, fluidClass, onOutput, wetSponge);
    }

    public SimpleCustomSponges(Properties properties, TagKey<Block> types, Item onOutput, Block wetSponge, int... customCountAndDepth) {
        super(properties, types, onOutput, wetSponge, customCountAndDepth);
    }

    public SimpleCustomSponges(Properties properties, TagKey<Block> types, Item onOutput, Block wetSponge) {
        super(properties, types, onOutput, wetSponge);
    }

    public SimpleCustomSponges(Properties properties, Block type, Item onOutput, Block wetSponge, int... configurazioni) {
        super(properties, type, onOutput, wetSponge, configurazioni);
    }

    public SimpleCustomSponges(Properties properties, Block type, Item onOutput, Block wetSponge) {
        super(properties, type, onOutput, wetSponge);
    }
    @Override
    protected void onPlace(final BlockState state, final @NotNull Level level, final @NotNull BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        if (!oldState.is(state.getBlock()) && !movedByPiston) {
            if (level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity blockEntity) {
                blockEntity.startCooldown();
                if (hasCustomConfigurations) {
                    blockEntity.ORIGINAL_MAX_COUNT = CUSTOM_COUNT;
                    blockEntity.ORIGINAL_MAX_DEPTH = CUSTOM_DEPTH;
                    blockEntity.MAX_COUNT = blockEntity.ORIGINAL_MAX_COUNT;
                    blockEntity.MAX_DEPTH = blockEntity.ORIGINAL_MAX_DEPTH;
                }
            }
            for(Direction dir: ALL_DIRECTIONS) {
                if(level.getBlockEntity(pos.relative(dir)) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
                    blockEntity.MAX_COOLDOWN *= 2;
                    blockEntity.resetCooldown();
                    if(level.getBlockEntity(pos) instanceof SimpleCustomSpongeBlockEntity myBlockEntity) {
                        myBlockEntity.MAX_COOLDOWN *= 2;
                        myBlockEntity.resetCooldown();
                    }
                }
            }
            ArrayList<SimpleCustomSpongeBlockEntity> blockEntities = new ArrayList<>();
            blockEntities.add((SimpleCustomSpongeBlockEntity) level.getBlockEntity(pos));
            byte finalUnoX = 0;
            byte finalUnoY = 0;
            byte finalUnoZ = 0;
            int trovati = 0;
            byte uno = 1;
            for (int i = 0; i < 6; i++) {
                uno = (byte) -uno;
                if (i == 0 || i == 1) {
                    BlockPos newPos = new BlockPos(pos.getX() + uno, pos.getY(), pos.getZ());
                    if (level.getBlockEntity(newPos) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoX = uno;
                        trovati++;
                    }
                } else if (i == 2 || i == 3) {
                    BlockPos newPos = new BlockPos(pos.getX(), pos.getY() + uno, pos.getZ());
                    if (level.getBlockEntity(newPos) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoY = uno;
                        trovati++;
                    }
                } else {
                    BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + uno);
                    if (level.getBlockEntity(newPos) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoZ = uno;
                        trovati++;
                    }
                }

            }
            if (finalUnoZ != 0 && finalUnoY != 0 && finalUnoX != 0) {
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY() + finalUnoY, pos.getZ() + finalUnoZ)) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY(), pos.getZ() + finalUnoZ)) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + finalUnoY, pos.getZ() + finalUnoZ)) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY() + finalUnoY, pos.getZ())) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
            }
            if (trovati == 7) {
                ArrayList<BlockPos> bigCubePos = new ArrayList<BlockPos>();
                for (SimpleCustomSpongeBlockEntity blockEntity : blockEntities) {
                    blockEntity.stopCooldown();
                    bigCubePos.add(blockEntity.getBlockPos());
                    blockEntity.moltiplicaValori();
                    blockEntity.isInABigCube = true;
                }
                for (SimpleCustomSpongeBlockEntity blockEntity : blockEntities) {
                    blockEntity.bigCubePos = bigCubePos;
                }
                this.tryAbsorbWater(level, pos);
            }
        }
    }

    @Override
    protected boolean removeWaterBreadthFirstSearch(final Level level, final BlockPos startPos) {
       //LOGGER.info("Inizio ad Assorbire fuoco!");
        boolean removedAnything = false;
        int count = 0;
        if(!(level.getBlockEntity(startPos) instanceof SimpleCustomSpongeBlockEntity blockEntity)) return false;
        if(!blockEntity.blockPos.isEmpty()) {
            for (BlockPos pos : blockEntity.blockPos) {
                  if (!pos.equals(startPos) && removeThing(level, startPos, pos) == BlockPos.TraversalNodeStatus.ACCEPT) {
                        removedAnything = true;
                        count++;
                        if (count >= blockEntity.MAX_COUNT) break;
                  }

            }
        }
        return removedAnything;


    }
    public static double pitagora(double a, double b) {
        return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
    public static double pitagora3d(double a, double b, double c) {
        return pitagora(pitagora(a, b), c);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos worldPosition, @NotNull BlockState blockState) {
        return new SimpleCustomSpongeBlockEntity(worldPosition, blockState);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (type == SpongeBlockEntities.SIMPLE_CUSTOM_SPONGE_BLOCK_ENTITY) {
            return (BlockEntityTicker<T>) (BlockEntityTicker<@NotNull SimpleCustomSpongeBlockEntity>)
                    (lvl, pos, st, be) -> be.tick(lvl, pos, st);
        }
        return null;
    }
}
