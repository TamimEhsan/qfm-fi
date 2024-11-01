/*
 * Author: Sharmin Akter Mim
 * Code for QFM-FI
 * */
package qfm_fi;



import java.util.Arrays;
import java.util.Objects;

public class Quartet {

    public Taxa t1;
    public Taxa t2;
    public Taxa t3;
    public Taxa t4;
    public byte status;
    public byte t1_status;
    public byte t2_status;
    public byte t3_status;
    public byte t4_status;
    /*violated v = 1 only one taxa of each partition are in their accurate position wrt a partition
	 * satisfied s = 2 that means 2 taxa of each quartet are in their accurate position wrt a partition
	 *                 that also means all 4 taxa are in their accurate position
	 * deferred d = 3 that means 3 taxa of a quartet are in same part
	 * deferred b = 4 that means all 4 taxa of a quartet are in same part*/

    private int qFrequency;

    private static void swap(Taxa[] rgTaxa, int i, int j) {
        Taxa temp = rgTaxa[i];
        rgTaxa[i] = rgTaxa[j];
        rgTaxa[j] = temp;
    }
    
    private static Taxa[] toCanonicalForm(Taxa t1, Taxa t2, Taxa t3, Taxa t4) {
        Taxa[] rgTaxa = new Taxa[4];

        rgTaxa[0] = t1;
        rgTaxa[1] = t2;
        rgTaxa[2] = t3;
        rgTaxa[3] = t4;

        if (rgTaxa[0].getName().compareTo(rgTaxa[1].getName()) > 0) {
            swap(rgTaxa, 0, 1);
        }
        if (rgTaxa[2].getName().compareTo(rgTaxa[3].getName()) > 0) {
            swap(rgTaxa, 2, 3);
        }

        if (rgTaxa[0].getName().compareTo(rgTaxa[2].getName()) > 0) {
            swap(rgTaxa, 0, 2);
            swap(rgTaxa, 1, 3);
        }

        return rgTaxa;
    }

        
    private void initQuartet(Taxa t1, Taxa t2, Taxa t3, Taxa t4, int freq) {
//        Taxa[] rgTaxa = toCanonicalForm(t1, t2, t3, t4);
//
//        this.t1 = rgTaxa[0];
//        this.t2 = rgTaxa[1];
//        this.t3 = rgTaxa[2];
//        this.t4 = rgTaxa[3];

        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.qFrequency = freq;
    }
        
    public Quartet(Taxa t1, Taxa t2, Taxa t3, Taxa t4) {
        initQuartet(t1, t2, t3, t4, 1);
    }

    public Quartet(Taxa t1, Taxa t2, Taxa t3, Taxa t4, int qFrequency) {

        initQuartet(t1, t2, t3, t4, qFrequency);
   }

    public int getQFrequency() {
        return qFrequency;
    }

    public void setQFrequency(int qFrequency) {
        this.qFrequency = qFrequency;
    }

    public void initiallyFillUpRelaventQIDs(int QID) {
        t1.relaventQIDs.add(QID);
        t2.relaventQIDs.add(QID);
        t3.relaventQIDs.add(QID);
        t4.relaventQIDs.add(QID);
    }

    public void initialSatVatCalculation() {
        int a = t1.getPartition();
        int b = t2.getPartition();
        int c = t3.getPartition();
        int d = t4.getPartition();
        this.status = Routines.iCheckQuartet2(a, b, c, d);

        if (this.status == 3) {
            t1_status = statusWRTmovingTaxaI(t1, 1 - a, b, c, d);
            t2_status = statusWRTmovingTaxaI(t2, a, 1 - b, c, d);
            t3_status = statusWRTmovingTaxaI(t3, a, b, 1 - c, d);
            t4_status = statusWRTmovingTaxaI(t4, a, b, c, 1 - d);

        } else {
            if (this.status == 2) {
                t1.addSatScoresWRTmovingTaxa(-this.qFrequency);
                t2.addSatScoresWRTmovingTaxa(-this.qFrequency);
                t3.addSatScoresWRTmovingTaxa(-this.qFrequency);
                t4.addSatScoresWRTmovingTaxa(-this.qFrequency);
            }// d =  q.getQFrequency();} // s d
            else if (this.status == 1) {
                t1.addVatScoresWRTmovingTaxa(-this.qFrequency);
                t2.addVatScoresWRTmovingTaxa(-this.qFrequency);
                t3.addVatScoresWRTmovingTaxa(-this.qFrequency);
                t4.addVatScoresWRTmovingTaxa(-this.qFrequency);
            }//d = q.getQFrequency();}  // v d

            t1_status = 3;
            t2_status = 3;
            t3_status = 3;
            t4_status = 3;

        }

    }

    public void updateSatVatCalculation() {
        int a = t1.getPartition();
        int b = t2.getPartition();
        int c = t3.getPartition();
        int d = t4.getPartition();

        if (this.status == 3) {

            this.status = Routines.iCheckQuartet2(a, b, c, d);
            if (!t1.locked) {
                statusComparison(t1, t1_status, -qFrequency);
                t1_status = statusWRTmovingTaxa(t1, 1 - a, b, c, d);
            }
            if (!t2.locked) {
                statusComparison(t2, t2_status, -qFrequency);
                t2_status = statusWRTmovingTaxa(t2, a, 1 - b, c, d);
            }
            if (!t3.locked) {
                statusComparison(t3, t3_status, -qFrequency);
                t3_status = statusWRTmovingTaxa(t3, a, b, 1 - c, d);
            }
            if (!t4.locked) {
                statusComparison(t4, t4_status, -qFrequency);
                t4_status = statusWRTmovingTaxa(t4, a, b, c, 1 - d);
            }

        } else if (this.status == 2) {
            this.status = 3;
            if (!t1.locked) {
                t1.addSatScoresWRTmovingTaxa(this.qFrequency);
                t1_status = statusWRTmovingTaxaI(t1, 1 - a, b, c, d);
            }
            if (!t2.locked) {
                t2.addSatScoresWRTmovingTaxa(this.qFrequency);
                t2_status = statusWRTmovingTaxaI(t2, a, 1 - b, c, d);
            }
            if (!t3.locked) {
                t3.addSatScoresWRTmovingTaxa(this.qFrequency);
                t3_status = statusWRTmovingTaxaI(t3, a, b, 1 - c, d);
            }
            if (!t4.locked) {
                t4.addSatScoresWRTmovingTaxa(this.qFrequency);
                t4_status = statusWRTmovingTaxaI(t4, a, b, c, 1 - d);
            }
        }// d =  q.getQFrequency();} // s d
        else if (this.status == 1) {
            this.status = 3;
            if (!t1.locked) {
                t1.addVatScoresWRTmovingTaxa(this.qFrequency);
                t1_status = statusWRTmovingTaxaI(t1, 1 - a, b, c, d);
            }
            if (!t2.locked) {
                t2.addVatScoresWRTmovingTaxa(this.qFrequency);
                t2_status = statusWRTmovingTaxaI(t2, a, 1 - b, c, d);
            }
            if (!t3.locked) {
                t3.addVatScoresWRTmovingTaxa(this.qFrequency);
                t3_status = statusWRTmovingTaxaI(t3, a, b, 1 - c, d);
            }
            if (!t4.locked) {
                t4.addVatScoresWRTmovingTaxa(this.qFrequency);
                t4_status = statusWRTmovingTaxaI(t4, a, b, c, 1 - d);
            }

        }//d = q.getQFrequency();}  // v d
        else if (this.status == 4) {

            this.status = 3;
            if (!t1.locked) {
                t1_status = statusWRTmovingTaxaI(t1, 1 - a, b, c, d);
            }
            if (!t2.locked) {
                t2_status = statusWRTmovingTaxaI(t2, a, 1 - b, c, d);
            }
            if (!t3.locked) {
                //statusComparison(t3, t3_status, -qFrequency);
                t3_status = statusWRTmovingTaxaI(t3, a, b, 1 - c, d);
            }
            if (!t4.locked) {
                t4_status = statusWRTmovingTaxaI(t4, a, b, c, 1 - d);
            }

        }

    }

    public byte statusWRTmovingTaxaI(Taxa taxa, int a, int b, int c, int d) {
        //if quartet status is satisfied, violated or b, then moving taxa from its current partition will must make q status deffered
        //But if quartet status is deffered, then we must have to check its status with respect to moving taxa

        byte qStat = Routines.iCheckQuartet2(a, b, c, d);
        statusComparison(taxa, qStat, this.qFrequency);
        return qStat;
    }

    public byte statusWRTmovingTaxa(Taxa taxa, int a, int b, int c, int d) {

        byte qStat;
        //if quartet status is satisfied, violated or b, then moving taxa from its current partition will must make q status deffered
        //But if quartet status is deffered, then we must have to check its status with respect to moving taxa
        if (this.status == 3) {
            qStat = statusWRTmovingTaxaI(taxa, a, b, c, d);
        } else {
            qStat = 3;
            statusComparison(taxa, this.status, -qFrequency);

        }
        return qStat;
    }

    public void statusComparison(Taxa taxa, byte qStat, int frequency) {
        if (qStat == 1) {
            taxa.addVatScoresWRTmovingTaxa(frequency);
        } // d v
        else if (qStat == 2) {
            taxa.addSatScoresWRTmovingTaxa(frequency);
        } // d s
    }
    
    @Override
    public int hashCode() {
        
        return Arrays.hashCode(toCanonicalForm(t1, t2, t3, t4));
        //return Objects.hash(t1, t2, t3, t4);
    }

    @Override
    public boolean equals(Object obj) {
        boolean fEquals = false;
        int i;
        
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Quartet)) {
            return false;
        }
        
        Quartet other = (Quartet) obj;
        
        Taxa[] rgTaxa_this = toCanonicalForm(this.t1, this.t2, this.t3, this.t4);
        Taxa[] rgTaxa_obj  = toCanonicalForm(other.t1, other.t2, other.t3, other.t4);

        for (i = 0; i < rgTaxa_this.length; i++) {
            if (!Objects.equals(rgTaxa_this[i], rgTaxa_obj[i])) {
                break;
            }
        }
        
        if (i == rgTaxa_this.length) {
            other.setQFrequency(other.getQFrequency() + this.qFrequency);
            fEquals = true;
        }
        
//        if (Objects.equals(t1, other.t1) && Objects.equals(t2, other.t2)
//                && Objects.equals(t3, other.t3) && Objects.equals(t4, other.t4)) {
//            other.setQFrequency(other.getQFrequency() + this.qFrequency);
//            fEquals = true;
//        } else {
//            fEquals =  false;
//        }

        return fEquals;
    }
}
