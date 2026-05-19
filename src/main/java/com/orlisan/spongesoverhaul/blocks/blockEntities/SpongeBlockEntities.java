package com.orlisan.spongesoverhaul.blocks.blockEntities;

import com.orlisan.spongesoverhaul.SpongesOverhaul;
import com.orlisan.spongesoverhaul.blocks.SpongeBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;


public class SpongeBlockEntities {
    public static final BlockEntityType<@NotNull CustomSpongeBlockEntity> CUSTOM_SPONGE_BLOCK_ENTITY =
            Registry.register(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "custom_sponge_block_entity"),
                    FabricBlockEntityTypeBuilder.create(CustomSpongeBlockEntity::new, SpongeBlocks.SPONGE_BLOCK).build()
            );

    public static void register() {}
}