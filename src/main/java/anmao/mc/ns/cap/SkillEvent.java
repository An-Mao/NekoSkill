package anmao.mc.ns.cap;

import anmao.mc.ns.NS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class SkillEvent {

    @Mod.EventBusSubscriber(modid = NS.MOD_ID)
    public static class ForgeEvent {
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
            event.register(SkillCap.class);
        }
        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof ServerPlayer serverPlayer) {
                if (!serverPlayer.getCapability(SkillProvider.SKILL).isPresent()) {
                    event.addCapability(new ResourceLocation(NS.MOD_ID, "skill"), new SkillProvider());
                }
            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event)
        {
            if (event.getEntity() instanceof ServerPlayer) {
                event.getOriginal().reviveCaps();
                event.getOriginal().getCapability(SkillProvider.SKILL).ifPresent(oldStore -> event.getOriginal().getCapability(SkillProvider.SKILL).ifPresent(newStore -> newStore.copyFrom(oldStore)));
            }
        }
        @SubscribeEvent
        public static void onHurt(LivingAttackEvent event) {
            if (event.getEntity() instanceof Player player) {
                player.getCapability(SkillProvider.SKILL).ifPresent(mobSkillCap -> mobSkillCap.hurt(player,event.getSource().getEntity()));

            }
        }
        @SubscribeEvent
        public static void onAttack(AttackEntityEvent event) {
            Player player = event.getEntity();
            player.getCapability(SkillProvider.SKILL).ifPresent(mobSkillCap -> mobSkillCap.hurt(player,event.getTarget()));
        }
    }
}
