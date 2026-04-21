package net.neganote.gtutilities.client.keybind;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class UtilKeybinds {

    public static final KeyMapping SPRAY_CAN_MENU = new KeyMapping(
            "key.gtmutils.spray_can_menu",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "key.categories.gtmutils");

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent event) {
        event.register(SPRAY_CAN_MENU);
    }
}
