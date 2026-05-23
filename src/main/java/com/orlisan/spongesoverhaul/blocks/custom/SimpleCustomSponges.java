package com.orlisan.spongesoverhaul.blocks.custom;

import com.orlisan.spongesoverhaul.blocks.blockEntities.SimpleCustomSpongeBlockEntity;
import com.orlisan.spongesoverhaul.blocks.blockEntities.SpongeBlockEntities;
import net.minecraft.core.BlockPos;
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
        if(!(level.getBlockEntity(startPos) instanceof SimpleCustomSpongeBlockEntity blockEntity)) return false;
        if(!blockEntity.blockPos.isEmpty()) {
            for (BlockPos pos : blockEntity.blockPos) {
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
