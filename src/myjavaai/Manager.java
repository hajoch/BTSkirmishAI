package myjavaai;

import com.springrts.ai.oo.AbstractOOAI;
import com.springrts.ai.oo.clb.OOAICallback;
import myjavaai.utils.UnitListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hallvard on 24.11.2015.
 */
public abstract class Manager extends AbstractOOAI {

    protected OOAICallback callback;

    @Override
    public int init(int teamId, OOAICallback callback) {
        this.callback = callback;
        return 0;

    }
}
