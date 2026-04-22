package net.neganote.gtutilities.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.neganote.gtutilities.GregTechModernUtilities;
import net.neganote.gtutilities.client.gui.screen.ColorRadialMenuScreen;
import net.neganote.gtutilities.client.keybind.UtilKeybinds;
import net.neganote.gtutilities.common.item.InfiniteSprayCanItem;
import net.neganote.gtutilities.network.UtilsNetwork;
import net.neganote.gtutilities.network.packet.SelectColorPacket;

@Mod.EventBusSubscriber(modid = GregTechModernUtilities.MOD_ID,
                        bus = Mod.EventBusSubscriber.Bus.FORGE,
                        value = Dist.CLIENT)
public class ClientTickHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        if (UtilKeybinds.SPRAY_CAN_MENU.consumeClick()) {
            ItemStack stack = mc.player.getMainHandItem();
            if (stack.getItem() instanceof InfiniteSprayCanItem) {
                mc.setScreen(new ColorRadialMenuScreen(InteractionHand.MAIN_HAND));
            }
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.screen != null) return;

        if (mc.options.keyShift.isDown()) {
            ItemStack stack = mc.player.getMainHandItem();
            if (stack.getItem() instanceof InfiniteSprayCanItem) {
                double scrollDelta = event.getScrollDelta();

                event.setCanceled(true);

                int currentColor = stack.getOrCreateTag().getInt("color");
                if (!stack.getOrCreateTag().contains("color")) currentColor = -1;

                int direction = scrollDelta > 0 ? 1 : -1;
                int nextColor = currentColor + direction;

                if (nextColor < -1) nextColor = 15;
                if (nextColor > 15) nextColor = -1;

                UtilsNetwork.CHANNEL.sendToServer(new SelectColorPacket(InteractionHand.MAIN_HAND, nextColor));

                mc.player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 0.1f, 1.5f + (nextColor * 0.05f));
            }
        }
    }

    @SubscribeEvent
    public static void onRightClick(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (!event.isUseItem()) return;
        ItemStack stack = mc.player.getMainHandItem();
        if (!(stack.getItem() instanceof InfiniteSprayCanItem)) return;

        if (!mc.player.isShiftKeyDown()) return;
        if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.MISS) return;

        event.setCanceled(true);
        mc.setScreen(new ColorRadialMenuScreen(InteractionHand.MAIN_HAND));
    }
}
