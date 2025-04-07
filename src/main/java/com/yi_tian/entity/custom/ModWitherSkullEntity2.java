package com.yi_tian.entity.custom;

import com.yi_tian.YitianMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Random;

public class ModWitherSkullEntity2 extends WitherSkullEntity{
    Random rand = new Random();
    public ModWitherSkullEntity2(EntityType<? extends WitherSkullEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getOwner() instanceof LivingEntity livingEntity&&entityHitResult.getEntity() instanceof LivingEntity&&!this.getWorld().isClient) {
            entityHitResult.getEntity().damage(this.getDamageSources().mobProjectile(this, livingEntity), 60.0F);
        }
    }

    @Override
    public void tick() {
        double v_x=0;
        double v_y=0;
        double v_z=0;
            v_x=this.getVelocity().x;
            v_y=this.getVelocity().y;
            v_z=this.getVelocity().z;
        super.tick();
            this.setVelocity(v_x,v_y,v_z);
    }


    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 5.0F, false, World.ExplosionSourceType.MOB);
        // 仅在服务端执行生成逻辑
        if (!this.getWorld().isClient()) {
            // 获取碰撞点正上方20格的中心坐标
            BlockPos centerPos = this.getBlockPos().up(20);

            // 生成8个 ModWitherSkullEntity
            for (int i = 0; i < 12; i++) {
                // 计算水平随机偏移（-7到7格）
                double offsetX = (rand.nextDouble() - 0.5) * 22; // [-7,7)
                double offsetZ = (rand.nextDouble() - 0.5) * 22;
                double offsetY = (rand.nextDouble() - 0.5) * 14;

                // 生成位置（中心上方20格 + 水平随机偏移）
                BlockPos spawnPos = centerPos.add((int)offsetX, (int)offsetY, (int)offsetZ);

                // 创建新实体（ModWitherSkullEntity）
                ModWitherSkullEntity newSkull = new ModWitherSkullEntity(
                        EntityType.WITHER_SKULL, this.getWorld()
                );

                // 设置位置和速度
                newSkull.setPosition(
                        spawnPos.getX() + 0.5, // 居中在方块中心
                        spawnPos.getY() + 0.5,
                        spawnPos.getZ() + 0.5
                );
                newSkull.setVelocity(0, -0.5, 0); // 竖直向下速度
                // 将实体加入世界
                this.getWorld().spawnEntity(newSkull);
            }
        }
    }
}
