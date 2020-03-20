package org.example;

import org.jtransforms.fft.FloatFFT_1D;
import uk.me.berndporr.iirj.Butterworth;

import java.util.ArrayList;
import java.util.List;

public class Preprocessing {

    // implementation of https://github.com/scipy/scipy/blob/v1.3.0/scipy/signal/signaltools.py#L2162-L2290
    public static List<Double> resample(Double[] input, int targetNumSamples) {
        // copy input signal because FFT is in-place
        float[] inputCopy = new float[2*input.length];
        System.arraycopy(input, 0, inputCopy, 0, input.length);

        // perform FFT
        FloatFFT_1D fft = new FloatFFT_1D(input.length);
        fft.realForwardFull(inputCopy);

        int n = Math.min(input.length, targetNumSamples);
        float[] output = new float[2*targetNumSamples];
        System.arraycopy(inputCopy, 0, output, 0, n);
        System.arraycopy(inputCopy, inputCopy.length - n, output, output.length - n, n);

        if (n % 2 == 0) {
            if (n < input.length) {
                // if downsampling
                output[n] += inputCopy[n]; // select the component at frequency N/2
                output[n + 1] += inputCopy[n + 1]; // add the component of X at N/2
            } else if (n < targetNumSamples) {
                // if upsampling
                output[targetNumSamples - n] /= 2; // halve the component at frequency N/2
                output[targetNumSamples - n + 1] /= 2;
                output[n] = output[targetNumSamples - n]; // set the component at -N/2 equal to the component at N/2
                output[n + 1] = output[targetNumSamples - n + 1];
            }
        }

        fft = new FloatFFT_1D(output.length / 2);
        fft.complexInverse(output, true);

        List<Double> realOutput = new ArrayList<>();
        double realMultiplier = (double) targetNumSamples / input.length;

        for (int i = 0; i < output.length / 2; i++) {
            realOutput.add(output[i * 2] * realMultiplier);
        }

        return realOutput;
    }



    public static List<Double> butterworthLowPass(List<Double> signal, float lowCutOff, int fs, int order){
        Butterworth butterworth = new Butterworth();
        butterworth.lowPass(order, fs, lowCutOff);

        List<Double> filteredData = new ArrayList<>();
        for (int i = 0; i < signal.size(); i++){
            filteredData.add(
                    butterworth.filter(signal.get(i))
            );
        }
        return filteredData;
    }

    public static List<Double> butterworthHighPass(List<Double> signal, float highCutOff, int fs, int order){
        Butterworth butterworth = new Butterworth();
        butterworth.highPass(order, fs, highCutOff);

        List<Double> filteredData = new ArrayList<>();
        for (int i = 0; i < signal.size(); i++){
            filteredData.add(
                    butterworth.filter(signal.get(i))
            );
        }
        return filteredData;
    }

    public static List<Double> phasicComponent(List<Double> signal, int samplingRate, int seconds){
        List<Double> phasicSignal = new ArrayList<>();
        for(int i = 0; i < signal.size(); i++){
            int smin = i - seconds* samplingRate;
            int smax = i + seconds* samplingRate;
            if (smin < 0){
                smin = i;
            }
            if (smax > signal.size()){
                smax = i;
            }
            double newSample = signal.get(i) - Utils.getMean(Utils.getSlice(signal, smin, smax));
            phasicSignal.add(newSample);
        }
        return phasicSignal;
    }

    public static List<Peak> findPeaksAndOffset(List<Double> data, double onset, double offset){
        List<Peak> results = new ArrayList<>();
        boolean isOnset = false;
        int lastPeak = 0;

        for(int i = 0; i < data.size(); i++){
            double dat = data.get(i);
            if(isOnset){
                if(dat <= offset ){
                    int peakOnset = Utils.argMax(Utils.getSlice(data, lastPeak, i));
                    if(data.get(lastPeak+peakOnset) > onset) {

                        Peak peak = new Peak();

                        peak.start = lastPeak;
                        peak.max = lastPeak + peakOnset;
                        peak.end = i;
                    }
                    isOnset = false;
                }
            }else{
                if (dat > offset){
                    lastPeak = i;
                    isOnset = true;
                }
            }
        }

        return results;
    }

    public static GSRFeatures extractGSRFeatures(List<Double> filteredGSR, int sampleRate, Peak peak){
        GSRFeatures gsrFeatures = new GSRFeatures(peak);
        gsrFeatures.riseTime = (peak.max - peak.start)/sampleRate;
        gsrFeatures.latency = peak.start/sampleRate;
        gsrFeatures.amplitude = filteredGSR.get(peak.max) - filteredGSR.get(peak.start);

        List<Double> slice = Utils.broadcastSub(
                Utils.getSlice(filteredGSR, peak.max, peak.end),
                gsrFeatures.amplitude
        );
        int min = Utils.argMin(slice);

        gsrFeatures.decayTime = (min)/sampleRate;
        gsrFeatures.riseTime = (min + peak.max - peak.start)/sampleRate;

        return gsrFeatures;
    }

}