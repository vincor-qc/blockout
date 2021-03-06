package io.github.vincorqc.lockout.handlers;

import io.github.vincorqc.lockout.common.LockoutMod;
import io.github.vincorqc.lockout.util.Keybinds;
import io.github.vincorqc.lockout.gui.LockoutScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandler {
    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void openLockoutGUI(TickEvent.ClientTickEvent event) {

        if(Keybinds.key.isDown()) {

            Minecraft.getInstance().setScreen(new LockoutScreen(new TextComponent("Blockout")));
        }
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void leaveServer(ClientPlayerNetworkEvent.LoggedOutEvent event) {
        LockoutGameHandler.reset();
        LockoutMod.LOGGER.info("LOGGED OUT");
    }
}
