
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
public class VehicleRegistry {

    private HashMap<LicensePlate, String> liPlatehashMap;

    public VehicleRegistry() {

        this.liPlatehashMap = new HashMap<>();

    }

    public boolean add(LicensePlate liPlate, String owner) {

        for (LicensePlate e : liPlatehashMap.keySet()) {
            if (e.equals(liPlate)) {
                return false;

            }

        }
        liPlatehashMap.put(liPlate, owner);

        return true;

    }

    public String get(LicensePlate licensePlate) {

        return liPlatehashMap.getOrDefault(licensePlate, null);

    }

    public boolean remove(LicensePlate licensePlate) {

        if (!liPlatehashMap.containsKey(licensePlate)) {

            return false;
        }

        liPlatehashMap.remove(licensePlate);

        return true;

    }

    public void printLicensePlates() {

        for (LicensePlate e : liPlatehashMap.keySet()) {
            System.out.println(e);
        }
    }

    public void printOwners() {

        ArrayList<String> ownerList = new ArrayList<>();

        for (String e : liPlatehashMap.values()) {

            if (!ownerList.contains(e)) {

                ownerList.add(e);
            }

        }
        for (String e : ownerList) {
            System.out.println(e);
        }

    }

}
