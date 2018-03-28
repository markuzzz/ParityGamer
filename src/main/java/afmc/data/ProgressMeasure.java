package afmc.data;

public class ProgressMeasure {
    private Integer[] progressMeasure;
    private boolean top = false;
    public Integer length;
    
    public ProgressMeasure(Integer length) {
        this.progressMeasure = new Integer[length];
        for(Integer j = 0; j < length + 1; j++) {
            this.progressMeasure[j] = 0;
        }
        this.length = length;
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
        ProgressMeasure result = arrayProgressMeasures[0];
        for(ProgressMeasure progressMeasure: arrayProgressMeasures) {
            if(isSmallerThanOrEqual(progressMeasure, result, result.length)) {
                result = progressMeasure;
            }
        }
        return result;
    }
    
    public static ProgressMeasure max(ProgressMeasure[] arrayProgressMeasures) {
        ProgressMeasure result = arrayProgressMeasures[0];
        for(ProgressMeasure progressMeasure: arrayProgressMeasures) {
            if(isLargerThanOrEqual(progressMeasure, result, result.length)) {
                result = progressMeasure;
            }
        }
        return result;
    }
    
    public static boolean isSmallerThanOrEqual(ProgressMeasure pm1, ProgressMeasure pm2, Integer upTo) {
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
}
