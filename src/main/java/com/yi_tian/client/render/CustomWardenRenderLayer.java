package com.yi_tian.client.render;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.WardenEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.WardenEntity;

public class CustomWardenRenderLayer extends FeatureRenderer<WardenEntity, WardenEntityModel<WardenEntity>> {
    public static final TrackedData<Boolean> LOYAL= DataTracker.registerData(WardenEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final RenderLayer ENCHANTED_GLINT = RenderLayer.getEntityGlint();
    public CustomWardenRenderLayer(FeatureRendererContext<WardenEntity, WardenEntityModel<WardenEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WardenEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.getDataTracker().get(LOYAL)) {
            // 使用顶灯亮度（15）保证附魔效果在任何光照条件下都可见
            int packedLight = LightmapTextureManager.pack(15, 15);

            // 获取模型并复制当前姿势状态
            WardenEntityModel<WardenEntity> model = this.getContextModel();
            model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

            // 获取附魔光效的VertexConsumer
            VertexConsumer glintConsumer = vertexConsumers.getBuffer(ENCHANTED_GLINT);

            // 渲染所有模型部件
            matrices.push();
            model.render(matrices, glintConsumer, packedLight, OverlayTexture.DEFAULT_UV,
                    1.0F, 0.0F, 1.0F, 5.0F); // 使用半透明效果
            matrices.pop();
        }
    }
}
