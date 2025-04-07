package com.yi_tian.mixin;

import com.yi_tian.YitianMod;
import com.yi_tian.client.render.*;
import com.yi_tian.entity.custom.ModFireBallEntity;
import com.yi_tian.entity.custom.ModWitherSkullEntity;
import com.yi_tian.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;

import static com.yi_tian.client.render.CustomGhastRenderLayer.*;
import static java.lang.Math.abs;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {
    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void onSetTarget(LivingEntity target, CallbackInfo ci) {
        // 仅处理骷髅的目标设定
        if ((Object) this instanceof SkeletonEntity) {
            if (target instanceof PlayerEntity player && isWearingFullBoneArmor(player)) {
                ci.cancel();       // 取消目标设定
                this.setAttacking(null); // 清除攻击状态
                if (!this.getWorld().isClient&&player.age%10==0) {
                    ((ServerWorld) this.getWorld()).spawnParticles(
                            ParticleTypes.HEART,
                            this.getX(), this.getY() + 1.0, this.getZ(),
                            3, 0.3, 0.3, 0.3, 0.1
                    );
                }
            }
        }
    }

    // 检查玩家是否穿戴全套骨甲（可替换为标签检测）
    private boolean isWearingFullBoneArmor(PlayerEntity player) {
        return player.getInventory().getArmorStack(3).getItem() == ModItems.BONE_HELMET &&
                player.getInventory().getArmorStack(2).getItem() == ModItems.BONE_CHEST &&
                player.getInventory().getArmorStack(1).getItem() == ModItems.BONE_LEGGING &&
                player.getInventory().getArmorStack(0).getItem() == ModItems.BONE_BOOTS;
    }
    // 存储上一tick的空中状态
    double fallVelocity;
    private boolean wasInAir = true;
    private static final double MIN_TRIGGER_VELOCITY = 0.4;
    @Shadow
    protected abstract ActionResult interactMob(PlayerEntity player, Hand hand);

    @Shadow public abstract void setTarget(@Nullable LivingEntity target);

    @Shadow public abstract boolean startRiding(Entity entity, boolean force);

    @Shadow @Nullable public abstract LivingEntity getTarget();

    @Shadow @Final protected GoalSelector goalSelector;

    @Shadow public abstract void writeCustomDataToNbt(NbtCompound nbt);

    @Shadow public abstract Iterable<ItemStack> getArmorItems();

    @Shadow @Nullable private LivingEntity target;
    private static final TrackedData<Long> LAST_JUMP_TIME =
            DataTracker.registerData(IronGolemEntity.class, TrackedDataHandlerRegistry.LONG);
    private static final TrackedData<Long> GHOST_TP =
            DataTracker.registerData(GhastEntity.class, TrackedDataHandlerRegistry.LONG);
    private static final TrackedData<Long> GHOST_STL =
            DataTracker.registerData(GhastEntity.class, TrackedDataHandlerRegistry.LONG);
    @Inject(method = "initDataTracker",at=@At("RETURN"))
    private void initDataTrackerMixin(CallbackInfo ci){
        MobEntity mob = (MobEntity) (Object) this;
        if(mob instanceof IronGolemEntity ironGolemEntity) {
            ironGolemEntity.getDataTracker().startTracking(CustomIronGolemRenderLayer.IS_ENCHANTMENT, false);
            ironGolemEntity.getDataTracker().startTracking(LAST_JUMP_TIME, 0L);
        }
        if(mob instanceof SnowGolemEntity snowGolemEntity) {
            snowGolemEntity.getDataTracker().startTracking(CustomSnowGolemRenderLayer.IS_ENCHANTMENT, false);
        }
        if(mob instanceof ZombieEntity zombieEntity) {
            zombieEntity.getDataTracker().startTracking(CustomZombieRenderLayer.LOYAL, false);
        }
        if(mob instanceof SpiderEntity spiderEntity) {
            spiderEntity.getDataTracker().startTracking(CustomSpiderRenderLayer.LOYAL, false);
        }
        if(mob instanceof WardenEntity wardenEntity) {
            wardenEntity.getDataTracker().startTracking(CustomWardenRenderLayer.LOYAL, false);
        }
        if(mob instanceof SkeletonEntity skeletonEntity) {
            skeletonEntity.getDataTracker().startTracking(CustomSkeletonRenderLayer.LOYAL, false);
        }
        if(mob instanceof GhastEntity ghastEntity) {
            ghastEntity.getDataTracker().startTracking(XTL, false);
            ghastEntity.getDataTracker().startTracking(NC,false);
            ghastEntity.getDataTracker().startTracking(CustomGhastRenderLayer.STL,false);
            ghastEntity.getDataTracker().startTracking(CustomGhastRenderLayer.CPPP,false);
            ghastEntity.getDataTracker().startTracking(GHOST_TP, 0L);
            ghastEntity.getDataTracker().startTracking(GHOST_STL, 0L);
        }
    }
    @Inject(method = "tick",at = @At("HEAD"))
    private void tickMixin(CallbackInfo ci){
        MobEntity mob = (MobEntity) (Object) this;
        if(mob instanceof IronGolemEntity ironGolemEntity&&ironGolemEntity.getDataTracker().get(CustomIronGolemRenderLayer.IS_ENCHANTMENT)){
            if(ironGolemEntity.age%20==0){
                ironGolemEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 300, 2,false,false,false));
            }
            if(ironGolemEntity.getTarget()!=null){
                LivingEntity target=ironGolemEntity.getTarget();
                // 计算水平距离
                double horizontalDistance = Math.sqrt(
                        (ironGolemEntity.getPos().x-target.getPos().x)*(ironGolemEntity.getPos().x-target.getPos().x)+
                                (ironGolemEntity.getPos().z-target.getPos().z)*(ironGolemEntity.getPos().z-target.getPos().z)
                );
                // 获取冷却状态
                long lastJump = ironGolemEntity.getDataTracker().get(LAST_JUMP_TIME);
                long currentTime = ironGolemEntity.getWorld().getTime();
                boolean canJump = (currentTime - lastJump) > 100; // 5秒冷却（150 ticks）
                if (horizontalDistance < 4.5 && canJump) {
                    // ====== 起跳爆炸 ======
                    ironGolemEntity.getWorld().createExplosion(
                            ironGolemEntity,
                            ironGolemEntity.getX(),
                            ironGolemEntity.getY(),
                            ironGolemEntity.getZ(),
                            3.0f, // 爆炸威力
                            World.ExplosionSourceType.NONE
                    );
                    // 生成额外粒子
                    for (int i = 0; i < 20; i++) {
                        ironGolemEntity.getWorld().addParticle(
                                ParticleTypes.EXPLOSION_EMITTER, // 粒子类型
                                ironGolemEntity.getX() + Math.random() * 2,
                                ironGolemEntity.getY() + Math.random() * 2,
                                ironGolemEntity.getZ() + Math.random() * 2,
                                (Math.random() - 0.5) * 0.5, // 随机速度
                                Math.random() * 0.5,
                                (Math.random() - 0.5) * 0.5
                        );
                    }
                    // 更新冷却时间
                    ironGolemEntity.getDataTracker().set(LAST_JUMP_TIME, currentTime);
                    // 获取双方坐标
                    Vec3d targetPos = target.getPos();
                    Vec3d golemPos = ironGolemEntity.getPos();

                    // 计算坐标差
                    double dx = targetPos.x - golemPos.x;
                    double dz = targetPos.z - golemPos.z;

                    // 弹道参数配置
                    final double gravity = 0.08; // Minecraft重力加速度
                    final double targetHeight = 15.0; // 跳跃高度

                    // 计算垂直初速度（公式推导：v = sqrt(2gh)）
                    double verticalVelocity = Math.sqrt(2 * gravity * targetHeight);

                    // 计算总滞空时间（上升+下落）
                    double airTime = (verticalVelocity / gravity) * 2; // 总时间=2*(v/g)

                    // 计算水平速度（确保落地时到达目标点）
                    double horizontalVelocityX = dx / airTime;
                    double horizontalVelocityZ = dz / airTime;

                    // 应用运动向量（添加微小Y偏移确保离开地面）
                    ironGolemEntity.setVelocity(new Vec3d(
                            horizontalVelocityX*4.5,
                            verticalVelocity + 0.1, // 防止卡地
                            horizontalVelocityZ*4.5
                    ));

                    // 强制更新运动状态
                    ironGolemEntity.velocityDirty = true;

                    // 添加特效（紫色粒子+音效）
                    ironGolemEntity.getWorld().addParticle(
                            ParticleTypes.ELECTRIC_SPARK,
                            golemPos.x, golemPos.y + 1, golemPos.z,
                            0, 0.5, 0
                    );
                    ironGolemEntity.playSound(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, 1.0f, 0.5f);
                }
            }
            // 当前是否在空中（未接触地面）
            boolean isInAir = !ironGolemEntity.isOnGround() && !ironGolemEntity.isClimbing();
            if (wasInAir && !isInAir) {
                if (fallVelocity >= MIN_TRIGGER_VELOCITY) {
                    // 计算爆炸强度（基础2.0 + 速度倍率）
                    float power = (float) (1.0 + (fallVelocity - MIN_TRIGGER_VELOCITY) * 3.0);

                    // 生成爆炸（不破坏方块）
                    ironGolemEntity.getWorld().createExplosion(
                            ironGolemEntity,
                            ironGolemEntity.getX(),
                            ironGolemEntity.getY(),
                            ironGolemEntity.getZ(),
                            power,
                            World.ExplosionSourceType.NONE
                    );
                    // 生成额外粒子
                    for (int i = 0; i < 2; i++) {
                        ironGolemEntity.getWorld().addParticle(
                                ParticleTypes.EXPLOSION_EMITTER, // 粒子类型
                                ironGolemEntity.getX() + Math.random() * 3,
                                ironGolemEntity.getY() + Math.random() * 2,
                                ironGolemEntity.getZ() + Math.random() * 3,
                                (Math.random() - 0.5) * 0.5, // 随机速度
                                Math.random() * 0.5,
                                (Math.random() - 0.5) * 0.5
                        );
                    }

                }
            }
            fallVelocity=abs(ironGolemEntity.getVelocity().y);
            wasInAir = isInAir;
        }
        if(mob instanceof SnowGolemEntity snowGolemEntity&&snowGolemEntity.getDataTracker().get(CustomSnowGolemRenderLayer.IS_ENCHANTMENT)){
            if(!snowGolemEntity.getWorld().isClient) {
                if (snowGolemEntity.getTarget() != null) {
                    snowGolemEntity.shootAt(snowGolemEntity.getTarget(), 1.0f);
                }
            }
        }
        if(mob instanceof SkeletonEntity skeletonEntity&&skeletonEntity.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL)){
            if(!skeletonEntity.getWorld().isClient) {
                if (mob.getTarget() != null&&!(mob.getTarget() instanceof PlayerEntity)) {
                    YitianMod.LOGGER.info("骷髅射箭");
                    skeletonEntity.shootAt(mob.getTarget(), 1.0f);
                }
            }
        }
        if(mob instanceof GhastEntity ghastEntity){
            if(ghastEntity.getDataTracker().get(XTL)||ghastEntity.getDataTracker().get(NC)){
                if(ghastEntity.getTarget()==null){
                    LivingEntity closestEntity = null;
                    double closestDistance = Double.MAX_VALUE;

                    for (LivingEntity entity : mob.getWorld().getEntitiesByClass(LivingEntity.class, mob.getBoundingBox().expand(60), entity ->
                            entity != mob && entity instanceof GhastEntity ghastEntity1 &&
                                    (ghastEntity1.getDataTracker().get(STL)||ghastEntity1.getDataTracker().get(CPPP))&&
                                    entity.isAlive()
                    )) {
                        double distance = mob.squaredDistanceTo(entity);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestEntity = entity;
                        }
                    }

                    if (closestEntity != null) {
                        mob.setTarget(closestEntity);
                    }
                }
            }
            if(ghastEntity.getDataTracker().get(STL)||ghastEntity.getDataTracker().get(CPPP)){
                if(ghastEntity.getTarget()==null){
                    LivingEntity closestEntity = null;
                    double closestDistance = Double.MAX_VALUE;

                    for (LivingEntity entity : mob.getWorld().getEntitiesByClass(LivingEntity.class, mob.getBoundingBox().expand(60), entity ->
                            entity != mob && entity instanceof GhastEntity ghastEntity1 &&
                                    (ghastEntity1.getDataTracker().get(NC)||ghastEntity1.getDataTracker().get(XTL))&&
                                    entity.isAlive()
                    )) {
                        double distance = mob.squaredDistanceTo(entity);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestEntity = entity;
                        }
                    }

                    if (closestEntity != null) {
                        mob.setTarget(closestEntity);
                    }
                }
            }
        }
        if(mob instanceof GhastEntity ghastEntity){
            if(ghastEntity.getTarget()!=null){
                LivingEntity livingEntity=ghastEntity.getTarget();
                if(!livingEntity.isAlive()){
                    ghastEntity.setTarget(null);
                }
            }
        }
        if(mob instanceof GhastEntity ghastEntity&&ghastEntity.getDataTracker().get(XTL)&&ghastEntity.getTarget()!=null){
            if(ghastEntity.getDataTracker().get(GHOST_TP) <= 0){
                triggerNearbyGhastTeleport(ghastEntity);
                ghastEntity.getDataTracker().set(GHOST_TP, 100L);
                attemptSafeTeleport(ghastEntity);
                YitianMod.LOGGER.info("tp");
            }
            else{
                // 减少冷却时间
                ghastEntity.getDataTracker().set(GHOST_TP,ghastEntity.getDataTracker().get(GHOST_TP)-1);;
            }
        }
        if(mob instanceof GhastEntity ghastEntity&&ghastEntity.getDataTracker().get(STL)&&ghastEntity.getTarget()!=null){
            if(ghastEntity.getDataTracker().get(GHOST_STL) <= 0){
                startGhastfireball(ghastEntity);
                ghastEntity.getDataTracker().set(GHOST_STL, 50L);
                spawnfileball(ghastEntity);
                YitianMod.LOGGER.info("tp");
            }
            else{
                // 减少冷却时间
                ghastEntity.getDataTracker().set(GHOST_STL,ghastEntity.getDataTracker().get(GHOST_STL)-1);;
            }
        }
        if(mob instanceof SpiderEntity spiderEntity&&spiderEntity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL)){
            if(!spiderEntity.getWorld().isClient) {
                if (mob.getTarget() != null) {
                    if (spiderEntity.age % 20 == 0) {
                        LivingEntity target = spiderEntity.getTarget();
                        BlockPos targetPos = target.getBlockPos();

                        // 计算目标脚下的位置
                        BlockPos targetFeetPos = new BlockPos(
                                MathHelper.floor(target.getX()),
                                MathHelper.floor(target.getY()),
                                MathHelper.floor(target.getZ())
                        );

                        // 在目标脚下生成蜘蛛网
                        if (spiderEntity.getWorld().getBlockState(targetFeetPos).isAir()) {
                            spiderEntity.getWorld().setBlockState(targetFeetPos, Blocks.COBWEB.getDefaultState());
                        }
                    }
                }
            }
        }
        if ((mob instanceof ZombieEntity zombie && zombie.getDataTracker().get(CustomZombieRenderLayer.LOYAL))||
                (mob instanceof SpiderEntity spider && spider.getDataTracker().get(CustomSpiderRenderLayer.LOYAL))||
                (mob instanceof SkeletonEntity skeleton && skeleton.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL))) {

            // 阻止燃烧
            mob.extinguish();
            mob.setOnFire(false);

            LivingEntity currentTarget = mob.getTarget();

            // 阻止起内讧
            if (currentTarget == null || !currentTarget.isAlive() ||
                    (currentTarget instanceof ZombieEntity && currentTarget.getDataTracker().get(CustomZombieRenderLayer.LOYAL))||
                    (currentTarget instanceof SpiderEntity && currentTarget.getDataTracker().get(CustomSpiderRenderLayer.LOYAL))||
                    (currentTarget instanceof WardenEntity && currentTarget.getDataTracker().get(CustomWardenRenderLayer.LOYAL))||
                    (currentTarget instanceof SkeletonEntity && currentTarget.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL))) {
                mob.setTarget(null);
            }

            for (PlayerEntity player : mob.getWorld().getPlayers()) {
                LivingEntity lastAttackedEntity = player.getAttacking();

                // 优先攻击玩家攻击的生物
                if (lastAttackedEntity != null && lastAttackedEntity.isAlive() &&
                        !(lastAttackedEntity instanceof ZombieEntity && lastAttackedEntity.getDataTracker().get(CustomZombieRenderLayer.LOYAL))&&
                        !(lastAttackedEntity instanceof SpiderEntity && lastAttackedEntity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL))&&
                        !(lastAttackedEntity instanceof SkeletonEntity && lastAttackedEntity.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL))&&
                !(lastAttackedEntity instanceof WardenEntity && lastAttackedEntity.getDataTracker().get(CustomWardenRenderLayer.LOYAL))) {
                    mob.setTarget(lastAttackedEntity);
                    return;
                }

                // 如果没有攻击目标，寻找距离最近的敌对生物
                if (mob.getTarget() == null&&player.getOffHandStack().getItem()==ModItems.BATON) {
                    LivingEntity closestEntity = null;
                    double closestDistance = Double.MAX_VALUE;

                    for (LivingEntity entity : mob.getWorld().getEntitiesByClass(LivingEntity.class, mob.getBoundingBox().expand(20), entity ->
                            entity != mob && entity != player &&
                                    !(entity instanceof ZombieEntity && entity.getDataTracker().get(CustomZombieRenderLayer.LOYAL)) &&
                                    !(entity instanceof SpiderEntity && entity.getDataTracker().get(CustomSpiderRenderLayer.LOYAL)) &&
                                    !(entity instanceof SkeletonEntity && entity.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL)) &&
                                    !(entity instanceof WardenEntity && entity.getDataTracker().get(CustomWardenRenderLayer.LOYAL)) &&
                                    entity.isAlive()
                    )) {
                        double distance = mob.squaredDistanceTo(entity);
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestEntity = entity;
                        }
                    }

                    if (closestEntity != null) {
                        mob.setTarget(closestEntity);
                    }
                }
            }
        }
    }
    // 被魅惑的生物不会攻击玩家
    @Inject(method = "canTarget(Lnet/minecraft/entity/EntityType;)Z", at = @At("HEAD"), cancellable = true)
    private void onCanTarget(EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
        MobEntity mob = (MobEntity) (Object) this;

        if ((mob instanceof ZombieEntity zombie && zombie.getDataTracker().get(CustomZombieRenderLayer.LOYAL)) ||
                (mob instanceof SpiderEntity spider && spider.getDataTracker().get(CustomSpiderRenderLayer.LOYAL))||
                (mob instanceof SkeletonEntity skeletonEntity && skeletonEntity.getDataTracker().get(CustomSkeletonRenderLayer.LOYAL))||
                (mob instanceof WardenEntity wardenEntity && wardenEntity.getDataTracker().get(CustomWardenRenderLayer.LOYAL))) {
            if (type == EntityType.PLAYER) {
                cir.setReturnValue(false);
            }
        }
    }
    @Inject(method = "setTarget",at=@At("HEAD"),cancellable = true)
    private void setTargetMixin(LivingEntity target, CallbackInfo ci){
        MobEntity mob = (MobEntity) (Object) this;
        if(mob instanceof IronGolemEntity ironGolemEntity&&ironGolemEntity.getDataTracker().get(CustomIronGolemRenderLayer.IS_ENCHANTMENT)){
            if(target instanceof SnowGolemEntity){
                ci.cancel();
            }
        }
    }
    private void triggerNearbyGhastTeleport(GhastEntity ghastEntity) {
        Box area = new Box(
                ghastEntity.getX() - 80, ghastEntity.getY() - 40, ghastEntity.getZ() - 80,
                ghastEntity.getX() + 80, ghastEntity.getY() + 40, ghastEntity.getZ() + 80
        );

        // 获取范围内所有恶魂
        List<GhastEntity> ghasts = this.getWorld().getEntitiesByClass(
                GhastEntity.class,
                area,
                entity -> entity.getDataTracker().get(NC) // 过滤带 NC 标签的
        );

        // 对每个目标执行瞬移
        ghasts.forEach(ghast -> {
                attemptSafeTeleport(ghast);
        });
    }
    private void attemptSafeTeleport(GhastEntity ghast) {
        if (ghast.getTarget() != null) {
            Random random = ghast.getRandom();
            World world = ghast.getWorld();
            LivingEntity target=ghast.getTarget();
            // 获取目标坐标
            double targetX = target.getX();
            double targetY = target.getY();
            double targetZ = target.getZ();

            // 在目标位置召唤闪电（仅服务端）
            if (!world.isClient()) {
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.refreshPositionAfterTeleport(Vec3d.ofCenter(new BlockPos((int)targetX, (int)targetY, (int)targetZ)));
                world.spawnEntity(lightning);
            }

            // 播放音效（全端）
            world.playSound(
                    null,
                    targetX,
                    targetY,
                    targetZ,
                    SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER,
                    SoundCategory.HOSTILE,
                    5.0F,
                    1.0F
            );
            // 传送恶魂到目标附近
            double dx =(random.nextDouble() - 0.5) * 10;
            double dy =(random.nextDouble()-0.5) * 6;
            double dz =(random.nextDouble() - 0.5) * 10;
            if(dx>=0){
                dx+=6;
            }
            else{
                dx-=6;
            }
            if(dz>=0){
                dz+=6;
            }
            else{
                dz-=6;
            }
            world.playSound(
                    null, ghast.getX(), ghast.getY(), ghast.getZ(),
                    SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                    SoundCategory.HOSTILE,
                    1.0F,
                    1.0F
            );
            if (!world.isClient) {
                for (int i = 0; i < 15; ++i) {
                    ((ServerWorld) world).spawnParticles(
                            new DustParticleEffect(new Vector3f(1.0f, 0.0f, 1.0f), 1.0f),
                            ghast.getX() + (random.nextDouble() - 0.5) * 4,
                            ghast.getX() + (random.nextDouble() - 0.5) * 4,
                            ghast.getX() + (random.nextDouble() - 0.5) * 4,
                            5,    // 粒子数量
                            0.3,   // 水平扩散
                            0.5,   // 垂直扩散
                            0.3,   // 水平扩散
                            0.1    // 速度基数
                    );
                }
            }
            ghast.setPos(dx+targetX, dy+targetY, dz+targetZ);
        }
    }
    private void spawnfileball(GhastEntity ghast){
        if(ghast.getTarget()!=null){
            if(!ghast.getWorld().isClient) {
                LivingEntity target = ghast.getTarget();
                Random random = ghast.getRandom();
                World world = ghast.getWorld();
                // 获取目标坐标
                double targetX = target.getX();
                double targetY = target.getY();
                double targetZ = target.getZ();
                FireballEntity fireBallEntity1 = new FireballEntity(
                        EntityType.FIREBALL, this.getWorld()
                );
                // 设置位置和速度
                fireBallEntity1.setPosition(
                        targetX, // 居中在方块中心
                        targetY + 5,
                        targetZ
                );
                fireBallEntity1.setVelocity(0, -1.5f, 0); // 竖直向下速度
                fireBallEntity1.setOwner(ghast);
                ghast.getWorld().spawnEntity(fireBallEntity1);
                for(int i=1;i<=10;++i){
                    // 传送恶魂到目标附近
                    double dx =(random.nextDouble() - 0.5) * 10;
                    double dy =(random.nextDouble()) * 10;
                    double dz =(random.nextDouble() - 0.5) * 10;
                    FireballEntity fireBallEntity = new FireballEntity(
                            EntityType.FIREBALL, this.getWorld()
                    );
                    // 设置位置和速度
                    fireBallEntity.setPosition(
                            targetX+dx, // 居中在方块中心
                            targetY + dy,
                            targetZ+dz
                    );
                    fireBallEntity.setVelocity(0, -1.5f, 0); // 竖直向下速度
                    fireBallEntity.setOwner(ghast);
                    ghast.getWorld().spawnEntity(fireBallEntity);
                }
            }
        }
    }
    private void startGhastfireball(GhastEntity ghastEntity){
        Box area = new Box(
                ghastEntity.getX() - 80, ghastEntity.getY() - 40, ghastEntity.getZ() - 80,
                ghastEntity.getX() + 80, ghastEntity.getY() + 40, ghastEntity.getZ() + 80
        );

        // 获取范围内所有恶魂
        List<GhastEntity> ghasts = this.getWorld().getEntitiesByClass(
                GhastEntity.class,
                area,
                entity -> entity.getDataTracker().get(CPPP) // 过滤带 NC 标签的
        );

        ghasts.forEach(ghast -> {
            spawnfileball(ghast);
        });
    }
}
