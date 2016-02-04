package myjavaai.bt.builder.actions;

import bt.Task;
import bt.leaf.Action;
import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Unit;
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

import java.util.Optional;

/**
 * Created by Hallvard on 12.01.2016.
 */
public abstract class Build extends Action<MyJavaAI> {
    private Boolean res = null;

    protected abstract String defConstant();
    protected abstract Optional<AIFloat3> buildSpot();
    protected abstract Unit getBuilder();

    @Override
    public void start() {
        MyJavaAI bb = getBlackboard();

        bb.debug("Build_X.start() called");

        Unit builder = getBuilder();
        UnitDef def = bb.unitDefs.get(defConstant());


        if(buildSpot().isPresent()) {
            builder.build(def, buildSpot().get(), 0, (short)0, Integer.MAX_VALUE);
        } else {
            bb.build(builder, defConstant(), 10);
        }

        bb.addListener(new UnitListener<BaseTask>(new BaseTask(builder, def)) {
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


    @Override
    public Task.TaskState execute(){
        if(null == res)
            return Task.TaskState.RUNNING;
        return res ? Task.TaskState.SUCCEEDED : Task.TaskState.FAILED;

        //TODO handle death. How should the behaviour tree handle this?
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

    }
}
