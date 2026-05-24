package com.orlisan.spongesoverhaul.blocks.custom;

import com.orlisan.spongesoverhaul.blocks.blockEntities.CustomSpongeBlockEntity;
import com.orlisan.spongesoverhaul.blocks.blockEntities.SpongeBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;

import static com.orlisan.spongesoverhaul.SpongesOverhaul.LOGGER;

public class CustomSponges extends Block implements EntityBlock {

    protected static final Direction[] ALL_DIRECTIONS = Direction.values();

    protected Block type = null;
    protected TagKey<Block> types = null;
    protected final boolean isATag;
    protected boolean isAClass = false;
    protected final Item onOutput;
    protected final Block WET_SPONGE;
    protected Class<?> fluidClass = null;
    protected int CUSTOM_COUNT = 0;
    protected int CUSTOM_DEPTH = 0;
    protected boolean hasCustomConfigurations = false;

    public CustomSponges(Properties properties, Class<?> fluidClass, Item onOutput, Block wetSponge, int... configurazioni) {
        super(properties);
        settaConfigurazioniCustom(configurazioni);
        isATag = false;
        this.onOutput = onOutput;
        this.WET_SPONGE = wetSponge;
        this.fluidClass = fluidClass;
        this.isAClass = true;
    }

    public CustomSponges(Properties properties, Class<?> fluidClass, Item onOutput, Block wetSponge) {
        super(properties);
        isATag = false;
        this.onOutput = onOutput;
        this.WET_SPONGE = wetSponge;
        this.fluidClass = fluidClass;
        this.isAClass = true;
    }

    public CustomSponges(Properties properties, TagKey<Block> types, Item onOutput, Block wetSponge, int... customCountAndDepth) {
        super(properties);
        settaConfigurazioniCustom(customCountAndDepth);
        isATag = true;
        this.types = types;
        this.onOutput = onOutput;
        this.WET_SPONGE = wetSponge;
    }

    public CustomSponges(Properties properties, TagKey<Block> types, Item onOutput, Block wetSponge) {
        super(properties);
        isATag = true;
        this.types = types;
        this.onOutput = onOutput;
        this.WET_SPONGE = wetSponge;
    }

    public CustomSponges(Properties properties, Block type, Item onOutput, Block wetSponge, int... configurazioni) {
        super(properties);
        settaConfigurazioniCustom(configurazioni);
        isATag = false;
        this.type = type;
        this.onOutput = onOutput;
        this.WET_SPONGE = wetSponge;
    }

    public CustomSponges(Properties properties, Block type, Item onOutput, Block wetSponge) {
        super(properties);
        isATag = false;
        this.type = type;
        this.onOutput = onOutput;
        this.WET_SPONGE = wetSponge;
    }

    public void settaConfigurazioniCustom(int... configurazioni) {
        if (configurazioni.length >= 3) {
            LOGGER.info("[Sponges Overhaul] Le Configurazioni Specificate non rientrano nei limiti logici, verranno presi in considerazione solo i primi 2");
        } else if (configurazioni.length == 1) {
            throw new IllegalArgumentException("[Sponges Overhaul] Le Configurazioni Specificate sono incomplete");
        }
        if (configurazioni.length >= 2) {
            setCUSTOM_COUNT(configurazioni[0]);
            setCUSTOM_DEPTH(configurazioni[1]);
            hasCustomConfigurations = true;
        }
    }

    public Block getType() {
        return type;
    }

    public void setType(Block type) {
        this.type = type;
    }

    public TagKey<Block> getTypes() {
        return types;
    }

    public void setTypes(TagKey<Block> types) {
        this.types = types;
    }

    public boolean isATag() {
        return isATag;
    }

    ;

    public Item getOutput() {
        return onOutput;
    }

    public Block getWET_SPONGE() {
        return WET_SPONGE;
    }

    ;

    protected void onPlace(final BlockState state, final @NotNull Level level, final @NotNull BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        if (!oldState.is(state.getBlock()) && !movedByPiston) {
            if (level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity blockEntity) {
                blockEntity.startCooldown();
                if (hasCustomConfigurations) {
                    blockEntity.ORIGINAL_MAX_COUNT = CUSTOM_COUNT;
                    blockEntity.ORIGINAL_MAX_DEPTH = CUSTOM_DEPTH;
                    blockEntity.MAX_COUNT = blockEntity.ORIGINAL_MAX_COUNT;
                    blockEntity.MAX_DEPTH = blockEntity.ORIGINAL_MAX_DEPTH;
                }
            }
            for(Direction dir: ALL_DIRECTIONS) {
                if(level.getBlockEntity(pos.relative(dir)) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntity.MAX_COOLDOWN *= 2;
                    blockEntity.resetCooldown();
                    if(level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity myBlockEntity) {
                        myBlockEntity.MAX_COOLDOWN *= 2;
                        myBlockEntity.resetCooldown();
                    }
                }
            }
            ArrayList<CustomSpongeBlockEntity> blockEntities = new ArrayList<>();
            blockEntities.add((CustomSpongeBlockEntity) level.getBlockEntity(pos));
            byte finalUnoX = 0;
            byte finalUnoY = 0;
            byte finalUnoZ = 0;
            int trovati = 0;
            byte uno = 1;
            for (int i = 0; i < 6; i++) {
                uno = (byte) -uno;
                if (i == 0 || i == 1) {
                    BlockPos newPos = new BlockPos(pos.getX() + uno, pos.getY(), pos.getZ());
                    if (level.getBlockEntity(newPos) instanceof CustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoX = uno;
                        trovati++;
                    }
                } else if (i == 2 || i == 3) {
                    BlockPos newPos = new BlockPos(pos.getX(), pos.getY() + uno, pos.getZ());
                    if (level.getBlockEntity(newPos) instanceof CustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoY = uno;
                        trovati++;
                    }
                } else {
                    BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + uno);
                    if (level.getBlockEntity(newPos) instanceof CustomSpongeBlockEntity blockEntity) {
                        blockEntities.add(blockEntity);
                        blockEntity.resetCooldown();
                        finalUnoZ = uno;
                        trovati++;
                    }
                }

            }
            if (finalUnoZ != 0 && finalUnoY != 0 && finalUnoX != 0) {
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY() + finalUnoY, pos.getZ() + finalUnoZ)) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY(), pos.getZ() + finalUnoZ)) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + finalUnoY, pos.getZ() + finalUnoZ)) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
                if (level.getBlockEntity(new BlockPos(pos.getX() + finalUnoX, pos.getY() + finalUnoY, pos.getZ())) instanceof CustomSpongeBlockEntity blockEntity) {
                    blockEntities.add(blockEntity);
                    blockEntity.resetCooldown();
                    trovati++;
                }
            }
            if (trovati == 7) {
                ArrayList<BlockPos> bigCubePos = new ArrayList<BlockPos>();
                for (CustomSpongeBlockEntity blockEntity : blockEntities) {
                    blockEntity.stopCooldown();
                    bigCubePos.add(blockEntity.getBlockPos());
                    blockEntity.moltiplicaValori();
                    blockEntity.isInABigCube = true;
                }
                for (CustomSpongeBlockEntity blockEntity : blockEntities) {
                    blockEntity.bigCubePos = bigCubePos;
                }
                this.tryAbsorbWater(level, pos);
            }
        }
    }

    protected void neighborChanged(final @NotNull BlockState state, final @NotNull Level level, final @NotNull BlockPos pos, final @NotNull Block block, final @Nullable Orientation orientation, final boolean movedByPiston) {
        if (level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity blockEntity) {
            if (blockEntity.isCooldownFinished()) this.tryAbsorbWater(level, pos);
        }
        super.neighborChanged(state, level, pos, block, orientation, movedByPiston);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (type == SpongeBlockEntities.CUSTOM_SPONGE_BLOCK_ENTITY) {
            return (BlockEntityTicker<T>) (BlockEntityTicker<@NotNull CustomSpongeBlockEntity>)
                    (lvl, pos, st, be) -> be.tick(lvl, pos, st);
        }
        return null;
    }

    public void tryAbsorbWater(final Level level, final BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity blockEntity)) return;

        boolean absorbed = false;

        if (blockEntity.isInABigCube && blockEntity.bigCubePos != null) {
            for (BlockPos cubePos : blockEntity.bigCubePos) {
                if (this.removeWaterBreadthFirstSearch(level, cubePos)) absorbed = true;
            }
            if (absorbed) {
                for (BlockPos cubePos : blockEntity.bigCubePos) {
                    level.setBlock(cubePos, WET_SPONGE.defaultBlockState(), 2);
                }
                level.playSound((Entity) null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        } else {
            if (this.removeWaterBreadthFirstSearch(level, pos)) {
                level.setBlock(pos, WET_SPONGE.defaultBlockState(), 2);
                level.playSound((Entity) null, pos, SoundEvents.SPONGE_ABSORB, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    protected boolean removeWaterBreadthFirstSearch(final Level level, final BlockPos startPos) {
        if (!(level.getBlockEntity(startPos) instanceof CustomSpongeBlockEntity customBlockEntity)) return false;
        return BlockPos.breadthFirstTraversal(startPos, customBlockEntity.MAX_DEPTH, customBlockEntity.MAX_COUNT, (pos, consumer) -> {
            for (Direction direction : ALL_DIRECTIONS) {
                consumer.accept(pos.relative(direction));
            }

        }, (pos) -> removeThing(level, startPos, pos)

        ) > 1;
    }
    public BlockPos.TraversalNodeStatus removeThing(Level level, BlockPos startPos, BlockPos pos) {
        if (!pos.equals(startPos)) {
            BlockState state = level.getBlockState(pos);
            FluidState fluidState = level.getFluidState(pos);
            if (!isATag && !isAClass) {
                if (state.is(type)) {
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    //LOGGER.info("Trovato  oggetto {} a {} e sostituito con l'aria", type, pos);
                } else {

                    //LOGGER.info("Non è stato trovato nessuno oggetto di tipo {} alla posizione {}", type, pos);
                    if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT) || state.is(Blocks.SEAGRASS) || state.is(Blocks.TALL_SEAGRASS)) {
                        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                        dropResources(state, level, pos, blockEntity);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    } else {
                        return BlockPos.TraversalNodeStatus.SKIP;
                    }
                }
            } else if (isATag) {
                if (state.is(types)) {
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                  //  LOGGER.info("Trovato  oggetto {} a {} e sostituito con l'aria", types, pos);
                } else {
                    //LOGGER.info("Non è stato trovato nessuno oggetto di tipo {} alla posizione {}", types, pos);
                    if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT) || state.is(Blocks.SEAGRASS) || state.is(Blocks.TALL_SEAGRASS)) {
                        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                        dropResources(state, level, pos, blockEntity);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    } else {
                        return BlockPos.TraversalNodeStatus.SKIP;
                    }
                }
            } else {
                if (fluidClass.isInstance(fluidState.getType()) || fluidClass.isInstance(state.getBlock())) {
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    //LOGGER.info("Trovato  oggetto {} a {} e sostituito con l'aria", fluidClass, pos);
                } else {
                   // LOGGER.info("Non è stato trovato nessuno oggetto di tipo {} alla posizione {}", fluidClass.getSimpleName(), pos);
                    if (state.is(Blocks.KELP) || state.is(Blocks.KELP_PLANT) ||
                            state.is(Blocks.SEAGRASS) || state.is(Blocks.TALL_SEAGRASS)) {
                        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
                        dropResources(state, level, pos, blockEntity);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    } else {
                        return BlockPos.TraversalNodeStatus.SKIP;
                    }
                }

            }

        }
        return BlockPos.TraversalNodeStatus.ACCEPT;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos worldPosition, @NotNull BlockState blockState) {
        return new CustomSpongeBlockEntity(worldPosition, blockState);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(final Level level, final @NotNull BlockPos pos, final @NotNull BlockState state, final @NotNull Player player) {
        if (level.getBlockEntity(pos) instanceof CustomSpongeBlockEntity blockEntity) {
            if (blockEntity.isInABigCube) {
                ArrayList<BlockPos> copy = new ArrayList<>(blockEntity.bigCubePos);
                for (BlockPos entityPos : copy) {
                    if (level.getBlockEntity(entityPos) instanceof CustomSpongeBlockEntity otherBlockEntity) {
                        otherBlockEntity.isInABigCube = false;
                        otherBlockEntity.MAX_COUNT = otherBlockEntity.ORIGINAL_MAX_COUNT;
                        otherBlockEntity.MAX_DEPTH = otherBlockEntity.ORIGINAL_MAX_DEPTH;
                        otherBlockEntity.bigCubePos = null;
                    }
                }
            }
            blockEntity.bigCubePos = null;
        }
        return super.playerWillDestroy(level, pos, state, player);
    }

    public int getCUSTOM_DEPTH() {
        return CUSTOM_DEPTH;
    }

    public void setCUSTOM_DEPTH(int CUSTOM_DEPTH) {
        this.CUSTOM_DEPTH = CUSTOM_DEPTH;
    }

    public int getCUSTOM_COUNT() {
        return CUSTOM_COUNT;
    }

    public void setCUSTOM_COUNT(int CUSTOM_COUNT) {
        this.CUSTOM_COUNT = CUSTOM_COUNT;
    }
}
