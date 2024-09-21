package lab14;

import lab14lib.Generator;

/**
 * ClassName: AcceleratingSawToothGenerator
 * Package: lab14
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/21 - 9:15
 * Version: v1.0
 */
public class AcceleratingSawToothGenerator implements Generator {
    private int state;
    private int period;
    private double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.period = period;
        this.factor = factor;
        state = 0;
    }

    @Override
    public double next() {
        state = state + 1;
        if (state >= period) {
            state = state % period;
            period = (int) (period * factor);
        }
        return (double) 2 / period * state - 1;
    }
}
