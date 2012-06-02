/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wintrisstech.erik.iaroc;

import android.os.SystemClock;
import ioio.lib.api.exception.ConnectionLostException;
import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;

/**
 *
 * @author droid3
 */
public class JackBeaconDock extends IRobotCreateAdapter implements JackState
{
    StateControllerJackBasic controller;

    public JackBeaconDock(IRobotCreateInterface delegate, StateControllerJackBasic controller)
    {
        super(delegate);
        this.controller = controller;
    }

    public JackState bothBump()
    {
        return this;
    }

    public JackState leftBump()
    {
        return this;
    }

    public JackState rightBump()
    {
        return this;
    }

    public JackState noBump()
    {
        return this;
    }

    public JackState action()
    {
        try
        {
            readSensors(SENSORS_INFRARED_BYTE);
            if (getInfraredByte() != 255)
            {
                controller.getDashboard().log("sensing..." + getInfraredByte());
                this.demo(1);
                SystemClock.sleep(50000); 
                controller.getDashboard().log("awake");
            }
            //    private static final int RED_BUOY_CODE = 248;
            //    private static final int GREEN_BUOY_CODE = 244;
            //    private static final int FORCE_FIELD_CODE = 242;
            //    private static final int BOTH_BUOY_CODE = 252;
            //    private static final int BOTH_BUOY_FORCE_FIELD_CODE = 254;
            //    private static final int GREEN_BUOY_FORCE_FIELD_CODE = 246;
            //    private static final int BOTH_BUOY_FORCE_FIELD_CODE = 254;
        } catch (ConnectionLostException ex)
        {
            controller.getDashboard().log("Reading infrared sensors!");
        }
        return this;
    }
}
