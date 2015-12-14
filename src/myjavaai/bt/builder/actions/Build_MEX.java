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

/**
 * Created by Hallvard on 26.11.2015.
 */
public class Build_MEX extends Action<MyJavaAI> {

    @Override
    public void run(){
        MyJavaAI bb = getBlackboard();

        bb.debug("Build_MEX.run() called");

        UnitDef def = bb.unitDefs.get(C.METAL_EXTRACTOR);
        AIFloat3 place = bb.callback.getMap().getResourceMapSpotsNearest(bb.callback.getResourceByName("Metal"), bb.commander.getPos());

        bb.commander.build(def, bb.callback.getMap().findClosestBuildSite(def, place, 5, (short)0, Integer.MAX_VALUE), 0, (short)0, Integer.MAX_VALUE);

        bb.addListener(new UnitListener<BaseTask>(new BaseTask(bb.commander, def)) {
            @Override
            public void unitDestroyed() {
                fail();
                bb.debug("Failed");
                bb.removeListener(this);
                //TODO repercussions?
            }
            @Override
            public void TaskCompleted() {
                success();
                bb.debug("Succeeded");
                bb.removeListener(this);
            }
            @Override
            public void TaskInterrupted() {
                fail();
                bb.debug("Interrupted");
                bb.removeListener(this);
            }
        });
        //TODO handle death. How should the behaviour tree handle this?
    }


    @Override
    public String toString() {
        return "";
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

    }
}