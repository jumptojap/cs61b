package lab14;

import lab14lib.Generator;

/**
 * ClassName: SawToothGenerator
 * Package: lab14
 * Description:
 *
 * @Author: east_moon
 * @Create: 2024/9/21 - 8:59
 * Version: v1.0
 */
public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        this.period = period;
        state = 0;
    }

    @Override
    public double next() {
        state = (state + 1);
        int weirdState = state & (state >>> 3) % period;
        weirdState = state & (state >> 3) & (state >> 8) % period;
        return weirdState % period * ((double) 2 / period) - 1;
    }
}
