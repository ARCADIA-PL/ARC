package com.arc.arc.Registries;

import com.arc.arc.item.ArcbladeItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArcBladeItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "arc");
    // 注册 Arcblade
    public static final RegistryObject<Item> ARCBLADE = ITEMS.register(
            "arcblade",
            () -> new ArcbladeItem(
                    Tiers.NETHERITE,
                    7,
                    -3.1F,
                    new Item.Properties()
                            .tab(CreativeModeTab.TAB_COMBAT) // 放在创造模式“战斗”标签页
            )
    );
    // 注册 变形Arcblade
    public static final RegistryObject<Item> ARCBLADETRANSFORMED = ITEMS.register(
            "arcbladetransformed",
            () -> new ArcbladeItem(
                    Tiers.NETHERITE,
                    3,
                    -2.4F,
                    new Item.Properties()
                            .tab(CreativeModeTab.TAB_COMBAT) // 放在创造模式“战斗”标签页
            )


    );
}
