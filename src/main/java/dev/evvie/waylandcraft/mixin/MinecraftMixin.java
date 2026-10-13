package dev.evvie.waylandcraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.evvie.waylandcraft.WaylandCraft;
import dev.evvie.waylandcraft.render.WindowTranslucencyHotfix;
import net.minecraft.client.Minecraft;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	
	@Inject(method = "runTick", at = @At(value = "INVOKE_STRING", target = "Lcom/mojang/blaze3d/platform/Window;setErrorSection(Ljava/lang/String;)V", args = "ldc=Render"))
	public void updateRunTick(boolean doTick, CallbackInfo info) {
		WaylandCraft.instance.update();
	}
	
	@Inject(method = "runTick", at = @At(value = "INVOKE_STRING", target = "Lnet/minecraft/util/profiling/ProfilerFiller;popPush(Ljava/lang/String;)V", args = "ldc=blit"))
	public void hotfixRenderFrame(boolean advanceGameTime, CallbackInfo info) {
		WindowTranslucencyHotfix.render();
	}
	
}
