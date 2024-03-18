package anmao.mc.ns.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final String KEY_NS_CATEGORY = "key.category.ns.mc";
    public static final String KEY_NS_SKILL_SELECT = "key.ns.skill_select";

    public static final KeyMapping OPEN_SKILL_SELECT = new KeyMapping(KEY_NS_SKILL_SELECT, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, KEY_NS_CATEGORY);
}
