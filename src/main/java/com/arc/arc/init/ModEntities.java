package com.arc.arc.init;

import com.arc.arc.ArcMod;
import com.arc.arc.entity.Arcblade_Hexagram_Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class ModEntities {
    // 创建 DeferredRegister 用于注册实体
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, ArcMod.MOD_ID);
    // 注册 Arcblade_Hexagram_Entity
    public static final RegistryObject<EntityType<Arcblade_Hexagram_Entity>> ARCBLADE_HEXAGRAM = ENTITIES.register(
            "arcblade_hexagram",
            () -> EntityType.Builder.<Arcblade_Hexagram_Entity>of(Arcblade_Hexagram_Entity::new, MobCategory.MISC)
                    .sized(1.0F, 0.1F) // 设置实体大小
                    .clientTrackingRange(4) // 客户端跟踪范围
                    .updateInterval(20) // 更新间隔
                    .build("arcblade_hexagram")
    );
}
