package net.kaylamay.terranova;

import net.kaylamay.terranova.event.BlockEvents;
import net.kaylamay.terranova.event.PlayerEvents;
import net.kaylamay.terranova.registry.*;
import net.kaylamay.terranova.registry.block.ModBlocks;
import net.kaylamay.terranova.registry.item.ModItems;
import net.kaylamay.terranova.worldgen.feature.ModFeatures;
import net.minecraft.client.particle.FlameParticle;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(TerraNova.MODID)
public class TerraNova {
    public static final String MODID = "terranova";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TerraNova(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);
        ModDataComponents.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModParticles.register(modEventBus);

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        ModRecipeSerializers.register(modEventBus);
        ModRecipeTypes.register(modEventBus);

        ModFeatures.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}