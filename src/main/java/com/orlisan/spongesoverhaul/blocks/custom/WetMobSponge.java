package com.orlisan.spongesoverhaul.blocks.custom;

import com.orlisan.spongesoverhaul.blocks.blockEntities.SpongeBlockEntities;
import com.orlisan.spongesoverhaul.blocks.blockEntities.WetMobCustomSpongeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class WetMobSponge extends CustomWetSponges implements EntityBlock {
    public WetMobSponge(Properties properties, BlockItem dryVersion, SimpleParticleType particleTypes, boolean dryOnNether) {
        super(properties, dryVersion, particleTypes, dryOnNether);
    }

    public WetMobSponge(Properties properties, SimpleParticleType particleTypes, boolean dryOnNether) {
        super(properties, particleTypes, dryOnNether);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos worldPosition, @NotNull BlockState blockState) {
        return new WetMobCustomSpongeBlockEntity(SpongeBlockEntities.WET_CUSTOM_MOB_SPONGE_BLOCK_ENTITY, worldPosition, blockState);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (type == SpongeBlockEntities.WET_CUSTOM_MOB_SPONGE_BLOCK_ENTITY) {
            return (BlockEntityTicker<T>) (BlockEntityTicker<@NotNull WetMobCustomSpongeBlockEntity>)
                    (lvl, pos, st, be) -> be.tick(lvl, pos, st);
        }
        return null;
    }

    private static final Random random = new Random();

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if(level.getBlockEntity(pos) instanceof WetMobCustomSpongeBlockEntity blockEntity && !blockEntity.mobsAbsorbed.isEmpty() && level instanceof ServerLevel serverLevel) {
            ArrayList<EntityType<?>> copy = new ArrayList<>(blockEntity.mobsAbsorbed);
            for(EntityType<?> type: copy) {
                double val = random.nextDouble();
                if(val < 0.25) {
                    type.spawn(serverLevel, pos, EntitySpawnReason.TRIGGERED);
                }
                blockEntity.mobsAbsorbed.remove(type);
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
}
