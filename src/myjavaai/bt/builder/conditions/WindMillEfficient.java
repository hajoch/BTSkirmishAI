package myjavaai.bt.builder.conditions;

import bt.leaf.Condition;
import myjavaai.MyJavaAI;

/**
 * Created by Hallvard on 13.01.2016.
 */
public class WindMillEfficient extends Condition<MyJavaAI> {
    @Override
    protected boolean condition() { //TODO not random result
        return Math.random() > 0.8;
    }

}
