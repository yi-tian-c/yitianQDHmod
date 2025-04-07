package com.yi_tian.item.custom;

import com.yi_tian.entity.custom.ModWitherSkullEntity2;
import com.yi_tian.entity.custom.ModWitherSkullEntity3;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BoneStaffItem4 extends Item {

    public BoneStaffItem4(Settings settings) {
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
        return TypedActionResult.consume(stack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int useDuration = this.getMaxUseTime(stack) - remainingUseTicks;
        if(useDuration>20){
            if (!user.getWorld().isClient) {
                ModWitherSkullEntity3 skullEntity = new ModWitherSkullEntity3(EntityType.WITHER_SKULL, user.getWorld());
                skullEntity.setOwner(user);
                skullEntity.setPosition(
                        user.getX() - (double) (user.getWidth() + 1.0F) * 0.5 * (double) MathHelper.sin(user.bodyYaw * (float) (Math.PI / 180.0)),
                        user.getEyeY() - 0.5F,
                        user.getZ() + (double) (user.getWidth() + 1.0F) * 0.5 * (double) MathHelper.cos(user.bodyYaw * (float) (Math.PI / 180.0))
                );
                skullEntity.setVelocity(
                        user, // 发射者
                        user.getPitch(), // 俯仰角1
                        user.getYaw(),   // 偏航角
                        0.0F,           // 滚动角
                        6.5F,           // 速度
                        1.0F            // 精度
                );
                user.getWorld().spawnEntity(skullEntity);
            }
        }
        else{
            super.onStoppedUsing(stack, world, user, remainingUseTicks);
        }
    }
}
