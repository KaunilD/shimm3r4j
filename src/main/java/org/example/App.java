package org.example;


import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int SAMPLING_RATE = 10;
        float LOWPASS_CUTOFF = 1.0f, HIGHPASS_CUTOFF = 0.05f;
        int BUTTERWORTH_ORDER = 5;
        List<Double>[] data = Utils.readCSVData("/GSR_calibrated_signal.csv");
        String out = "";
        /*
        for(Double d: data[0] ){
            out += (d.toString()+", ");
        }
        Utils.print(out);
        */

        /*
        List<Double> gsr = Preprocessing.getGSR(data[0], 3300000);
        out = "";
        for(Double d: gsr ){
            out += (d.toString()+", ");
        }
        Utils.print(out);
        */
        List<Double> filteredGSR = data[0];
        filteredGSR = Preprocessing.butterworthLowPass(data[0], LOWPASS_CUTOFF, SAMPLING_RATE, BUTTERWORTH_ORDER);
        /*
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }
        Utils.print(out);
        */

        filteredGSR = Preprocessing.butterworthHighPass(filteredGSR, HIGHPASS_CUTOFF, SAMPLING_RATE, BUTTERWORTH_ORDER);
        /*
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }
        Utils.print(out);
        */

        /*
        int downSamplingFac = 128/10;
        int nsamples = filteredGSR.size()/downSamplingFac;

        float floatFilteredGSR[] = new float[filteredGSR.size()];
        for(int i = 0; i < filteredGSR.size(); i++){
            floatFilteredGSR[i] = filteredGSR.get(i).floatValue();
        }
        filteredGSR = Preprocessing.resample(floatFilteredGSR, nsamples);
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }
        Utils.print(out);
        */

        filteredGSR = Preprocessing.phasicComponent(new ArrayList<Double>(filteredGSR), SAMPLING_RATE, 10);

        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }

        Utils.print(out);

        List<Peak> peaks = Preprocessing.findPeaksAndOffset(filteredGSR, 0.001, 0);
        List<GSRFeatures> features = new ArrayList<>();
        out = "";
        for(Peak peak : peaks) {
            out += (peak.max + ", ");
            //peak.print();
            features.add(Preprocessing.extractGSRFeatures(filteredGSR, SAMPLING_RATE, peak));
        }
        Utils.print(out);

    }}
