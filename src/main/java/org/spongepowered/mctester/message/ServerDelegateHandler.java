package org.spongepowered.mctester.message;

import org.spongepowered.mctester.message.toserver.BaseServerMessage;
import org.spongepowered.api.Platform;
import org.spongepowered.api.network.MessageHandler;
import org.spongepowered.api.network.RemoteConnection;

public class ServerDelegateHandler implements MessageHandler<BaseServerMessage> {

    @Override
    public void handleMessage(BaseServerMessage message, RemoteConnection connection, Platform.Type side) {
        message.process();
        /*((MinecraftServer) Sponge.getServer()).addScheduledTask(new Runnable() {

            @Override
            public void run() {
                message.process();
            }
        });*/
    }
}
