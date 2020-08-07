package application;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
public class Dictionary {

    private List<String> words;
    private Map<String, String> translations;

    public Dictionary() {
        this.words = new ArrayList<>();
        this.translations = new HashMap<>();

    }

    public String get(String word) {
        //returns translation from hashmap
        return this.translations.get(word);

    }

    public void add(String word, String translation) {
        //if key isn't in hashmap add it to the array list of words
        if (!this.translations.containsKey(word)) {
            this.words.add(word);
        }

        //put word in map if words is already in map it overwrites it with this new translation
        this.translations.put(word, translation);
    }

    public String getRandomWord() {
        Random random = new Random();
        
        //gets random index of arraylist words not larger than its size
        return this.words.get(random.nextInt(this.words.size()));

    }

}
