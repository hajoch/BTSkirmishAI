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
public class Build_SolarPanel extends Action<MyJavaAI> {

   @Override
   public void run() {
       MyJavaAI bb = getBlackboard();

       bb.debug("Build_SolarPanel.run() called");


       UnitDef def = bb.unitDefs.get(C.SOLAR_COLLECTOR);
       AIFloat3 place = bb.callback.getMap().findClosestBuildSite(def, bb.commander.getPos(), 10, (short)0, Integer.MAX_VALUE);

       bb.commander.build(def, place, 0, (short)0, Integer.MAX_VALUE);

       bb.addListener(new UnitListener<BaseTask>(new BaseTask(bb.commander, def)) {
           @Override
           public void unitDestroyed() {
               fail();
           }
           @Override
           public void TaskCompleted() {
                success();
           }
           @Override
           public void TaskInterrupted() {
                fail();
           }
       });
   }


    @Override
    public String toString() {
        return "";
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {

    }
}
