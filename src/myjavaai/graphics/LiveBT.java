package myjavaai.graphics;

import bt.BehaviourTree;
import bt.Task;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Hallvard on 26.11.2015.
 */
public class LiveBT extends JComponent {

    static JFrame window;
    static LiveBT live;

    private HashMap<Task, TaskRep> nodes = new HashMap<>();
    private BehaviourTree behaviourTree;

    public LiveBT(BehaviourTree bt) {
        this.behaviourTree = bt;

        Task root = behaviourTree.getChild(0);
        generate(root);
    }

    private void update() {
        nodes.values().forEach(TaskRep::update);
    }

    private void generate(Task task){
        nodes.put(task, new TaskRep(task));

        final int chldrn = task.getChildCount();
        if(chldrn==0)    return;

        for(int i=0; i<chldrn; i++) {
            generate(task.getChild(i));
        }
    }

    @Override
    public void paint(Graphics g) {
        // Painting smoother graphics with Anti-aliasing
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        nodes.values().forEach(c -> this.add(c));

    }

    private void paintTree(Graphics g, int depth, int width) {

    }


    public static void startTransmission(BehaviourTree bt) {
        live = new LiveBT(bt);
        window = new JFrame();

//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(30, 30, 300, 300);
        window.getContentPane().add(live);
        window.setVisible(true);
    }

    public static void draw() {
        if(null == live)
            return;
        live.update();
    }
}
