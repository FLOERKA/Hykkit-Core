package ru.floerka.hykkit;

import com.hypixel.hytale.math.vector.Vector3d;

public class Location extends Vector3d {

    private final double x;
    private final double y;
    private final double z;

    public Location(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(Vector3d vector3d) {
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double distance(Location location) {

        double dx = x - location.getX();
        double dy = y - location.getY();
        double dz = z - location.getZ();

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    public static double distance(Vector3d loc1, Vector3d loc2) {

        double dx = loc1.getX() - loc2.getX();
        double dy = loc1.getY() - loc2.getY();
        double dz = loc1.getZ() - loc2.getZ();

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
