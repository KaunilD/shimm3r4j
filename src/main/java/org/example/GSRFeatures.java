package org.example;

import java.util.ArrayList;
import java.util.List;

public class GSRFeatures {

    Peak peak;
    double riseTime = 0.0, latency = 0.0, amplitude = 0.0, decayTime = 0.0, scrWidth = 0.0;


    public GSRFeatures(Peak peak) {
        this.peak = new Peak();
        this.peak.start = peak.start;
        this.peak.max = peak.max;
        this.peak.end = peak.end;
    }
}
