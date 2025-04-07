package com.yi_tian.enchantment;

import com.yi_tian.YitianMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final Enchantment MOBILE = new MobileEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
    public static final Enchantment CONTINUOUS = new ContinuousEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
    public static final Enchantment LOYAL = new LoyalEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
    public static final Enchantment STICK = new StickEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
    public static final Enchantment HEAVY = new HeavyEnchantment(Enchantment.Rarity.RARE, EquipmentSlot.MAINHAND);
    // 注册自定义附魔的方法
    public static void registerModEnchantments(){
        Registry.register(Registries.ENCHANTMENT, new Identifier(YitianMod.MOD_ID, "mobile"), MOBILE);
        Registry.register(Registries.ENCHANTMENT, new Identifier(YitianMod.MOD_ID, "continuous"), CONTINUOUS);// 注册机动附魔
        Registry.register(Registries.ENCHANTMENT, new Identifier(YitianMod.MOD_ID, "loyal"), LOYAL);// 注册忠诚附魔
        Registry.register(Registries.ENCHANTMENT, new Identifier(YitianMod.MOD_ID, "stick"), STICK);// 注册一秒6棍
        Registry.register(Registries.ENCHANTMENT, new Identifier(YitianMod.MOD_ID, "heavy"), HEAVY);// 注册重拳出击
    }
}
