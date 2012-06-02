/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wintrisstech.erik.iaroc;

import ioio.lib.api.exception.ConnectionLostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;

/**
 *
 * @author Rob
 */
public class JackForwardBasic extends IRobotCreateAdapter implements JackState
{
    StateControllerJackBasic controller;

    public JackForwardBasic(IRobotCreateInterface delegate, StateControllerJackBasic controller)
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
        controller.getDashboard().log("left bump");
        controller.getDashboard().speak("left bump ha");
        return controller.BACKFACERIGHT;
    }

    public JackState rightBump()
    {
        return controller.BACKFACELEFT;
    }

    public JackState noBump()
    {
        return this;
    }

    public JackState action()
    {
        try
        {
            driveDirect(300, 300);
        } catch (ConnectionLostException ex)
        {
            Logger.getLogger(JackForwardBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            readSensors(SENSORS_GROUP_ID6);
        } catch (ConnectionLostException ex)
        {
            Logger.getLogger(JackForwardBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
        if ( getInfraredByte() != 255)
        {
            return controller.BEACONDOCK;
        }
        if (isBumpLeft() && isBumpRight())
        {
            controller.getDashboard().log("both bump detected ");
            controller.getDashboard().speak("both bump detected ");
            return bothBump();
        } else if (isBumpLeft())
        {
            controller.getDashboard().log("left bump detected ");
            controller.getDashboard().speak("left bump detected ");
            return leftBump();
        } else if (isBumpRight())
        {
            controller.getDashboard().log("right bump detected ");
            controller.getDashboard().speak("right bump detected ");
            return rightBump();
        } else
        {
            return noBump();
        }
    }
}
