package com.yi_tian.mixin;

import com.yi_tian.client.render.CustomIronGolemRenderLayer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntity.class)
public class IronGolemEntityMixin2 {
    @Inject(method = "damage",at=@At("HEAD"),cancellable = true)
    public void damageMixin(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir){
        IronGolemEntity mob = (IronGolemEntity) (Object) this;
        if(source.isIn(DamageTypeTags.IS_EXPLOSION)&&mob.getDataTracker().get(CustomIronGolemRenderLayer.IS_ENCHANTMENT)){
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
