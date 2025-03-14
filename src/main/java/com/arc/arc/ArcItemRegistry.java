package com.arc.arc;
import com.arc.arc.init.ModBlocks;
import com.arc.arc.item.ArcbladeItem;
import com.arc.arc.item.YomibladeItem;
import com.arc.arc.item.ArcbladeMiniItem;
import com.arc.arc.item.ArcbladeMini2Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ArcItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, "arc");
    // 注册 ArcbladeMini
    public static final RegistryObject<Item> ARCBLADEMINI = ITEMS.register(
            "arcblademini",
            () -> new ArcbladeMiniItem(
                    Tiers.NETHERITE,
                    6,
                    -2.0F,
                    new Item.Properties()
                            .tab(CreativeModeTab.TAB_COMBAT) // 放在创造模式“战斗”标签页
            )
    );
    // 注册 ArcbladeMini2
    public static final RegistryObject<Item> ARCBLADEMINI2 = ITEMS.register(
            "arcblademini2",
            () -> new ArcbladeMini2Item(
                    Tiers.NETHERITE,
                    6,
                    -2.0F,
                    new Item.Properties()
                            .tab(CreativeModeTab.TAB_COMBAT) // 放在创造模式“战斗”标签页
            )
    );
    // 注册 Yomiblade
    public static final RegistryObject<Item> YOMIBLADE = ITEMS.register(
            "yomiblade",
            () -> new YomibladeItem(
                    Tiers.NETHERITE,
                    6,
                    -2.0F,
                    new Item.Properties()
                            .tab(CreativeModeTab.TAB_COMBAT) // 放在创造模式“战斗”标签页
            )
    );
    // 注册 Arcblade
    public static final RegistryObject<Item> ARCBLADE = ITEMS.register(
            "arcblade",
            () -> new ArcbladeItem(
                    Tiers.NETHERITE,
                    3,
                    -3F,
                    new Item.Properties()
                            .tab(CreativeModeTab.TAB_COMBAT) // 放在创造模式“战斗”标签页
            )


    );
    // 注册 变形Arcblade
    public static final RegistryObject<Item> ARCBLADETransformed = ITEMS.register(
            "arcbladetransformed",
            () -> new ArcbladeItem(
                    Tiers.NETHERITE,
                    3,
                    -2.4F,
                    new Item.Properties()
                            .tab(CreativeModeTab.TAB_COMBAT) // 放在创造模式“战斗”标签页
            )


    );
    // 注册法阵方块的物品形式
    public static final RegistryObject<Item> MAGIC_CIRCLE = ITEMS.register("magic_circle",
                                                                           () -> new BlockItem(ModBlocks.MAGIC_CIRCLE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

}
