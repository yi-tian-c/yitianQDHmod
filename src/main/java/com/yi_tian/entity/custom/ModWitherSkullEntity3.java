package com.yi_tian.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ModWitherSkullEntity3 extends WitherSkullEntity {
    public ModWitherSkullEntity3(EntityType<? extends WitherSkullEntity> entityType, World world) {
        super(entityType, world);
    }
    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getOwner() instanceof LivingEntity livingEntity&&entityHitResult.getEntity() instanceof LivingEntity&&!this.getWorld().isClient) {
            entityHitResult.getEntity().damage(this.getDamageSources().mobProjectile(this, livingEntity), 900.0F);
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
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 200.0F, false, World.ExplosionSourceType.MOB);
    }

    @Override
    public void tick() {
        double v_x=this.getVelocity().x;
        double v_y=this.getVelocity().y;
        double v_z=this.getVelocity().z;
        super.tick();
        this.setVelocity(v_x,v_y,v_z);
    }
}
