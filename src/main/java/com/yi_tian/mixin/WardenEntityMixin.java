package com.yi_tian.mixin;

import com.yi_tian.client.render.CustomSkeletonRenderLayer;
import com.yi_tian.client.render.CustomSpiderRenderLayer;
import com.yi_tian.client.render.CustomWardenRenderLayer;
import com.yi_tian.client.render.CustomZombieRenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin {
    @Shadow @Nullable public abstract LivingEntity getTarget();

    @Inject(method = "increaseAngerAt(Lnet/minecraft/entity/Entity;IZ)V",at = @At("HEAD"),cancellable = true)
    private void increaseAngerAtMixin(Entity entity, int amount, boolean listening, CallbackInfo ci){
        WardenEntity warden=(WardenEntity) (Object) this;
        if(warden.getDataTracker().get(CustomWardenRenderLayer.LOYAL)&&entity instanceof PlayerEntity){
            ci.cancel();//不攻击玩家
        }
        if((entity == null || !entity.isAlive() ||
                (entity instanceof ZombieEntity && entity.getDataTracker().get(CustomZombieRenderLayer.LOYAL))||
                (entity instanceof SpiderEntity && entity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL))||
                (entity instanceof WardenEntity && entity.getDataTracker().get(CustomWardenRenderLayer.LOYAL))||
                (entity instanceof SkeletonEntity && entity.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL)))&&
                warden.getDataTracker().get(CustomWardenRenderLayer.LOYAL)){
            ci.cancel();//阻止内讧
        }
    }
    @Inject(method = "updateAttackTarget",at = @At("HEAD"),cancellable = true)
    private void updateAttackTargetMixin(LivingEntity target, CallbackInfo ci){
        WardenEntity warden=(WardenEntity) (Object) this;
        if(warden.getDataTracker().get(CustomWardenRenderLayer.LOYAL)&&target instanceof PlayerEntity){
            ci.cancel();//不攻击玩家
        }
        if((target == null || !target.isAlive() ||
                (target instanceof ZombieEntity && target.getDataTracker().get(CustomZombieRenderLayer.LOYAL))||
                (target instanceof SpiderEntity && target.getDataTracker().get(CustomSpiderRenderLayer.LOYAL))||
                (target instanceof WardenEntity && target.getDataTracker().get(CustomWardenRenderLayer.LOYAL))||
                (target instanceof SkeletonEntity && target.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL)))&&
                warden.getDataTracker().get(CustomWardenRenderLayer.LOYAL)){
            ci.cancel();//阻止内讧
        }
    }
    @Inject(method = "addDarknessToClosePlayers",at = @At("HEAD"),cancellable = true)
    private static void addDarknessToClosePlayersMixin(ServerWorld world, Vec3d pos, Entity entity, int range, CallbackInfo ci){
        ci.cancel();//取消黑暗
    }
    @Inject(method = "tick",at = @At("HEAD"))
    private void tickMixin(CallbackInfo ci){
        WardenEntity warden=(WardenEntity) (Object) this;
        if(warden.age%20==0){
            warden.setTarget(null);
        }
        if(warden.getDataTracker().get(CustomWardenRenderLayer.LOYAL)) {
            for (PlayerEntity player : warden.getWorld().getPlayers()) {
                LivingEntity lastAttackedEntity = player.getAttacking();

                // 优先攻击玩家攻击的生物
                if (lastAttackedEntity != null && lastAttackedEntity.isAlive() &&
                        !(lastAttackedEntity instanceof ZombieEntity && lastAttackedEntity.getDataTracker().get(CustomZombieRenderLayer.LOYAL))&&
                        !(lastAttackedEntity instanceof SpiderEntity && lastAttackedEntity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL))&&
                        !(lastAttackedEntity instanceof SkeletonEntity && lastAttackedEntity.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL))) {
                    warden.setTarget(lastAttackedEntity);
                    warden.increaseAngerAt(lastAttackedEntity);
                    warden.updateAttackTarget(lastAttackedEntity);
                    return;
                }

                // 如果没有攻击目标，寻找距离最近的敌对生物
                if (warden.getTarget() == null) {
                    LivingEntity closestEntity = null;
                    double closestDistance = Double.MAX_VALUE;

                    for (LivingEntity entity : warden.getWorld().getEntitiesByClass(LivingEntity.class, warden.getBoundingBox().expand(10), entity ->
                            entity != warden && entity != player &&
                                    !(entity instanceof ZombieEntity && entity.getDataTracker().get(CustomZombieRenderLayer.LOYAL)) &&
                                    !(entity instanceof SpiderEntity && entity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL)) &&
                                    !(entity instanceof SkeletonEntity && entity.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL)) &&
                                    entity.isAlive()
                    )) {
                        double distance = warden.squaredDistanceTo(entity);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestEntity = entity;
                        }
                    }

                    if (closestEntity != null) {
                        warden.setTarget(closestEntity);
                        warden.increaseAngerAt(closestEntity);
                        warden.updateAttackTarget(closestEntity);
                    }
                }
            }
        }
    }
}
