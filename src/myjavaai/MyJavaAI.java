package myjavaai;

import bt.BehaviourTree;
import bt.composite.Sequence;
import bt.decorator.UntilFail;
import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.AbstractOOAI;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.clb.Resource;
import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.UnitDef;
import myjavaai.bt.builder.actions.*;
import myjavaai.construction.ConstructionManager;
import myjavaai.economy.EconomyManager;
import myjavaai.graphics.LiveBT;
import myjavaai.utils.BaseTask;
import myjavaai.utils.UnitListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    {
        managers = new ArrayList<Manager>() {{
            add(new EconomyManager());
            add(new ConstructionManager());
        }};
        listeners = new ArrayList<>();

        bt = TempTree.fullBuilder(this);

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
        debug("Test start YEHAAAH!");

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
            debug("Commander created: YEAGGGHHH");
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
                bt.step();
                LiveBT.draw();
            }
        }


    //    managers.forEach(h -> h.update(frame));
/*
        if(frame == 100) {
            debug("Test");
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
