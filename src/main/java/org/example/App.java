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
        List<Double>[] data = Utils.readCSVData("/1585078039159_gsr.csv");
        String out = "";
        /*
        for(Double d: data[0] ){
            out += (d.toString()+", ");
        }
        Utils.print(out);
        */

        /*
        List<Double> gsr = Preprocessing.getGSR(data[0], 40200);
        out = "";
        for(Double d: gsr ){
            out += (d.toString()+", ");
        }
        Utils.print(out);
        */

        List<Double> filteredGSR = Preprocessing.butterworthLowPass(data[0], 1.0f, 50, 5);
        /*
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }
        Utils.print(out);
        */

        filteredGSR = Preprocessing.butterworthHighPass(filteredGSR, 0.05f, 50, 5);
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

        filteredGSR = Preprocessing.phasicComponent(new ArrayList<Double>(filteredGSR), 50, 10);
        /*
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }

        Utils.print(out);
         */

        List<Peak> peaks = Preprocessing.findPeaksAndOffset(filteredGSR, 0.01, 0);
        List<GSRFeatures> features = new ArrayList<>();
        out = "";
        for(Peak peak : peaks) {
            out += (peak.max + ", ");
            //peak.print();
            features.add(Preprocessing.extractGSRFeatures(filteredGSR, 10, peak));
        }
        Utils.print(out);

    }}
