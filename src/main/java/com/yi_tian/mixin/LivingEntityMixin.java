package com.yi_tian.mixin;

import com.yi_tian.client.render.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "damage",at = @At("HEAD"),cancellable = true)
    private void damageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        LivingEntity mob = (LivingEntity) (Object) this;
        if(mob instanceof IronGolemEntity ironGolemEntity&&ironGolemEntity.getDataTracker().get(CustomIronGolemRenderLayer.IS_ENCHANTMENT)){
            if(source.isIn(DamageTypeTags.IS_FALL)&&amount>=3){
                triggerExplosion(ironGolemEntity,amount);
            }
        }

        if(mob instanceof SnowGolemEntity snowGolemEntity&&snowGolemEntity.getDataTracker().get(CustomSnowGolemRenderLayer.IS_ENCHANTMENT)){
            if(source.isIn(DamageTypeTags.IS_EXPLOSION)&&amount>=3){
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
        if(mob instanceof SkeletonEntity skeletonEntity&& skeletonEntity.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL)){
            if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
        if(mob instanceof ZombieEntity zombieEntity&& zombieEntity.getDataTracker().get(CustomZombieRenderLayer.LOYAL)){
            if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
        if(mob instanceof SpiderEntity spiderEntity&& spiderEntity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL)){
            if(source.isIn(DamageTypeTags.IS_PROJECTILE)){
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
    private void triggerExplosion(IronGolemEntity golem, float damage) {
        World world = golem.getWorld();

        // 计算爆炸威力（基础2.0 + 每超过4点增加0.5）
        float power = 2.0f + (Math.max(0, damage - 3) * 0.5f);

        // 创建中心爆炸
        world.createExplosion(
                golem,
                golem.getX(),
                golem.getY(),
                golem.getZ(),
                power,
                true, // 破坏方块
                World.ExplosionSourceType.MOB
        );

        // 生成冲击波粒子
        for (int i = 0; i < 360; i += 20) {
            double rad = Math.toRadians(i);
            world.addParticle(
                    ParticleTypes.POOF,
                    golem.getX() + Math.cos(rad) * 3,
                    golem.getY(),
                    golem.getZ() + Math.sin(rad) * 3,
                    0, 0.5, 0
            );
        }

        // 播放冲击音效
        golem.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0f, 0.8f);
    }
}
