package com.yi_tian.datagen;

import com.yi_tian.enchantment.ModEnchantments;
import com.yi_tian.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModENUSLanProvider extends FabricLanguageProvider {


    public ModENUSLanProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.BONE_SWORD, "骨剑");
        translationBuilder.add(ModItems.BONE_PICKAXE, "骨镐");
        translationBuilder.add(ModItems.BONE_AXE, "骨斧");
        translationBuilder.add(ModItems.BONE_SHOVEL, "骨铲");
        translationBuilder.add(ModItems.BONE_HELMET, "骨制头盔");
        translationBuilder.add(ModItems.BONE_CHEST, "骨制胸甲");
        translationBuilder.add(ModItems.BONE_LEGGING, "骨制护腿");
        translationBuilder.add(ModItems.BONE_BOOTS, "骨制靴子");
        translationBuilder.add(ModItems.BONE_COMBINE, "组合骨制工具");
        translationBuilder.add(ModItems.BONE_STAFF, "骨神法杖");
        translationBuilder.add(ModItems.BONE_STAFF2, "骨神法杖");
        translationBuilder.add(ModItems.BONE_STAFF3, "骨神法杖");
        translationBuilder.add(ModItems.BONE_STAFF4, "骨神法杖");
        translationBuilder.add(ModItems.STLITEM, "斯大林之星");
        translationBuilder.add(ModItems.XTLITEM, "佛教符号");
        translationBuilder.add(ModItems.NCITEM, "铁十字");
        translationBuilder.add(ModItems.CPPPITEM, "C!P!P!P!");
        translationBuilder.add(ModItems.CPPPTOTEM, "巨熊图腾");
        translationBuilder.add(ModItems.NCTOTEM, "黑鹰图腾");
        translationBuilder.add(ModEnchantments.MOBILE, "机动");
        translationBuilder.add(ModEnchantments.CONTINUOUS, "连射");
        translationBuilder.add(ModEnchantments.LOYAL, "忠！诚！");
        translationBuilder.add(ModEnchantments.STICK, "一秒六棍");
        translationBuilder.add(ModItems.BATON, "警棍");
        translationBuilder.add(ModEnchantments.HEAVY, "重拳出击");
    }
}
