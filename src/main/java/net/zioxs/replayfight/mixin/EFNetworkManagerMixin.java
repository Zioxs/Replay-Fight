package net.zioxs.replayfight.mixin;

import com.replaymod.recording.ReplayModRecording;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.network.ManagedCustomPacketPayload;

@Mixin(PacketDistributor.class)
public abstract class EFNetworkManagerMixin {

    @Inject(method = "makeClientboundPacket", at = @At("RETURN"))
    private static void makeClientboundPacket(CustomPacketPayload payload, CustomPacketPayload[] payloads, CallbackInfoReturnable<Packet<?>> cir) {
        if (ReplayModRecording.instance.getConnectionEventHandler().getPacketListener() != null) {
            if (payload.type().id().getNamespace().equals("epicfight")) {
                ReplayModRecording.instance.getConnectionEventHandler().getPacketListener().save(cir.getReturnValue());
            }
        }
    }

}
