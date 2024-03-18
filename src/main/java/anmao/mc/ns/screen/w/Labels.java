package anmao.mc.ns.screen.w;

import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.amlib.screen.widget.RenderWidgetCore;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class Labels extends RenderWidgetCore {
    private final int color;
    public Labels(int x,int y,int w,int h, Component pMessage, int color , int textColor) {
        super(x,y,w,h, pMessage);
        this.color = color;
        this.bgUsualColor = textColor;
    }
    public Labels(DT_XYWH dt_xywh, Component pMessage, int color , int textColor) {
        this(dt_xywh.x(),dt_xywh.y(),dt_xywh.width(),dt_xywh.height(), pMessage,color,textColor);
    }
    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        drawSquare(guiGraphics,color);
        drawString(guiGraphics,getX(),getY(),getMessage());
    }
}
