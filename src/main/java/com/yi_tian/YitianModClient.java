package com.yi_tian;

import com.yi_tian.client.render.*;
import com.yi_tian.event.ModSkullHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.*;
import net.minecraft.entity.EntityType;


public class YitianModClient implements ClientModInitializer {
    public static final String MOD_ID = "yi_tian";
    @Override
    public void onInitializeClient() {
        ModSkullHandler.register();
        EntityRendererRegistry.register(EntityType.IRON_GOLEM,(context)->new IronGolemEntityRenderer(context){
            {
                this.addFeature(new CustomIronGolemRenderLayer(this));
            }
        });
        EntityRendererRegistry.register(EntityType.SNOW_GOLEM,(context)->new SnowGolemEntityRenderer(context){
            {
                this.addFeature(new CustomSnowGolemRenderLayer(this));
            }
        });
        EntityRendererRegistry.register(EntityType.ZOMBIE,(context)->new ZombieEntityRenderer(context){
            {
                this.addFeature(new CustomZombieRenderLayer(this));
            }
        });
        EntityRendererRegistry.register(EntityType.SPIDER,(context)->new SpiderEntityRenderer<>(context){
            {
                this.addFeature(new CustomSpiderRenderLayer(this));
            }
        });
        EntityRendererRegistry.register(EntityType.WARDEN,(context)->new WardenEntityRenderer(context){
            {
                this.addFeature(new CustomWardenRenderLayer(this));
            }
        });
        EntityRendererRegistry.register(EntityType.SKELETON,(context)->new SkeletonEntityRenderer(context){
            {
                this.addFeature(new CustomSkeletonRenderLayer(this));
            }
        });
        EntityRendererRegistry.register(EntityType.GHAST,(context)->new GhastEntityRenderer(context){
            {
                this.addFeature(new CustomGhastRenderLayer(this));
            }
        });
//        ModKeyBindingHelper.register();
        // 注册客户端tick事件
//        R_Key();
    }
}

