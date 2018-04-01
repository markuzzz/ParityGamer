package afmc.data;

public class ProgressMeasure {
    private Integer[] progressMeasure;
    private boolean top = false;
    public Integer length;

    public ProgressMeasure(Integer length) {
        this.progressMeasure = new Integer[length];
        for(Integer j = 0; j < length; j++) {
            this.progressMeasure[j] = 0;
        }
        this.length = length;
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
        if (1 == arrayProgressMeasures.length) {
            return arrayProgressMeasures[0];
        }

        ProgressMeasure result = arrayProgressMeasures[0];
        for(ProgressMeasure progressMeasure: arrayProgressMeasures) {
            if(isSmallerThanOrEqual(progressMeasure, result, result.length)) {
                result = progressMeasure;
            }
        }
        return result;
    }

    public static ProgressMeasure max(ProgressMeasure[] arrayProgressMeasures) {
        if (1 == arrayProgressMeasures.length) {
            return arrayProgressMeasures[0];
        }

        ProgressMeasure result = arrayProgressMeasures[0];
        for(ProgressMeasure progressMeasure: arrayProgressMeasures) {
            if(isLargerThanOrEqual(progressMeasure, result, result.length)) {
                result = progressMeasure;
            }
        }
        return result;
    }

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
        for(int i = 0; i < upTo; i++) {
            if(pm1.progressMeasure[i] > pm2.progressMeasure[i]) {
                return false;
            }
            if(pm1.progressMeasure[i] < pm2.progressMeasure[i]) {
                return true;
            }
        }
        return true;
    }

    public static boolean isLargerThanOrEqual(ProgressMeasure pm1, ProgressMeasure pm2, Integer upTo) {
        if(pm1.top) {return true;}
        if(pm2.top) {return false;}
        for(int i = 0; i < upTo; i++) {
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
