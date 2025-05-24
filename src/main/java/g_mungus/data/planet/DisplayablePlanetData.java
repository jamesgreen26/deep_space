package g_mungus.data.planet;

public class DisplayablePlanetData {
    public String object_name;
    public double x, y, z;
    public float yaw, pitch, roll;
    public double scale;

    @Override
    public String toString() {
        return "PlanetData{" +
                "object_name='" + object_name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                ", roll=" + roll +
                ", scale=" + scale +
                '}';
    }
}

