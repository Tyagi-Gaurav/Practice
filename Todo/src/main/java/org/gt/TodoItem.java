package org.gt;

import org.immutables.value.Value;

import java.util.UUID;

@Value.Immutable
public abstract class TodoItem {
    public abstract UUID id();
    public abstract String name();
    public abstract boolean completed();
}
