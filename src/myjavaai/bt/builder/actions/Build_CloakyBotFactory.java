package myjavaai.bt.builder.actions;

import com.springrts.ai.oo.AIFloat3;
import com.springrts.ai.oo.clb.Unit;
import myjavaai.utils.C;

import java.util.Optional;

/**
 * Created by Hallvard on 12.01.2016.
 */
public class Build_CloakyBotFactory extends Build {

    @Override
    protected String defConstant() { return C.CLOAKY_BOT_FACTORY; }

    @Override
    protected Optional<AIFloat3> buildSpot() {
        return Optional.empty();
    }

    @Override
    protected Unit getBuilder() {
        return getBlackboard().commander;
    }
}
