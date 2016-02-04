package myjavaai;

import bt.BehaviourTree;
import bt.utils.TreeInterpreter;
import myjavaai.bt.test.Right;
import myjavaai.bt.test.Wrong;

import java.util.Optional;

/**
 * Created by Hallvard on 26.01.2016.
 */
public class Test {

    public static void main(String[] args) {
        String exp = "failer(inverter(selector[succeeder(selector[untilFail(untilFail(inverter(wrong))), failer(inverter(succeeder(wrong))), selector[parallel[parallel[right, right], failer(right)], failer(sequence[right, right, right]), untilFail(failer(right))]]), failer(untilSucceed(failer(sequence[untilFail(right), untilSucceed(wrong), inverter(right)]))), untilFail(untilSucceed(succeeder(selector[succeeder(right), sequence[right, wrong, right], parallel[wrong, right]])))]))";
        Optional<BehaviourTree<Test>> bt = new TreeInterpreter<>(new Test()).create(new Class[]{Right.class, Wrong.class}, exp);

        System.out.println(exp);
        System.out.println("-------------------------\n\n\n");
        System.out.println(bt.get().humanToString());
    }

}
