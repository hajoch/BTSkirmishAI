package myjavaai.bt.builder.conditions;

import bt.leaf.Condition;
import com.springrts.ai.oo.clb.Resource;
import myjavaai.MyJavaAI;

/**
 * Created by Hallvard on 13.01.2016.
 */
public class NeedEnergy extends Condition<MyJavaAI> {
    @Override
    protected boolean condition() {
        MyJavaAI bb = getBlackboard();
        Resource e = bb.callback.getResourceByName("Energy");
        return (bb.callback.getEconomy().getPull(e) < -10f) || (bb.callback.getEconomy().getCurrent(e) < 50);
    }

}
