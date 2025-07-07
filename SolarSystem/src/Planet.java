import java.awt.*;

public class Planet {
    String name;
    int orbitRadius;
    int size;
    double speed;
    double angle = Math.random() * 2 * Math.PI;

    Color color;

    public Planet(String name, int orbitRadius, int size, double speed, Color color) {
        this.name = name;
        this.orbitRadius = orbitRadius;
        this.size = size;
        this.speed = speed;
        this.color = color;
    }

    // creating planets for our solar system
    public static Planet[] getPlanets() {
        return new Planet[] {
                new Planet("Mercury", 30, 4, 0.02, Color.GRAY),
                new Planet("Venus", 50, 6, 0.015, new Color(255, 204, 102)),
                new Planet("Earth", 70, 6, 0.012, Color.CYAN),
                new Planet("Mars", 90, 5, 0.009, Color.RED),
                new Planet("Jupiter", 120, 12, 0.006, Color.ORANGE),
                new Planet("Saturn", 160, 10, 0.005, new Color(210, 180, 140)),
                new Planet("Uranus", 200, 8, 0.004, Color.BLUE),
                new Planet("Neptune", 240, 8, 0.0035, new Color(0, 0, 128)),
                new Planet("Pluto", 280, 3, 0.002, new Color(190, 190, 190))
       };
    }
}
