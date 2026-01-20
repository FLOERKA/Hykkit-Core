package ru.floerka.hykkit.plugins.playermove.event;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.event.ICancellable;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.event.events.player.PlayerEvent;
import com.hypixel.hytale.server.core.event.events.player.PlayerRefEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;


public class PlayerMoveEvent extends PlayerRefEvent<Void> implements ICancellable {


    private final PlayerRef playerRef;
    private final Vector3d from;
    private final Vector3d to;
    private boolean cancelled;

    public PlayerMoveEvent(PlayerRef playerRef, Vector3d from, Vector3d to) {
        super(playerRef);
        this.playerRef = playerRef;
        this.from = from;
        this.to = to;
    }

    public PlayerRef getPlayerRef() { return playerRef; }
    public Vector3d getFrom() { return from; }
    public Vector3d getTo() { return to; }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}