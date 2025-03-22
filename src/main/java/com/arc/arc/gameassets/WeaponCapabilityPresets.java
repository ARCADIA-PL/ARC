package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import com.guhao.star.efmex.StarAnimations;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder> ARCBLADE = (item) ->
            (CapabilityItem.Builder) WeaponCapability.builder().category(CapabilityItem.WeaponCategories.TACHI)//蹭格挡，用别的也行
                    .styleProvider((entityPatch) -> CapabilityItem.Styles.TWO_HAND)
                    .collider(new MultiOBBCollider(8, 0.4, 0.4, 0.95, 0.0, 0.0, -0.95))//这里可以用预设的，也可以new 一个
                    .swingSound(EpicFightSounds.WHOOSH)
                    .hitSound(EpicFightSounds.BLADE_HIT)
                    .hitParticle(WOMParticles.OVERBLOOD_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.AXE_AUTO1)
                    .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> Arcblade.Arcblade)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
                    .comboCancel((style) -> false);

    public static final Function<Item, CapabilityItem.Builder> ARCBLADETRANSFORMED = (item) ->
            (CapabilityItem.Builder) WeaponCapability.builder().category(CapabilityItem.WeaponCategories.LONGSWORD)//蹭格挡，用别的也行
                    .styleProvider((entityPatch) -> CapabilityItem.Styles.TWO_HAND)
                    .collider(ColliderPreset.TACHI)//这里可以用预设的，也可以new 一个
                    .swingSound(EpicFightSounds.WHOOSH_BIG)
                    .hitSound(EpicFightSounds.BLADE_HIT)
                    .hitParticle(WOMParticles.OVERBLOOD_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.AXE_AUTO1)
                    .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> ArcbladeTransformed.ArcbladeTransformed)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.RUINE_IDLE)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, StarAnimations.GREATSWORD_OLD_WALK)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.RUINE_RUN)
                    .comboCancel((style) -> false);
    @SubscribeEvent
        public static void register(WeaponCapabilityPresetRegistryEvent event) {
            event.getTypeEntry().put("arcblade", ARCBLADE);
            event.getTypeEntry().put("arcbladetransformed", ARCBLADETRANSFORMED);
        }
    }


