package com.orlisan.spongesoverhaul.blocks.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class LingeringPotionWetSponge extends CustomWetSponges {
    public LingeringPotionWetSponge(Properties properties) {
        super(properties);
    }

    public static final Random RANDOM = new Random();

    @Override
    public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
        Direction direction = Direction.getRandom(random);
        if (direction != Direction.UP) {
            BlockPos relativePos = pos.relative(direction);
            BlockState blockState = level.getBlockState(relativePos);
            if (!state.canOcclude() || !blockState.isFaceSturdy(level, relativePos, direction.getOpposite())) {
                double xx = (double) pos.getX();
                double yy = (double) pos.getY();
                double zz = (double) pos.getZ();
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
                level.addParticle(outputRandomPotionColor(), xx, yy, zz, (double) 0.0F, (double) 0.0F, (double) 0.0F);
            }
        }
    }

    public ParticleOptions outputRandomPotionColor() {
        double val = RANDOM.nextDouble();
        return switch (val) {
            case double v when v < 0.1 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.80f, 0.36f, 0.67f);
            case double v when v < 0.2 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 0.97f,
                    0.14f, 0.14f);
            case double v when v < 0.3 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.22f, 0.65f, 0.85f);
            case double v when v < 0.4 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.59f, 0.12f, 0.09f);
            case double v when v < 0.5 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.31f, 0.58f, 0.11f);
            case double v when v < 0.6 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.21f, 0.19f, 0.15f);
            case double v when v < 0.7 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.36f, 0.42f, 0.48f);
            case double v when v < 0.8 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.53f, 0.58f, 0.65f);
            case double v when v < 0.9 -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.12f, 0.12f, 0.12f);
            default -> ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT,
                    0.12f, 0.12f, 0.60f);
        };
    }

}

