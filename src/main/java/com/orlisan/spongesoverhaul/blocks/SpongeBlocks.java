package com.orlisan.spongesoverhaul.blocks;

import com.orlisan.spongesoverhaul.SpongesOverhaul;
import com.orlisan.spongesoverhaul.blocks.custom.CustomSponges;
import com.orlisan.spongesoverhaul.blocks.custom.CustomWetSponges;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static net.minecraft.resources.Identifier.fromNamespaceAndPath;

public class SpongeBlocks {

    public static Block WET_SPONGE_BLOCK;
    public static Block SPONGE_BLOCK;

    public static void register() {

        Identifier wetId = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "wet_sponge_block");
        Identifier dryId = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "sponge_block");

        WET_SPONGE_BLOCK = Registry.register(
                BuiltInRegistries.BLOCK,
                wetId,
                new CustomWetSponges(BlockBehaviour.Properties.of())
        );

        SPONGE_BLOCK = Registry.register(
                BuiltInRegistries.BLOCK,
                dryId,
                new CustomSponges(
                        BlockBehaviour.Properties.of(),
                        Blocks.WATER,
                        Items.WATER_BUCKET,
                        WET_SPONGE_BLOCK
                )
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                wetId,
                new BlockItem(WET_SPONGE_BLOCK, new Item.Properties())
        );

        Registry.register(
                BuiltInRegistries.ITEM,
                dryId,
                new BlockItem(SPONGE_BLOCK, new Item.Properties())
        );

        ((CustomWetSponges) WET_SPONGE_BLOCK).setDryVersion(
                new BlockItem(SPONGE_BLOCK, new Item.Properties())
        );
    }
}