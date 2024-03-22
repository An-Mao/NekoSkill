package anmao.mc.ns.screen.w;

import anmao.mc.amlib.screen.widget.RenderWidgetCore;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;

public class CircularMenu extends RenderWidgetCore {
    private int sectors = 9;
    private int innerRadius = 20;
    private int outerRadius = 80;
    private int highlightColor = 0x50646464;
    private int normalColor = 0x50898989;
    private double addAngle = Math.PI / 180;
    public CircularMenu(int x, int y, int w, int h, Component message) {
        this(x, y, w, h,9,20,80, message);
    }
    public CircularMenu(int x, int y, int w, int h,int sectors,int innerRadius,int outerRadius, Component message) {
        this(x,y,w,h,sectors,innerRadius,outerRadius,0x50646464,0x50898989,message);
    }
    public CircularMenu(int x, int y, int w, int h,int sectors,int innerRadius,int outerRadius,int highlightColor,int normalColor, Component message) {
        super(x, y, w, h, message);
        setSectors(sectors);
        setInnerRadius(innerRadius);
        setOuterRadius(outerRadius);
        setHighlightColor(highlightColor);
        setNormalColor(normalColor);
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

    public void setInnerRadius(int innerRadius) {
        this.innerRadius = innerRadius;
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
            for (int i = 0; i < sectors; i++) {
                double startAngle = i * sectorAngle;
                double endAngle = (i + 1) * sectorAngle;
                int bgColor = (angle >= startAngle && angle < endAngle) ? highlightColor : normalColor;
                drawSector(centerX,centerY,startAngle,endAngle,bgColor);
            }
        }
    }
    private void drawSector(int centerX, int centerY, double startAngle, double endAngle, int color) {

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
        for (double angle = startAngle; angle <= endAngle; angle += addAngle) {
            double x1 = centerX + Math.cos(angle) * innerRadius;
            double y1 = centerY + Math.sin(angle) * innerRadius;
            double x2 = centerX + Math.cos(angle) * outerRadius;
            double y2 = centerY + Math.sin(angle) * outerRadius;
            buffer.vertex(x2, y2, 0).color(color).endVertex();
            buffer.vertex(x1, y1, 0).color(color).endVertex();
        }

        tesselator.end();

        /*
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

        // 添加中心点
        buffer.vertex(centerX, centerY, 0).color(color).endVertex();

        // 添加扇形边界上的顶点
        for (double angle = startAngle; angle <= endAngle; angle += addAngle) {
            double x = centerX + Math.cos(angle) * outerRadius;
            double y = centerY + Math.sin(angle) * outerRadius;
            buffer.vertex(x, y, 0).color(color).endVertex();
        }

        // 添加内圆上的顶点
        for (double angle = endAngle; angle >= startAngle; angle -= addAngle) {
            double x = centerX + Math.cos(angle) * innerRadius;
            double y = centerY + Math.sin(angle) * innerRadius;
            buffer.vertex(x, y, 0).color(color).endVertex();
        }

        // 添加起始点来封闭扇形
        double xStart = centerX + Math.cos(startAngle) * outerRadius;
        double yStart = centerY + Math.sin(startAngle) * outerRadius;
        buffer.vertex(xStart, yStart, 0).color(color).endVertex();

        tesselator.end();

         */
        /*
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();
        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);

        // 添加中心点
        buffer.vertex(centerX, centerY, 0).color(color).endVertex();

        // 添加扇形边界上的顶点
        for (double angle = startAngle; angle <= endAngle; angle += addAngle) {
            double x = centerX + Math.cos(angle) * outerRadius;
            double y = centerY + Math.sin(angle) * outerRadius;
            buffer.vertex(x, y, 0).color(color).endVertex();
        }

        // 添加起始点来封闭扇形
        double xStart = centerX + Math.cos(startAngle) * outerRadius;
        double yStart = centerY + Math.sin(startAngle) * outerRadius;
        buffer.vertex(xStart, yStart, 0).color(color).endVertex();

        tesselator.end();

         */
    }
}
