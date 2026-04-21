package net.neganote.gtutilities.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.neganote.gtutilities.network.packet.SelectColorPacket;

import java.util.Optional;

public class UtilsNetwork {

    private static final String PROTOCOL = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("gtm_utils", "main"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals);

    private static int id = 0;

    public static void init() {
        CHANNEL.registerMessage(id++,
                SelectColorPacket.class,
                SelectColorPacket::encode,
                SelectColorPacket::decode,
                SelectColorPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
