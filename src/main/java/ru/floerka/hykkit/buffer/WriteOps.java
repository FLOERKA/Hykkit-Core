package ru.floerka.hykkit.buffer;

import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public final class WriteOps {
    public static <T extends Component<EntityStore>> void put(Ref<EntityStore> ref,
                                                              ComponentType<EntityStore, T> type, T value) {

        CommandBuffer<EntityStore> cb = HykkitContext.commandBufferOrNull();
        if (cb != null) {
            cb.putComponent(ref, type, value);
        } else {
            ref.getStore().putComponent(ref, type, value);
        }
    }

    public static <T extends Component<EntityStore>> void add(Ref<EntityStore> ref,
                                                              ComponentType<EntityStore, T> type, T value) {

        CommandBuffer<EntityStore> cb = HykkitContext.commandBufferOrNull();
        if (cb != null) {
            cb.addComponent(ref, type, value);
        } else {
            ref.getStore().addComponent(ref, type, value);
        }
    }
}