package com.yi_tian.datagen;

import com.yi_tian.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.ArmorItem;

public class ModModelsProvider extends FabricModelProvider {
    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BONE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_AXE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_PICKAXE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_SHOVEL,Models.HANDHELD);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_BOOTS);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_CHEST);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_LEGGING);
        itemModelGenerator.registerArmor((ArmorItem) ModItems.BONE_HELMET);
        itemModelGenerator.register(ModItems.BONE_COMBINE,Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_STAFF,Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_STAFF2,Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_STAFF3,Models.HANDHELD);
        itemModelGenerator.register(ModItems.BONE_STAFF4,Models.HANDHELD);
        itemModelGenerator.register(ModItems.BATON,Models.HANDHELD);
        itemModelGenerator.register(ModItems.STLITEM,Models.GENERATED);
        itemModelGenerator.register(ModItems.XTLITEM,Models.GENERATED);
        itemModelGenerator.register(ModItems.CPPPITEM,Models.GENERATED);
        itemModelGenerator.register(ModItems.NCITEM,Models.GENERATED);
        itemModelGenerator.register(ModItems.NCTOTEM,Models.GENERATED);
        itemModelGenerator.register(ModItems.CPPPTOTEM,Models.GENERATED);
    }
}
