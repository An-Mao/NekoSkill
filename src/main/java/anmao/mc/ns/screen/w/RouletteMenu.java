package anmao.mc.ns.screen.w;

import anmao.mc.amlib.screen.widget.RenderWidgetCore;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class RouletteMenu extends RenderWidgetCore {
    private int sectors = 9;
    private int outerRadius = 80;
    private int highlightColor = 0x50646464;
    private int normalColor = 0x50898989;
    private double addAngle = Math.PI / (5 * outerRadius);
    public RouletteMenu(int x, int y, int w, int h, Component pMessage) {
        super(x, y, w, h, pMessage);
    }

    public void setAddAngle(double addAngle) {
        this.addAngle = addAngle;
    }

    public void setHighlightColor(int highlightColor) {
        this.highlightColor = highlightColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public void setOuterRadius(int outerRadius) {
        this.outerRadius = outerRadius;
    }

    public void setSectors(int sectors) {
        this.sectors = sectors;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if (visible){
            int centerX = getX();
            int centerY = getY();
            double angle = Math.atan2(mouseY - centerY, mouseX - centerX);
            if (angle < 0) {
                angle += Math.PI * 2;
            }
            double sectorAngle =  2 * Math.PI / sectors;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.getBuilder();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            buffer.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            buffer.vertex(centerX, centerY, 0.0).color(normalColor).endVertex();

            for (int i = 0; i < sectors; i++) {
                double startAngle = i * sectorAngle;
                double endAngle = (i + 1) * sectorAngle;
                int bgColor = (angle >= startAngle && angle < endAngle) ? highlightColor : normalColor;
                for (double a = startAngle; a < endAngle; a += Math.PI / 180) {
                    double x2 = centerX + Math.cos(a) * outerRadius;
                    double y2 = centerY + Math.sin(a) * outerRadius;
                    buffer.vertex(x2, y2, 0).color(bgColor).endVertex();
                }
            }
            tesselator.end();
        }
    }
}
