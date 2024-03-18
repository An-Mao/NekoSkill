package anmao.mc.ns.skill;

import anmao.mc.ns.NS;
import anmao.mc.ns.skill.all.BaseSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Skills {

    public static final ResourceLocation KEY = new ResourceLocation(NS.MOD_ID, "skill");
    public static final DeferredRegister<Skill> SKILL = DeferredRegister.create(KEY, NS.MOD_ID);
    public static final Supplier<IForgeRegistry<Skill>> REGISTRY = SKILL.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<Skill> BASE = SKILL.register("base", BaseSkill::new);



    public static void register(IEventBus eventBus){
        SKILL.register(eventBus);
    }
    public static Skill getSkill(String resourceLocation){
        return getSkill(ResourceLocation.tryParse(resourceLocation));
    }
    public static Skill getSkill(ResourceLocation resourceLocation){
        return REGISTRY.get().getValue(resourceLocation);
    }
    public static ResourceLocation getResourceLocation(Skill skill){
        return REGISTRY.get().getKey(skill);
    }
}
