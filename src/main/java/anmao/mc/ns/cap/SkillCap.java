package anmao.mc.ns.cap;

import anmao.mc.amlib.amlib.network.Net;
import anmao.mc.amlib.amlib.network.easy_net.EasyNetSTC;
import anmao.mc.ns.net.NetReg;
import anmao.mc.ns.skill.Career;
import anmao.mc.ns.skill.Faction;
import anmao.mc.ns.skill.Skill;
import anmao.mc.ns.skill.Skills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.HashMap;

public class SkillCap {
    private Faction.Behavior behavior = null;
    private Faction.Morality morality = null;
    private Career career = null;
    private HashMap<Integer, Skill> skills = new HashMap<>();
    private HashMap<ResourceLocation,Integer> learnSkills = new HashMap<>();
    public void addSkill(Skill skill){
        addSkill(Skills.getResourceLocation(skill));
    }
    public void addSkill(ResourceLocation resourceLocation){
        int res = learnSkills.getOrDefault(resourceLocation,0);
        if (res < Skills.getSkill(resourceLocation).getMaxLevel()){
            System.out.println("add level");
            learnSkills.put(resourceLocation,res+1);
        }
    }
    public int getSkillLevel(Skill skill){
        return getSkillLevel(Skills.getResourceLocation(skill));
    }
    public int getSkillLevel(ResourceLocation resourceLocation){
        return learnSkills.getOrDefault(resourceLocation,0);
    }
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
    public boolean hasFaction(){
        return this.behavior != null && this.morality != null;
    }
    public boolean hasCareer(){
        return this.career != null;
    }


    public Faction.Behavior getBehavior() {
        return behavior;
    }

    public Faction.Morality getMorality() {
        return morality;
    }

    public Career getCareer() {
        return career;
    }

    public void setFaction(ServerPlayer player, Faction.Behavior behavior, Faction.Morality morality){
        if (this.behavior == null && this.morality == null){
            this.behavior = behavior;
            this.morality = morality;
            sendChange(player);
        }
    }
    public void setCareer(ServerPlayer player,Career career){
        if (this.career == null){
            this.career = career;
            sendChange(player);
        }
    }



















    public void sendChange(ServerPlayer player){
        System.out.println("send cap info");
        CompoundTag data = new CompoundTag();

        saveNBTData(data);
        data.putString(Net.EASY_NET_KEY, NetReg.Skill.getId().toString());
        Net.EasyNetSTP(new EasyNetSTC(data),player);
    }

    @OnlyIn(Dist.CLIENT)
    public void handle(CompoundTag data){
        loadNBTData(data);
    }


    public void copyFrom(ServerPlayer serverPlayer,SkillCap skill){
        this.skills = skill.skills;
        this.behavior = skill.behavior;
        this.morality = skill.morality;
        this.career = skill.career;
        this.learnSkills = skill.learnSkills;
        sendChange(serverPlayer);
    }
    public void saveNBTData(CompoundTag nbt)
    {
        ListTag skillsTag = new ListTag();
        skills.forEach((s, skill) -> {
            CompoundTag tag = new CompoundTag();
            tag.putInt("slot",s);
            tag.putString("id", Skills.getResourceLocation(skill).toString());
            skillsTag.add(tag);
        });
        nbt.put("skill.ns.list",skillsTag);
        if (behavior != null) {
            nbt.putString("skill.ns.faction.behavior", behavior.name());
        }
        if (morality != null) {
            nbt.putString("skill.ns.faction.morality", morality.name());
        }
        if (career != null) {
            nbt.putString("skill.ns.career", career.name());
        }
        ListTag learnSkillsTag = new ListTag();
        learnSkills.forEach((resourceLocation, integer) -> {
            CompoundTag tag = new CompoundTag();
            tag.putString("skill",resourceLocation.toString());
            tag.putInt("int", integer);
            learnSkillsTag.add(tag);
        });
        nbt.put("skill.ns.learn",learnSkillsTag);
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
        String value = nbt.getString("skill.ns.faction.behavior");
        if (value.isEmpty()){
            behavior = null;
        }else {
            behavior = Faction.Behavior.valueOf(value);
        }
        value = nbt.getString("skill.ns.faction.morality");
        if (value.isEmpty()){
            morality = null;
        }else {
            morality = Faction.Morality.valueOf(value);
        }
        value = nbt.getString("skill.ns.career");
        if (value.isEmpty()){
            career = null;
        }else {
            career = Career.valueOf(value);
        }
        ListTag learn = nbt.getList("skill.ns.learn", Tag.TAG_COMPOUND);
        for (int i=0;i<learn.size();i++){
            CompoundTag tag = learn.getCompound(i);
            learnSkills.put(ResourceLocation.tryParse(tag.getString("skill")), tag.getInt("int"));
        }
    }
}
