package myjavaai;

import bt.BehaviourTree;
import bt.Leaf;
import bt.Task;
import bt.utils.TreeInterpreter;
import bt.utils.graphics.LiveBT;
import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.AbstractOOAI;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.clb.Resource;
import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.UnitDef;
import myjavaai.bt.builder.conditions.NeedEnergy;
import myjavaai.bt.test.Right;
import myjavaai.construction.ConstructionManager;
import myjavaai.economy.EconomyManager;
import myjavaai.utils.BaseTask;
import myjavaai.utils.UnitListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Hallvard on 16.11.2015.
 */
public class MyJavaAI extends AbstractOOAI{

    public HashMap<String, UnitDef> unitDefs = new HashMap<>();
    private List<Manager> managers;
    private List<UnitListener<BaseTask>> listeners;

    public List<AIFloat3> metalDetectors = new ArrayList<>(); //TODO remove this shit

    public OOAICallback callback;
    public Unit commander;

    Resource m;
    Resource e;

    BehaviourTree<MyJavaAI> bt;
    ScheduledExecutorService executor;
    Runnable btTask;

    {
        managers = new ArrayList<Manager>() {{
            add(new EconomyManager());
            add(new ConstructionManager());
        }};
        listeners = new ArrayList<>();

        executor = Executors.newScheduledThreadPool(1);

        bt = TempTree.test2(this);

        btTask = ()-> {
            bt.step();
            LiveBT.draw();
        };

    }

    @Override
    public int init(int teamId, OOAICallback callback) {
        this.callback = callback;

        List<UnitDef> unitDefsList = this.callback.getUnitDefs();
        for(UnitDef def : unitDefsList){
            unitDefs.put(def.getName(), def);
        }

        //TODO

        m = callback.getResourceByName("Metal");
        e = callback.getResourceByName("Energy");
        metalDetectors = callback.getMap().getResourceMapSpotsPositions(m);

        LiveBT.startTransmission(bt);

        return 0; // status OK
    }

    @Override
    public int unitFinished(Unit unit){

        UnitListener<BaseTask> ul = getRelevantListenerDef(unit.getDef());
        if (null != ul)
            if(ul.getFocal().isPresent()) {
                ul.TaskCompleted();
            }

        if(null == commander) {
            commander = unit;
            executor.scheduleWithFixedDelay(btTask, 100, 1000, TimeUnit.MILLISECONDS);
        }
/*
        if(unit.getDef().getName().equals(C.CLOAKY_BOT_FACTORY)) {
            unit.setRepeat(true, (short) 0, Integer.MAX_VALUE);
            build(unit, C.CONJURER, 0);
        }*/
/*
        if(unit.getDef().getName().equals(C.WARRIOR) || unit.getDef().getName().equals(C.GLAIVE)) {
            List<AIFloat3> l = callback.getMap().getResourceMapSpotsPositions(m);
            AIFloat3 rand = l.get(new Random().nextInt(l.size()));
            unit.patrolTo(rand, (short)0, Integer.MAX_VALUE);
        }
*/
        /*
        if(unit.getDef().getName().equals(C.CONJURER)) {
            List<AIFloat3> l = callback.getMap().getResourceMapSpotsPositions(m);
            AIFloat3 rand = l.get(new Random().nextInt(l.size()));
            unit.build(unitDefs.get(C.METAL_EXTRACTOR), callback.getMap().findClosestBuildSite(unitDefs.get(C.METAL_EXTRACTOR), rand, 1000, (short) 0, Integer.MAX_VALUE), 0 ,(short) 0, Integer.MAX_VALUE);
          }
*/
        return 0; // OK
    }

    @Override
    public int unitMoveFailed(Unit unit) {
        UnitListener<BaseTask> l = getRelevantListener(unit);
        if(l.getFocal().isPresent()) {
            l.TaskInterrupted();
        }
        return 0; // OK
    }

    @Override
    public int unitDestroyed(Unit unit, Unit attacker) {
        UnitListener<BaseTask> l = getRelevantListener(unit);
        if (l.getFocal().isPresent()) {
            l.unitDestroyed();
        }
        return 0; // OK
    }


    @Override
    public int commandFinished(Unit unit, int commandId, int commandTopicId) {
        return 0; // OK
    }

    @Override
    public int update(int frame) {

        if(null != commander) {
            if (frame % 10 == 0 && frame > 50) {
//                bt.step();
  //              LiveBT.draw();
            }
        }

    //    managers.forEach(h -> h.update(frame));

        return 0;
    }

    private UnitListener<BaseTask> getRelevantListener(Unit unit) {
        for(UnitListener<BaseTask> listener : listeners) {
            if(unit.equals(listener.getFocal().get().builder))
                return listener;
        }
        return (null);
    }
    private UnitListener<BaseTask> getRelevantListenerDef(UnitDef unit) {
        for(UnitListener<BaseTask> listener : listeners) {
            if(unit.getName().equals(listener.getFocal().get().buildee.getName()))
                return listener;
        }
        return (null);
    }


    public void build(Unit builder, String uniqueName, int radius) {
        builder.build(unitDefs.get(uniqueName), callback.getMap().findClosestBuildSite(unitDefs.get(uniqueName), builder.getPos(), 1000, radius, Integer.MAX_VALUE), 0 ,(short) 0, Integer.MAX_VALUE);


    }

    public void addListener(UnitListener<BaseTask> listener) {
        listeners.add(listener);
    }
    public boolean removeListener(UnitListener listener) {
        return listeners.remove(listener);
    }

    public void debug(String s) {
        if(null == callback)
            return;
        callback.getGame().sendTextMessage(s, 0);
    }

}
