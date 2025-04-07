package com.yi_tian.mixin;

import com.yi_tian.client.render.CustomSpiderRenderLayer;
import com.yi_tian.client.render.CustomZombieRenderLayer;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MeleeAttackGoal.class)
public class MeleeAttackGoalMixin {
    @Inject(method = "getMaxCooldown",at=@At("HEAD"),cancellable = true)
    private void getMaxCooldownMixin(CallbackInfoReturnable<Integer> cir){
        MeleeAttackGoal meleeAttackGoal=(MeleeAttackGoal) (Object) this;
        MeleeAttackGoalAccessor accessor=(MeleeAttackGoalAccessor) meleeAttackGoal;
        if(accessor.mob() instanceof ZombieEntity zombieEntity){
            if(zombieEntity.getDataTracker().get(CustomZombieRenderLayer.LOYAL)){
                cir.setReturnValue(2);
            }
        }
        if(accessor.mob() instanceof SpiderEntity spiderEntity){
            if(spiderEntity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL)){
                cir.setReturnValue(2);
            }
        }
    }
    @Inject(method = "tick",at=@At("RETURN"),cancellable = true)
    private void tickMixin(CallbackInfo ci){
        MeleeAttackGoal meleeAttackGoal=(MeleeAttackGoal) (Object) this;
        MeleeAttackGoalAccessor accessor=(MeleeAttackGoalAccessor) meleeAttackGoal;
        if(accessor.mob() instanceof ZombieEntity zombieEntity){
            if(zombieEntity.getDataTracker().get(CustomZombieRenderLayer.LOYAL)){
                accessor.setCooldown(Math.max(accessor.getCooldown()-2,0));
            }
        }
        if(accessor.mob() instanceof SpiderEntity spiderEntity){
            if(spiderEntity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL)){
                accessor.setCooldown(Math.max(accessor.getCooldown()-2,0));
            }
        }
    }
}
