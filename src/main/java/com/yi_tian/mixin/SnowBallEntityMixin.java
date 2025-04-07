package com.yi_tian.mixin;

import com.yi_tian.client.render.CustomSnowGolemRenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public class SnowBallEntityMixin {
    @Inject(method = "onEntityHit",at = @At("HEAD"))
    private void onEntityHitMixin(EntityHitResult entityHitResult, CallbackInfo ci) {
        SnowballEntity snowball = (SnowballEntity) (Object) this;
        Entity owner = snowball.getOwner();
        World world = snowball.getWorld();
        if (owner instanceof SnowGolemEntity snowGolemEntity && snowGolemEntity.getDataTracker().get(CustomSnowGolemRenderLayer.IS_ENCHANTMENT)) {
            if (owner instanceof SnowGolemEntity snowGolem
                    && snowGolem.getDataTracker().get(CustomSnowGolemRenderLayer.IS_ENCHANTMENT)) {

                Entity target = entityHitResult.getEntity();
                Random random = snowGolem.getRandom();

                // ====== 爆炸逻辑（1/3概率） ======
                if (random.nextFloat() < 1.0f / 3) {
                    // 生成小型爆炸（不破坏方块）
                    world.createExplosion(
                            snowGolem, // 爆炸来源
                            target.getX(),
                            target.getY(),
                            target.getZ(),
                            0.5f,      // 威力
                            false,     // 是否破坏方块
                            World.ExplosionSourceType.NONE
                    );


                    // ====== 击飞逻辑（1/10概率） ======
                    if (random.nextFloat() < 1.0f / 10) {
                        // 生成小型爆炸（不破坏方块）
                        world.createExplosion(
                                snowGolem, // 爆炸来源
                                target.getX(),
                                target.getY(),
                                target.getZ(),
                                3.5f,      // 威力
                                false,     // 是否破坏方块
                                World.ExplosionSourceType.NONE
                        );
                    }
                }
            }
        }
    }
}
