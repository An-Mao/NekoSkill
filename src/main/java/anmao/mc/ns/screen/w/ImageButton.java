package anmao.mc.ns.screen.w;

import anmao.mc.amlib.screen.widget.DT_XYWH;
import anmao.mc.ns.component.ToolTip;
import anmao.mc.ns.data_type.DT_ImageInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ImageButton extends AbstractWidget implements Renderable {
    private final Font font = Minecraft.getInstance().font;
    private final DT_ImageInfo imageInfo;
    private MouseType mouseType = MouseType.Normal;
    private final OnPress onPress;
    private ToolTip tip;
    public ImageButton(DT_ImageInfo imageInfo, DT_XYWH xywh, Component pMessage,OnPress onPress ) {
        this(imageInfo,xywh.x(), xywh.y(), xywh.width(), xywh.height(), pMessage,onPress);
    }
    public ImageButton(DT_ImageInfo imageInfo, int pX, int pY, int pWidth, int pHeight, Component pMessage,OnPress onPress ) {
        super(pX, pY, pWidth, pHeight, pMessage);
        this.imageInfo = imageInfo;
        this.onPress = onPress;
        tip = new ToolTip(pMessage);
    }

    public void setTip(ToolTip tip) {
        this.tip = tip;
    }
    public ToolTip getTip() {
        return tip;
    }public void setTipShow(boolean show) {
        this.tip.setShow(show);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (isMouseOver(pMouseX,pMouseY)) {
            mouseType = MouseType.Pressed;
            onPress.onPress();
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        mouseType = MouseType.Normal;
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public void mouseMoved(double pMouseX, double pMouseY) {
        System.out.println("-----move-----");
        if (!isMouseOver(pMouseX,pMouseY)){
            mouseType = MouseType.Normal;
        }else if (mouseType == MouseType.Normal){
            mouseType = MouseType.Hover;
        }
        super.mouseMoved(pMouseX, pMouseY);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int u = imageInfo.u,v = imageInfo.v;
        if (isMouseOver(pMouseX,pMouseY)){
            u = imageInfo.u + imageInfo.elementWidth;
            switch (mouseType) {
                case Pressed -> u = imageInfo.u + imageInfo.elementWidth * 2;
                case Dragged -> u = imageInfo.u + imageInfo.elementWidth * 3;
            }
            pGuiGraphics.renderTooltip(font, getTip().getDetails(), Optional.empty(), pMouseX, pMouseY);
        }
        pGuiGraphics.blit(
                imageInfo.image,
                getX(),getY(),
                width,height,
                u,v,
                imageInfo.elementWidth,imageInfo.elementHeight,
                imageInfo.imageWidth,imageInfo.imageHeight);
    }
    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput pNarrationElementOutput) {}
    public enum MouseType{
        Normal(CoreType.Normal),
        Hover(CoreType.Hover),
        Pressed(CoreType.Hover),
        Released(CoreType.Hover),
        Dragged(CoreType.Hover),
        Scrolled(CoreType.Hover);
        private final CoreType coreType;
        MouseType(CoreType coreType) {
            this.coreType = coreType;
        }

        public CoreType getCoreType() {
            return coreType;
        }

        public enum CoreType{
            Normal,
            Hover
        }
    }
    @OnlyIn(Dist.CLIENT)
    public interface OnPress {
        void onPress();
    }
}
