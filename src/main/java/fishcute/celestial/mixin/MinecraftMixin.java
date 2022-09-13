package fishcute.celestial.mixin;

import fishcute.celestial.util.ClientTick;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        ClientTick.tick();
    }
}
