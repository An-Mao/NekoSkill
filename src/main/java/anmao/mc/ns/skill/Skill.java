package anmao.mc.ns.skill;

import com.google.common.collect.Maps;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public abstract class Skill implements SkillCore{
    public Skill(){}
    @Override
    public void attack(LivingEntity player, Entity target){}
    @Override
    public void hurt(LivingEntity player, Entity attacker){}
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
}
