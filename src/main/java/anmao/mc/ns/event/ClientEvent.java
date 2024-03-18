package anmao.mc.ns.event;

import anmao.mc.ns.NS;
import anmao.mc.ns.screen.SkillSelectScreen;
import anmao.mc.ns.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ClientEvent {
    @Mod.EventBusSubscriber(modid = NS.MOD_ID,value = Dist.CLIENT)
    public static class ForgeClientEvent{
        @SubscribeEvent
        public static void onKeyInput(InputEvent event){
            if (KeyBinding.OPEN_SKILL_SELECT.consumeClick()){
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().setScreen(new SkillSelectScreen());
                }
            }
        }
    }
    @Mod.EventBusSubscriber(modid = NS.MOD_ID,value = Dist.CLIENT,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModClientEvent{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event){
            event.register(KeyBinding.OPEN_SKILL_SELECT);
        }
    }
}
