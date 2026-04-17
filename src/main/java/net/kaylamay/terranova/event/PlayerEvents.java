package net.kaylamay.terranova.event;

import net.kaylamay.terranova.TerraNova;
import net.kaylamay.terranova.util.ModItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.function.Predicate;

import static net.kaylamay.terranova.util.ModItemTags.UNSUPPRESSED_ATTACK;
import static net.kaylamay.terranova.util.ModItemTags.UNSUPPRESSED_MINE;

public class PlayerEvents {
    @SubscribeEvent
    public static void fistBreakEvent(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        ItemStack itemStack = player.getMainHandItem();
        BlockState blockState = event.getState();

        if (itemStack.is(UNSUPPRESSED_MINE) || blockState.is(BlockTags.LEAVES) || blockState.is(BlockTags.REPLACEABLE)) {
            return;
        }

        event.setNewSpeed(event.getNewSpeed() * 0.05f);
    }

    @SubscribeEvent
    public static void fistDamageEvent(AttackEntityEvent event) {
        Player player = event.getEntity();
        Entity attackedEntity = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();

        if (itemStack.is(UNSUPPRESSED_ATTACK) || !(attackedEntity instanceof LivingEntity)) {
            return;
        }

        event.setCanceled(true);
        player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(0.35);
    }
}