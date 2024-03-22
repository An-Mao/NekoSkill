package anmao.mc.ns.skill;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public abstract class Skill implements SkillCore{
    private String descriptionId = null;
    private String nameId = null;
    public Skill(){}
    @Override
    public void spontaneous(Player player){}
    @Override
    public void attack(Player player, Entity target){}
    @Override
    public void hurt(Player player, Entity attacker){}
    public int getMaxLevel(){
        return 1;
    }
    @Override
    public boolean canLearn(Player player) {
        return true;
    }
    public boolean equipped(Player player){
        return true;
    }
    public boolean disarmed(Player player){
        return true;
    }
    public String getId(){
        return getRes().getPath();
    }
    public ResourceLocation getRes(){
        return Skills.REGISTRY.get().getKey(this);
    }
    public Component getName(){
        if (nameId == null){
            ResourceLocation resourceLocation = getRes();
            nameId = "skill."+resourceLocation.getNamespace()+"."+resourceLocation.getPath();
        }
        return Component.translatable(nameId);
    }
    public Component getDesc(){
        if (descriptionId == null){
            ResourceLocation resourceLocation = getRes();
            descriptionId = "skill."+resourceLocation.getNamespace()+"."+resourceLocation.getPath()+".desc";
        }
        return Component.translatable(descriptionId);
    }
}
