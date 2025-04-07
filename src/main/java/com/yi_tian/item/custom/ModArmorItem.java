package com.yi_tian.item.custom;

import com.google.common.collect.ImmutableMap;
import com.yi_tian.item.ModArmorMaterials;
import com.yi_tian.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ModArmorItem extends ArmorItem {
    private static final Map<ArmorMaterial, List<StatusEffectInstance>> MAP =
            (new ImmutableMap.Builder<ArmorMaterial, List<StatusEffectInstance>>())
                    .put(ModArmorMaterials.BONE,
                            Arrays.asList(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,1000, 1, false,false,true),
                                    new StatusEffectInstance(StatusEffects.SPEED, 1000, 1, false,false,true)))
                    .build();

    public ModArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient() && entity instanceof PlayerEntity player && hasFullSuitOfArmor(player)) {
            evaluateArmorEffects(player);
        }
//        if (!world.isClient() && entity instanceof PlayerEntity player) {
//            ItemStack helmet = player.getInventory().getArmorStack(3);
//            ItemStack chestplate = player.getInventory().getArmorStack(2);
//            ItemStack leggings = player.getInventory().getArmorStack(1);
//            ItemStack boots = player.getInventory().getArmorStack(0);
//            if(helmet.getItem()== ModItems.BONE_HELMET&&
//                    chestplate.getItem()== ModItems.BONE_CHEST&&
//                    leggings.getItem()== ModItems.BONE_LEGGING&&
//                    boots.getItem()== ModItems.BONE_BOOTS){
//
//            }
//        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private void evaluateArmorEffects(PlayerEntity player) {
        for (Map.Entry<ArmorMaterial, List<StatusEffectInstance>> entry : MAP.entrySet()) {
            ArmorMaterial material = entry.getKey();
            List<StatusEffectInstance> effects = entry.getValue();

            if (hasCorrectArmorSet(player, material)) {
                for (StatusEffectInstance effect : effects) {
                    addStatusEffectForMaterial(player, effect);
                }
            }
        }
    }

    private void addStatusEffectForMaterial(PlayerEntity player, StatusEffectInstance effect) {
        boolean hasEffect = player.hasStatusEffect(effect.getEffectType());

        if (!hasEffect) {
            player.addStatusEffect(new StatusEffectInstance(effect));
        }
    }

    private boolean hasCorrectArmorSet(PlayerEntity player, ArmorMaterial material) {
        for (ItemStack stack : player.getInventory().armor) {
            if (!(stack.getItem() instanceof ArmorItem)) {
                return false;
            }
        }

        ArmorItem helmet = (ArmorItem) player.getInventory().getArmorStack(3).getItem();
        ArmorItem chestplate = (ArmorItem) player.getInventory().getArmorStack(2).getItem();
        ArmorItem leggings = (ArmorItem) player.getInventory().getArmorStack(1).getItem();
        ArmorItem boots = (ArmorItem) player.getInventory().getArmorStack(0).getItem();

        return helmet.getMaterial() == material &&
                chestplate.getMaterial() == material &&
                leggings.getMaterial() == material &&
                boots.getMaterial() == material;
    }

    private boolean hasFullSuitOfArmor(PlayerEntity player) {
        ItemStack helmet = player.getInventory().getArmorStack(3);
        ItemStack chestplate = player.getInventory().getArmorStack(2);
        ItemStack leggings = player.getInventory().getArmorStack(1);
        ItemStack boots = player.getInventory().getArmorStack(0);

        return !helmet.isEmpty() && !chestplate.isEmpty() && !leggings.isEmpty() && !boots.isEmpty();
    }
}
