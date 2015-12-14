package myjavaai.economy;

import com.springrts.ai.oo.clb.Economy;
import com.springrts.ai.oo.clb.OOAICallback;
import com.springrts.ai.oo.clb.Resource;
import myjavaai.Manager;

/**
 * Created by Hallvard on 24.11.2015.
 */
public class EconomyManager extends Manager {

    int frame = -1;

    Economy eco;
    Resource metal;
    Resource energy;

    @Override
    public int init(int teamId, OOAICallback callback) {

        metal = callback.getResourceByName("Metal");
        energy = callback.getResourceByName("Energy");

        return 0;
    }

    @Override
    public int update(int frame) {
        this.frame = frame;

        float incomeMetal = eco.getIncome(metal);
        float incomeEnergy = eco.getIncome(energy);

        float usageMetal = eco.getUsage(metal);
        float usageEnergy = eco.getUsage(energy);

        float pullMetal = eco.getPull(metal);
        float pullEnergy = eco.getPull(energy);


        return 0;
    }

    public boolean needMoreStorage() {
        float currMetal = eco.getCurrent(metal);
        float currEnergy = eco.getCurrent(energy);

        eco.getStorage(metal);
        eco.getStorage(energy);

        return false;
    }


}
