package anmao.mc.ns.screen;

import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.ns.NS;
import anmao.mc.ns.data_type.DT_ImageInfo;
import anmao.mc.ns.screen.w.ImageButton;
import anmao.mc.ns.screen.w.Labels;
import anmao.mc.ns.skill.Faction;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SkillSelectScreen extends Screen {
    private static final ResourceLocation texture = new ResourceLocation("textures/entity/end_portal.png");
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
        /*
        for (SkillType skillType : SkillType.values()){
            posX += 64;
            this.addRenderableWidget(new ImageButton(new DT_XYWH(x + posX,y + posY,16,16),Component.literal(skillType.name()), () -> {
                System.out.println(skillType.name());
            }));
        }
         */
        this.addRenderableWidget(new Labels(50,50,64,64,Component.translatable("screen.ns.skill.select.title.faction"), Color.darkGray.getRGB(),0x00ff00));
        int i = 0;
        for (Faction.Behavior behavior : Faction.Behavior.values()){
            for (Faction.Morality morality : Faction.Morality.values()){
                this.addRenderableWidget(
                        new ImageButton(
                                new DT_ImageInfo(
                                        new ResourceLocation(NS.MOD_ID,"textures/screen/button.png")),
                                new DT_XYWH(100 + i *  18,100,16,16),
                                Component.translatable( "skill.ns.faction.behavior." + behavior.name()).append(" " + Component.translatable("skill.ns.faction.morality." + morality.name()).getString()),
                                ()->{
                    System.out.println("Value:" + behavior.name()+" - " + morality.name());
                }));
                i++;
            }
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
        guiGraphics.blit(texture,0,0,width,height,0,0,width,height,256,256);
        renderBackground(guiGraphics);
        super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
    }
}
