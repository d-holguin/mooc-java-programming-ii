/*
 * Copyright (C) 2020 Dantes
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package application;

import java.util.Random;

/**
 *
 * @author Dantes
 */
public class TemperatureSensor implements Sensor {

    private boolean isOn;

    public TemperatureSensor() {
        this.isOn = false;
    }

    @Override
    public boolean isOn() {

        return isOn;

    }

    @Override
    public void setOn() {

        if (!isOn) {

            isOn = true;
        }

    }

    @Override
    public void setOff() {

        if (isOn) {

            isOn = false;
        }

    }

    @Override
    public int read() {
        
        if(isOn){
        Random rand = new Random();
        int range = 30 - (-30) + 1;

        return rand.nextInt(range) + (-30);
        } else {
            throw new IllegalArgumentException("error reaging temp sens");
        }

    }

}
