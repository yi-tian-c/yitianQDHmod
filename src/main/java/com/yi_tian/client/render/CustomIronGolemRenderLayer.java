package com.yi_tian.client.render;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.IronGolemEntity;

public class CustomIronGolemRenderLayer extends FeatureRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
    public static final TrackedData<Boolean> IS_ENCHANTMENT= DataTracker.registerData(IronGolemEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final RenderLayer ENCHANTED_GLINT = RenderLayer.getEntityGlint();
    public CustomIronGolemRenderLayer(FeatureRendererContext<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, IronGolemEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.getDataTracker().get(IS_ENCHANTMENT)) {
            // 使用顶灯亮度（15）保证附魔效果在任何光照条件下都可见
            int packedLight = LightmapTextureManager.pack(15, 15);

            // 获取模型并复制当前姿势状态
            IronGolemEntityModel<IronGolemEntity> model = this.getContextModel();
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
