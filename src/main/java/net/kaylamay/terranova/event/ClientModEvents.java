package net.kaylamay.terranova.event;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.client.model.GlowwormModel;
import net.kaylamay.terranova.client.renderer.GlowwormRenderer;
import net.kaylamay.terranova.registry.ModParticles;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.registry.entity.ModEntities;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = TerraNova.MODID, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.RESIN_FLAME.get(), FlameParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GlowwormModel.LAYER_LOCATION, GlowwormModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.GLOWWORM.get(), GlowwormRenderer::new);
    }
}