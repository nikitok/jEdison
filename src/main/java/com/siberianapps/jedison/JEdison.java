package com.siberianapps.jedison;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by n.navalikhin on 01.06.2015.
 */
public class JEdison implements GPIO {

    private Map<GPIO.Pin, GPIO.Mode> pinCache = new HashMap<GPIO.Pin, GPIO.Mode>();

    @Override
    public void pinMode(Pin pin, Mode mode) {

        //Initialize PIN
        if (!pinCache.containsKey(pin)) {
            try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/export")) {
                PrintWriter pw = new PrintWriter(out);
                pw.print(pin.getGPIO());
                pw.close();
                pinCache.put(pin, mode);
            } catch (IOException exception) {
                System.out.println(exception);
            }
        }

        // Initialize Pin Mode (IN/OUT)
        if (pinCache.containsKey(pin) && pinCache.get(pin) != mode) {
            try (FileOutputStream out = new FileOutputStream("/sys/class/gpio/gpio" + pin.getGPIO() + "/direction")) {
                PrintWriter pw = new PrintWriter(out);
                pw.print(mode == Mode.INPUT ? "in" : "out");
                pw.close();
                pinCache.put(pin, mode);
            } catch (IOException exception) {
                System.out.println(exception);
            }
        }

    }

    @Override
    public void digitalWrite(Pin pin, Value value) {

    }


    @Override
    public Value digitalRead(Pin pin) {
        try (FileInputStream in = new FileInputStream("/sys/class/gpio/gpio" + pin.getGPIO() + "/value")) {
            return in.read() == 1 ? Value.HIGH : Value.LOW;
        } catch (IOException exception) {
            System.out.println(exception);
            return null;
        }
    }


    public enum Analog implements GPIO.Pin {
        A0(36),
        A1(36),
        A2(23),
        A3(22),
        A4(21),
        A5(20);

        public int gpio;

        private Analog(final int GPIO) {
            gpio = GPIO;
        }

        @Override
        public int getGPIO() {
            return gpio;
        }
    }

    public enum Digital implements GPIO.Pin {
        RX(51),
        TX(51),
        D0(50),
        D1(51),
        D2(14),
        D3(15),
        D4(28),
        D5(17),
        D6(24),
        D7(27),
        D8(26),
        D9(19),
        D10(16),
        D11(25),
        D12(38),
        D13(39);

        public int gpio;

        private Digital(final int GPIO) {
            gpio = GPIO;
        }

        @Override
        public int getGPIO() {
            return gpio;
        }
    }
}
