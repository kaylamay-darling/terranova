package net.kaylamay.terranova.event;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.entity.ModEntities;
import net.kaylamay.terranova.registry.entity.custom.GlowwormEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = TerraNova.MODID)
public class EntityCreationAttributeEvent {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GLOWWORM.get(), GlowwormEntity.createAttributes().build());
    }
}
