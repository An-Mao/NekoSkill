package anmao.mc.ns.screen;

import anmao.mc.amlib.amlib.network.Net;
import anmao.mc.amlib.amlib.network.easy_net.EasyNetCTS;
import anmao.mc.amlib.screen.widget.DT_ListBoxData;
import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.amlib.screen.widget.ListBox;
import anmao.mc.ns.NS;
import anmao.mc.ns.cap.SkillProvider;
import anmao.mc.ns.data_type.DT_ImageInfo;
import anmao.mc.ns.net.NetReg;
import anmao.mc.ns.screen.w.CircularMenu;
import anmao.mc.ns.screen.w.ImageButton;
import anmao.mc.ns.screen.w.Labels;
import anmao.mc.ns.screen.w.RouletteMenu;
import anmao.mc.ns.skill.Career;
import anmao.mc.ns.skill.Faction;
import anmao.mc.ns.skill.Skill;
import anmao.mc.ns.skill.Skills;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SkillSelectScreen extends Screen {
    private static final ResourceLocation texture = new ResourceLocation(NS.MOD_ID,"textures/screen/bg.jpg");
    private int x , y;
    public SkillSelectScreen() {
        super(Component.translatable("screen.ns.skill.select.title"));
    }
    @Override
    protected void init() {
        super.init();
        this.x = 0;
        this.y = 0;
        int posX = 0,posY = 100;
        addRenderableWidget(new CircularMenu(width/2,height/2,width,height,Component.empty()));
    }
    public void addSkillSelectButton(){
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            player.getCapability(SkillProvider.SKILL).ifPresent(skillCap -> {
                if (!skillCap.hasFaction()) {
                    this.addRenderableWidget(new Labels(50, 50, 64, 16, Component.translatable("screen.ns.skill.select.title.faction"), Color.darkGray.getRGB(), 0x00ff00));
                    int i = 0;
                    for (Faction.Behavior behavior : Faction.Behavior.values()) {
                        for (Faction.Morality morality : Faction.Morality.values()) {
                            this.addRenderableWidget(
                                    new ImageButton(
                                            new DT_ImageInfo(
                                                    new ResourceLocation(NS.MOD_ID, "textures/screen/button.png")),
                                            new DT_XYWH(100 + i * 18, 100, 16, 16),
                                            Component.translatable("skill.ns.faction.behavior." + behavior.name()).append(" " + Component.translatable("skill.ns.faction.morality." + morality.name()).getString()),
                                            () -> {
                                                System.out.println("----send----");
                                                CompoundTag dat = new CompoundTag();
                                                dat.putString(Net.EASY_NET_KEY, NetReg.Skill.getId().toString());
                                                dat.putInt("type", 1);
                                                dat.putString("behavior", behavior.name());
                                                dat.putString("morality", morality.name());
                                                Net.EasyNetCTS(new EasyNetCTS(dat));
                                            }));
                            i++;
                        }
                    }
                    return;
                }
                addRenderableWidget(new Labels(64,32,64,16,Component.translatable("screen.ns.skill.title.faction").append(":").append(Component.translatable("skill.ns.faction.behavior." + skillCap.getBehavior().name()).append(" " + Component.translatable("skill.ns.faction.morality." + skillCap.getMorality().name()).getString())),0x000000,0xffffff));
                if (!skillCap.hasCareer()){
                    this.addRenderableWidget(new Labels(50, 34, 64, 16, Component.translatable("screen.ns.skill.select.title.career"), Color.darkGray.getRGB(), 0x00ff00));
                    List<DT_ListBoxData> data = new ArrayList<>();
                    for (Career career:Career.values()){
                        data.add(new DT_ListBoxData(Component.translatable("skill.ns.career."+career.name()),career.name(),(value)->{
                            if (value instanceof String s) {
                                System.out.println("value:"+value);
                                CompoundTag dat = new CompoundTag();
                                dat.putString(Net.EASY_NET_KEY, NetReg.Skill.getId().toString());
                                dat.putInt("type", 2);
                                dat.putString("career", s);
                                Net.EasyNetCTS(new EasyNetCTS(dat));
                            }
                        }));
                    }
                    this.addRenderableWidget(new ListBox(new DT_XYWH(50,50,300,150),32,16,Component.empty(),data));
                    return;
                }
                addRenderableWidget(new Labels(64,48,64,16,Component.translatable("screen.ns.skill.title.career").append(":").append(Component.translatable("skill.ns.career."+skillCap.getCareer().name())),0x000000,0xffffff));

                List<DT_ListBoxData> data = new ArrayList<>();
                Skills.REGISTRY.get().forEach(skill -> {
                    if (skill.canLearn(player)) {
                        data.add(new DT_ListBoxData(skill.getName(),
                                skill,
                                List.of(
                                        skill.getName(),
                                        skill.getDesc(),
                                        Component.translatable("skill.ns.tooltip.lvl").append(":").append(String.valueOf(skillCap.getSkillLevel(skill)))
                                ),


                                (value) -> {
                                    if (value instanceof Skill newSkill) {
                                        CompoundTag dat = new CompoundTag();
                                        dat.putString(Net.EASY_NET_KEY, NetReg.Skill.getId().toString());
                                        dat.putInt("type", 3);
                                        dat.putString("skill", newSkill.getRes().toString());
                                        Net.EasyNetCTS(new EasyNetCTS(dat));
                                    }
                                }));
                    }
                });
                this.addRenderableWidget(new ListBox(new DT_XYWH(155,32,200,150),64,16,Component.empty(),data));
            });
        }
    }
    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        super.mouseMoved(pMouseX, pMouseY);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,texture);
        //guiGraphics.blit(texture,0,0,width,height,0,0,width,height,256,256);
        renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
