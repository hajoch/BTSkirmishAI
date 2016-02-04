package myjavaai.bt.builder.actions;

import bt.leaf.Action;
import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.UnitDef;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import myjavaai.MyJavaAI;
import myjavaai.utils.BaseTask;
import myjavaai.utils.C;
import myjavaai.utils.UnitListener;

/**
 * Created by Hallvard on 03.12.2015.
 */
public class Build_Lotus extends Action<MyJavaAI>{

    private Boolean res = (null);

    @Override
    public void start() {
        MyJavaAI bb = getBlackboard();
        bb.debug("Build_LOTUS.run() called");
        UnitDef def = bb.unitDefs.get(C.LOTUS);

        bb.build(bb.commander, C.LOTUS, 10);


        bb.addListener(new UnitListener<BaseTask>(new BaseTask(bb.commander, def)) {
            @Override
            public void unitDestroyed() {
                res = false;
                bb.removeListener(this);
            }
            @Override
            public void TaskCompleted() {
                res = true;
                bb.removeListener(this);
            }
            @Override
            public void TaskInterrupted() {
                res = false;
                bb.removeListener(this);
            }
        });
    }
    @Override
    public TaskState execute() {
        if(null == res)
            return TaskState.RUNNING;
        getBlackboard().debug("Build_Lotus ended");
        return res ? TaskState.SUCCEEDED : TaskState.FAILED;
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

    }
}
