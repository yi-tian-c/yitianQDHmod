package com.yi_tian.entity.custom;

import com.yi_tian.YitianMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Random;
import org.joml.Vector3f;

public class ModWitherSkullEntity extends WitherSkullEntity {
    Random rand =new Random();
    public ModWitherSkullEntity(EntityType<? extends WitherSkullEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getOwner() instanceof LivingEntity livingEntity&&entityHitResult.getEntity() instanceof LivingEntity&&!this.getWorld().isClient) {
            entityHitResult.getEntity().damage(this.getDamageSources().mobProjectile(this, livingEntity), 6.0F);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.setVelocity(0.0f,-0.6f,0.0f);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.0F, false, World.ExplosionSourceType.MOB);
    }
}
