package anmao.mc.ns.skill.all;

import anmao.mc.amlib.bytes.Byte;
import anmao.mc.ns.cap.SkillProvider;
import anmao.mc.ns.skill.Skill;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class BaseSkill extends Skill {
    @Override
    public boolean equipped(Player player) {
        int[] level = {0};
        player.getCapability(SkillProvider.SKILL).ifPresent(skillCap -> level[0] = skillCap.getSkillLevel(this));
        if (level[0] < 1){
            return false;
        }
        AttributeInstance att = player.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
        if (att == null){
            return false;
        }
        AttributeModifier attributeModifier = new AttributeModifier(Byte.getUUID( Byte.getMd5("attribute.ns.skill.base.atk_add")),"attribute.ns.skill.base.atk_add",level[0], AttributeModifier.Operation.ADDITION);
        if (att.hasModifier(attributeModifier)){
            att.removeModifier(attributeModifier);
        }
        att.addPermanentModifier(attributeModifier);

        att = player.getAttributes().getInstance(Attributes.ARMOR);
        attributeModifier = new AttributeModifier(Byte.getUUID( Byte.getMd5("attribute.ns.skill.base.armor_add")),"attribute.ns.skill.base.armor_add",level[0], AttributeModifier.Operation.ADDITION);
        if (att.hasModifier(attributeModifier)){
            att.removeModifier(attributeModifier);
        }
        att.addPermanentModifier(attributeModifier);
        return true;
    }

    @Override
    public boolean disarmed(Player player) {
        int[] level = {0};
        player.getCapability(SkillProvider.SKILL).ifPresent(skillCap -> level[0] = skillCap.getSkillLevel(this));
        if (level[0] < 1){
            return false;
        }
        AttributeInstance att = player.getAttributes().getInstance(Attributes.ATTACK_DAMAGE);
        if (att == null){
            return false;
        }
        AttributeModifier attributeModifier = new AttributeModifier(Byte.getUUID( Byte.getMd5("attribute.ns.skill.base.atk_add")),"attribute.ns.skill.base.atk_add",level[0], AttributeModifier.Operation.ADDITION);
        if (att.hasModifier(attributeModifier)){
            att.removeModifier(attributeModifier);
        }
        att = player.getAttributes().getInstance(Attributes.ARMOR);
        attributeModifier = new AttributeModifier(Byte.getUUID( Byte.getMd5("attribute.ns.skill.base.armor_add")),"attribute.ns.skill.base.armor_add",level[0], AttributeModifier.Operation.ADDITION);
        if (att.hasModifier(attributeModifier)){
            att.removeModifier(attributeModifier);
        }
        return true;
    }
}
