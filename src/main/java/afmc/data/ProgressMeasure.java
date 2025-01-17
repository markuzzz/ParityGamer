package afmc.data;

import java.util.Arrays;
import java.util.Collections;

public class ProgressMeasure
{
    private int[] progressMeasure;
    private boolean top = false;
    public int length;

    public ProgressMeasure(int length) {
        // Should be initialized to zero, https://docs.oracle.com/javase/specs/jls/se7/html/jls-4.html#jls-4.12.5
        this.progressMeasure = new int[length];
        this.length = length;
    }

    public ProgressMeasure(int[] progressMeasure) {
        this.progressMeasure = progressMeasure;
        this.length = progressMeasure.length;
    }

    public ProgressMeasure(ProgressMeasure pm) {
        if (pm.isTop()) {
            this.top = true;
        } else {
            this.progressMeasure = pm.getProgressMeasure();
        }
    }

    public int[] getProgressMeasure() {
        return this.progressMeasure;
    }

    public int getProgress(int index) {
        return this.progressMeasure[index];
    }

    public void updateProgress(int index, int newValue) {
        this.progressMeasure[index] = newValue;
    }

    public void setTop() {
        this.top = true;
    }

    public boolean isTop() {
        return this.top;
    }

    public String toString() {
        String result = "";

        if(this.top) {
            result += "top";
        } else {
            result += "(";
            for(int i = 0; i < length; i++) {
                result += this.progressMeasure[i] + ",";
            }
            result += ")";
        }

        return result;
    }

    public static ProgressMeasure min(ProgressMeasure[] arrayProgressMeasures) {
        return Collections.min(Arrays.asList(arrayProgressMeasures), (pm1, pm2) -> compare(pm1, pm2, pm1.length));
    }

    public static ProgressMeasure max(ProgressMeasure[] arrayProgressMeasures) {
        return Collections.max(Arrays.asList(arrayProgressMeasures), (pm1, pm2) -> compare(pm1, pm2, pm1.length));
    }

    public static ProgressMeasure leastEqual(ProgressMeasure pm, int upTo) {
        // Top has the lowest order
        if (pm.isTop()) {
            return new ProgressMeasure(pm);
        }
        ProgressMeasure result = new ProgressMeasure(pm.length);

        for (int i = 0; i <= upTo; i++) {
            result.updateProgress(i, pm.getProgress(i));
        }

        return result;
    }

    public static ProgressMeasure leastGreater(ProgressMeasure pm, int upTo, ProgressMeasure max) {
        ProgressMeasure leastEqual = leastEqual(pm, upTo);
        ProgressMeasure increased = increaseProgress(leastEqual, max, upTo);

        return increased;
    }

    private static ProgressMeasure increaseProgress(ProgressMeasure pm, ProgressMeasure max, int upTo) {
        if (pm.isTop()) {
            return pm;
        }

        boolean shouldUpdate = true;
        for (int i = upTo; i > 0; i--) {
            if (0 == i % 2) {
                continue;
            }

            if (pm.getProgress(i) < max.getProgress(i)) {
                pm.updateProgress(i, pm.getProgress(i)+1);
                shouldUpdate = false;
                break;
            }
        }

        if (shouldUpdate) {
            pm.setTop();
        }

        return pm;
    }

    public static int compare(ProgressMeasure pm1, ProgressMeasure pm2, int upTo) {
        if (pm1.isTop() && pm2.isTop()) {
            return 0;
        } else if(pm1.top) {
            return 1;
        } else if(pm2.top) {
            return -1;
        }

        for (int i = 0; i < upTo; i++) {
            if (pm1.progressMeasure[i] < pm2.progressMeasure[i]) {
                return -1;
            }
            if (pm1.progressMeasure[i] > pm2.progressMeasure[i]) {
                return 1;
            }
        }
        return 0;
    }

    public static boolean isEqual(ProgressMeasure pm1, ProgressMeasure pm2) {
        if (pm1.isTop() && pm2.isTop()) {
            return true;
        } else if (pm1.isTop() || pm2.isTop()) {
            return false;
        }

        return Arrays.equals(pm1.progressMeasure, pm2.progressMeasure);
    }
}
