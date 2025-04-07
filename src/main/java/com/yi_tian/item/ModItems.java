package com.yi_tian.item;

import com.yi_tian.YitianMod;
import com.yi_tian.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item BONE_SWORD=registerItems("bone_sword", new BoneSwordItem(ModToolMaterials.BONE,3,-2.4f,new Item.Settings()));
    public static Item BONE_AXE=registerItems("bone_axe",new BoneAxeItem(ModToolMaterials.BONE,5,-3.0f,new Item.Settings()));
    public static Item BONE_PICKAXE=registerItems("bone_pickaxe",new BonePickaxeItem(ModToolMaterials.BONE,1,-3.0f,new Item.Settings()));
    public static Item BONE_SHOVEL=registerItems("bone_shovel",new BoneShovelItem(ModToolMaterials.BONE,1,-3.0f,new Item.Settings()));
    public static Item BONE_HELMET=registerItems("bone_helmet",new ModArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.HELMET,new Item.Settings().maxCount(1)));
    public static Item BONE_CHEST=registerItems("bone_chest",new ModArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.CHESTPLATE,new Item.Settings().maxCount(1)));
    public static Item BONE_BOOTS=registerItems("bone_boots",new ModArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.BOOTS,new Item.Settings().maxCount(1)));
    public static Item BONE_LEGGING=registerItems("bone_leggings",new ModArmorItem(ModArmorMaterials.BONE, ArmorItem.Type.LEGGINGS,new Item.Settings().maxCount(1)));
    public static Item BONE_COMBINE=registerItems("bone_combine",new BoneCombineItem(ModToolMaterials.BONE,5,-2.4f,new Item.Settings()));
    public static Item BONE_STAFF=registerItems("bone_staff",new BoneStaffItem(new Item.Settings().maxCount(1)));
    public static Item BONE_STAFF2=registerItems("bone_staff2",new BoneStaffItem2(new Item.Settings().maxCount(1)));
    public static Item BONE_STAFF3=registerItems("bone_staff3",new BoneStaffItem3(new Item.Settings().maxCount(1)));
    public static Item BONE_STAFF4=registerItems("bone_staff4",new BoneStaffItem4(new Item.Settings().maxCount(1)));
    public static Item BATON=registerItems("baton",new BatonItem(new Item.Settings().maxCount(1)));
    public static Item XTLITEM=registerItems("xtl",new XtlItem(new Item.Settings()));
    public static Item STLITEM=registerItems("stl",new StlItem(new Item.Settings()));
    public static Item CPPPITEM=registerItems("cppp",new CpppItem(new Item.Settings()));
    public static Item NCITEM=registerItems("nc",new NcItem(new Item.Settings()));
    public static Item NCTOTEM=registerItems("nctotem",new Nctotem(new Item.Settings().maxCount(1)));
    public static Item CPPPTOTEM=registerItems("cppptotem",new Cppptotem(new Item.Settings().maxCount(1)));
    private static Item registerItems(String id, Item item){
        // 使用原版的注册方法
//        return Registry.register(Registries.ITEM, RegistryKey.of(Registries.ITEM.getKey(), Identifier.of(TutorialMod.MOD_ID, id)), item);
        // 由原版简化的方法
        return Registry.register(Registries.ITEM, new Identifier(YitianMod.MOD_ID, id), item);
    }
    // 使用Fabric的ItemGroupEntries添加物品
    private static void addItemToIG(FabricItemGroupEntries fabricItemGroupEntries){
        fabricItemGroupEntries.add(Items.DIAMOND);
    }
    // 初始化方法
    public static void registerModItems(){
        // 通过Fabric的ItemGroupEvents添加物品
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemToIG);
        YitianMod.LOGGER.info("Registering Items");
    }
}
