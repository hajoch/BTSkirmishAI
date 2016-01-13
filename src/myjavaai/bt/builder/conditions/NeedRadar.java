package myjavaai.bt.builder.conditions;

import bt.leaf.Condition;
import myjavaai.MyJavaAI;

/**
 * Created by Hallvard on 13.01.2016.
 */
public class NeedRadar extends Condition<MyJavaAI> {
    @Override
    protected boolean condition() {
        return Math.random() > 0.8;
    }

    @Override
    public String toString() {
        return null;
    }
}
