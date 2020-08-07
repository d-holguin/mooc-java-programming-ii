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
public class ProductWarehouseWithHistory extends ProductWarehouse {

    //private double initialBalance;
    private ChangeHistory hisoryListObj = new ChangeHistory();

    public ProductWarehouseWithHistory(String productName, double capacity, double initialBalance) {
        super(productName, capacity);

       
        //this line very important we have to add the firs thing to it, so we just use the simle method we inheirited 
        super.addToWarehouse(initialBalance);
        
        
        
        
        

        hisoryListObj.add(initialBalance);

    }

    @Override
    public void addToWarehouse(double amount) {

        double result = 0;
        
        result = (double) super.getBalance() + amount;
        
        hisoryListObj.add(result);
        //histroy to be added
        
        
        
        super.addToWarehouse(amount);
        
        

    }

    @Override
    public double takeFromWarehouse(double amount) {
        double result = 0;
        
        result = (double) super.getBalance() - amount;
        
        
        //the difference is added not just the amount
        
        hisoryListObj.add(result);
        
        
        
        
        return super.takeFromWarehouse(amount);

    }

    public String history() {

        return this.hisoryListObj.toString();
    }
    
    public void printAnalysis(){
        
        System.out.println("Product: " + this.getName());
        
        System.out.println("History: " + hisoryListObj.toString());
        
        System.out.println("Largest amount of product: " + hisoryListObj.maxValue());
        
        System.out.println("Smallest amount of product:" + hisoryListObj.minValue());
        
        System.out.println("Average: " + hisoryListObj.average());
        
        
    }

}
