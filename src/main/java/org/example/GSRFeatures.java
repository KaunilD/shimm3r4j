package org.example;

import java.util.ArrayList;
import java.util.List;

public class GSRFeatures {

    Peak peak;
    double riseTime = 0.0, latency = 0.0, amplitude = 0.0, decayTime = 0.0, scrWidth = 0.0, halfAmplitude =  0.0, apexValue = 0.0f;
    int halfAmplitudeIdx = 0;


    public GSRFeatures(Peak peak) {
        this.peak = new Peak();
        this.peak.start = peak.start;
        this.peak.max = peak.max;
        this.peak.end = peak.end;

    }

    void print(){
        String out = "peak_start: %d, peak_max: %d, peak_end: %d, rise_time: %f, latency: %f, amplitude: %f, decay_time: %f, scr_width: %f, half_amplitude_index: %d, half_amplitude: %f, apex_value: %f ";

        Utils.print(
                String.format(out, peak.start, peak.max, peak.end, riseTime, latency, amplitude, decayTime, scrWidth, halfAmplitudeIdx, halfAmplitude, apexValue)
        );
    }
}
