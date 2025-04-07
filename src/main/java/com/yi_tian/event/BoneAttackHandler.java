package com.yi_tian.event;

import com.yi_tian.item.ModItems;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BoneAttackHandler implements AttackEntityCallback {
    public static void register() {
        AttackEntityCallback.EVENT.register(new BoneAttackHandler());
    }

    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        ItemStack stack = player.getStackInHand(hand);

        // 判断是否为骨剑攻击
        if ((stack.getItem() == ModItems.BONE_AXE || stack.getItem() == ModItems.BONE_SWORD||stack.getItem()==ModItems.BONE_COMBINE) && entity instanceof AnimalEntity mob) {
            if (!world.isClient()) {
                // 强制让幼年动物长大
                if (mob.isBaby()) {
                    mob.setBreedingAge(0); // 设为成年
                    world.playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS);
                    // 生成粒子效果
                    for (int i = 0; i < 5; i++) {
                        ((ServerWorld) world).spawnParticles(
                                ParticleTypes.HAPPY_VILLAGER,
                                entity.getX(),
                                entity.getY() + 1,
                                entity.getZ(),
                                10,
                                0.5, 0.5, 0.5,
                                0.1
                        );
                    }
                }
            }
            return ActionResult.PASS; // 默认攻击伤害
        }
        return ActionResult.PASS;
    }
}

