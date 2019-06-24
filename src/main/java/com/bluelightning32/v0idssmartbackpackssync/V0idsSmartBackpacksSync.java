package com.bluelightning32.v0idssmartbackpackssync;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

import org.spongepowered.api.service.permission.PermissionDescription;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.text.Text;
import world.jnc.invsync.util.serializer.PlayerSerializer;

@Plugin(
        id = "v0idssmartbackpackssync",
        name = "V0idsSmartBackpacksSync",
        description = "An addon for InvSync to synchronize the player's equipped V0id's Smart Backpacks.",
        dependencies = {
                @Dependency(id = "invsync"),
                @Dependency(id = "v0idssmartbackpacks")
        }
)
public class V0idsSmartBackpacksSync {
  @Inject
  private Logger logger;

  @Listener
  public void onPostInitialization(GamePostInitializationEvent event) {
    PermissionService permissionService =
            Sponge.getServiceManager().provide(PermissionService.class).get();

    V0idsSmartBackpacksSyncModule module = new V0idsSmartBackpacksSyncModule();

    PermissionDescription.Builder builder = permissionService.newDescriptionBuilder(this);

    builder.id(module.getPermission())
           .description(Text.of("Allow this user's equipped V0id's Smart Backpack to be synchronized"))
           .assign(PermissionDescription.ROLE_USER, true)
           .register();
    PlayerSerializer.registerModule(module);
  }
}
