package com.siberianapps.jedison;

/**
 * Created by n.navalikhin on 01.06.2015.
 */
public interface GPIO {

    interface Pin {
        //get hardware pin number
        int getHWPin();
    }

    enum Mode {
        INPUT("in"),
        OUTPUT("out");

        public String cmd;

        Mode(final String CMD) {
            cmd = CMD;
        }
    }

    enum Value {
        HIGH, LOW
    }

    void pinMode(Pin pin, Mode mode);

    void digitalWrite(Pin pin, Value value);

    Value digitalRead(Pin pin);
}
