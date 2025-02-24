package com.arc.arc;

import com.arc.arc.command.HurtCounterCommand;
import com.arc.arc.events.MobHurtCounterHandler;
import com.arc.arc.events.PlayerAttackCounterHandler;
import com.arc.arc.gameassets.Skills;
import com.arc.arc.init.ArcEffectsRegistry;
import com.arc.arc.network.ComboSoundPacket;
import com.arc.arc.sound.SoundRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;


@Mod(ArcMod.MOD_ID)
public class ArcMod {
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("arc", "main"),
            () -> "1.0", // 协议版本
            "1.0"::equals,
            "1.0"::equals);
    public static final String MOD_ID = "arc";
    public ArcMod() {
        Skills.registerSkills();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        ItemRegistry.ITEMS.register(bus);
        ArcEffectsRegistry.EFFECTS.register(bus);
        MinecraftForge.EVENT_BUS.register(new MobHurtCounterHandler());
        MinecraftForge.EVENT_BUS.register(PlayerAttackCounterHandler.class);
        SoundRegistry.SOUNDS.register(bus);

        int packetId = 0; // 每个包需唯一ID
        CHANNEL.registerMessage(
                packetId++,
                ComboSoundPacket.class,
                ComboSoundPacket::encode,
                ComboSoundPacket::new,
                ComboSoundPacket::handle // 使用 .consumer 而非 .consumerMainThread
        );



        ;}

    private void registerCommands(RegisterCommandsEvent event) {
        HurtCounterCommand.register(event.getDispatcher());
    }

}

