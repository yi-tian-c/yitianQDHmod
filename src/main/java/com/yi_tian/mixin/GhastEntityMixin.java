package com.yi_tian.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.FireballEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Method;

import static com.yi_tian.client.render.CustomGhastRenderLayer.*;

@Mixin(GhastEntity.class)
public class GhastEntityMixin {
    /**
     * 修改免疫判断逻辑，允许被同类火球伤害
     */
    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    public void checkGhastFireballVulnerability(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        // 判断伤害来源是否是火球且发射者是恶魂
        GhastEntity ghastEntity = (GhastEntity) (Object) this;
        if (damageSource.getSource() instanceof FireballEntity fireball) {
            Entity attacker = fireball.getOwner();
            if (attacker instanceof GhastEntity ghastEntity1){
                if((((ghastEntity1.getDataTracker().get(XTL)||ghastEntity1.getDataTracker().get(NC))&&
                    ((ghastEntity.getDataTracker().get(XTL)||ghastEntity.getDataTracker().get(NC))))||
                            ((ghastEntity1.getDataTracker().get(STL)||ghastEntity1.getDataTracker().get(CPPP))&&
                                    ((ghastEntity.getDataTracker().get(STL)||ghastEntity.getDataTracker().get(CPPP)))))) {
                    cir.setReturnValue(true); // 继承原版其他免疫判断
                    cir.cancel();
                }
                else{
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
        }
    }
}
