package com.yi_tian.mixin;

import com.yi_tian.YitianMod;
import com.yi_tian.client.render.*;
import com.yi_tian.enchantment.ModEnchantments;
import com.yi_tian.item.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(
            method = "useOnEntity",
            at = @At("HEAD"),
            cancellable = true
    )
    private void useOnEntityMixin(ItemStack m_stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir){
        ItemStack stack = user.getStackInHand(hand);
        if(EnchantmentHelper.getLevel(ModEnchantments.MOBILE, stack) > 0){
            if(entity instanceof IronGolemEntity ironGolemEntity) {
                // 播放音效
                user.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_IRON_GOLEM_REPAIR, entity.getSoundCategory(), 1.0F, 1.0F);
                stack.decrement(1);
                ironGolemEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 900000000, 99,false,false,false));
                ironGolemEntity.setHealth(500);
                ironGolemEntity.getDataTracker().set(CustomIronGolemRenderLayer.IS_ENCHANTMENT, true);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
        if(EnchantmentHelper.getLevel(ModEnchantments.CONTINUOUS, stack) > 0) {
            if (entity instanceof SnowGolemEntity snowGolemEntity) {
                YitianMod.LOGGER.info("雪傀儡附魔调用");
                snowGolemEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 900000000, 10, false, false, false));
                snowGolemEntity.setHealth(50);
                stack.decrement(1);
                user.getWorld().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_SNOW_GOLEM_SHEAR, entity.getSoundCategory(), 1.0F, 1.0F);
                snowGolemEntity.getDataTracker().set(CustomSnowGolemRenderLayer.IS_ENCHANTMENT, true);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
        if(EnchantmentHelper.getLevel(ModEnchantments.LOYAL,stack)>0){
            if(entity instanceof ZombieEntity zombieEntity){
                stack.decrement(1);
                zombieEntity.getDataTracker().set(CustomZombieRenderLayer.LOYAL,true);
                zombieEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 900000000, 10,false,false,false));
                zombieEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 900000000, 0,false,false,false));
                // 设置装备
                zombieEntity.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.BATON)); // 主手
                zombieEntity.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));    // 副手
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            if(entity instanceof SpiderEntity spiderEntity){
                stack.decrement(1);
                spiderEntity.getDataTracker().set(CustomSpiderRenderLayer.LOYAL,true);
                spiderEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 900000000, 10,false,false,false));
                spiderEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 900000000, 0,false,false,false));

                cir.setReturnValue(ActionResult.SUCCESS);
            }
            if(entity instanceof WardenEntity wardenEntity){
                stack.decrement(1);
                wardenEntity.getDataTracker().set(CustomWardenRenderLayer.LOYAL,true);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            if(entity instanceof SkeletonEntity skeletonEntity){
                stack.decrement(1);
                skeletonEntity.getDataTracker().set(CustomSkeletonRenderLayer.LOYAL,true);
                skeletonEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 900000000, 10,false,false,false));
                skeletonEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 900000000, 0,false,false,false));
                // 给骷髅实体的弓添加力量 II 附魔
                ItemStack bow = skeletonEntity.getMainHandStack();
                if (bow != null && bow.isOf(Items.BOW)) {
                    // 创建附魔映射
                    Map<Enchantment, Integer> enchantments = new HashMap<>();
                    enchantments.put(Enchantments.POWER, 2); // 力量 II

                    EnchantmentHelper.set(enchantments,bow);
                }
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }

}
