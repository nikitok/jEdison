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
    private static final String COMAND_PREFIX = "/sys/class/gpio/";

    @Override
    public void pinMode(Pin pin, Mode mode) {

        //initialize PIN
        if (!pinCache.containsKey(pin)) {
            //echo mode0 > /sys/kernel/debug/gpio_debug/gpio27/current_pinmux
            try (FileOutputStream out = new FileOutputStream(COMAND_PREFIX + "/sys/class/gpio/export")) {
                PrintWriter pw = new PrintWriter(out);
                pw.print(pin.getHWPin());
                pw.close();
                pinCache.put(pin, mode);
            } catch (IOException exception) {
                System.out.println(exception);
            }
        }

        //initialize Pin Mode (IN/OUT)
        if (pinCache.containsKey(pin) && pinCache.get(pin) != mode) {
            try (FileOutputStream out = new FileOutputStream(COMAND_PREFIX + "gpio" + pin.getHWPin() + "/direction")) {
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
        try (FileOutputStream out = new FileOutputStream(COMAND_PREFIX + "gpio" + pin.getHWPin() + "/value")) {
            PrintWriter pw = new PrintWriter(out);
            pw.print(value == null || value == Value.LOW ? "0" : "1");
            pw.close();
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }


    @Override
    public Value digitalRead(Pin pin) {
        try (FileInputStream in = new FileInputStream(COMAND_PREFIX + "gpio" + pin.getHWPin() + "/value")) {
            return in.read() == 1 ? Value.HIGH : Value.LOW;
        } catch (IOException exception) {
            System.out.println(exception);
            return null;
        }
    }


    /**
     * maps with edison pins
     * http://www.emutexlabs.com/project/215-intel-edison-gpio-pin-multiplexing-guide
     * https://communities.intel.com/message/279206
     */
    public enum Analog implements GPIO.Pin {
        A0(44),
        A1(45),
        A2(46),
        A3(47),
        A4(14),
        A5(165);

        public int hwPin;

        Analog(final int hwPin) {
            this.hwPin = hwPin;
        }

        @Override
        public int getHWPin() {
            return hwPin;
        }
    }

    public enum Digital implements GPIO.Pin {
        D0(130),
        D1(131),
        D2(128),
        D3(12),
        D4(129),
        D5(13),
        D6(182),
        D7(48),
        D8(49),
        D9(183),
        D10(41),
        D11(43),
        D12(42),
        D13(40);

        public int hwPin;

        Digital(final int hwPin) {
            this.hwPin = hwPin;
        }

        @Override
        public int getHWPin() {
            return hwPin;
        }
    }
}
