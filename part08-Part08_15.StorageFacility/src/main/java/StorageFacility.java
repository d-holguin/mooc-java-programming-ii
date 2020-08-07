
import java.util.ArrayList;
import java.util.HashMap;

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
public class StorageFacility {

    private HashMap<String, ArrayList<String>> storageHashMap;

    public StorageFacility() {
        this.storageHashMap = new HashMap<>();
    }

    public void add(String unit, String item) {
        this.storageHashMap.putIfAbsent(unit, new ArrayList<>());

        this.storageHashMap.get(unit).add(item);

    }

    public ArrayList<String> contents(String storageUnit) {

        ArrayList<String> emptyList = new ArrayList<>();

        return this.storageHashMap.getOrDefault(storageUnit, emptyList);

    }

    //I don't need to be using this keyword so much
    public void remove(String storageUnit, String item) {

        this.storageHashMap.get(storageUnit).remove(item);

        if (this.storageHashMap.get(storageUnit).isEmpty()) {
            this.storageHashMap.remove(storageUnit);
        }

    }
    
    public ArrayList<String> storageUnits(){
        
        ArrayList<String> sUnitsKeys = new ArrayList<>();
        
        for(String e: storageHashMap.keySet()){
            
            if(!storageHashMap.get(e).isEmpty()){
                
                sUnitsKeys.add(e);
            }
            
            
        }
        return sUnitsKeys;
        
        
        
    }

}
