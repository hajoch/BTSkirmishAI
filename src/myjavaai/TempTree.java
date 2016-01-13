package myjavaai;

import bt.BehaviourTree;
import bt.composite.Selector;
import bt.composite.Sequence;
import bt.decorator.Succeeder;
import bt.decorator.UntilFail;
import myjavaai.bt.builder.actions.*;
import myjavaai.bt.builder.conditions.*;

/**
 * Created by Hallvard on 13.01.2016.
 */
public class TempTree {

    private static BehaviourTree<MyJavaAI> bt = null;

    public static BehaviourTree<MyJavaAI> basic(MyJavaAI bb){
        bt = new BehaviourTree<MyJavaAI>(
                new Sequence<>(
                        new Build_CloakyBotFactory(),
                        new Succeeder<>(
                                new UntilFail<MyJavaAI>(
                                        new Sequence<MyJavaAI>(
                                                new AvailMetalSpot(),
                                                new Build_MEX(),
                                                new Build_SolarPanel(),
                                                new Build_Lotus()
                                        )
                                )
                        )
                )
        , bb);
        return bt;
    }

    public static BehaviourTree<MyJavaAI> test1(MyJavaAI bb) {
        bt = new BehaviourTree<MyJavaAI>(
                new Sequence<MyJavaAI>(
                        new Build_CloakyBotFactory(),
                        new Sequence<MyJavaAI>(
                                new Build_Storage()
                        ),
                        new UntilFail<MyJavaAI>(
                                new Sequence<MyJavaAI>(
                                        new Build_MEX(),
                                        new Build_SolarPanel(),
                                        new Build_SolarPanel()
                                )
                        )
                )
                , bb);
        return bt;
    }

    public static BehaviourTree<MyJavaAI> energyTree(MyJavaAI bb) {
        bt = new BehaviourTree<MyJavaAI>(
                new Sequence<>(
                        new Build_CloakyBotFactory(),
                        new Succeeder<>(
                                new UntilFail<>(
                                        new Sequence<>(
                                                new NeedEnergy(),
                                                new Selector<MyJavaAI>(
                                                        new Sequence<>(
                                                                new WindMillEfficient(),
                                                                new Build_WindMill()
                                                        ),
                                                        new Build_SolarPanel()
                                                )
                                        )
                                )
                        )
                )
        , bb);
        return bt;
    }

    public static BehaviourTree<MyJavaAI> fullBuilder(MyJavaAI bb) {
        bt = new BehaviourTree<MyJavaAI>(
                new Sequence<>(
                        new Build_CloakyBotFactory(),
                        new UntilFail<>(
                                new Sequence<>(
                                        new Sequence<>(
                                                new AvailMetalSpot(),
                                                new Build_MEX(),
                                                new Build_SolarPanel(),
                                                new Build_Lotus()
                                        ),
                                        new Sequence<>(
                                                new Succeeder<>(
                                                        new UntilFail<>(
                                                                new Sequence<>(
                                                                        new NeedEnergy(),
                                                                        new Selector<>(
                                                                                new Sequence<>(
                                                                                        new WindMillEfficient(),
                                                                                        new Build_WindMill()
                                                                                ),
                                                                                new Build_SolarPanel()
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new Succeeder<>(
                                                        new Sequence<>(
                                                                new NeedRadar(),
                                                                new Build_Radar()
                                                        )

                                                ),
                                                new Succeeder<>(
                                                        new Sequence<>(
                                                                new NeedStorage(),
                                                                new Build_Storage()
                                                        )
                                                ) //TODO Rebuiild, protection, metalCollection and Assisting factory
                                        )
                                )
                        )
                )
        , bb);
        return bt;
    }
}
