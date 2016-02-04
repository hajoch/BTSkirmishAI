package myjavaai.bt.builder.conditions;

import bt.leaf.Condition;
import myjavaai.MyJavaAI;

/**
 * Created by Hallvard on 13.01.2016.
 */
public class AvailMetalSpot extends Condition<MyJavaAI> {
    @Override
    protected boolean condition() {
        return true;// TODO !getBlackboard().metalDetectors.isEmpty();
    }

}
