package com.arc.arc.gameassets;

import com.arc.arc.ArcMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.BattojutsuPassive;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = ArcMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeaponCapabilityPresets {
    public static final Function<Item, CapabilityItem.Builder> ARC = (item) ->
            (CapabilityItem.Builder) WeaponCapability.builder().category(CapabilityItem.WeaponCategories.TACHI)//蹭格挡，用别的也行
                    .styleProvider((entityPatch) -> CapabilityItem.Styles.TWO_HAND)
                    .collider(ColliderPreset.TACHI)//这里可以用预设的，也可以new 一个
                    .swingSound(EpicFightSounds.WHOOSH)
                    .hitSound(EpicFightSounds.BLADE_HIT)
                    .hitParticle(WOMParticles.KATANA_SHEATHED_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.AXE_AUTO1)
                    .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> Skills.StellarisTachi)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                    .comboCancel((style) -> false);


    public static final Function<Item, CapabilityItem.Builder> ARC1 = (item) ->
            (CapabilityItem.Builder) WeaponCapability.builder().category(CapabilityItem.WeaponCategories.UCHIGATANA)//蹭格挡，用别的也行
                    .styleProvider((entitypatch) -> {
                        if (entitypatch instanceof PlayerPatch<?> playerpatch) {
                            if (playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().hasData(BattojutsuPassive.SHEATH) &&
                                    playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue(BattojutsuPassive.SHEATH)) {
                                return CapabilityItem.Styles.SHEATH;
                            }
                        }
                        return CapabilityItem.Styles.TWO_HAND;
                    })
                    .collider(ColliderPreset.TACHI)//这里可以用预设的，也可以new 一个
                    .swingSound(EpicFightSounds.WHOOSH)
                    .hitSound(EpicFightSounds.BLADE_HIT)
                    .hitParticle(EpicFightParticles.HIT_BLADE.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.AXE_AUTO1)
                    .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> Skills.UchigatanaPower)
                    .innateSkill(CapabilityItem.Styles.SHEATH, (itemstack) -> Skills.UchigatanaPower)
                    .passiveSkill(EpicFightSkills.BATTOJUTSU_PASSIVE)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_WALK_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.KNEEL, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.CHASE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.SNEAK, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.SWIM, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.FLOAT, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.FALL, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD)
                    .comboCancel((style) -> false);

        public static final Function<Item, CapabilityItem.Builder> ARC2 = (item) ->
                (CapabilityItem.Builder) WeaponCapability.builder().category(CapabilityItem.WeaponCategories.UCHIGATANA)//蹭格挡，用别的也行
                        .styleProvider((entityPatch) -> CapabilityItem.Styles.TWO_HAND)
                        .collider(ColliderPreset.UCHIGATANA)//这里可以用预设的，也可以new 一个
                        .swingSound(EpicFightSounds.WHOOSH)
                        .hitSound(EpicFightSounds.BLADE_HIT)
                        .hitParticle(EpicFightParticles.HIT_BLADE.get())
                        .canBePlacedOffhand(false)
                        .newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.AXE_AUTO1)
                        .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> Skills.Arcblade)
                        .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD)
                        .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
                        .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
                        .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
                        .comboCancel((style) -> false);


    public static final Function<Item, CapabilityItem.Builder> ARC3 = (item) ->
            (CapabilityItem.Builder) WeaponCapability.builder().category(CapabilityItem.WeaponCategories.UCHIGATANA)//蹭格挡，用别的也行
                    .styleProvider((entityPatch) -> CapabilityItem.Styles.TWO_HAND)
                    .collider(ColliderPreset.TACHI)//这里可以用预设的，也可以new 一个
                    .swingSound(EpicFightSounds.WHOOSH)
                    .hitSound(EpicFightSounds.BLADE_HIT)
                    .hitParticle(WOMParticles.OVERBLOOD_HIT.get())
                    .canBePlacedOffhand(false)
                    .newStyleCombo(CapabilityItem.Styles.TWO_HAND, Animations.AXE_AUTO1)
                    .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> Skills.ArcbladeMini)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.UCHIGATANA_GUARD)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
                    .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
                    .comboCancel((style) -> false);


        @SubscribeEvent
        public static void register(WeaponCapabilityPresetRegistryEvent event) {
            event.getTypeEntry().put("tachipower", ARC);
            event.getTypeEntry().put("uchigatanapower", ARC1);
            event.getTypeEntry().put("arcblade", ARC2);
            event.getTypeEntry().put("arcblademini", ARC3);
        }
    }


