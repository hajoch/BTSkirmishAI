package myjavaai.bt.test;

import bt.Task;
import bt.leaf.Action;
import bt.utils.BooleanData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;

/**
 * Created by Hallvard on 25.01.2016.
 */
public class Wrong<E> extends Action<E> {
    @Override
    public TaskState execute() {
        return TaskState.FAILED;
    }

    @Override
    public String toString() {
        return "wrong";
    }

    @Override
    public void eval(EvolutionState evolutionState, int i, GPData gpData, ADFStack adfStack, GPIndividual gpIndividual, Problem problem) {
        BooleanData data = (BooleanData)gpData;
        data.result = false;
    }
}
