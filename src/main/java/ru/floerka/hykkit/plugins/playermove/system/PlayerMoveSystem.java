package ru.floerka.hykkit.plugins.playermove.system;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.math.vector.Vector3f;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.teleport.Teleport;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import ru.floerka.hykkit.Hykkit;
import ru.floerka.hykkit.HykkitPlayer;
import ru.floerka.hykkit.Location;
import ru.floerka.hykkit.plugins.playermove.event.PlayerMoveEvent;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public final class PlayerMoveSystem extends EntityTickingSystem<EntityStore> {

    private final Query<EntityStore> QUERY = Archetype.of(Player.getComponentType(), PlayerRef.getComponentType(), TransformComponent.getComponentType());


    private final Map<UUID, Vector3d> lastPos =
            new LinkedHashMap<>(16, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<UUID, Vector3d> eldest) {
                    return size() > 1000;
                }
            };

    @Override
    public void tick(float dt, int index,
                     ArchetypeChunk<EntityStore> chunk,
                     Store<EntityStore> store,
                     CommandBuffer<EntityStore> commandBuffer) {

        PlayerRef playerRef = chunk.getComponent(index, PlayerRef.getComponentType());
        Ref<EntityStore> ref = chunk.getReferenceTo(index);

        TransformComponent transform = ref.getStore().getComponent(ref, TransformComponent.getComponentType());

        UUID uuid = ref.getStore().getComponent(ref, UUIDComponent.getComponentType()).getUuid();

        Vector3d now = transform.getPosition();
        Vector3d prev = lastPos.get(uuid);

        if (prev == null) {
            lastPos.put(uuid, now.clone());
            return;
        }

        if (Location.distance(now, prev) < 0.5d) {
            return;
        }

        PlayerMoveEvent moveEvent = Hykkit.callEvent(new PlayerMoveEvent(playerRef, prev,now));

        if (moveEvent.isCancelled()) {

            Vector3f rotation = transform.getRotation();
            Teleport teleport = new Teleport(prev, new Vector3f(rotation.getPitch(), rotation.getYaw(), rotation.getRoll())).withHeadRotation(new Vector3f(rotation.getPitch() ,rotation.getYaw(),rotation.getRoll()));
            commandBuffer.addComponent(ref, Teleport.getComponentType(), teleport);

            lastPos.put(uuid, prev);
        } else {
            lastPos.put(uuid, now);
        }
    }

    @Override
    public Query<EntityStore> getQuery() {
        return QUERY;
    }
}
