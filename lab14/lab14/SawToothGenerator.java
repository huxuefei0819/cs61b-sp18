package lab14;

import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    protected int state;
    protected int period;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    @Override
    public double next() {
        state = (state + 1);
        state %= period;
        return normalize();
    }

    protected double normalize() {
        return 2 * (double) state / period - 1;
    }
}
