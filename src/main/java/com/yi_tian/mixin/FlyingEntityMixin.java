package com.yi_tian.mixin;

import com.yi_tian.YitianMod;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.yi_tian.client.render.CustomGhastRenderLayer.NC;
import static com.yi_tian.client.render.CustomGhastRenderLayer.XTL;

@Mixin(FlyingEntity.class)
public class FlyingEntityMixin {
    @Inject(method = "travel",at=@At("RETURN"))
    private void travelMixin(Vec3d movementInput, CallbackInfo ci){
        FlyingEntity fly=(FlyingEntity) (Object) this;
        if(fly instanceof GhastEntity ghastEntity&&(ghastEntity.getDataTracker().get(XTL)||ghastEntity.getDataTracker().get(NC))){
            if(Math.abs(fly.getVelocity().x)+Math.abs(fly.getVelocity().y)+Math.abs(fly.getVelocity().z)<=6) {
                fly.setVelocity(fly.getVelocity().multiply(1.1f));
            }
        }
    }
}
