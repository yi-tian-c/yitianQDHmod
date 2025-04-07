package com.yi_tian.client.render;

import com.yi_tian.YitianMod;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.GhastEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.Identifier;

public class CustomGhastRenderLayer extends FeatureRenderer<GhastEntity, GhastEntityModel<GhastEntity>> {
    public static final TrackedData<Boolean> XTL = DataTracker.registerData(GhastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> NC = DataTracker.registerData(GhastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> STL = DataTracker.registerData(GhastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> CPPP = DataTracker.registerData(GhastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Identifier TEXTURE_XTL = new Identifier(YitianMod.MOD_ID, "textures/entity/ghast_xtl.png");
    private static final Identifier TEXTURE_NC = new Identifier(YitianMod.MOD_ID, "textures/entity/ghast_xtl.png");
    private static final Identifier TEXTURE_STL = new Identifier(YitianMod.MOD_ID, "textures/entity/ghast_stl.png");
    private static final Identifier TEXTURE_CPPP = new Identifier(YitianMod.MOD_ID, "textures/entity/ghast_stl.png");
    public CustomGhastRenderLayer(FeatureRendererContext<GhastEntity, GhastEntityModel<GhastEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, GhastEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.getDataTracker().get(XTL)) {
            // 渲染模型并应用纹理
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE_XTL));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1.0F);
        }
        if (entity.getDataTracker().get(NC)) {
            // 渲染模型并应用纹理
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE_NC));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1.0F);
        }
        if (entity.getDataTracker().get(STL)) {
            // 渲染模型并应用纹理
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE_STL));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1.0F);
        }
        if (entity.getDataTracker().get(CPPP)) {
            // 渲染模型并应用纹理
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE_CPPP));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1F, 1F, 1F, 1.0F);
        }
    }
}
