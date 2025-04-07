package com.yi_tian.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class ModFireBallEntity extends FireballEntity {
    public ModFireBallEntity(EntityType<? extends FireballEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        this.setVelocity(0.0f,-1.5f,0.0f);
        if(this.getWorld().isClient){
            Vector3f color=new Vector3f(1.0f,1.0f,1.0f);
            for(int i=1;i<=20;++i) {
                double m_x = Math.random() - 0.5;
                double m_y = Math.random() - 0.5;
                double m_z = Math.random() - 0.5;
                this.getWorld().addParticle(
                        new DustParticleEffect(color, 2.0f), // 使用 DustParticleEffect 定义颜色
                        this.getX() + m_x*4,
                        this.getY() + m_y*8,
                        this.getZ() + m_z*4,
                        0.0f, 0.0f, 0.0f
                );
            }
            color=new Vector3f(1.0f,0.0f,0.0f);
            for(int i=1;i<=20;++i) {
                double m_x = Math.random() - 0.5;
                double m_y = Math.random() - 0.5;
                double m_z = Math.random() - 0.5;
                this.getWorld().addParticle(
                        new DustParticleEffect(color, 2.0f), // 使用 DustParticleEffect 定义颜色
                        this.getX() + m_x*4,
                        this.getY() + m_y*8,
                        this.getZ() + m_z*4,
                        0.0f, 0.0f, 0.0f
                );
            }
        }
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        this.getWorld().createExplosion(null, this.getX(), this.getY(), this.getZ(), 3.0F, false, World.ExplosionSourceType.MOB);
    }
}
