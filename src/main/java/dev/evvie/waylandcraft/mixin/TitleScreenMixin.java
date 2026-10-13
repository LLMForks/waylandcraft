package dev.evvie.waylandcraft.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import dev.evvie.waylandcraft.WaylandCraft;
import dev.evvie.waylandcraft.WaylandCraftCommon;
import dev.evvie.waylandcraft.gui.WaylandCraftSettingsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	
	protected TitleScreenMixin() {
		super(null);
	}
	
	@Unique
	private int topPos;
	
	// The language button is placed at the final topPos value of init(), capture it from there
	@WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/SpriteIconButton;setPosition(II)V", ordinal = 0))
	private void captureTopPos(SpriteIconButton languageButton, int x, int y, Operation<Void> original) {
		topPos = y;
		original.call(languageButton, x, y);
	}
	
	@Inject(method = "init", at = @At("TAIL"))
	public void addWaylandCraftButton(CallbackInfo info) {
		SpriteIconButton button = SpriteIconButton
				.builder(Component.literal("waylandcraft"), (b) -> {Minecraft.getInstance().setScreen(new WaylandCraftSettingsScreen(WaylandCraft.instance));}, true)
				.sprite(Identifier.fromNamespaceAndPath(WaylandCraftCommon.MOD_ID, "logo"), 16, 16)
				.width(20)
				.build();
		button.setPosition(width / 2 - 124, topPos - 36);
		this.addRenderableWidget(button);
	}
	
}
