package com.yi_tian.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class BoneShovelItem extends ShovelItem {
    public BoneShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // 获取右键点击的方块位置
        BlockPos blockPos = context.getBlockPos();

        // 获取方块状态
        BlockState blockState = context.getWorld().getBlockState(blockPos);

        // 判断是否是能被铲子破坏的方块
        if (blockState.isIn(BlockTags.SHOVEL_MINEABLE)) {
            // 替换为骨块
            context.getWorld().setBlockState(blockPos, Blocks.BONE_BLOCK.getDefaultState());

            // 播放替换音效（可选）
            context.getWorld().playSound(null, blockPos, SoundEvents.BLOCK_BONE_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);

            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }
}
