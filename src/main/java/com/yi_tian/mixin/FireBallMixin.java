package com.yi_tian.mixin;

import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.FireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.yi_tian.client.render.CustomGhastRenderLayer.CPPP;
import static com.yi_tian.client.render.CustomGhastRenderLayer.STL;

@Mixin(ExplosiveProjectileEntity.class)
public class FireBallMixin {
    @Inject(method = "tick",at=@At("RETURN"))
    private void tickMixin(CallbackInfo ci){
        ExplosiveProjectileEntity entity=(ExplosiveProjectileEntity)(Object) this;
        if(entity instanceof FireballEntity&&entity.getOwner() instanceof GhastEntity ghastEntity){
            if(ghastEntity.getDataTracker().get(STL)||ghastEntity.getDataTracker().get(CPPP)){
                if(entity.getVelocity().x<=0.01&&entity.getVelocity().y<=0.01&&entity.getVelocity().z<=0.01){
                    entity.discard();
                }
            }
        }
    }
}
