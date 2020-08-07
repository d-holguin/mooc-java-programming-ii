
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
public class BoxWithMaxWeight extends Box {

    private ArrayList<Item> itemsInMaxBox;

    private int capcity;

    public BoxWithMaxWeight(int capcity) {

        this.capcity = capcity;
        this.itemsInMaxBox = new ArrayList<>();

    }

    public int currentAmount() {
        int sum = 0;

        for (Item e : itemsInMaxBox) {

            sum += e.getWeight();
        }
        return sum;

    }

    @Override
    public void add(Item item) {

        if (currentAmount() + item.getWeight() <= this.capcity) {

            itemsInMaxBox.add(item);
        }

    }

    @Override
    public boolean isInBox(Item item) {
        
        
        return itemsInMaxBox.contains(item);

    }

//    @Override
//    public String toString() {
//        return "BoxWithMaxWeight{" + "itemsInMaxBox= " + itemsInMaxBox + ", capcity=" + capcity + '}';
//    }
    

}
