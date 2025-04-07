package com.yi_tian.item.custom;

import com.google.common.collect.Maps;
import com.yi_tian.YitianMod;
import com.yi_tian.entity.ModEntities;
import com.yi_tian.entity.ModSkeletonEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.util.*;

public class BoneStaffItem extends Item{
    private static final List<LivingEntity> targetentity = new ArrayList<>();
    private static final Map<PlayerEntity, LivingEntity> playerCurrentLookingentity = new HashMap<>();

    public BoneStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setCurrentHand(hand);
        ItemStack stack = user.getStackInHand(hand);

        // 获取玩家准心对准的实体
        if(!user.getWorld().isClient) {
            YitianMod.LOGGER.info("正在检查");
            LivingEntity hitResult =null;
            Box box1 = user.getBoundingBox().expand(30);
            for (Entity entity : world.getEntitiesByClass(LivingEntity.class, box1, entity -> true)) {
                // 跳过玩家自己
                if (entity == user || entity instanceof ModSkeletonEntity) {
                    continue;
                }
                // 判断玩家是否在直视这个生物的眼睛
                if (isLookingAtEntity(user, entity)) {
                    // 如果玩家在直视这个生物，获取该生物实体
                    hitResult = (LivingEntity) entity;
                    break;
                }
            }
            if (hitResult != null) {
                YitianMod.LOGGER.info("不为空");
                LivingEntity targetEntity=hitResult;
                if (targetEntity instanceof LivingEntity) {
                    // 在目标生物上生成红色粒子效果
                    if (world instanceof ServerWorld) {
                        // 生成粒子群
                        DustParticleEffect particleEffect = new DustParticleEffect(
                                new Vector3f(1.0f, 0.0f, 0.0f), // 归一化颜色
                                1.0f // 粒子尺寸
                        );
                        ((ServerWorld)world).spawnParticles(
                                particleEffect,
                                targetEntity.getX() + 0.5,
                                targetEntity.getY() + 1.5,
                                targetEntity.getZ() + 0.5,
                                20,    // 粒子数量
                                0.3,   // 水平扩散
                                0.5,   // 垂直扩散
                                0.3,   // 水平扩散
                                0.1    // 速度基数
                        );
                    }

                    // 获取周围 30 格内的 ModSkeletonEntity
                    Box box = new Box(user.getBlockPos()).expand(30);
                    for (Entity entity : world.getEntitiesByClass(ModSkeletonEntity.class, box, e -> true)) {
                        if (entity instanceof ModSkeletonEntity) {
                            ((ModSkeletonEntity) entity).setTarget((LivingEntity) targetEntity);
                        }
                    }

                    return TypedActionResult.success(stack);
                }
            }
        }

        return TypedActionResult.consume(stack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int useDuration = this.getMaxUseTime(stack) - remainingUseTicks;

        if (useDuration > 20) {
            super.onStoppedUsing(stack, world, user, remainingUseTicks);

            if (!world.isClient) { // 确保只在服务端执行
                ServerWorld serverWorld = (ServerWorld) world;
                Random random = user.getRandom();

                // 尝试最多100次寻找有效位置
                for (int attempts = 0; attempts < 25; attempts++) {
                    // 生成极坐标随机点（均匀分布）
                    double radius = 10 * Math.sqrt(random.nextDouble());
                    double angle = random.nextDouble() * 2 * Math.PI;

                    // 计算相对坐标
                    double xOffset = radius * Math.cos(angle);
                    double zOffset = radius * Math.sin(angle);

                    // 从玩家Y坐标+20开始向下寻找地面
                    BlockPos.Mutable pos = new BlockPos.Mutable(
                            user.getX() + xOffset,
                            user.getY() + 20,
                            user.getZ() + zOffset
                    );

                    // 向下寻找固体表面
                    while (pos.getY() > world.getBottomY() &&
                            !world.getBlockState(pos).isSolid()) {
                        pos.move(Direction.DOWN);
                    }

                    // 检查是否找到有效位置
                    if (pos.getY() > world.getBottomY() &&
                            world.getBlockState(pos.up()).isAir()) {

                        // 生成Mod骷髅实体
                        ModSkeletonEntity skeleton = new ModSkeletonEntity(
                                ModEntities.MOD_SKELETON,  // 你的自定义实体类型
                                world
                        );
                        skeleton.refreshPositionAndAngles(
                                pos.getX() + 0.5,
                                pos.getY() + 1,
                                pos.getZ() + 0.5,
                                random.nextFloat() * 360f,
                                0f
                        );
                        world.spawnEntity(skeleton);
                        // 为骷髅装备铁头盔
                        ItemStack ironHelmet = new ItemStack(Items.IRON_HELMET); // 创建铁头盔物品堆
                        skeleton.equipStack(EquipmentSlot.HEAD, ironHelmet); // 为骷髅装备铁头盔

                        // 为骷髅装备附魔弓
                        ItemStack enchantedBow = new ItemStack(Items.BOW); // 创建弓物品堆
                        // 为弓添加附魔效果（例如：力量I）

                        skeleton.equipStack(EquipmentSlot.MAINHAND, enchantedBow); // 为骷髅装备弓
                        // 创建紫色粒子效果（RGB: 158,0,255）
                        DustParticleEffect particleEffect = new DustParticleEffect(
                                new Vector3f(158/255f, 0, 255/255f), // 归一化颜色
                                2.0f // 粒子尺寸
                        );

                        // 生成粒子群
                        serverWorld.spawnParticles(
                                particleEffect,
                                pos.getX() + 0.5,
                                pos.getY() + 1.5,
                                pos.getZ() + 0.5,
                                100,    // 粒子数量
                                0.3,   // 水平扩散
                                0.5,   // 垂直扩散
                                0.3,   // 水平扩散
                                0.1    // 速度基数
                        );
                    }
                }
            }
        } else {
            super.onStoppedUsing(stack, world, user, remainingUseTicks);
        }
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
        return dotProduct > (double) 1.0F - 0.08 / distance && player.canSee(entity);
    }
}
