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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.WaterFluid;

public class SpongeBlocks {

    public static Block WET_WATER_SPONGE_BLOCK;
    public static Block WATER_SPONGE_BLOCK;

    public static Block WET_LAVA_SPONGE_BLOCK;
    public static Block LAVA_SPONGE_BLOCK;

    private SpongeBlocks() {
    }

    public static void register() {
        Identifier wet_water_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "wet_sponge_block");
        Identifier dry_water_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "sponge_block");

        Identifier wet_lava_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "wet_lava_sponge_block");
        Identifier dry_lava_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "lava_sponge_block");

        ResourceKey<Block> wetWaterKey = ResourceKey.create(Registries.BLOCK, wet_water_id);
        ResourceKey<Block> dryWaterKey = ResourceKey.create(Registries.BLOCK, dry_water_id);

        ResourceKey<Block> wetLavaKey = ResourceKey.create(Registries.BLOCK, wet_lava_id);
        ResourceKey<Block> dryLavaKey = ResourceKey.create(Registries.BLOCK, dry_lava_id);

        ResourceKey<Item> wetWaterItemKey = ResourceKey.create(Registries.ITEM, wet_water_id);
        ResourceKey<Item> dryWaterItemKey = ResourceKey.create(Registries.ITEM, dry_water_id);
        ResourceKey<Item> wetLavaItemKey = ResourceKey.create(Registries.ITEM, wet_lava_id);
        ResourceKey<Item> dryLavaItemKey = ResourceKey.create(Registries.ITEM, dry_lava_id);

        WET_WATER_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, wet_water_id,
                new CustomWetSponges(BlockBehaviour.Properties.of().setId(wetWaterKey)));

        WATER_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, dry_water_id,
                new CustomSponges(BlockBehaviour.Properties.of().setId(dryWaterKey),
                        WaterFluid.class, Items.WATER_BUCKET, WET_WATER_SPONGE_BLOCK));

        WET_LAVA_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, wet_lava_id,
                new CustomWetSponges(BlockBehaviour.Properties.of().setId(wetLavaKey)));

        LAVA_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, dry_lava_id,
                new CustomSponges(BlockBehaviour.Properties.of().setId(dryLavaKey),
                        LavaFluid.class, Items.LAVA_BUCKET, WET_LAVA_SPONGE_BLOCK));

        Registry.register(BuiltInRegistries.ITEM, wet_water_id,
                new BlockItem(WET_WATER_SPONGE_BLOCK, new Item.Properties().setId(wetWaterItemKey)));
        Registry.register(BuiltInRegistries.ITEM, wet_lava_id,
                new BlockItem(WET_LAVA_SPONGE_BLOCK, new Item.Properties().setId(wetLavaItemKey).fireResistant()));

        BlockItem dryBlockItem = new BlockItem(WATER_SPONGE_BLOCK, new Item.Properties().setId(dryWaterItemKey));
        Registry.register(BuiltInRegistries.ITEM, dry_water_id, dryBlockItem);

        BlockItem dryLavaBlockItem = new BlockItem(LAVA_SPONGE_BLOCK, new Item.Properties().setId(dryLavaItemKey).fireResistant());
        Registry.register(BuiltInRegistries.ITEM, dry_lava_id, dryLavaBlockItem);

        ((CustomWetSponges) WET_WATER_SPONGE_BLOCK).setDryVersion(dryBlockItem);
        ((CustomWetSponges) WET_LAVA_SPONGE_BLOCK).setDryVersion(dryLavaBlockItem);

    }
}