package fishcute.celestial.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import fishcute.celestial.sky.CelestialSky;
import fishcute.celestial.util.Util;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(BackgroundRenderer.class)
public class FogRendererMixin {
    @ModifyVariable(method = "applyFog", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private static boolean setupFog(boolean thickFog) {
        if (CelestialSky.doesDimensionHaveCustomSky() && CelestialSky.getDimensionRenderInfo().environment.useSimpleFog())
            return CelestialSky.getDimensionRenderInfo().environment.hasThickFog;
        return thickFog;
    }
    @Inject(method = "applyFog", at = @At("RETURN"))
    private static void setupFog(Camera p_228372_0_, BackgroundRenderer.FogType p_228372_1_, float p_228372_2_, boolean p_228372_3_, CallbackInfo ci) {
        if (CelestialSky.doesDimensionHaveCustomSky() && !CelestialSky.getDimensionRenderInfo().environment.useSimpleFog()) {

            Map<String, String> toReplaceMap = new HashMap<>();
            toReplaceMap.put("#viewDistance", p_228372_2_ + "");
            toReplaceMap.put("#minViewDistance", Math.min(p_228372_2_, 192.0F) + "");

            toReplaceMap.putAll(Util.getReplaceMapNormal());
            RenderSystem.fogStart((float) Util.solveEquation(CelestialSky.getDimensionRenderInfo().environment.fogStart, toReplaceMap));
            RenderSystem.fogEnd((float) Util.solveEquation(CelestialSky.getDimensionRenderInfo().environment.fogEnd, toReplaceMap));
        }
    }
}
