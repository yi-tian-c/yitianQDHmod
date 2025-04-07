package com.yi_tian.item.custom;

import com.yi_tian.entity.ModEntities;
import com.yi_tian.entity.ModSkeletonEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.Random;

import static com.yi_tian.client.render.CustomGhastRenderLayer.NC;
import static com.yi_tian.client.render.CustomGhastRenderLayer.XTL;

public class Nctotem extends Item {
    public Nctotem(Settings settings) {
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
        if(useDuration>=10){
            Random rand=new Random();
            stack.decrement(1);
            // 生成XTL
            GhastEntity ghastEntity = new GhastEntity(
                    EntityType.GHAST,  // 你的自定义实体类型
                    world
            );
            ghastEntity.refreshPositionAndAngles(
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    rand.nextFloat() * 360f,
                    0f
            );
            world.spawnEntity(ghastEntity);
            ghastEntity.getDataTracker().set(XTL,true);
            ghastEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 900000000, 70,false,false,false));
            ghastEntity.setHealth(500);
            for(int i=0;i<10;++i){
                double dx = user.getX() + (rand.nextDouble() - 0.5) * 40;
                double dy = user.getY() + (rand.nextDouble() - 0.5) * 16;
                double dz = user.getZ() + (rand.nextDouble() - 0.5) * 40;
                GhastEntity ghastEntity1 = new GhastEntity(
                        EntityType.GHAST,
                        world
                );
                ghastEntity1.refreshPositionAndAngles(
                        dx,
                        dy,
                        dz,
                        rand.nextFloat() * 360f,
                        0f
                );
                world.spawnEntity(ghastEntity1);
                ghastEntity1.getDataTracker().set(NC,true);
                ghastEntity1.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 900000000, 10,false,false,false));
                ghastEntity1.setHealth(50);
            }
        }
    }
}
