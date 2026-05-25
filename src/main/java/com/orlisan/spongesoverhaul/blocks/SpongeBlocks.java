package com.orlisan.spongesoverhaul.blocks;

import com.orlisan.spongesoverhaul.SpongesOverhaul;
import com.orlisan.spongesoverhaul.blocks.custom.*;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.WaterFluid;

import java.util.ArrayList;

public class SpongeBlocks {

    public static Block WET_WATER_SPONGE_BLOCK, WATER_SPONGE_BLOCK;
    public static Block WET_LAVA_SPONGE_BLOCK, LAVA_SPONGE_BLOCK;
    public static Block WET_FIRE_SPONGE_BLOCK, FIRE_SPONGE_BLOCK;
    public static Block WET_MOB_SPONGE_BLOCK, MOB_SPONGE_BLOCK;
    public static Block WET_SNOW_SPONGE_BLOCK, SNOW_SPONGE_BLOCK;
    public static Block WET_DIAMOND_SPONGE_BLOCK, DIAMOND_SPONGE_BLOCK;
    public static Block WET_LINGERING_POTION_SPONGE_BLOCK, LINGERING_POTION_SPONGE_BLOCK;

    public static ArrayList<Block> spongeBlocks = new ArrayList<>();
    public static ArrayList<Block> simpleSpongeBlocks = new ArrayList<>();
    public static BlockBehaviour.Properties spongeProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.SPONGE);
    public static BlockBehaviour.Properties wetSpongeProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.WET_SPONGE);
    private SpongeBlocks() {}

    private static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, name));
    }
    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, name));
    }
    private static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, name);
    }

    public static void register() {
        // WATER
        WET_WATER_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("wet_sponge_block"),
                new CustomWetSponges(wetSpongeProperties.setId(blockKey("wet_sponge_block")), ParticleTypes.DRIPPING_WATER));
        WATER_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("sponge_block"),
                new CustomSponges(spongeProperties.setId(blockKey("sponge_block")), WaterFluid.class, Items.WATER_BUCKET, WET_WATER_SPONGE_BLOCK));
        spongeBlocks.add(WATER_SPONGE_BLOCK);

        // LAVA
        WET_LAVA_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("wet_lava_sponge_block"),
                new CustomWetSponges(wetSpongeProperties.setId(blockKey("wet_lava_sponge_block")), ParticleTypes.DRIPPING_LAVA));
        LAVA_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("lava_sponge_block"),
                new CustomSponges(spongeProperties.setId(blockKey("lava_sponge_block")), LavaFluid.class, Items.LAVA_BUCKET, WET_LAVA_SPONGE_BLOCK));
        spongeBlocks.add(LAVA_SPONGE_BLOCK);

        // FIRE
        WET_FIRE_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("wet_fire_sponge_block"),
                new CustomWetSponges(wetSpongeProperties.setId(blockKey("wet_fire_sponge_block")), ParticleTypes.FLAME));
        FIRE_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("fire_sponge_block"),
                new SimpleCustomSponges(spongeProperties.setId(blockKey("fire_sponge_block")), BaseFireBlock.class, Items.FIRE_CHARGE, WET_FIRE_SPONGE_BLOCK));
        simpleSpongeBlocks.add(FIRE_SPONGE_BLOCK);

        // MOB
        WET_MOB_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("wet_mob_sponge_block"),
                new WetMobSponge(wetSpongeProperties.setId(blockKey("wet_mob_sponge_block")), ParticleTypes.ANGRY_VILLAGER));
        MOB_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("mob_sponge_block"),
                new MobSponge(spongeProperties.setId(blockKey("mob_sponge_block")), EntityTypeTags.UNDEAD, Items.BONE, WET_MOB_SPONGE_BLOCK));
        simpleSpongeBlocks.add(MOB_SPONGE_BLOCK);

        //SNOW
        WET_SNOW_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("wet_snow_sponge_block"),
                new CustomWetSponges(wetSpongeProperties.setId(blockKey("wet_snow_sponge_block")), ParticleTypes.ITEM_SNOWBALL));
        SNOW_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("snow_sponge_block"),
                new SimpleCustomSponges(spongeProperties.setId(blockKey("snow_sponge_block")), BlockTags.SNOW, Items.SNOWBALL, WET_SNOW_SPONGE_BLOCK));
        simpleSpongeBlocks.add(SNOW_SPONGE_BLOCK);

        //DIAMOND
        WET_DIAMOND_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("wet_diamond_sponge_block"),
                new CustomWetSponges(wetSpongeProperties.setId(blockKey("wet_diamond_sponge_block")), ParticleTypes.DRIPPING_WATER));
        DIAMOND_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("diamond_sponge_block"),
                new CustomSponges(spongeProperties.setId(blockKey("diamond_sponge_block")), WaterFluid.class, Items.WATER_BUCKET, WET_DIAMOND_SPONGE_BLOCK, 16, 216));
        spongeBlocks.add(DIAMOND_SPONGE_BLOCK);

        //LINGERING POTION
        WET_LINGERING_POTION_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("wet_lingering_potion_sponge_block"),
                new LingeringPotionWetSponge(wetSpongeProperties.setId(blockKey("wet_lingering_potion_sponge_block"))));
        LINGERING_POTION_SPONGE_BLOCK = Registry.register(BuiltInRegistries.BLOCK, id("lingering_potion_sponge_block"),
                new LingeringPotionSponge(spongeProperties.setId(blockKey("lingering_potion_sponge_block")), Items.LINGERING_POTION, WET_LINGERING_POTION_SPONGE_BLOCK));
        simpleSpongeBlocks.add(LINGERING_POTION_SPONGE_BLOCK);

        // ITEMS
        BlockItem dryWaterItem   = registerBlockItem("sponge_block",         WATER_SPONGE_BLOCK,   false);
        BlockItem dryLavaItem    = registerBlockItem("lava_sponge_block",     LAVA_SPONGE_BLOCK,    true);
        BlockItem dryFireItem    = registerBlockItem("fire_sponge_block",     FIRE_SPONGE_BLOCK,    true);
        BlockItem dryMobItem     = registerBlockItem("mob_sponge_block",      MOB_SPONGE_BLOCK,     false);
        BlockItem drySnowItem     = registerBlockItem("snow_sponge_block",      SNOW_SPONGE_BLOCK,     false);
        BlockItem dryDiamondItem   = registerBlockItem("diamond_sponge_block",  DIAMOND_SPONGE_BLOCK,   false);
        BlockItem dryLingeringPotionItem   = registerBlockItem("lingering_potion_sponge_block",  LINGERING_POTION_SPONGE_BLOCK,   false);
        registerBlockItem("wet_sponge_block",      WET_WATER_SPONGE_BLOCK, false);
        registerBlockItem("wet_lava_sponge_block", WET_LAVA_SPONGE_BLOCK,  true);
        registerBlockItem("wet_fire_sponge_block", WET_FIRE_SPONGE_BLOCK,  true);
        registerBlockItem("wet_snow_sponge_block", WET_SNOW_SPONGE_BLOCK,  false);
        registerBlockItem("wet_diamond_sponge_block", WET_DIAMOND_SPONGE_BLOCK,  false);
        registerBlockItem("wet_mob_sponge_block",  WET_MOB_SPONGE_BLOCK,   false);
        registerBlockItem("wet_lingering_potion_sponge_block",  WET_LINGERING_POTION_SPONGE_BLOCK,   false);

        ((CustomWetSponges) WET_WATER_SPONGE_BLOCK).setDryVersion(dryWaterItem);
        ((CustomWetSponges) WET_LAVA_SPONGE_BLOCK).setDryVersion(dryLavaItem);
        ((CustomWetSponges) WET_FIRE_SPONGE_BLOCK).setDryVersion(dryFireItem);
        ((CustomWetSponges) WET_MOB_SPONGE_BLOCK).setDryVersion(dryMobItem);
        ((CustomWetSponges) WET_SNOW_SPONGE_BLOCK).setDryVersion(drySnowItem);
        ((CustomWetSponges) WET_DIAMOND_SPONGE_BLOCK).setDryVersion(dryDiamondItem);
        ((CustomWetSponges) WET_LINGERING_POTION_SPONGE_BLOCK).setDryVersion(dryLingeringPotionItem);
    }

    private static BlockItem registerBlockItem(String name, Block block, boolean fireResistant) {
        Item.Properties props = new Item.Properties().setId(itemKey(name));
        if (fireResistant) props = props.fireResistant();
        BlockItem item = new BlockItem(block, props);
        Registry.register(BuiltInRegistries.ITEM, id(name), item);
        return item;
    }
}

