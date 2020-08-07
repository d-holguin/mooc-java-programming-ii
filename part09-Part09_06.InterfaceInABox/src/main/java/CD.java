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
public class CD implements Packable{
    
    
    private String artist;
    
    
    private String name;
    
    private int pubYear;

    public CD(String artist, String name, int pubYear) {
        this.artist = artist;
        this.name = name;
        this.pubYear = pubYear;
        this.weight = 0.1;
    }
    
    
    
    
    
    private double weight;
    

  
    
    

    @Override
    public double weight() {
        
        return this.weight;
        
       
    
        
    }

    @Override
    public String toString() {
        return this.artist + ": " + this.name + " (" + this.pubYear + ")";
    }
    
    
    
    
    
}
