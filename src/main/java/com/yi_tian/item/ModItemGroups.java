package com.yi_tian.item;

import com.yi_tian.YitianMod;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup TUTORIAL_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(YitianMod.MOD_ID, "tutorial_group"),
            ItemGroup.create(null, -1).displayName(Text.translatable("itemGroup.tutorial_group"))
                    .icon(() -> new ItemStack(ModItems.BONE_SWORD))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.BONE_SWORD);
                        entries.add(ModItems.BONE_AXE);
                        entries.add(ModItems.BONE_PICKAXE);
                        entries.add(ModItems.BONE_SHOVEL);
                        entries.add(ModItems.BONE_HELMET);
                        entries.add(ModItems.BONE_BOOTS);
                        entries.add(ModItems.BONE_LEGGING);
                        entries.add(ModItems.BONE_CHEST);
                        entries.add(ModItems.BONE_COMBINE);
                        entries.add(ModItems.BONE_STAFF);
                        entries.add(ModItems.BONE_STAFF2);
                        entries.add(ModItems.BONE_STAFF3);
                        entries.add(ModItems.BONE_STAFF4);
                        entries.add(ModItems.BATON);
                        entries.add(ModItems.XTLITEM);
                        entries.add(ModItems.STLITEM);
                        entries.add(ModItems.CPPPITEM);
                        entries.add(ModItems.NCITEM);
                        entries.add(ModItems.NCTOTEM);
                        entries.add(ModItems.CPPPTOTEM);
                    }).build());
    public static void registerModItemGroups() {
        YitianMod.LOGGER.info("Registering Item Groups");
    }
}
