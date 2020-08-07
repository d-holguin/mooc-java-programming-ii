
import java.util.HashMap;
import java.util.Map;

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
public class ShoppingCart {

    private Map<String, Item> itemMap;

    public ShoppingCart() {

        this.itemMap = new HashMap<>();
    }

    public void add(String product, int price) {

        if (itemMap.keySet().contains(product)) {

            increaseQuantity(product);

        } else {

            itemMap.put(product, new Item(product, 1, price));
        }

    }

    public int price() {

        int totalPrice = 0;

        for (Item e : itemMap.values()) {

            totalPrice += e.price();
        }

        return totalPrice;
    }

    public void increaseQuantity(String product) {

        this.itemMap.get(product).increaseQuantity();

    }

    public void print() {

        for (String e : itemMap.keySet()) {

            System.out.println(itemMap.get(e).toString());

        }

    }

}
