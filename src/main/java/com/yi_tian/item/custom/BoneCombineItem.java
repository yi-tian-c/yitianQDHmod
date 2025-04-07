package com.yi_tian.item.custom;

import com.yi_tian.tag.ModBlockTags;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static net.minecraft.item.BowItem.getPullProgress;

public class BoneCombineItem extends AxeItem {
    public BoneCombineItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state){
        return (state.isIn(BlockTags.AXE_MINEABLE)||state.isIn(BlockTags.PICKAXE_MINEABLE)||state.isIn(BlockTags.SHOVEL_MINEABLE)) ? this.miningSpeed: 1.0F;
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // 获取右键点击的方块位置
        BlockPos blockPos = context.getBlockPos();

        // 获取方块状态
        BlockState blockState = context.getWorld().getBlockState(blockPos);

        // 判断是否是能被镐子破坏的方块
        if (blockState.isIn(BlockTags.AXE_MINEABLE)||blockState.isIn(BlockTags.PICKAXE_MINEABLE)||blockState.isIn(BlockTags.SHOVEL_MINEABLE)) {
            // 替换为骨块
            context.getWorld().setBlockState(blockPos, Blocks.BONE_BLOCK.getDefaultState());

            // 播放替换音效（可选）
            context.getWorld().playSound(null, blockPos, SoundEvents.BLOCK_BONE_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
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
        ItemStack itemStack = user.getStackInHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int i = this.getMaxUseTime(stack) - remainingUseTicks;
        if(i>20) {
            // 获取玩家位置
            BlockPos playerPos = user.getBlockPos();

            // 半径为10格
            int radius = 10;

            // 遍历半径范围内的所有方块
            for (int x = playerPos.getX() - radius; x <= playerPos.getX() + radius; x++) {
                for (int y = playerPos.getY() - radius; y <= playerPos.getY() + radius; y++) {
                    for (int z = playerPos.getZ() - radius; z <= playerPos.getZ() + radius; z++) {
                        // 计算当前方块位置与玩家位置的距离
                        double distance = Math.sqrt((x - playerPos.getX()) * (x - playerPos.getX()) +
                                (y - playerPos.getY()) * (y - playerPos.getY()) +
                                (z - playerPos.getZ()) * (z - playerPos.getZ()));

                        // 如果距离在半径范围内
                        if (distance <= radius) {
                            double rand = Math.random();
                            if (1 - (distance / radius) + 0.4 > rand) {
                                // 获取当前方块位置
                                BlockPos blockPos = new BlockPos(x, y, z);

                                // 获取方块状态
                                BlockState blockState = world.getBlockState(blockPos);

                                if (blockState.isIn(BlockTags.AXE_MINEABLE)||blockState.isIn(BlockTags.PICKAXE_MINEABLE)||blockState.isIn(BlockTags.SHOVEL_MINEABLE)) {
                                    // 替换为骨块
                                    world.setBlockState(blockPos, Blocks.BONE_BLOCK.getDefaultState());
                                }
                            }
                        }
                    }
                }
            }

            super.onStoppedUsing(stack, world, user, remainingUseTicks);
        }
    }
}
