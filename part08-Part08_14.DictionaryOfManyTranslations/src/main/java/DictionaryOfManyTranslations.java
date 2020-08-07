
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
public class DictionaryOfManyTranslations {

    private HashMap<String, ArrayList<String>> translationHashMap;

    public DictionaryOfManyTranslations() {

        this.translationHashMap = new HashMap<>();

    }

    public void add(String word, String translation) {

        this.translationHashMap.putIfAbsent(word, new ArrayList<String>());

//        ArrayList<String> translationsArrayList = this.translationHashMap.get(word);       //defines the arraylist 
//        translationsArrayList.add(word);                                            //adds to the arraylist
        this.translationHashMap.get(word).add(translation);   //should do all the above in one line

    }

    public ArrayList<String> translate(String word) {

        ArrayList<String> emptyList = new ArrayList<>();

        //tried to implment if statment at first forgetting about the getordefault method and i oculdn't get it too work.
        return this.translationHashMap.getOrDefault(word, emptyList);

    }

    public void remove(String word) {

        this.translationHashMap.remove(word);

    }

}
