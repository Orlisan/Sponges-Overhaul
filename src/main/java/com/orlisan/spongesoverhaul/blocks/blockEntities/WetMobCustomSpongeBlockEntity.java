package com.orlisan.spongesoverhaul.blocks.blockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Random;

public class WetMobCustomSpongeBlockEntity extends BlockEntity {
    public ArrayList<EntityType<?>> mobsAbsorbed = new ArrayList<>();
    public final Random RANDOM = new Random();
    public WetMobCustomSpongeBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }
    public WetMobCustomSpongeBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(SpongeBlockEntities.WET_CUSTOM_MOB_SPONGE_BLOCK_ENTITY, worldPosition, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if(!mobsAbsorbed.isEmpty() && level instanceof ServerLevel serverLevel) {

            double val = RANDOM.nextDouble();
            if(val < 0.01) {
                int id = RANDOM.nextInt(0, mobsAbsorbed.size());
                EntityType<?> mob = mobsAbsorbed.get(id);
                int X = RANDOM.nextInt(3) -1;
                int Z = RANDOM.nextInt(3) -1;
                BlockPos spawnPos = new BlockPos(this.getBlockPos().getX() + X, this.getBlockPos().getY() +1, this.getBlockPos().getZ()+Z);
                mob.spawn(serverLevel, spawnPos, EntitySpawnReason.TRIGGERED);
                mobsAbsorbed.remove(id);
            }
        }
    }
    public void setMobsAbsorbed(ArrayList<EntityType<?>> newMobs) {
        mobsAbsorbed = new ArrayList<>(newMobs);
    }
}
