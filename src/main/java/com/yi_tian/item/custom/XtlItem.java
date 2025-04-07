package com.yi_tian.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import static com.yi_tian.client.render.CustomGhastRenderLayer.*;
import static com.yi_tian.client.render.CustomGhastRenderLayer.STL;

public class XtlItem extends Item {
    public XtlItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity instanceof GhastEntity ghastEntity){
            stack.decrement(1);
            ghastEntity.getDataTracker().set(NC,false);
            ghastEntity.getDataTracker().set(XTL,true);
            ghastEntity.getDataTracker().set(CPPP,false);
            ghastEntity.getDataTracker().set(STL,false);
            ghastEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 900000000, 70,false,false,false));
            ghastEntity.setHealth(500);
            return ActionResult.SUCCESS;
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}
