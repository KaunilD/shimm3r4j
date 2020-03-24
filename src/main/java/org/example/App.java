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
        List<Double>[] data = Utils.readCSVData("/20200323214736_Device4B78.csv");
        String out = "";
        /*
        for(Double d: data[0] ){
            out += (d.toString()+", ");
        }
        //Utils.print(out);
        */
        List<Double> gsr = Preprocessing.getGSR(data[0], 40200);
        /*
        out = "";
        for(Double d: gsr ){
            out += (d.toString()+", ");
        }

         */
        //Utils.print(out);

        List<Double> filteredGSR = Preprocessing.butterworthLowPass(gsr, 1.0f, 128, 2);
        /*
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }
         */
        //Utils.print(out);

        filteredGSR = Preprocessing.butterworthHighPass(filteredGSR, 0.05f, 128, 2);
        /*
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }

         */
        //Utils.print(out);


        int downSamplingFac = 128/10;
        int nsamples = filteredGSR.size()/downSamplingFac;

        float floatFilteredGSR[] = new float[filteredGSR.size()];
        for(int i = 0; i < filteredGSR.size(); i++){
            floatFilteredGSR[i] = filteredGSR.get(i).floatValue();
        }
        filteredGSR = Preprocessing.resample(floatFilteredGSR, nsamples);
        /*
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }

         */
        //Utils.print(out);

        filteredGSR = Preprocessing.phasicComponent(new ArrayList<Double>(filteredGSR), 128, 10);
        /*
        out = "";
        for(Double d: filteredGSR ){
            out += (d.toString()+", ");
        }

         */
        //Utils.print(out);


        List<Peak> peaks = Preprocessing.findPeaksAndOffset(filteredGSR, 0.01, 0);
        List<GSRFeatures> features = new ArrayList<>();

        //out = "";
        for(Peak peak : peaks) {
        //    out += (peak.max + ", ");
            peak.print();
            features.add(Preprocessing.extractGSRFeatures(filteredGSR, 10, peak));
        }
        //Utils.print(out);

    }}
