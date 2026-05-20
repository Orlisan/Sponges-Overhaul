package com.orlisan.spongesoverhaul.blocks.blockEntities;

import com.orlisan.spongesoverhaul.blocks.custom.CustomSponges;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

import static com.orlisan.spongesoverhaul.blocks.custom.CustomSponges.MAX_COOLDOWN;

public class CustomSpongeBlockEntity extends BlockEntity {
    public int ORIGINAL_MAX_COUNT = 65;
    public int ORIGINAL_MAX_DEPTH = 6;
    public int MAX_COUNT = ORIGINAL_MAX_COUNT;
    public int MAX_DEPTH = ORIGINAL_MAX_DEPTH;
    public CustomSpongeBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(SpongeBlockEntities.CUSTOM_SPONGE_BLOCK_ENTITY, worldPosition, blockState);
    }
    public boolean startCooldown = false;
    public boolean FINISHED_COOLDOWN = false;
    public int cooldown = MAX_COOLDOWN;
    public boolean isInABigCube = false;
    public ArrayList<BlockPos> bigCubePos = new ArrayList<BlockPos>();
    public void tick(Level level, BlockPos pos, BlockState state) {
        if(startCooldown) {
            cooldown--;
        }
        if(cooldown == 0) {
            stopCooldown();
            this.finishCooldown();
            if(this.getBlockState().getBlock() instanceof CustomSponges sponge) {
                sponge.tryAbsorbWater(level, pos);
            }
        }
    }
    public void startCooldown() {
        startCooldown = true;
    }
    public void resetCooldown() {
        cooldown = MAX_COOLDOWN;
    }
    public void stopCooldown() {
        startCooldown = false;
    }
    public void finishCooldown() {
        FINISHED_COOLDOWN = true;
    }
    public boolean isCooldownFinished() {
        return FINISHED_COOLDOWN;
    }
}
