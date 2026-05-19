package com.orlisan.spongesoverhaul.blocks;

import com.orlisan.spongesoverhaul.SpongesOverhaul;
import com.orlisan.spongesoverhaul.blocks.custom.CustomSponges;
import com.orlisan.spongesoverhaul.blocks.custom.CustomWetSponges;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SpongeBlocks {

    public static Block WET_SPONGE_BLOCK;
    public static Block SPONGE_BLOCK;

    public static void register() {

        Identifier wetId = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "wet_sponge_block");
        Identifier dryId = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "sponge_block");

        ResourceKey<Block> wetKey = ResourceKey.create(Registries.BLOCK, wetId);
        ResourceKey<Block> dryKey = ResourceKey.create(Registries.BLOCK, dryId);

        ResourceKey<Item> wetItemKey = ResourceKey.create(Registries.ITEM, wetId);
        ResourceKey<Item> dryItemKey = ResourceKey.create(Registries.ITEM, dryId);

        WET_SPONGE_BLOCK = Registry.register(
                BuiltInRegistries.BLOCK,
                wetId,
                new CustomWetSponges(BlockBehaviour.Properties.of().setId(wetKey))
        );

        SPONGE_BLOCK = Registry.register(
                BuiltInRegistries.BLOCK,
                dryId,
                new CustomSponges(
                        BlockBehaviour.Properties.of().setId(dryKey),
                        Blocks.WATER,
                        Items.WATER_BUCKET,
                        WET_SPONGE_BLOCK
                )
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                wetId,
                new BlockItem(WET_SPONGE_BLOCK, new Item.Properties().setId(wetItemKey))
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                dryId,
                new BlockItem(SPONGE_BLOCK, new Item.Properties().setId(dryItemKey))
        );

        ((CustomWetSponges) WET_SPONGE_BLOCK).setDryVersion(
                new BlockItem(SPONGE_BLOCK, new Item.Properties().setId(dryItemKey))
        );
    }
}