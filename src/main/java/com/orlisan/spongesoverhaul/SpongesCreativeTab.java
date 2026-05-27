package com.orlisan.spongesoverhaul;

import com.orlisan.spongesoverhaul.blocks.SpongeBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class SpongesCreativeTab {
    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath(SpongesOverhaul.MODID, "sponges_overhaul_tab"),
                CreativeModeTab.builder(CreativeModeTab.Row.TOP, 7)
                        .title(Component.translatable("itemGroup.spongesoverhaul.sponges_overhaul_tab"))
                        .icon(() -> new ItemStack(SpongeBlocks.LAVA_SPONGE_BLOCK))
                        .displayItems((params, output) -> {
                            output.accept(SpongeBlocks.WATER_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.WET_WATER_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.LAVA_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.WET_LAVA_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.FIRE_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.WET_FIRE_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.MOB_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.WET_MOB_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.SNOW_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.WET_SNOW_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.DIAMOND_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.WET_DIAMOND_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.LINGERING_POTION_SPONGE_BLOCK);
                            output.accept(SpongeBlocks.WET_LINGERING_POTION_SPONGE_BLOCK);
                        })
                        .build()
        );
    }
}