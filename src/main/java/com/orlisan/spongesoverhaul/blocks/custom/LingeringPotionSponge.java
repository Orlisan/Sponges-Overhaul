package com.orlisan.spongesoverhaul.blocks.custom;

import com.orlisan.spongesoverhaul.blocks.blockEntities.SimpleCustomSpongeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

public class LingeringPotionSponge extends SimpleCustomSponges {

    public LingeringPotionSponge(Properties properties, Item onOutput, Block wetSponge) {
        super(properties, onOutput, wetSponge);
    }

    @Override
    protected int removeWaterBreadthFirstSearchFalse(final Level level, final BlockPos startPos) {
        boolean foundedOne = false;
        if (level.getBlockEntity(startPos) instanceof SimpleCustomSpongeBlockEntity spongeBlockEntity) {

            for (AreaEffectCloud claude : level.getEntitiesOfClass(AreaEffectCloud.class, spongeBlockEntity.area)) {
                if (pitagora3d(claude.getX() - startPos.getX()
                        , claude.getY() - startPos.getY(), claude.getZ() - startPos.getZ()) <= spongeBlockEntity.MAX_DEPTH) {

                    claude.discard();
                    foundedOne = true;
                }
            }

        }
        if (foundedOne) return 1;
        else return 0;
    }
}
