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

package dictionary;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author Dantes
 */
 

public class SaveableDictionary {

 

    private Map<String, String> words;

    private String file;

 

    public SaveableDictionary() {

        this.words = new HashMap<>();

    }

 

    public SaveableDictionary(String file) {

        this(); //calls the above constructor

        this.file = file;

    }

 

    public boolean load() {

        try {

            Files.lines(Paths.get(this.file))

                    .map(l -> l.split(":"))

                    .forEach(parts -> {

                        this.words.put(parts[0], parts[1]);

                        this.words.put(parts[1], parts[0]);

                    });

            return true;

        } catch (IOException ex) {

            return false;

        }

    }

 

    public boolean save() {

        try {

            PrintWriter writer = new PrintWriter(new File(file));

            saveWords(writer);

            writer.close();

        } catch (Exception e) {

            return false;

        }

        return true;

    }

 

    public void add(String word, String translation) {

        if (words.containsKey(word)) {

            return;

        }

 

        words.put(word, translation);

        words.put(translation, word);

    }

 

    public String translate(String word) {

        return words.get(word);

    }

 

    public void delete(String word) {

        String translation = translate(word);

 

        words.remove(word);

        words.remove(translation);

    }

 

    private void saveWords(PrintWriter writer) throws IOException {

        List<String> allreadySaved = new ArrayList<>();

        words.keySet().stream().forEach(word -> {

            if (allreadySaved.contains(word)) {

                return;

            }

 

            String pari = word + ":" + words.get(word);

            writer.println(pari);

 

            allreadySaved.add(word);

            allreadySaved.add(words.get(word));

        });

    }

}

 