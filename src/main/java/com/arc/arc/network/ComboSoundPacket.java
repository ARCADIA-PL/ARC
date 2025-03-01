package com.arc.arc.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;
import com.arc.arc.client.ClientSoundHandler;
import java.util.function.Supplier;

public class ComboSoundPacket {
    private final int level;

    public ComboSoundPacket(int level) {
        this.level = level;
    }

    public ComboSoundPacket(FriendlyByteBuf buf) {
        this.level = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(level);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                if (this.level == 4) {
                    ClientSoundHandler.playCombo5();
                } else if (this.level == 9) {
                    ClientSoundHandler.playCombo10();
                }
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
