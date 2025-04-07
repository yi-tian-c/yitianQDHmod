package com.yi_tian.item.custom;

import com.yi_tian.entity.custom.ModWitherSkullEntity2;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class BoneStaffItem3 extends Item {
    public BoneStaffItem3(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!user.getWorld().isClient) {
            ModWitherSkullEntity2 skullEntity = new ModWitherSkullEntity2(EntityType.WITHER_SKULL, user.getWorld());
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
                    2.5F,           // 速度
                    1.0F            // 精度
            );
            user.getWorld().spawnEntity(skullEntity);
        }
//         5. 播放音效和更新统计
        world.playSound(
                null,
                user.getX(),
                user.getY(),
                user.getZ(),
                SoundEvents.ENTITY_WITHER_SHOOT,
                SoundCategory.PLAYERS,
                0.3F,
                1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F)
        );
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        ItemStack m_itemStack = user.getStackInHand(hand);
//        user.getItemCooldownManager().set(m_itemStack.getItem(), 20);
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
