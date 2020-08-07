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

/**
 *
 * @author Dantes
 */
public class StandardSensor implements Sensor {

    private int number;
    

    public StandardSensor(int number) {
        this.number = number;
       
    }

    @Override
    public boolean isOn() {

        return true;

    }

    @Override
    public void setOn() {
 return;
    }

    @Override
    public void setOff() {
        
        return;
    }

    @Override
    public int read() {

        return this.number;

    }

}
