package anmao.mc.ns.skill;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface SkillCore {
    void attack(LivingEntity player, Entity target);
    void hurt(LivingEntity player, Entity attacker);
    boolean canLearn(Player player);
}
