package com.arc.arc.init;

import com.arc.arc.capability.ILastAttacker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class ModCapabilities {
    // 定义 Capability
    public static final Capability<ILastAttacker> LAST_ATTACKER =
            CapabilityManager.get(new CapabilityToken<>(){});

    public static void register() {
        // 注册到实体附加事件
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, ModCapabilities::onAttachCapabilities);
    }

    private static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof LivingEntity) {
            event.addCapability(
                    new ResourceLocation("yourmodid", "last_attacker"),
                    new LastAttackerProvider()
            );
        }
    }
}
