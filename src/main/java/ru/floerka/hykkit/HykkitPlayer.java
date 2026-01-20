package ru.floerka.hykkit;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.protocol.GameMode;
import com.hypixel.hytale.protocol.MovementSettings;
import com.hypixel.hytale.server.core.command.system.CommandManager;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.movement.MovementManager;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.damage.Damage;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageCause;
import com.hypixel.hytale.server.core.modules.entity.damage.DamageSystems;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import ru.floerka.hykkit.buffer.WriteOps;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HykkitPlayer {

    private final PlayerRef playerRef;


/*    public HykkitPlayer(@Nonnull Holder<EntityStore> holder, @Nonnull UUID uuid, @Nonnull String username, @Nonnull String language, @Nonnull PacketHandler packetHandler, @Nonnull ChunkTracker chunkTracker) {
        super(holder, uuid, username, language, packetHandler, chunkTracker);
    }*/

    public HykkitPlayer(PlayerRef playerRef) {
        this.playerRef = playerRef;
    }


    public void damage(Damage.Source source, DamageCause cause, float amount) {
        Damage damage = new Damage(source,cause, amount);
        findRef().ifPresent(ref -> {
            DamageSystems.executeDamage(ref, ref.getStore(), damage);
        });
    }

    public void damage(float amount) {
        damage(Damage.NULL_SOURCE, DamageCause.COMMAND, amount);
    }
    public void damage(DamageCause cause, float amount) {
        damage(Damage.NULL_SOURCE, cause, amount);
    }

    public void setGameMode(GameMode gameMode) {
        findRef().ifPresent(ref -> {
            Player playerComponent = ref.getStore().getComponent(ref, Player.getComponentType());
            if(playerComponent != null && playerComponent.getGameMode() != gameMode) {
                Player.setGameMode(ref, gameMode, ref.getStore());
            }
        });
    }

    public void executeCommand(String command) {
        getPlayer().ifPresent(player -> {
            CommandManager.get().handleCommand(player, command);
        });
    }

    public Optional<World> getWorld() {
        return findRef().map(ref -> ref.getStore().getExternalData().getWorld());
    }

    public Optional<Vector3d> getLocation() {
        return findComponent(TransformComponent.getComponentType()).map(tc -> tc.getPosition());
    }

    public Optional<Location> getHykkitLoc() {
        return getLocation().map(Location::new);
    }

    public void teleport(Vector3d vector3d) {

        //findComponent(TransformComponent.getComponentType()).ifPresent(tc -> tc.teleportPosition(vector3d));

        findRef().ifPresent(ref -> {
            findComponent(TransformComponent.getComponentType()).ifPresent(transformComponent -> {
                Vector3f rotation = transformComponent.getRotation();
                Teleport teleport = new Teleport(vector3d, new Vector3f(rotation.getPitch(), rotation.getYaw(), rotation.getRoll()));
                WriteOps.add(ref, Teleport.getComponentType(), teleport);
                //ref.getStore().addComponent(ref, Teleport.getComponentType(), teleport);
            });

        });

    }

    public boolean isFly() {
        return findComponent(MovementManager.getComponentType()).map(movementManager -> movementManager.getSettings().canFly).orElse(false);
    }

    public List<PlayerRef> getNearbyPlayers(int radius) {
        List<PlayerRef> list = new ArrayList<>();
        Location current = getHykkitLoc().orElse(null);
        if(current == null) return list;

        getWorld().ifPresent(world -> world.execute(() -> world.getPlayerRefs().forEach(playerRef -> {
            HykkitPlayer hykkitPlayer = new HykkitPlayer(playerRef);
            hykkitPlayer.getHykkitLoc().ifPresent(location -> {
                double dist = location.distance(current);
                if(dist <= radius) list.add(playerRef);
            });
        })));
        return list;
    }

    public List<HykkitPlayer> getNearbyHykkitPlayers(int radius) {
        return getNearbyPlayers(radius).stream().map(HykkitPlayer::new).toList();
    }

    public void setFly(boolean enable) {
        if (isFly() == enable) return;

        findRef().ifPresent(ref -> {
            MovementManager movement = ref.getStore().getComponent(ref, MovementManager.getComponentType());

            if (movement != null) {
                MovementSettings settings = movement.getSettings();
                settings.canFly = enable;
                WriteOps.add(ref, MovementManager.getComponentType(), movement);
                //ref.getStore().putComponent(ref, MovementManager.getComponentType(), movement);
            }
        });
    }

    public Optional<Player> getPlayer() {
        return findRef().map(ref -> ref.getStore().getComponent(ref, Player.getComponentType()));
    }

    public <T extends Component<EntityStore>> Optional<T> findComponent(@Nonnull ComponentType<EntityStore, T> type) {
        return findRef().map(ref -> ref.getStore().getComponent(ref, type));
    }


    public Optional<Ref<EntityStore>> findRef() {
        Ref<EntityStore> ref = playerRef.getReference();
        return ref == null || !ref.isValid() ? Optional.empty() : Optional.of(ref);
    }
}
