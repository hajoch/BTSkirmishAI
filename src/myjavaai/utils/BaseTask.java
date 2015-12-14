package myjavaai.utils;

import com.springrts.ai.oo.clb.Unit;
import com.springrts.ai.oo.clb.UnitDef;

/**
 * Created by Hallvard on 03.12.2015.
 */
public class BaseTask {

    public Unit builder;
    public UnitDef buildee;

    public BaseTask(){}
    public BaseTask(Unit builder, UnitDef buildee){
        this.builder = builder;
        this.buildee = buildee;
    }
}
