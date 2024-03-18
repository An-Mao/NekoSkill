package anmao.mc.ns.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ToolTip {
    private Font font = Minecraft.getInstance().font;
    public List<Component> details;
    public boolean show;
    public ToolTip(Component... details){
        this(Arrays.asList(details),true);
    }
    public ToolTip(boolean show, Component... details){
        this(Arrays.asList(details),show);
    }
    public ToolTip(List<Component> details,boolean show){
        setDetails(details);
        setShow(show);
    }

    public void setDetails(List<Component> details) {
        this.details = details;
    }

    public List<Component> getDetails() {
        return details;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void render(GuiGraphics guiGraphics, int x, int y){
        if (show){
            guiGraphics.renderTooltip(font,details, Optional.empty(),x,y);
        }
    }
    @Override
    public String toString() {
        return "ToolTip{" +
                "details=" + details +
                ", show=" + show +
                '}';
    }
}
