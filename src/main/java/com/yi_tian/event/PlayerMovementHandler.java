package com.yi_tian.event;

import com.yi_tian.item.ModItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlayerMovementHandler {
    public static void register() {
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                // 每 10 tick 检测一次，避免性能问题
                if (player.age % 10 == 0) {
                    checkAndApplyBoneArmorEffect(player);
                }
            }
        });
    }
    // 检测骨套并应用效果
    private static void checkAndApplyBoneArmorEffect(ServerPlayerEntity player) {
        Iterable<BlockPos> area = BlockPos.iterate(
                player.getBlockPos().add(-3, -1, -3), // 范围：3x3x3区域
                player.getBlockPos().add(3, 1, 3)
        );

        // 检查是否穿戴全套骨甲
        if (isWearingFullBoneArmor(player)) {
            for (BlockPos pos : area) {
                BlockState state = player.getWorld().getBlockState(pos);
                // 对可催熟的方块应用骨粉效果
                if (state.getBlock() instanceof Fertilizable fertilizable) {
                    if (fertilizable.isFertilizable(player.getWorld(), pos, state)) {
                        if (fertilizable.canGrow(player.getWorld(), player.getWorld().random, pos, state)) {
                            fertilizable.grow((ServerWorld) player.getWorld(), player.getWorld().random, pos, state);
                            spawnParticles(player.getWorld(), pos); // 生成粒子
                        }
                    }
                }
            }
        }
    }
    // 生成粒子效果（客户端）
    private static void spawnParticles(World world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    5,
                    0.3, 0.3, 0.3,
                    0.1
            );
        }
    }
    // 检测是否穿戴全套骨甲
    private static boolean isWearingFullBoneArmor(PlayerEntity player) {
        return player.getInventory().getArmorStack(3).getItem() == ModItems.BONE_HELMET &&   // 头盔
                player.getInventory().getArmorStack(2).getItem() == ModItems.BONE_CHEST &&   // 胸甲
                player.getInventory().getArmorStack(1).getItem() == ModItems.BONE_LEGGING && // 腿甲
                player.getInventory().getArmorStack(0).getItem() == ModItems.BONE_BOOTS;     // 靴子
    }
}
