package myjavaai.bt.builder.conditions;

import bt.leaf.Condition;
import com.springrts.ai.oo.clb.Resource;
import myjavaai.MyJavaAI;

/**
 * Created by Hallvard on 13.01.2016.
 */
public class NeedStorage extends Condition<MyJavaAI> {

    private MyJavaAI bb = null;

    @Override
    protected boolean condition() {
        bb = getBlackboard();

        Resource m = bb.callback.getResourceByName("Metal");
        Resource e = bb.callback.getResourceByName("Energy");

        return resourceFullAndIncreasing(m) || resourceFullAndIncreasing(e);
    }

    private boolean resourceFullAndIncreasing(Resource r) {
        boolean full = bb.callback.getEconomy().getCurrent(r) >= bb.callback.getEconomy().getStorage(r);
        boolean increasing = bb.callback.getEconomy().getPull(r) > 0;
        return full && increasing;
    }

}
