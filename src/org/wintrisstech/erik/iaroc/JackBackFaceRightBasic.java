/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wintrisstech.erik.iaroc;

import android.os.SystemClock;
import ioio.lib.api.exception.ConnectionLostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;

/**
 *
 * @author Rob
 */
class JackBackFaceRightBasic extends IRobotCreateAdapter implements JackState
{
    StateControllerJackBasic controller;

    public JackBackFaceRightBasic(IRobotCreateInterface delegate, StateControllerJackBasic controller)
    {
        super(delegate);
        this.controller = controller;
    }

    public JackState bothBump()
    {
        if (Math.random() < .5)
        {
            return leftBump();
        } else
        {
            return rightBump();
        }
    }

    public JackState leftBump()
    {
        return controller.BACKFACERIGHT;
    }

    public JackState rightBump()
    {
        return controller.BACKFACELEFT;
    }

    public JackState noBump()
    {
        return controller.FORWARD;
    }

    public JackState action()
    {
        try
        {
            controller.getDashboard().log("backing up, face right");
            controller.getDashboard().speak("backing up, face right");
            driveDirect(-100, -300);
            SystemClock.sleep(300);
        } catch (ConnectionLostException ex)
        {
            Logger.getLogger(JackBackFaceLeftBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            readSensors(SENSORS_BUMPS_AND_WHEEL_DROPS);
        } catch (ConnectionLostException ex)
        {
            Logger.getLogger(JackBackFaceRightBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isBumpLeft() && isBumpRight())
        {
            return bothBump();
        } else if (isBumpLeft())
        {
            return leftBump();
        } else if (isBumpRight())
        {
            return rightBump();
        } else
        {
            return noBump();
        }


    }
}
