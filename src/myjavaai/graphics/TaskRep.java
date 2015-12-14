package myjavaai.graphics;

import bt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by Hallvard on 26.11.2015.
 */
public class TaskRep extends JPanel {
    public enum Type {
        DECORATOR,
        COMPOSITE,
        LEAF,
        OTHER
    }

    public final int WIDTH = 20;
    public final int HEIGHT = 10;
    public final int TEXT_MARGIN = 5;

    private final Task task;
    private Task.TaskState state = Task.TaskState.NEUTRAL;
    private final Type type;

    public TaskRep(Task task) {
        this.task = task;
        this.state = task.taskState;

        if      (task instanceof Decorator)
            type = Type.DECORATOR;
        else if (task instanceof bt.Composite)
            type = Type.COMPOSITE;
        else if (task instanceof Leaf)
            type = Type.LEAF;
        else
            type = Type.OTHER;
    }

    public void update() {
        if(task.taskState == state) {
            return;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawString(task.getClass().getSimpleName(), 0, HEIGHT+TEXT_MARGIN);

        g.setColor(state.color);
        switch (type) {
            case DECORATOR: {
                g.fillPolygon(new int[]{0, WIDTH / 2, WIDTH, WIDTH / 2}, new int[]{HEIGHT / 2, 0, HEIGHT / 2, HEIGHT}, 4);
                break;
            }
            case COMPOSITE: {
                g.fillOval(0, 0, WIDTH, HEIGHT);
                break;
            }
            case LEAF: {
                g.fillRect(0,0,WIDTH, HEIGHT);
                break;
            }
            case OTHER:
                g.fillPolygon(new int[]{WIDTH/4,WIDTH/2,WIDTH/4,WIDTH/2}, new int[]{HEIGHT/2, 0, HEIGHT/2, HEIGHT}, 4);
        }
    }
}
