package anmao.mc.ns;

import anmao.mc.ns.net.NetReg;
import anmao.mc.ns.skill.Skills;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod(NS.MOD_ID)
public class NS
{
    public static final String MOD_ID = "ns";
    public NS()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        Skills.register(modEventBus);
        NetReg.reg(modEventBus);
    }
}
