package myjavaai.bt.builder.actions;

import bt.leaf.Action;
import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.UnitDef;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import myjavaai.utils.BaseTask;
import myjavaai.utils.C;
import myjavaai.MyJavaAI;
import myjavaai.utils.UnitListener;

import java.util.List;

/**
 * Created by Hallvard on 26.11.2015.
 */
public class Build_MEX extends Action<MyJavaAI> {

    private Boolean res = null;

    @Override
    public void start() {
        MyJavaAI bb = getBlackboard();
        bb.debug("Build_MEX.run() called");

        UnitDef def = bb.unitDefs.get(C.METAL_EXTRACTOR);
        AIFloat3 place = getNextSpot();

        if(place==null){
            res = false;
            return;
        }

        bb.commander.build(def, bb.callback.getMap().findClosestBuildSite(def, place, 10000, (short)0, Integer.MAX_VALUE), 0, (short)0, Integer.MAX_VALUE);

        bb.addListener(new UnitListener<BaseTask>(new BaseTask(bb.commander, def)) {
            @Override
            public void unitDestroyed() {
                res = false;
                bb.debug("Failed");
                bb.removeListener(this);
                //TODO repercussions?
            }
            @Override
            public void TaskCompleted() {
                res = true;
                bb.debug("Succeeded");
                bb.removeListener(this);
            }
            @Override
            public void TaskInterrupted() {
                res = false;
                bb.debug("Interrupted");
                bb.removeListener(this);
            }
        });

    }

    private AIFloat3 getNextSpot() {
        MyJavaAI bb = getBlackboard();
        List<AIFloat3> list = bb.metalDetectors;
        AIFloat3 closest = null;
        float dist = Integer.MAX_VALUE;
        for(AIFloat3 f : list) {
            float temp = calculateDistance(f, bb.commander.getPos());
            if(temp < dist) {
                dist = temp;
                closest = f;
            }
        }
        bb.metalDetectors.remove(closest);
        return closest;
    }
    public float calculateDistance(AIFloat3 a, AIFloat3 b) {
        float xDist = a.x - b.x;
        float yDist = a.y - b.y;
        float zDist = a.z - b.z;
        float totDistSqrd = xDist*xDist + yDist*yDist + zDist*zDist;
        return totDistSqrd;
    }

    @Override
    public TaskState execute(){
        if(null == res)
            return TaskState.RUNNING;
        getBlackboard().debug("Build_MEX ended");
        return res ? TaskState.SUCCEEDED : TaskState.FAILED;

        //TODO handle death. How should the behaviour tree handle this?
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

    }
}
