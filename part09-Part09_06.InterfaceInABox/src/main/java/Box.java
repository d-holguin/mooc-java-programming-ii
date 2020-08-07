
import java.util.ArrayList;

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
public class Box implements Packable {

    private double maxCapcity;
    private ArrayList<Packable> itemsInBox;

    public Box(double maxCapcity) {
        this.maxCapcity = maxCapcity;
        this.itemsInBox = new ArrayList<>();
    }

    public void add(Packable itemToPack) {
        if (this.weight() + itemToPack.weight() <= maxCapcity) {

            itemsInBox.add(itemToPack);

        }

    }

    @Override
    public double weight() {
        double sum = 0;

        for (Packable e : itemsInBox) {
            sum += e.weight();

        }

        return (double) sum;
    }

    @Override
    public String toString() {
        return "Box: " + itemsInBox.size() + " items, total weight " + this.weight() + " kg";
    }

    
    
}
