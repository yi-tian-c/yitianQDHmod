package com.yi_tian.event;

import com.yi_tian.YitianMod;
import com.yi_tian.entity.custom.ModWitherSkullEntity2;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

import static com.yi_tian.client.render.CustomGhastRenderLayer.CPPP;
import static com.yi_tian.client.render.CustomGhastRenderLayer.STL;

@Environment(EnvType.CLIENT)
public class ModSkullHandler {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world != null) {
                // 遍历所有实体
                for (Entity entity : client.world.getEntities()) {
                    if (entity instanceof WitherSkullEntity) {
                        if (((WitherSkullEntity) entity).getOwner() instanceof PlayerEntity) {
                            double Skull_v2=((WitherSkullEntity) entity).getVelocity().x*((WitherSkullEntity) entity).getVelocity().x
                                    +((WitherSkullEntity) entity).getVelocity().y*((WitherSkullEntity) entity).getVelocity().y
                                    +((WitherSkullEntity) entity).getVelocity().z*((WitherSkullEntity) entity).getVelocity().z;
                            if(Skull_v2<=9) {
                                Vector3f color = new Vector3f(0.0f, 0.0f, 0.0f);
                                Vec3d m_v = entity.getVelocity();
                                double d = m_v.getX();
                                double e = m_v.getY();
                                double f = m_v.getZ();
                                double m_x;
                                double m_y;
                                double m_z;
                                for (int i = 0; i < 7; ++i) {
                                    m_x = Math.random() - 0.5;
                                    m_y = Math.random() - 0.5;
                                    m_z = Math.random() - 0.5;
                                    double g = 0.4 + 0.1 * (double) i;
                                    client.world.addParticle(
                                            new DustParticleEffect(color, 1.0f), // 使用 DustParticleEffect 定义颜色
                                            entity.getX() + m_x,
                                            entity.getY() + m_y,
                                            entity.getZ() + m_z,
                                            d * 3, e * 3, f * 3
                                    );
                                }
                                color = new Vector3f(1.0f, 0.0f, 0.0f);
                                for (int i = 0; i < 7; ++i) {
                                    m_x = Math.random() - 0.5;
                                    m_y = Math.random() - 0.5;
                                    m_z = Math.random() - 0.5;
                                    double g = 0.4 + 0.1 * (double) i;
                                    client.world.addParticle(
                                            new DustParticleEffect(color, 1.0f), // 使用 DustParticleEffect 定义颜色
                                            entity.getX() + m_x,
                                            entity.getY() + m_y,
                                            entity.getZ() + m_z,
                                            d * 3, e * 3, f * 3
                                    );
                                }
                            }
                            else{
                                double m_age=entity.age;
                                double m_pitch=entity.getPitch();
                                double m_yaw=entity.getYaw();
                                double m_x;
                                double m_y;
                                double m_z;
                                double xx=entity.getX();
                                double yy=entity.getY();
                                double zz=entity.getZ();
                                Vector3f color = new Vector3f(1.0f, 1.0f, 1.0f);
                                for(int i=0;i<200+m_age*3;++i){
                                    m_x = Math.random() - 0.5;
                                    m_y = Math.random() - 0.5;
                                    m_z = Math.random() - 0.5;
                                    double ii=i;
                                    client.world.addParticle(
                                            new DustParticleEffect(color,2.5f), // 使用 DustParticleEffect 定义颜色
                                            true,
                                            xx +m_x*(5+m_age),
                                            yy +m_y*(5+m_age),
                                            zz +m_z*(5+m_age),
                                            0.0, 0.0, 0.0
                                    );
                                }
                                color = new Vector3f(1.0f, 0.0f, 1.0f);
                                for (int i = 0; i < 200+m_age*3; ++i) {
                                    m_x = Math.random() - 0.5;
                                    m_y = Math.random() - 0.5;
                                    m_z = Math.random() - 0.5;

                                    client.world.addParticle(
                                            new DustParticleEffect(color,2.5f),
                                            true,
                                            xx +m_x*(5+m_age),
                                            yy +m_y*(5+m_age),
                                            zz +m_z*(5+m_age),
                                            0.0, // X 方向速度
                                            0.0, // 轻微向上速度（可选）
                                            0.0  // Z 方向速度
                                    );
                                }
                                if(entity.age%4==0||entity.age==1){
                                    color = new Vector3f(1.0f, 1.0f, 1.0f);
                                    Vec3d direction = new Vec3d(
                                            entity.getVelocity().x,
                                            entity.getVelocity().y,
                                            entity.getVelocity().z
                                    ).normalize();

                                    // 计算垂直平面的基向量
                                    Vec3d up = new Vec3d(1, 0, 0); // 假设向上方向为垂直方向
                                    Vec3d m_va = new Vec3d(direction.y*up.z-direction.z*up.y,direction.z*up.x-direction.x*up.z,direction.x*up.y-direction.y*up.x);
                                    if(m_va.x<=1e-4&&m_va.y<=1e-4&&m_va.z<=1e-4){
                                        up = new Vec3d(0, 0, 1);
                                        m_va = new Vec3d(direction.y*up.z-direction.z*up.y,direction.z*up.x-direction.x*up.z,direction.x*up.y-direction.y*up.x);
                                    }
                                    Vec3d m_vb= new Vec3d(direction.y*m_va.z-direction.z*m_va.y,direction.z*m_va.x-direction.x*m_va.z,direction.x*m_va.y-direction.y*m_va.x);
                                    m_vb.normalize();
                                    m_va.normalize();

                                    double radius=5+m_age*1.2;
                                    double m_num=180+m_age*3;
                                    // 生成圆形粒子
                                    for (int i = 0; i < m_num; i++) {
                                        // 计算圆周上的点
                                        double angle = 2 * Math.PI * i / m_num;
                                        // 生成粒子
                                        client.world.addParticle(
                                                new DustParticleEffect(color, 3.5f),
                                                true,
                                                xx+radius*m_va.x*Math.cos(angle)+radius*m_vb.x*Math.sin(angle),
                                                yy+radius*m_va.y*Math.cos(angle)+radius*m_vb.y*Math.sin(angle),
                                                zz+radius*m_va.z*Math.cos(angle)+radius*m_vb.z*Math.sin(angle),
                                                0.0, // 垂直扩散
                                                0.0, // 水平扩散
                                                0.0 // 速度基数
                                        );
                                    }
                                }
                            }
                        }
                        if(entity.getVelocity().x==0&&entity.getVelocity().z==0){
                            double m_x;
                            double m_y;
                            double m_z;
                            double xx=entity.getX();
                            double yy=entity.getY();
                            double zz=entity.getZ();
                            Vector3f color = new Vector3f(0.0f, 0.0f, 0.0f);
                            for(int i=0;i<10;++i){
                                m_x = Math.random() - 0.5;
                                m_y = Math.random() - 0.5;
                                m_z = Math.random() - 0.5;
                                double ii=i;
                                client.world.addParticle(
                                        new DustParticleEffect(color,1.5f), // 使用 DustParticleEffect 定义颜色
                                        true,
                                        xx +m_x*2,
                                        yy +m_y,
                                        zz +m_z*2,
                                        0.0, 0.0, 0.0
                                );
                            }
                            color = new Vector3f(1.0f, 0.0f, 0.0f);
                            for (int i = 0; i < 20; ++i) {
                                m_x = Math.random() - 0.5;
                                m_y = Math.random() - 0.5;
                                m_z = Math.random() - 0.5;

                                client.world.addParticle(
                                        new DustParticleEffect(color,1.5f),
                                        true,
                                        xx +m_x*2,
                                        yy +m_y,
                                        zz +m_z*2,
                                        0.0, // X 方向速度
                                        0.0, // 轻微向上速度（可选）
                                        0.0  // Z 方向速度
                                );
                            }
                            if(entity.age%10==0){
                                color = new Vector3f(0.0f, 0.0f, 0.0f);
                                for(int i=0;i<120;++i){
                                    m_x = Math.random() - 0.5;
                                    m_y = Math.random() - 0.5;
                                    m_z = Math.random() - 0.5;
                                    client.world.addParticle(
                                            new DustParticleEffect(color,1.5f),
                                            true,
                                            xx+5*Math.cos(i*3*Math.PI/180)+m_x*2,
                                            yy,
                                            zz+5*Math.sin(i*3*Math.PI/180)+m_z*2,
                                            1.5*Math.cos(i*9*Math.PI/180),
                                            0,
                                            1.5*Math.sin(i*9*Math.PI/180)
                                    );
                                }
                            }
                        }
                    }
                    if(entity instanceof FireballEntity &&((FireballEntity) entity).getOwner() instanceof GhastEntity ghastEntity
                            &&(ghastEntity.getDataTracker().get(STL)||ghastEntity.getDataTracker().get(CPPP))){
                            Vector3f color=new Vector3f(1.0f,1.0f,0.0f);
                            for(int i=1;i<=5;++i) {
                                double m_x = Math.random() - 0.5;
                                double m_y = Math.random() - 0.5;
                                double m_z = Math.random() - 0.5;
                                client.world.addParticle(
                                        new DustParticleEffect(color, 2.0f), // 使用 DustParticleEffect 定义颜色
                                        entity.getX() + m_x*4,
                                        entity.getY() + m_y*4,
                                        entity.getZ() + m_z*4,
                                        0.0f, 0.0f, 0.0f
                                );
                            }
                            color=new Vector3f(1.0f,0.0f,0.0f);
                            for(int i=1;i<=35;++i) {
                                double m_x = Math.random() - 0.5;
                                double m_y = Math.random() - 0.5;
                                double m_z = Math.random() - 0.5;
                                client.world.addParticle(
                                        new DustParticleEffect(color, 2.0f), // 使用 DustParticleEffect 定义颜色
                                        entity.getX() + m_x*4,
                                        entity.getY() + m_y*4,
                                        entity.getZ() + m_z*4,
                                        0.0f, 0.0f, 0.0f
                                );
                            }
                    }
                }
            }
        });
    }
}
