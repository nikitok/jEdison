package com.siberianapps.jedison;

/**
 * Created by n.navalikhin on 01.06.2015.
 */
public interface GPIO {

    public interface Pin {
        int getGPIO();
    }

    public static enum Mode {
        INPUT("in"),
        OUTPUT("out");

        public String cmd;

        private Mode(final String CMD) {
            cmd = CMD;
        }
    }

    public static enum Value {
        HIGH, LOW
    }

    void pinMode(Pin pin, Mode mode);

    void digitalWrite(Pin pin, Value value);

    Value digitalRead(Pin pin);
}
