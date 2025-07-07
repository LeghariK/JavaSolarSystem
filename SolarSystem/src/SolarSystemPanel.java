import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Random;

public class SolarSystemPanel extends JPanel implements ActionListener {
    private static final int WIDTH = 1000, HEIGHT = 750;

    private static final int NUM_STARS = 1200;
    private final int exclusionRadius = 60;
    private double zoom = 1.0;
    private double swirlAngle = 0;

    private final int sunRadius = 30;

    private final Star[] stars = new Star[NUM_STARS];
    private final Planet[] planets = Planet.getPlanets();
    private  final Timer timer = new Timer(16, this);

    public SolarSystemPanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);

        Random rand = new Random();

        for(int i = 0; i < NUM_STARS; i++) {
            double dist;

            do {
                dist = rand.nextDouble() * WIDTH * 3;
            } while (dist < exclusionRadius);

            double theta = rand.nextDouble() * 2 * Math.PI;
            stars[i] = new Star(dist, theta, Color.WHITE);
        }

        // Add mouse scroll listener
        addMouseWheelListener(e -> {
            zoom += e.getPreciseWheelRotation() * -0.1;
            zoom = Math.max(0.2, Math.min(zoom, 5));
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.translate(WIDTH / 2, HEIGHT / 2);
        g2.scale(zoom, zoom);

        // Draw stars
        for(Star s : stars) {
            double x = s.dist * Math.cos(s.theta + swirlAngle);
            double y = s.dist * Math.sin(s.theta + swirlAngle);

            double distanceFromCenter = Math.hypot(x, y);

            // we don't want to draw stars in center
            if(distanceFromCenter > exclusionRadius) {
                g2.setColor(Color.WHITE);
                g2.fillOval((int) x, (int) y, 2, 2);
            }
        }

        // Draw sun

        var radial = new RadialGradientPaint(
                new Point2D.Float(0, 0), sunRadius,
                new float[] {0f, 1f},
                new Color[] {Color.YELLOW, new Color(255, 140, 0, 0)}
        );

        g2.setPaint(radial);
        g2.fillOval(-sunRadius, -sunRadius, sunRadius * 2, sunRadius * 2);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("Sun", -10, -sunRadius - 5);

        // Draw orbits

        g2.setColor(new Color(255, 255, 255, 30));

        for(Planet p : planets) {
            g2.drawOval(-p.orbitRadius, -p.orbitRadius, p.orbitRadius * 2, p.orbitRadius*2);
        }

        // Draw planets + labels

        for(Planet p : planets) {
            double x = p.orbitRadius * Math.cos(p.angle);
            double y = p.orbitRadius * Math.sin(p.angle);

            g2.setColor(p.color);
            g2.fillOval((int) x - p.size / 2, (int) y - p.size / 2, p.size, p.size);

            g2.setColor(Color.WHITE);
            g2.drawString(p.name, (int) (x + p.size), (int) (y - p.size));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        swirlAngle += 0.0015;

        // change angle to animate planets

        for(Planet p : planets) {
            p.angle += p.speed;
        }
        repaint();
    }

}
