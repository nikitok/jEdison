package com.siberianapps.jedison;

import com.siberianapps.jedison.GPIO.Mode;
import com.siberianapps.jedison.GPIO.Value;
import com.siberianapps.jedison.JEdison.Digital;

/**
 * Created by n.navalikhin on 22.05.2015.
 */
public class Main {
    public Main(){
        GPIO edison = new JEdison();
        edison.pinMode(Digital.D0, Mode.OUTPUT);
        edison.digitalWrite(Digital.D0, Value.HIGH);
    }

    public static void main(String[] args){
        new Main();
    }
}
