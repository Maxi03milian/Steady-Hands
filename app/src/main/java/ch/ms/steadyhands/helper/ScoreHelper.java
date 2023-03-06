package ch.ms.steadyhands.helper;

import java.util.ArrayList;

public class ScoreHelper {

    public int calculateScore(ArrayList<Float> rotationValues) {

        //average change in rotation
        float sensorChanges = 0;

        //calculate average change in rotation
        for (int i = 1; i < rotationValues.size() - 1; i++) {
            sensorChanges += Math.abs(rotationValues.get(i) - rotationValues.get(i + 1));
        }
        return (int) (sensorChanges);
    }
}
