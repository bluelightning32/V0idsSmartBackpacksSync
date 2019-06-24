package com.bluelightning32.v0idssmartbackpackssync;

import java.util.Optional;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.entity.living.player.Player;
import v0id.api.vsb.capability.IVSBPlayer;
import v0id.api.vsb.capability.VSBCaps;
import world.jnc.invsync.util.serializer.NativeInventorySerializer;
import world.jnc.invsync.util.serializer.CapabilitySerializer;
import world.jnc.invsync.util.serializer.module.mod.BaseModSyncModule;

public class V0idsSmartBackpacksSyncModule extends BaseModSyncModule {
  private static final DataQuery PLAYER_DATA = DataQuery.of("player_data");

  @Override
  public String getModId() {
    return "v0idssmartbackpacks";
  }

  @Override
  public DataView serialize(Player player, DataView container) {
    final EntityPlayer nativePlayer = NativeInventorySerializer.getNativePlayer(player);

    container.set(
        PLAYER_DATA,
        CapabilitySerializer.serializeCapabilityToData(VSBCaps.PLAYER_CAPABILITY, nativePlayer));

    return container;
  }

  @Override
  public void deserialize(Player player, DataView container) {
    Optional<DataView> player_data = container.getView(PLAYER_DATA);

    if (player_data.isPresent()) {
      final EntityPlayer nativePlayer = NativeInventorySerializer.getNativePlayer(player);

      CapabilitySerializer.deserializeCapabilityFromData(
          VSBCaps.PLAYER_CAPABILITY, nativePlayer, player_data.get());
      IVSBPlayer vsb_player = IVSBPlayer.of(nativePlayer);
      if (vsb_player != null) vsb_player.sync();
    }

    if (getDebug()) {
      getLogger().info("\t\tisPresent:");
      getLogger().info("\t\t\tplayer_data:\t" + player_data.isPresent());
    }
  }
}
