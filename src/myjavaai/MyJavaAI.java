package myjavaai;

import bt.BehaviourTree;
import bt.Task;
import bt.composite.Sequence;
import bt.decorator.UntilFail;
import bt.leaf.Action;
import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.AbstractOOAI;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.clb.Resource;
import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.UnitDef;
import myjavaai.bt.builder.actions.Build_Lotus;
import myjavaai.bt.builder.actions.Build_MEX;
import myjavaai.bt.builder.actions.Build_SolarPanel;
import myjavaai.construction.ConstructionManager;
import myjavaai.economy.EconomyManager;
import myjavaai.utils.BaseTask;
import myjavaai.utils.C;
import myjavaai.utils.UnitListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by Hallvard on 16.11.2015.
 */
public class MyJavaAI extends AbstractOOAI{

    public HashMap<String, UnitDef> unitDefs = new HashMap<>();
    private List<Manager> managers;
    private List<UnitListener<BaseTask>> listeners;

    public OOAICallback callback;
    public Unit commander;

    Resource m;
    Resource e;

//    Runnable runBT;

    BehaviourTree<MyJavaAI> bt;

    {
        managers = new ArrayList<Manager>() {{
            add(new EconomyManager());
            add(new ConstructionManager());
        }};
        listeners = new ArrayList<>();
/*
        bt = new BehaviourTree<MyJavaAI>(
                new UntilFail<MyJavaAI>(
                        new Sequence<MyJavaAI>(
                                new Build_MEX(),
                                new Build_SolarPanel()
                        )
                )
        , this);
*/
        /*
        runBT = () -> {
            BehaviourTree<MyJavaAI> bt = new BehaviourTree<>(
                    new UntilFail<>(
                            new Sequence<MyJavaAI>(
                                    new Build_MEX(),
                                    new Build_SolarPanel(),
                                    new Build_Lotus()
                            )
                    )
            );
            bt.start();
        };
        */
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

        return 0;
    }

    @Override
    public int unitFinished(Unit unit){

        callback.getGame().sendTextMessage("Unit created: Test", 0);

        UnitListener<BaseTask> ul = getRelevantListenerDef(unit.getDef());
        if(ul.getFocal().isPresent()) {
            ul.TaskCompleted();
        }

        if(unit.getDef().getName().equals(C.STRIKE_COMMANDER)) {
            commander = unit;
            debug("Test");

            /*
            Thread btThread = new Thread(runBT);
            btThread.start();
            */
        }
/*
        if(unit.getDef().getName().equals(C.CLOAKY_BOT_FACTORY)) {
            unit.setRepeat(true, (short) 0, Integer.MAX_VALUE);
            build(unit, C.CONJURER, 0);
            build(unit, C.GLAIVE, 0);
            build(unit, C.GLAIVE, 0);
            build(unit, C.GLAIVE, 0);
            build(unit, C.WARRIOR, 0);
            build(unit, C.WARRIOR, 0);
            build(unit, C.WARRIOR, 0);
            build(unit, C.GLAIVE, 0);
            build(unit, C.WARRIOR, 0);
            build(unit, C.WARRIOR, 0);
        }
        */
/*
        if(unit.getDef().getName().equals(C.WARRIOR) || unit.getDef().getName().equals(C.GLAIVE)) {
            List<AIFloat3> l = callback.getMap().getResourceMapSpotsPositions(m);
            AIFloat3 rand = l.get(new Random().nextInt(l.size()));
            unit.patrolTo(rand, (short)0, Integer.MAX_VALUE);
        }

        if(unit.getDef().getName().equals(C.CONJURER)) {
            List<AIFloat3> l = callback.getMap().getResourceMapSpotsPositions(m);
            AIFloat3 rand = l.get(new Random().nextInt(l.size()));
            AIFloat3 rand2 = l.get(new Random().nextInt(l.size()));
            unit.build(unitDefs.get(C.METAL_EXTRACTOR), callback.getMap().findClosestBuildSite(unitDefs.get(C.METAL_EXTRACTOR), rand, 5, (short) 0, Integer.MAX_VALUE), 0 ,(short) 0, Integer.MAX_VALUE);
         //   build(unit, C.SOLAR_COLLECTOR, 10);
            unit.build(unitDefs.get(C.METAL_EXTRACTOR), callback.getMap().findClosestBuildSite(unitDefs.get(C.METAL_EXTRACTOR), rand2, 5, (short) 0, Integer.MAX_VALUE), 0, (short) 0, Integer.MAX_VALUE);
            //  build(unit, C.SOLAR_COLLECTOR, 10);
         //   build(unit, C.SOLAR_COLLECTOR, 10);
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
/*
        if(null != commander)
            if(frame % 10 == 0)
                bt.step();
*/

    //    managers.forEach(h -> h.update(frame));
/*
        if(frame == 100) {
            build(commander, C.CLOAKY_BOT_FACTORY, 50);
        }
        if(frame == 400) {
            build(commander, C.SOLAR_COLLECTOR, 20);
            build(commander, C.SOLAR_COLLECTOR, 20);
            build(commander, C.SOLAR_COLLECTOR, 20);
            build(commander, C.BIG_BERTHA, 20);
        }
        if(frame == 1000) {
            List<AIFloat3> l = callback.getMap().getResourceMapSpotsPositions(m);
            AIFloat3 rand = l.get(new Random().nextInt(l.size()));
            commander.build(unitDefs.get(C.METAL_EXTRACTOR), callback.getMap().findClosestBuildSite(unitDefs.get(C.METAL_EXTRACTOR), rand, 5, (short) 0, Integer.MAX_VALUE), 0, (short) 0, Integer.MAX_VALUE);
        } if(frame == 2000) {
            build(commander, C.BIG_BERTHA, 20);
        }
*/

        return 0;
    }

    public float calculateDistance(AIFloat3 a, AIFloat3 b) {
        float xDist = a.x - b.x;
        float yDist = a.y - b.y;
        float zDist = a.z - b.z;
        float totDistSqrd = xDist*xDist + yDist*yDist + zDist*zDist;
        return totDistSqrd;
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


    private void build(Unit builder, String uniqueName, int radius) {
        builder.build(unitDefs.get(uniqueName), callback.getMap().findClosestBuildSite(unitDefs.get(uniqueName), builder.getPos(), 1000, (short) radius, Integer.MAX_VALUE), 0 ,(short) 0, Integer.MAX_VALUE);
    }

    public void addListener(UnitListener listener) {
        listeners.add(listener);
    }
    public boolean removeListener(UnitListener listener) {
        return listeners.remove(listener);
    }

    public void debug(String s) {
        callback.getGame().sendTextMessage(s, 0);
    }

}
