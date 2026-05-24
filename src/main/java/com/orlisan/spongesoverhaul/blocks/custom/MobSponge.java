package com.orlisan.spongesoverhaul.blocks.custom;

import com.orlisan.spongesoverhaul.blocks.blockEntities.CustomSpongeBlockEntity;
import com.orlisan.spongesoverhaul.blocks.blockEntities.SimpleCustomSpongeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;


public class MobSponge extends SimpleCustomSponges {
    public MobSponge(Properties properties, Class<?> mobClass, Item onOutput, Block wetSponge, int... configurazioni) {
        super(properties, mobClass, onOutput, wetSponge, configurazioni);
    }

    public MobSponge(Properties properties, TagKey<EntityType<?>> types, Item onOutput, Block wetSponge) {
        super(properties, types, onOutput, wetSponge);
    }

    public MobSponge(Properties properties, Class<?> mobClass, Item onOutput, Block wetSponge) {
        super(properties, mobClass, onOutput, wetSponge);
    }

    public MobSponge(Properties properties, TagKey<EntityType<?>> types, Item onOutput, Block wetSponge, int... customCountAndDepth) {
        super(properties, types, onOutput, wetSponge, customCountAndDepth);
    }
    @Override
    public void tryAbsorbWater(Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity blockEntity)) return;

        boolean absorbed = false;

        if (blockEntity.isInABigCube && blockEntity.bigCubePos != null) {
            for (BlockPos cubePos : blockEntity.bigCubePos) {
                if (this.removeWaterBreadthFirstSearch(level, cubePos)) absorbed = true;
            }
            if (absorbed) {
                for (BlockPos cubePos : blockEntity.bigCubePos) {
                    level.setBlock(cubePos, WET_SPONGE.defaultBlockState(), 2);
                }
                level.playSound((Entity) null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        } else {
            if (this.removeWaterBreadthFirstSearch(level, pos)) {
                level.setBlock(pos, WET_SPONGE.defaultBlockState(), 2);
                level.playSound((Entity) null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }


    @SuppressWarnings({"unchecked", "deprecation"})
    public boolean removeWaterBreadthFirstSearch(Level level, BlockPos startPos) {
        boolean foundedOne = false;
        if (level.getBlockEntity(startPos) instanceof SimpleCustomSpongeBlockEntity blockEntity) {
            for (Mob entity : level.getEntitiesOfClass(Mob.class, blockEntity.area)) {
                if (pitagora3d(entity.getX() - startPos.getX(),
                        entity.getY() - startPos.getY(),
                        entity.getZ() - startPos.getZ()) <= blockEntity.MAX_DEPTH) {
                    if (types != null) {
                        if (entity.getType().builtInRegistryHolder().is((TagKey<EntityType<?>>) types)) {
                            entity.discard();
                            blockEntity.mobsAbsorbed.add(entity.getType());
                            foundedOne = true;
                        }
                    } else if (entity.getClass() == absorbThingClass) {
                        entity.discard();
                        blockEntity.mobsAbsorbed.add(entity.getType());
                        foundedOne = true;
                    }
                }
            }

        }
        return foundedOne;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos worldPosition, @NotNull BlockState blockState) {
        SimpleCustomSpongeBlockEntity blockEntity = new SimpleCustomSpongeBlockEntity(worldPosition, blockState);
        blockEntity.isMobSponge = true;
        return blockEntity;
    }
    private static Random random = new Random();
    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if(level.getBlockEntity(pos) instanceof SimpleCustomSpongeBlockEntity blockEntity && blockEntity.isMobSponge && !blockEntity.mobsAbsorbed.isEmpty()) {
            ArrayList<EntityType<?>> copy = new ArrayList<>(blockEntity.mobsAbsorbed);
            for(EntityType<?> type: copy) {
                double val = random.nextDouble();
                if(val < 0.75) {
                    type.spawn((ServerLevel) level, pos, EntitySpawnReason.TRIGGERED);
                }
                blockEntity.mobsAbsorbed.remove(type);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
