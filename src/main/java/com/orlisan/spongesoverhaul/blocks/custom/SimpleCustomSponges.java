package com.orlisan.spongesoverhaul.blocks.custom;

import com.orlisan.spongesoverhaul.blocks.blockEntities.CustomSpongeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;

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
    protected boolean removeWaterBreadthFirstSearch(final Level level, final BlockPos startPos) {
        LOGGER.info("Inizio ad Assorbire fuoco!");
        boolean removedAnything = false;
        int count = 0;
        if(!(level.getBlockEntity(startPos) instanceof CustomSpongeBlockEntity blockEntity)) return false;
        AABB area = new AABB(startPos).inflate(blockEntity.MAX_DEPTH);
        for (BlockPos pos : BlockPos.betweenClosed(area)) {
            double dist = pitagora3d(
                    Math.abs(pos.getX() - startPos.getX()),
                    Math.abs(pos.getY() - startPos.getY()),
                    Math.abs(pos.getZ() - startPos.getZ())
            );
            if (Math.ceil(dist) <= blockEntity.MAX_DEPTH || Math.floor(dist) <= blockEntity.MAX_DEPTH) {
                if (removeThing(level, startPos, pos) == BlockPos.TraversalNodeStatus.ACCEPT) {
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
}
