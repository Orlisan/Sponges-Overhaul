package com.orlisan.spongesoverhaul.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WetSpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CustomWetSponges extends Block {
    public static BooleanProperty SOUL = BooleanProperty.create("soul");
    private BlockItem dryVersion = null;
    private final SimpleParticleType particleTypes;
    public CustomWetSponges(Properties properties, BlockItem dryVersion, SimpleParticleType particleTypes) {
        super(properties);
        this.dryVersion = dryVersion;
        this.particleTypes = particleTypes;
        this.registerDefaultState(this.stateDefinition.any().setValue(SOUL, false));
    }


    public CustomWetSponges(Properties properties, SimpleParticleType particleTypes) {
        super(properties);
        this.particleTypes = particleTypes;
        this.registerDefaultState(this.stateDefinition.any().setValue(SOUL, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SOUL);
    }

    public BlockItem getDryVersion() {
        return dryVersion;
    }

    public void setDryVersion(BlockItem dryVersion) {
        this.dryVersion = dryVersion;
    }

    protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        if ((Boolean)level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, pos)) {
            level.setBlock(pos, this.getDryVersion().getBlock().defaultBlockState(), 3);
            level.levelEvent(2009, pos, 0);
            level.playSound((Entity)null, pos, SoundEvents.WET_SPONGE_DRIES, SoundSource.BLOCKS, 1.0F, (1.0F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
        }

    }

    public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
        Direction direction = Direction.getRandom(random);
        if (direction != Direction.UP) {
            BlockPos relativePos = pos.relative(direction);
            BlockState blockState = level.getBlockState(relativePos);
            if (!state.canOcclude() || !blockState.isFaceSturdy(level, relativePos, direction.getOpposite())) {
                double xx = (double)pos.getX();
                double yy = (double)pos.getY();
                double zz = (double)pos.getZ();
                if (direction == Direction.DOWN) {
                    yy -= 0.05;
                    xx += random.nextDouble();
                    zz += random.nextDouble();
                } else {
                    yy += random.nextDouble() * 0.8;
                    if (direction.getAxis() == Direction.Axis.X) {
                        zz += random.nextDouble();
                        if (direction == Direction.EAST) {
                            ++xx;
                        } else {
                            xx += 0.05;
                        }
                    } else {
                        xx += random.nextDouble();
                        if (direction == Direction.SOUTH) {
                            ++zz;
                        } else {
                            zz += 0.05;
                        }
                    }
                }

                level.addParticle(particleTypes, xx, yy, zz, (double)0.0F, (double)0.0F, (double)0.0F);
            }
        }
    }

}
