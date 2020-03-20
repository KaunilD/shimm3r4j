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
        System.out.println( "Hello World!" );
        List<Double>[] data = Utils.readCSVData("/data_device4B78.csv");
        List<Double> filteredGSR = Preprocessing.butterworthLowPass(data[0], 1.0f, 128, 2);
        filteredGSR = Preprocessing.butterworthHighPass(filteredGSR, 0.05f, 128, 2);

        int downSamplingFac = 128/10;
        int nsamples = filteredGSR.size()/downSamplingFac;
        Double data_[] = new Double[filteredGSR.size()];
        filteredGSR = Preprocessing.resample(filteredGSR.toArray(data_), nsamples);
        filteredGSR = Preprocessing.phasicComponent(new ArrayList<Double>(filteredGSR), 128, 10);
        List<Peak> peaks = Preprocessing.findPeaksAndOffset(filteredGSR, 0.01, 0);
        List<GSRFeatures> features = new ArrayList<>();
        for(Peak peak : peaks) {
            features.add(Preprocessing.extractGSRFeatures(filteredGSR, 10, peak));
        }
    }
}
