package com.teamacronymcoders.contenttweaker.modules.vanilla;

import com.teamacronymcoders.base.items.ItemCustomRecord;
import com.teamacronymcoders.base.registrysystem.ItemRegistry;
import com.teamacronymcoders.base.registrysystem.SoundEventRegistry;
import com.teamacronymcoders.base.sound.CustomSoundEvent;
import com.teamacronymcoders.base.util.Coloring;
import com.teamacronymcoders.contenttweaker.ContentTweaker;
import com.teamacronymcoders.contenttweaker.api.ctobjects.blockmaterial.IBlockMaterialDefinition;
import com.teamacronymcoders.contenttweaker.api.ctobjects.color.CTColor;
import com.teamacronymcoders.contenttweaker.modules.vanilla.blocks.BlockRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.fluids.FluidRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.functions.IItemStackSupplier;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.CreativeTabRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ICreativeTab;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.ItemRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.items.food.ItemFoodRepresentation;
import com.teamacronymcoders.contenttweaker.modules.vanilla.tileentity.TileEntityRepresentation;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.Optional;

@ZenRegister
@ZenClass("mods.contenttweaker.VanillaFactory")
public class VanillaFactory {
    @ZenMethod
    public static BlockRepresentation createBlock(String unlocalizedName, IBlockMaterialDefinition material) {
        BlockRepresentation blockRepresentation = new BlockRepresentation();
        blockRepresentation.setUnlocalizedName(unlocalizedName);
        blockRepresentation.setBlockMaterial(material);
        return blockRepresentation;
    }

    @ZenMethod
    public static ItemRepresentation createItem(String unlocalizedName) {
        ItemRepresentation itemRepresentation = new ItemRepresentation();
        itemRepresentation.setUnlocalizedName(unlocalizedName);
        return itemRepresentation;
    }

    @ZenMethod
    public static ItemFoodRepresentation createItemFood(String unlocalizedName, int healAmount) {
        ItemFoodRepresentation itemRepresentation = new ItemFoodRepresentation();
        itemRepresentation.setUnlocalizedName(unlocalizedName);
        itemRepresentation.setHealAmount(healAmount);
        return itemRepresentation;
    }

    @ZenMethod
    public static ICreativeTab createCreativeTab(String unlocalizedName, final IItemStack iItemStack) {
        CreativeTabRepresentation creativeTab = new CreativeTabRepresentation();
        creativeTab.setUnlocalizedName(unlocalizedName);
        creativeTab.setIconStackSupplier(() -> iItemStack);
        return creativeTab;
    }

    @ZenMethod
    public static ICreativeTab createCreativeTab(String unlocalizedName, final ItemRepresentation iItem) {
        return createCreativeTab(unlocalizedName, () -> new MCItemStack(new ItemStack(iItem.getInternal())));
    }

    @ZenMethod
    public static ICreativeTab createCreativeTab(String unlocalizedName, final BlockRepresentation iBlock) {
        return createCreativeTab(unlocalizedName, () -> new MCItemStack(new ItemStack(iBlock.getInternal())));
    }

    @ZenMethod
    public static ICreativeTab createCreativeTab(String unlocalizedName, IItemStackSupplier supplyItemStack) {
        CreativeTabRepresentation creativeTab = new CreativeTabRepresentation();
        creativeTab.setUnlocalizedName(unlocalizedName);
        creativeTab.setIconStackSupplier(supplyItemStack);
        return creativeTab;
    }

    @ZenMethod
    public static FluidRepresentation createFluid(String unlocalizedName, int color) {
        return createFluid(unlocalizedName, CTColor.fromInt(color));
    }

    @ZenMethod
    public static FluidRepresentation createFluid(String unlocalizedName, CTColor color) {
        return new FluidRepresentation(unlocalizedName, color.getIntColor());
    }

    @ZenMethod
    public static TileEntityRepresentation createTileEntity(String name) {
        return new TileEntityRepresentation(name);
    }

    @ZenMethod
    public static void createRecord(String name) {
        createRecord(name, null);
    }

    @ZenMethod
    public static void createRecord(String name, CTColor color) {
        final SoundEventRegistry registry = ContentTweaker.instance.getRegistry(SoundEventRegistry.class, "SOUND_EVENT");
        final ResourceLocation soundName = new ResourceLocation(ContentTweaker.MOD_ID, name);
        SoundEvent soundEvent = Optional.ofNullable(registry.get(soundName))
                .orElseGet(() -> {
                    SoundEvent newSoundEvent = new CustomSoundEvent(soundName, true);
                    registry.register(soundName, newSoundEvent);
                    return newSoundEvent;
                });

        ContentTweaker.instance.getRegistry(ItemRegistry.class, "ITEM")
                .register(new ItemCustomRecord(name, soundEvent, Optional.ofNullable(color)
                        .map(CTColor::getInternal)
                        .orElse(null)));
    }
}
