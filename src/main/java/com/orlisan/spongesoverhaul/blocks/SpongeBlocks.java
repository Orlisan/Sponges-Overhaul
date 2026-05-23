package com.orlisan.spongesoverhaul.blocks;

import com.orlisan.spongesoverhaul.SpongesOverhaul;
import com.orlisan.spongesoverhaul.blocks.custom.CustomSponges;
import com.orlisan.spongesoverhaul.blocks.custom.CustomWetSponges;

import com.orlisan.spongesoverhaul.blocks.custom.SimpleCustomSponges;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.WaterFluid;

import java.util.ArrayList;

public class SpongeBlocks {

    public static Block WET_WATER_SPONGE_BLOCK;
    public static Block WATER_SPONGE_BLOCK;

    public static Block WET_LAVA_SPONGE_BLOCK;
    public static Block LAVA_SPONGE_BLOCK;

    public static Block WET_FIRE_SPONGE_BLOCK;
    public static Block FIRE_SPONGE_BLOCK;

    public static ArrayList<Block> spongeBlocks = new ArrayList<>();
    public static ArrayList<Block> simpleSpongeBlocks = new ArrayList<Block>();


    private SpongeBlocks() {}

    public static void register() {
        Identifier wet_water_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "wet_sponge_block");
        Identifier dry_water_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "sponge_block");

        Identifier wet_lava_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "wet_lava_sponge_block");
        Identifier dry_lava_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "lava_sponge_block");

        Identifier wet_fire_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "wet_fire_sponge_block");
        Identifier dry_fire_id = Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "fire_sponge_block");

        ResourceKey<Block> wetWaterKey = ResourceKey.create(Registries.BLOCK, wet_water_id);
        ResourceKey<Block> dryWaterKey = ResourceKey.create(Registries.BLOCK, dry_water_id);

        ResourceKey<Block> wetLavaKey = ResourceKey.create(Registries.BLOCK, wet_lava_id);
        ResourceKey<Block> dryLavaKey = ResourceKey.create(Registries.BLOCK, dry_lava_id);

        ResourceKey<Block> wetFireKey = ResourceKey.create(Registries.BLOCK, wet_fire_id);
        ResourceKey<Block> dryFireKey = ResourceKey.create(Registries.BLOCK, dry_fire_id);

        ResourceKey<Item> wetWaterItemKey = ResourceKey.create(Registries.ITEM, wet_water_id);
        ResourceKey<Item> dryWaterItemKey = ResourceKey.create(Registries.ITEM, dry_water_id);
        ResourceKey<Item> wetLavaItemKey = ResourceKey.create(Registries.ITEM, wet_lava_id);
        ResourceKey<Item> dryLavaItemKey = ResourceKey.create(Registries.ITEM, dry_lava_id);
        ResourceKey<Item> wetFireItemKey = ResourceKey.create(Registries.ITEM, wet_fire_id);
        ResourceKey<Item> dryFireItemKey = ResourceKey.create(Registries.ITEM, dry_fire_id);

        WET_WATER_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, wet_water_id,
                new CustomWetSponges(BlockBehaviour.Properties.of().setId(wetWaterKey), ParticleTypes.DRIPPING_WATER));

        WATER_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, dry_water_id,
                new CustomSponges(BlockBehaviour.Properties.of().setId(dryWaterKey),
                        WaterFluid.class, Items.WATER_BUCKET, WET_WATER_SPONGE_BLOCK));
        spongeBlocks.add(WATER_SPONGE_BLOCK);


        WET_LAVA_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, wet_lava_id,
                new CustomWetSponges(BlockBehaviour.Properties.of().setId(wetLavaKey), ParticleTypes.DRIPPING_LAVA));

        LAVA_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, dry_lava_id,
                new CustomSponges(BlockBehaviour.Properties.of().setId(dryLavaKey),
                        LavaFluid.class, Items.LAVA_BUCKET, WET_LAVA_SPONGE_BLOCK));
        spongeBlocks.add(LAVA_SPONGE_BLOCK);


        WET_FIRE_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, wet_fire_id,
                new CustomWetSponges(BlockBehaviour.Properties.of().setId(wetFireKey), ParticleTypes.FLAME));

        FIRE_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, dry_fire_id,
                new SimpleCustomSponges(BlockBehaviour.Properties.of().setId(dryFireKey),
                        BaseFireBlock.class, Items.FIRE_CHARGE, WET_FIRE_SPONGE_BLOCK));

        simpleSpongeBlocks.add(FIRE_SPONGE_BLOCK);


        Registry.register(BuiltInRegistries.ITEM, wet_water_id,
                new BlockItem(WET_WATER_SPONGE_BLOCK, new Item.Properties().setId(wetWaterItemKey)));

        Registry.register(BuiltInRegistries.ITEM, wet_lava_id,
                new BlockItem(WET_LAVA_SPONGE_BLOCK, new Item.Properties().setId(wetLavaItemKey).fireResistant()));

        Registry.register(BuiltInRegistries.ITEM, wet_fire_id,
                new BlockItem(WET_FIRE_SPONGE_BLOCK, new Item.Properties().setId(wetFireItemKey).fireResistant()));

        BlockItem dryBlockItem = new BlockItem(WATER_SPONGE_BLOCK, new Item.Properties().setId(dryWaterItemKey));
        Registry.register(BuiltInRegistries.ITEM, dry_water_id, dryBlockItem);

        BlockItem dryLavaBlockItem = new BlockItem(LAVA_SPONGE_BLOCK, new Item.Properties().setId(dryLavaItemKey).fireResistant());
        Registry.register(BuiltInRegistries.ITEM, dry_lava_id, dryLavaBlockItem);

        BlockItem dryFireBlockItem = new BlockItem(FIRE_SPONGE_BLOCK, new Item.Properties().setId(dryFireItemKey).fireResistant());
        Registry.register(BuiltInRegistries.ITEM, dry_fire_id, dryFireBlockItem);

        ((CustomWetSponges) WET_WATER_SPONGE_BLOCK).setDryVersion(dryBlockItem);
        ((CustomWetSponges) WET_LAVA_SPONGE_BLOCK).setDryVersion(dryLavaBlockItem);
        ((CustomWetSponges) WET_FIRE_SPONGE_BLOCK).setDryVersion(dryFireBlockItem);

    }
}