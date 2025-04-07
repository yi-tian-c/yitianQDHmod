package com.yi_tian.mixin;

import net.minecraft.client.render.entity.GhastEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.GhastEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.yi_tian.client.render.CustomGhastRenderLayer.STL;
import static com.yi_tian.client.render.CustomGhastRenderLayer.XTL;

@Mixin(GhastEntityRenderer.class)
public class GhastEntityRendererMixin {
    @Inject(method = "scale(Lnet/minecraft/entity/mob/GhastEntity;Lnet/minecraft/client/util/math/MatrixStack;F)V",at=@At("RETURN"),cancellable = true)
    private void scaleMixin(GhastEntity ghastEntity, MatrixStack matrixStack, float f, CallbackInfo ci){
        if(ghastEntity.getDataTracker().get(STL)||ghastEntity.getDataTracker().get(XTL)){
            matrixStack.scale(2.0f,2.0f,2.0f);
        }
    }
}
