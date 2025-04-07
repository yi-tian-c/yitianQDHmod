package com.yi_tian.entity;

import com.yi_tian.YitianMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<ModSkeletonEntity> MOD_SKELETON = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(YitianMod.MOD_ID, "modskeleton"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ModSkeletonEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .build()
    );
    // 注册所有自定义实体的方法
    public static void registerEntities() {
    }
    // 为自定义实体注册属性
    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(MOD_SKELETON, ModSkeletonEntity.createAModSkeletonAttributes());
    }

}
