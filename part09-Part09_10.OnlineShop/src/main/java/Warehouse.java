
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
public class Warehouse {

    private Map<String, Integer> prices;

    private Map<String, Integer> stockBalanceMap;

    public Warehouse() {

        this.prices = new HashMap<>();
        this.stockBalanceMap = new HashMap<>();

    }

    public int price(String product) {

        return prices.getOrDefault(product, -99);

    }

    public void addProduct(String product, int price, int stock) {

        prices.put(product, price);
        stockBalanceMap.put(product, stock);
    } 

    public int stock(String product) {

        return stockBalanceMap.getOrDefault(product, 0);

        
        
    }

    public boolean take(String product) {

        if (this.stock(product) > 0) {

            stockBalanceMap.put(product, stockBalanceMap.get(product) - 1);

            return true;
        }

        return false;
    }

    public Set<String> products() {
        Set<String> productNameSet = new HashSet<>();

        for (String key : prices.keySet()) {

            productNameSet.add(key);

        }

        return productNameSet;

    }

}
