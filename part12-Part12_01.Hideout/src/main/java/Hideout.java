
import java.util.ArrayList;
import java.util.List;

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
 * @param <T>
 */
public abstract class Hideout<T> implements List<T> {

    private List<T> hideoutList;

    public Hideout() {
        this.hideoutList = new ArrayList<>();
    }

    public void putIntoHideout(T toHide) {

        if (!hideoutList.isEmpty()) {
            hideoutList.clear();
        }
        this.hideoutList.add(toHide);
    }

    public T takeFromHideout() {

        T value = hideoutList.get(0);
        hideoutList.clear();
        return value;

    }

    public boolean isInHideout() {

        if (hideoutList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

}
