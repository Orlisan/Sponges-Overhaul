# Sponges Overhaul — Developer API

This document explains how to add a custom sponge to Sponges Overhaul from your own mod.

## Dependency setup

Add Sponges Overhaul as a dependency in your `fabric.mod.json`:

```json
"depends": {
    "spongesoverhaul": "*"
}
```

And in your `build.gradle`, add the jar to your dependencies:

```groovy
dependencies {
    modImplementation files("path/to/sponges-overhaul-1.0.0.jar")
}
```

---

## Sponge types

There are two base classes you can extend or instantiate:

- **`CustomSponges`** — for fluid-based sponges (water, lava). Uses BFS traversal to absorb fluid blocks.
- **`SimpleCustomSponges`** — for block-based or entity-based sponges (fire, snow). Uses a spherical area instead of BFS.

---

## Creating a fluid sponge

Use `CustomSponges` and pass the fluid class as the target:

```java
Block WET_PETROL_SPONGE = Registry.register(BuiltInRegistries.BLOCK,
    Identifier.fromNamespaceAndPath("yourmodid", "wet_petrol_sponge"),
    new CustomWetSponges(
        SpongeBlocks.wetSpongeProperties.setId(
            ResourceKey.create(Registries.BLOCK,
                Identifier.fromNamespaceAndPath("yourmodid", "wet_petrol_sponge"))
        ),
        ParticleTypes.DRIPPING_LAVA, // particle effect
        false                        // does it dry in the Nether?
    )
);

Block PETROL_SPONGE = Registry.register(BuiltInRegistries.BLOCK,
    Identifier.fromNamespaceAndPath("yourmodid", "petrol_sponge"),
    new CustomSponges(
        SpongeBlocks.spongeProperties.setId(
            ResourceKey.create(Registries.BLOCK,
                Identifier.fromNamespaceAndPath("yourmodid", "petrol_sponge"))
        ),
        PetrolFluid.class,   // the fluid class to absorb
        PetrolBucketItem,    // item dropped when the sponge becomes wet
        WET_PETROL_SPONGE    // the corresponding wet sponge block
    )
);

SpongeBlocks.spongeBlocks.add(PETROL_SPONGE);
```

---

## Creating a block sponge

Use `SimpleCustomSponges` and pass the block class or a `TagKey<Block>` as the target:

```java
new SimpleCustomSponges(
    SpongeBlocks.spongeProperties.setId(...),
    MyCustomBlock.class, // or a TagKey<Block>
    MyDropItem,
    WET_MY_SPONGE
);

SpongeBlocks.simpleSpongeBlocks.add(MY_SPONGE);
```

---

## Custom range and capacity

Both `CustomSponges` and `SimpleCustomSponges` accept optional `int` parameters at the end for custom range and capacity:

```java
new CustomSponges(props, PetrolFluid.class, PetrolBucketItem, WET_PETROL_SPONGE, 512, 16);
//                                                                                 ^    ^
//                                                                             count  depth
```

Defaults are `count = 257` and `depth = 6`. These are multiplied by 8× and 4× respectively when the 2×2×2 cube mechanic activates.

---

## Registering the wet sponge's dry version

After registering both blocks as `BlockItem`, call `setDryVersion` on the wet sponge:

```java
BlockItem dryItem = registerBlockItem("petrol_sponge", PETROL_SPONGE, false);
((CustomWetSponges) WET_PETROL_SPONGE).setDryVersion(dryItem);
```

---

## Important: timing

Your registration must happen **before** `SpongeBlockEntities.register()` is called, which happens inside Sponges Overhaul's `onInitialize`. With Fabric this is guaranteed if you declare Sponges Overhaul as a required dependency — Fabric will ensure your `onInitialize` runs first.

If the block is not added to `spongeBlocks` or `simpleSpongeBlocks` before that point, placing the block in the world will cause a crash.