package com.orlisan.spongesoverhaul.blocks.blockEntities;

import com.orlisan.spongesoverhaul.SpongesOverhaul;
import com.orlisan.spongesoverhaul.blocks.SpongeBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;


public class SpongeBlockEntities {
    private SpongeBlockEntities(){}
    public static BlockEntityType<@NotNull CustomSpongeBlockEntity> CUSTOM_SPONGE_BLOCK_ENTITY;
    public static BlockEntityType<@NotNull SimpleCustomSpongeBlockEntity> SIMPLE_CUSTOM_SPONGE_BLOCK_ENTITY;

    public static void register() {
        ResourceKey<BlockEntityType<?>> key = ResourceKey.create(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "custom_sponge_block_entity")
        );

        CUSTOM_SPONGE_BLOCK_ENTITY = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "custom_sponge_block_entity"),
                FabricBlockEntityTypeBuilder.create(
                        CustomSpongeBlockEntity::new,
                        SpongeBlocks.spongeBlocks.toArray(new Block[0])
                ).build()  // <-- passa il key qui
        );

        ResourceKey<BlockEntityType<?>> simple_key = ResourceKey.create(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "simple_custom_sponge_block_entity")
        );

        SIMPLE_CUSTOM_SPONGE_BLOCK_ENTITY = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "simple_custom_sponge_block_entity"),
                FabricBlockEntityTypeBuilder.create(
                        SimpleCustomSpongeBlockEntity::new,
                        SpongeBlocks.simpleSpongeBlocks.toArray(new Block[0])
                ).build()  // <-- passa il key qui
        );
    }
}