package net.zioxs.replayfight.mixin;

import com.replaymod.recording.ReplayModRecording;
import net.minecraft.network.protocol.Packet;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.network.EpicFightNetworkManager;

@Mixin(EpicFightNetworkManager.class)
public abstract class EFNetworkManagerMixin {

    @Invoker(value = "createVanillaPacket", remap = false)
    private static Packet<?> callCreateVanillaPacket(Object message, PacketDistributor.PacketTarget packetTarget, Object[] messages) {
        throw new AssertionError();
    }

    @Inject(method = "sendToClient(Ljava/lang/Object;Lnet/minecraftforge/network/PacketDistributor$PacketTarget;[Ljava/lang/Object;)V", at = @At("HEAD"), remap = false)
    private static void sendToClient(Object message, PacketDistributor.PacketTarget packetTarget, Object[] messages, CallbackInfo ci) {
        if (ReplayModRecording.instance.getConnectionEventHandler().getPacketListener() != null)
            ReplayModRecording.instance.getConnectionEventHandler().getPacketListener().save(callCreateVanillaPacket(message, packetTarget, messages));
    }

}
