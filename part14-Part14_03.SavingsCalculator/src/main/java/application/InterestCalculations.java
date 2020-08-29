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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Dantes
 */
public class InterestCalculations {


    public Map<Integer, Double> calculateSavings(int years, double savings) {
        Map<Integer, Double> savingsMap = new HashMap<>();
        double current = 0;

        for (int i = 0; i < years; i++) {
            savingsMap.put(i, current);
            current += savings * 12;
        }

        return savingsMap;
    }

    public Map<Integer, Double> calculateInterest(int years, double savings, double rate) {
        Map<Integer, Double> interestMap = new HashMap<>();
        double current = 0;

        for (int i = 0; i < years; i++) {
            interestMap.put(i, current);
            current += savings * 12;
        }

        return interestMap;
    }
    
}
