package com.misaka10843.networkproxy.mixin.client;

import com.misaka10843.networkproxy.integration.ModMenuIntegration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JoinMultiplayerScreen.class)
public class MultiplayerScreenMixin extends Screen{

    protected MultiplayerScreenMixin(Component title){
        super(title);
    }

    @Unique
    public Button proxyButton = null;

    @Inject(method = "init", at = @At("HEAD"))
    private void addProxyButton(CallbackInfo ci){
        proxyButton = Button.builder(Component.translatable("gui.networkproxy.proxy_settings"), button -> {
            Minecraft.getInstance().setScreen(ModMenuIntegration.createConfigScreen( this));
        })
            .bounds(this.width - 75, 5, 70, 20)
            .build();
        this.addRenderableWidget(proxyButton);
    }

    @Inject(method = "repositionElements", at = @At("HEAD"))
    private void repositionElements(CallbackInfo ci){
        if(proxyButton != null){
            proxyButton.setX(this.width - 75);
            proxyButton.setY(5);
        }
    }
}
