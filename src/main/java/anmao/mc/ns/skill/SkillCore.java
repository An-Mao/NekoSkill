package anmao.mc.ns.skill;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface SkillCore {
    void spontaneous(Player player);
    void attack(Player player, Entity target);
    void hurt(Player player, Entity attacker);
    boolean canLearn(Player player);
}
