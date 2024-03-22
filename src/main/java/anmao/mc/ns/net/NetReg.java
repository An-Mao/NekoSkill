package anmao.mc.ns.net;

import anmao.mc.amlib.amlib.network.easy_net.EasyNet;
import anmao.mc.amlib.amlib.network.easy_net.EasyNetRegister;
import anmao.mc.ns.NS;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class NetReg {
    public static final DeferredRegister<EasyNet> EASY_NET = DeferredRegister.create(EasyNetRegister.KEY, NS.MOD_ID);
    public static final RegistryObject<EasyNet> Skill = EASY_NET.register("skill",SkillNet::new);
    public static void reg(IEventBus eventBus){
        EASY_NET.register(eventBus);
    }
}
