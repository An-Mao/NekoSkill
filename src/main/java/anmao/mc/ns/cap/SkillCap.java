package anmao.mc.ns.cap;

import anmao.mc.ns.skill.Skill;
import anmao.mc.ns.skill.Skills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.HashMap;

public class SkillCap {
    private HashMap<Integer, Skill> skills = new HashMap<>();
    public boolean setSkill(Player player, int index,ResourceLocation resourceLocation){
        return setSkill(player,index,Skills.getSkill(resourceLocation));
    }
    public boolean setSkill(Player player, int index , Skill skill){
        Skill oldSkill = skills.get(index);
        if (oldSkill == null || oldSkill.disarmed(player)){
            if (skill.equipped(player)){
                skills.put(index,skill);
                return true;
            }
        }
        return false;
    }
    public void hurt(Player player , Entity attacker){
        skills.forEach((integer, skill) -> skill.hurt(player, attacker));
    }
    public void attack(Player player,Entity target){
        skills.forEach((integer, skill) -> skill.attack(player, target));
    }
    public void copyFrom(SkillCap skill){
        this.skills = skill.skills;
    }
    public void saveNBTData(CompoundTag nbt)
    {
        ListTag tags = new ListTag();
        skills.forEach((s, skill) -> {
            CompoundTag tag = new CompoundTag();
            tag.putInt("slot",s);
            tag.putString("id", Skills.REGISTRY.get().getKey(skill).toString());
            tags.add(tag);
        });
        nbt.put("skill.ns.list",tags);
    }
    public void loadNBTData(CompoundTag nbt)
    {
        skills.clear();
        ListTag tags = nbt.getList("skill.ns.list", Tag.TAG_COMPOUND);
        for (int i=0;i<tags.size();i++){
            CompoundTag tag = tags.getCompound(i);
            Skill skill = Skills.REGISTRY.get().getValue(ResourceLocation.tryParse(tag.getString("id")));
            skills.put(tag.getInt("slot"),skill);
        }
    }
}
