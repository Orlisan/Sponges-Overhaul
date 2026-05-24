package com.orlisan.spongesoverhaul.blocks.blockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

import static com.orlisan.spongesoverhaul.blocks.custom.SimpleCustomSponges.pitagora3d;

public class SimpleCustomSpongeBlockEntity extends CustomSpongeBlockEntity {
    public SimpleCustomSpongeBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(SpongeBlockEntities.SIMPLE_CUSTOM_SPONGE_BLOCK_ENTITY, worldPosition, blockState);
    }
    private final Random RANDOM = new Random();
    public boolean isMobSponge;
    public ArrayList<EntityType<?>> mobsAbsorbed = new ArrayList<>();
    public AABB area = new AABB(this.getBlockPos()).inflate(this.MAX_DEPTH);
    public ArrayList<BlockPos> blockPos = new ArrayList<>();
    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);
        settaSfera();
    }
    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        super.tick(level, pos, state);
        if(isMobSponge && !mobsAbsorbed.isEmpty() && level instanceof ServerLevel serverLevel) {

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

    private void settaSfera() {
        blockPos.clear();
        area = new AABB(this.getBlockPos()).inflate(this.MAX_DEPTH);
        for (BlockPos pos : BlockPos.betweenClosed(area)) {
            if (
                pitagora3d(Math.abs(pos.getX() - this.getBlockPos().getX()), Math.abs(pos.getY() - this.getBlockPos().getY()), Math.abs(pos.getZ() - this.getBlockPos().getZ()))
                <= this.MAX_DEPTH
            ) {
                blockPos.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
            }
        }
    }
    @Override
    public void moltiplicaValori() {
        super.moltiplicaValori();
        this.settaSfera();
    }
}
