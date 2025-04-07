package com.yi_tian.mixin;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MeleeAttackGoal.class)
public interface MeleeAttackGoalAccessor {
    @Accessor("mob")
    PathAwareEntity mob();
    @Accessor("cooldown")
    int getCooldown();
    @Accessor("cooldown")
    void setCooldown(int cooldown);
}
