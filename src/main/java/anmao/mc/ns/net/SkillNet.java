package anmao.mc.ns.net;

import anmao.mc.amlib.amlib.network.easy_net.EasyNet;
import anmao.mc.ns.cap.SkillProvider;
import anmao.mc.ns.screen.SkillSelectScreen;
import anmao.mc.ns.skill.Career;
import anmao.mc.ns.skill.Faction;
import anmao.mc.ns.skill.Skill;
import anmao.mc.ns.skill.Skills;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillNet extends EasyNet {
    @Override
    public void client(Supplier<NetworkEvent.Context> contextSupplier, CompoundTag dat) {
        Player player = Minecraft.getInstance().player;
        if (player != null){

            player.getCapability(SkillProvider.SKILL).ifPresent(skillCap -> {
                skillCap.handle(dat);
                if (Minecraft.getInstance().screen instanceof SkillSelectScreen){
                    Minecraft.getInstance().setScreen(new SkillSelectScreen());
                }
            });
        }
        super.client(contextSupplier, dat);
    }

    @Override
    public void server(Supplier<NetworkEvent.Context> contextSupplier, CompoundTag dat) {
        ServerPlayer sender = contextSupplier.get().getSender();
        System.out.println("load sender info");
        if (sender != null){
            System.out.println("load sender cap");
            sender.getCapability(SkillProvider.SKILL).ifPresent(skillCap -> {

                System.out.println("load cap info");
                int type = dat.getInt("type");
                switch (type){
                    case 1 -> skillCap.setFaction(sender,
                            Faction.Behavior.valueOf(dat.getString("behavior")),
                            Faction.Morality.valueOf(dat.getString("morality"))
                    );
                    case 2 -> skillCap.setCareer(sender,Career.valueOf(dat.getString("career")));
                    case 3 ->{
                        Skill skill = Skills.getSkill(ResourceLocation.tryParse(dat.getString("skill")));
                        if (skill != null){
                            if (skill.canLearn(sender)){
                                System.out.println("learn skill");
                                skillCap.addSkill(skill);
                                skillCap.sendChange(sender);
                            }
                        }
                    }
                }
            });
        }
        super.server(contextSupplier, dat);
    }
}
