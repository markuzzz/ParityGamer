package afmc.data;

import java.util.List;
import java.util.function.Supplier;

public class ProgressMeasure
{
    private Integer[] progressMeasure;
    private boolean top = false;
    private boolean bottom = false;
    public Integer length;

    public ProgressMeasure(Integer length) {
        this.progressMeasure = new Integer[length];
        for (Integer j = 0; j < length; j++) {
            this.progressMeasure[j] = 0;
        }

        this.length = length;
        this.bottom = true;
    }

    public ProgressMeasure(Integer[] progressMeasure) {
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

    public Integer[] getProgressMeasure() {
        return this.progressMeasure;
    }

    public Integer getProgress(Integer index) {
        return this.progressMeasure[index];
    }

    public void updateProgress(Integer index, Integer newValue) {
        this.progressMeasure[index] = newValue;
        this.bottom = false;
    }

    public void setTop() {
        this.top = true;
        this.bottom = false;
    }

    public boolean isBottom() {
        return this.bottom;
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

    public static int earlyMinReturns = 0;

    public static ProgressMeasure min(List<Supplier<ProgressMeasure>> progressMeasures) {
        if (1 == progressMeasures.size()) {
            return progressMeasures.get(0).get();
        }

        ProgressMeasure result = progressMeasures.get(0).get();
        for (Supplier<ProgressMeasure> progressMeasureSupplier: progressMeasures) {
            if (result.isBottom()) {
                earlyMinReturns++; 
                return result;
            }
            ProgressMeasure progressMeasure = progressMeasureSupplier.get();
            if (isSmallerThanOrEqual(progressMeasure, result, result.length)) {
                result = progressMeasure;
            }
        }
        return result;
    }

    public static int earlyMaxReturns = 0;

    public static ProgressMeasure max(List<Supplier<ProgressMeasure>> progressMeasures) {
        if (1 == progressMeasures.size()) {
            return progressMeasures.get(0).get();
        }

        ProgressMeasure result = progressMeasures.get(0).get();
        for (Supplier<ProgressMeasure> progressMeasureSupplier: progressMeasures) {
            if (result.isTop()) {
                earlyMaxReturns++; 
                return result;
            }
            ProgressMeasure progressMeasure = progressMeasureSupplier.get();
            if (isLargerThanOrEqual(progressMeasure, result, result.length)) {
                result = progressMeasure;
            }
        }

        return result;
    }

    // Warning, can return argument, so do not modify the outcome
    public static ProgressMeasure leastEqual(ProgressMeasure pm, Integer upTo) {
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

    public static ProgressMeasure leastGreater(ProgressMeasure pm, Integer upTo, ProgressMeasure max) {
        ProgressMeasure leastEqual = leastEqual(pm, upTo);
        ProgressMeasure increased = increaseProgress(leastEqual, max, upTo);

        return increased;
    }

    private static ProgressMeasure increaseProgress(ProgressMeasure pm, ProgressMeasure max, Integer upTo) {
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

    public static boolean isSmallerThanOrEqual(ProgressMeasure pm1, ProgressMeasure pm2, Integer upTo) {
        if(pm1.top) {return false;}
        if(pm2.top) {return true;}
        for (int i = 1; i < upTo-1; i+=2) {
            if (pm1.progressMeasure[i] > pm2.progressMeasure[i]) {
                return false;
            }
            if (pm1.progressMeasure[i] < pm2.progressMeasure[i]) {
                return true;
            }
        }
        return true;
    }

    public static boolean isLargerThanOrEqual(ProgressMeasure pm1, ProgressMeasure pm2, Integer upTo) {
        if(pm1.top) {return true;}
        if(pm2.top) {return false;}
        for (int i = 1; i < upTo-1; i+=2) {
            if(pm1.progressMeasure[i] < pm2.progressMeasure[i]) {
                return false;
            }
            if(pm1.progressMeasure[i] > pm2.progressMeasure[i]) {
                return true;
            }
        }
        return true;
    }

    public static boolean isEqual(ProgressMeasure pm1, ProgressMeasure pm2) {
        if (pm1.isTop() && pm2.isTop()) {
            return true;
        } else if (pm1.isTop() || pm2.isTop()) {
            return false; 
        }

        for (int i = 0; i < pm1.length; i++) {
            if(pm1.progressMeasure[i] != pm2.progressMeasure[i]) {
                return false;
            }
        }
        return true;
    }
}
