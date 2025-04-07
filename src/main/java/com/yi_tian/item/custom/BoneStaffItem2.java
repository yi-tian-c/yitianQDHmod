package com.yi_tian.item.custom;

import com.yi_tian.YitianMod;
import com.yi_tian.entity.ModSkeletonEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class BoneStaffItem2 extends Item {
    public BoneStaffItem2(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 4;
    }

    private boolean isLookingAtEntity(PlayerEntity player, Entity entity) {
        Vec3d playerLookVec = player.getRotationVec(1.0F).normalize();
        Vec3d entityVec = new Vec3d(
                entity.getX() - player.getX(),
                entity.getY() - player.getY(),
                entity.getZ() - player.getZ()
        );
        double distance = entityVec.length();
        entityVec = entityVec.normalize();
        double dotProduct = playerLookVec.dotProduct(entityVec);

        // 判断是否在直视范围内
        return dotProduct > (double) 1.0F - 0.35 / distance && player.canSee(entity);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        YitianMod.LOGGER.info("正在右键");
        user.setCurrentHand(hand);
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getWorld().isClient) {
            YitianMod.LOGGER.info("正在检查");
            LivingEntity hitResult = null;
            Box box1 = user.getBoundingBox().expand(50);
            for (Entity entity : world.getEntitiesByClass(LivingEntity.class, box1, entity -> true)) {
                // 跳过玩家自己
                if (entity == user) {
                    continue;
                }
                // 判断玩家是否在直视这个生物的眼睛
                if (isLookingAtEntity(user, entity)) {
                    // 如果玩家在直视这个生物，获取该生物实体
                    hitResult = (LivingEntity) entity;
                    if (hitResult != null) {
                        YitianMod.LOGGER.info("不为空");
                        LivingEntity targetEntity = hitResult;
                        if (targetEntity instanceof LivingEntity) {
                            // 在目标生物上生成红色粒子效果
                            if (world instanceof ServerWorld) {
                                // 生成粒子群
                                DustParticleEffect particleEffect = new DustParticleEffect(
                                        new Vector3f(1.0f, 0.0f, 0.0f), // 归一化颜色
                                        2.0f // 粒子尺寸
                                );
                                ((ServerWorld) world).spawnParticles(
                                        particleEffect,
                                        targetEntity.getX() + 0.5,
                                        targetEntity.getY() + 1.5,
                                        targetEntity.getZ() + 0.5,
                                        5,    // 粒子数量
                                        0.3,   // 水平扩散
                                        0.5,   // 垂直扩散
                                        0.3,   // 水平扩散
                                        0.1    // 速度基数
                                );
                            }
                            float a=user.getHealth();
                            user.setHealth(a+0.5f);
                            targetEntity.damage(targetEntity.getDamageSources().indirectMagic(user,user), 6.0f);
                            double tx = user.getX();
                            double ty = user.getY()+0.3f;
                            double tz = user.getZ();
                            double sx = targetEntity.getX();
                            double sy = targetEntity.getY() + 0.6f;
                            double sz = targetEntity.getZ();
                            double dx = (sx - tx) / 30;
                            double dy = (sy - ty) / 30;
                            double dz = (sz - tz) / 30;
                            for (int i = 1; i <= 30; ++i) {
                                ((ServerWorld) user.getWorld()).spawnParticles(new DustParticleEffect(new Vector3f(1.0f, 0.0f, 0.0f), 1.0f),
                                        tx + i * dx,
                                        ty + i * dy,
                                        tz + i * dz,
                                        1,
                                        0.00f, 0.00f, 0.00f, 0.0f);
                            }

                        }
                    }
                }
            }
        }
        return TypedActionResult.consume(stack);
    }

}
