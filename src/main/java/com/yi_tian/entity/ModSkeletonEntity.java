package com.yi_tian.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class ModSkeletonEntity extends SkeletonEntity {
    public ModSkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new FleeEntityGoal<>(this, WolfEntity.class, 6.0F, (double)1.0F, 1.2));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, (double)1.0F));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }
    public static DefaultAttributeContainer.Builder createAModSkeletonAttributes() {
        return VillagerEntity.createVillagerAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3D) // 移动速度
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D); // 最大生命值
    }

    @Override
    public EntityType<?> getType() {
        return EntityType.SKELETON;
    }
    // 显示名称
    @Override
    public Text getDisplayName() {
        return Text.translatable("entity.yi_tian.modskeletonentity");
    }

    @Override
    public void tick() {
        if(this.getTarget()!=null){
            if(this.getTarget().isAlive()==false){
                this.setTarget(null);
            }
        }
        super.tick();
    }
    @Override
    public boolean damage(DamageSource source, float amount) {
        // 检查伤害来源是否是弓箭
        if (source.isOf(DamageTypes.ARROW)) {
            // 如果是弓箭伤害，返回false表示免疫
            return false;
        }

        // 对于其他类型的伤害，调用父类的方法进行正常处理
        return super.damage(source, amount);
    }
    //    @Override
//    public void shootAt(LivingEntity target, float pullProgress) {
//        if (target.isAlive()) {
//            ItemStack itemStack = this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW));
//            ItemStack itemStack2 = this.getProjectileType(itemStack);
//            PersistentProjectileEntity persistentProjectileEntity = this.createArrowProjectile(itemStack2, pullProgress, itemStack);
//            double d = target.getX() - this.getX();
//            double e = target.getBodyY(0.3333333333333333) - persistentProjectileEntity.getY();
//            double f = target.getZ() - this.getZ();
//            double g = Math.sqrt(d * d + f * f);
//            persistentProjectileEntity.setVelocity(d, e + g * (double) 0.2F, f, 1.6F, (float) (14 - this.getWorld().getDifficulty().getId() * 4));
//            this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
//            this.getWorld().spawnEntity(persistentProjectileEntity);
//        }
//    }
}
