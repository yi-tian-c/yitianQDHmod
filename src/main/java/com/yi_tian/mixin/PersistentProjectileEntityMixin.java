package com.yi_tian.mixin;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {
    @Inject(method = "age",at = @At("HEAD"))
    private void ageMixin(CallbackInfo ci){
        PersistentProjectileEntity persistentProjectileEntity =(PersistentProjectileEntity) (Object) this;
        PersistentProjectileEntityAccessor accessor=(PersistentProjectileEntityAccessor) persistentProjectileEntity;
        if(accessor.getLife()>=40){
            persistentProjectileEntity.discard();
        }
    }
}
