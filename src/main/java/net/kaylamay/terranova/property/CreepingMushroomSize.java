package net.kaylamay.terranova.property;

import net.minecraft.util.StringRepresentable;

public enum CreepingMushroomSize implements StringRepresentable {
    SMALL("small"), MEDIUM("medium"), LARGE("large");

    private final String name;

    CreepingMushroomSize(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public boolean canGrow() { return this != LARGE; }

    public CreepingMushroomSize grow() {
        return switch (this) {
            case SMALL -> MEDIUM;
            case MEDIUM -> LARGE;
            case LARGE -> LARGE;
        };
    }
}
