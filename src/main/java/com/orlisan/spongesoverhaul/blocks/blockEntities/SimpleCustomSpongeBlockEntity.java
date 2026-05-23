package com.orlisan.spongesoverhaul.blocks.blockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.orlisan.spongesoverhaul.blocks.custom.SimpleCustomSponges.pitagora3d;

public class SimpleCustomSpongeBlockEntity extends CustomSpongeBlockEntity {
    public SimpleCustomSpongeBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(worldPosition, blockState);
    }
    public ArrayList<BlockPos> blockPos = new ArrayList<>();
    @Override
    public void setLevel(@NotNull Level level) {
        super.setLevel(level);
        if(!blockPos.isEmpty()) return;
        AABB area = new AABB(this.getBlockPos()).inflate(this.MAX_DEPTH);
        for (BlockPos pos : BlockPos.betweenClosed(area)) {
            if (
                    Math.ceil(pitagora3d(Math.abs(pos.getX() - this.getBlockPos().getX()), Math.abs(pos.getY() - this.getBlockPos().getY()), Math.abs(pos.getZ() - this.getBlockPos().getZ())))
                            <= this.MAX_DEPTH
                            || Math.floor(pitagora3d(Math.abs(pos.getX() - this.getBlockPos().getX()), Math.abs(pos.getY() - this.getBlockPos().getY()), Math.abs(pos.getZ() - this.getBlockPos().getZ())))
                            <= this.MAX_DEPTH
            ) {
                blockPos.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
            }
        }
    }
}
