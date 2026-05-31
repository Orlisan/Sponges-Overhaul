package com.orlisan.spongesoverhaul.blocks.custom;

import com.orlisan.spongesoverhaul.blocks.blockEntities.CustomSpongeBlockEntity;
import com.orlisan.spongesoverhaul.blocks.blockEntities.SimpleCustomSpongeBlockEntity;
import com.orlisan.spongesoverhaul.blocks.blockEntities.WetMobCustomSpongeBlockEntity;
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
    public MobSponge(Properties properties, Class<?> mobClass,  Block wetSponge, int... configurazioni) {
        super(properties, mobClass,  wetSponge, configurazioni);
    }

    public MobSponge(Properties properties, TagKey<EntityType<?>> types,  Block wetSponge) {
        super(properties, types,  wetSponge);
    }

    public MobSponge(Properties properties, Class<?> mobClass, Block wetSponge) {
        super(properties, mobClass, wetSponge);
    }

    public MobSponge(Properties properties, TagKey<EntityType<?>> types, Block wetSponge, int... customCountAndDepth) {
        super(properties, types, wetSponge, customCountAndDepth);
    }
    @Override
    public void tryAbsorbWater(Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof SimpleCustomSpongeBlockEntity blockEntity)) return;

        boolean absorbed = false;

        if (blockEntity.isInABigCube && blockEntity.bigCubePos != null) {
            for (BlockPos cubePos : blockEntity.bigCubePos) {
                if (this.removeWaterBreadthFirstSearch(level, cubePos)) absorbed = true;
            }
            if (absorbed) {
                for (BlockPos cubePos : blockEntity.bigCubePos) {
                    ArrayList<EntityType<?>> mobsAbsorbed = null;
                    if(level.getBlockEntity(cubePos) instanceof SimpleCustomSpongeBlockEntity simpleBlockEntity) {
                        mobsAbsorbed = new ArrayList<>(simpleBlockEntity.getMobsAbsorbed());
                    }
                    level.setBlock(cubePos, WET_SPONGE.defaultBlockState(), 2);
                    if(mobsAbsorbed != null && level.getBlockEntity(cubePos) instanceof WetMobCustomSpongeBlockEntity newBlockEntity) {
                        newBlockEntity.setMobsAbsorbed(mobsAbsorbed);
                    }
                }
                level.playSound((Entity) null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        } else {
            if (this.removeWaterBreadthFirstSearch(level, pos)) {
                ArrayList<EntityType<?>> mobsAbsorbed = new ArrayList<>(blockEntity.getMobsAbsorbed());
                level.setBlock(pos, WET_SPONGE.defaultBlockState(), 2);
                if(level.getBlockEntity(pos) instanceof WetMobCustomSpongeBlockEntity newBlockEntity) {
                    newBlockEntity.setMobsAbsorbed(mobsAbsorbed);
                }
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
        return new SimpleCustomSpongeBlockEntity(worldPosition, blockState);
    }
}
