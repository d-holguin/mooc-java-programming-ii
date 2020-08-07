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
/**
 *
 * @author Dantes
 */
public class Container {

    private int amount;

    public Container() {

        this.amount = 0;
    }

    public int contains() {
        return amount;
    }

    public void add(int amountToAdd) {
        if (amountToAdd < 0) {
            return;

        } else if (this.contains() + amountToAdd <= 100) {

            amount += amountToAdd;
        } else{
            amount = 100;
        }
    }

    public void remove(int amountToRemove) {

        if (amountToRemove < 0) {
            return;
        }
        if (this.contains() - amountToRemove < 0) {
            amount = 0;
        } else {

            amount += -amountToRemove;
        }

    }
    
    @Override
    public String toString(){
        
        
        return this.contains() +"/100"; 
        
        
    }
    
}
