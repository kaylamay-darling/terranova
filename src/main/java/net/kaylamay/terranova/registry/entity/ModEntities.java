package net.kaylamay.terranova.registry.entity;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.registry.entity.custom.GlowwormEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, TerraNova.MODID);


    public static final DeferredHolder<EntityType<?>, EntityType<GlowwormEntity>> GLOWWORM =
            ENTITY_TYPES.register("glowworm", () ->
                    EntityType.Builder.<GlowwormEntity>of(GlowwormEntity::new, MobCategory.AMBIENT)
                            .sized(0.8f, 0.8f)
                            .clientTrackingRange(8)
                            .updateInterval(2)
                            .build(ResourceKey.create(
                                    Registries.ENTITY_TYPE,
                                    Identifier.fromNamespaceAndPath(TerraNova.MODID, "glowworm"))
            ));
}