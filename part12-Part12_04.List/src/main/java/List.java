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
public class List<Type> {

    private Type[] values;
    private int firstFreeIndex;

    public List() {

        this.values = (Type[]) new Object[10];
        this.firstFreeIndex = 0;
    }

    public void add(Type value) {
        if (this.firstFreeIndex == this.values.length) {
            grow();
        }
        this.values[this.firstFreeIndex] = value;
        this.firstFreeIndex++;

    }

    private void grow() {
        int newSize = this.values.length + this.values.length / 2;
        Type[] newValues = (Type[]) new Object[newSize];
        for (int i = 0; i < this.values.length; i++) {
            newValues[i] = this.values[i];
        }

        this.values = newValues;
    }

 public boolean contains(Type value) {
    return indexOfValue(value) >= 0;
}

    //using two methods indeoxOfValue and moveToLeft
  public void remove(Type value) {
    int indexOfValue = indexOfValue(value);
    if (indexOfValue < 0) {
        return; // not found
    }

    moveToTheLeft(indexOfValue);
    this.firstFreeIndex--;
}

    ///which searches for the index of the given value. The method returns -1 if the value is not found.
    public int indexOfValue(Type value) {
        for (int i = 0; i < this.firstFreeIndex; i++) {
            if (this.values[i].equals(value)) {
                return i;
            }
        }

        return -1;
    }

    //Then we will implement the method private void moveToTheLeft(int fromIndex), which moves values from the given index one place to the left.
    private void moveToTheLeft(int fromIndex) {
        for (int i = fromIndex; i < this.firstFreeIndex - 1; i++) {
            this.values[i] = this.values[i + 1];
        }
    }
    public Type value(int index) {
    if (index < 0 || index >= this.firstFreeIndex) {
        throw new ArrayIndexOutOfBoundsException("Index " + index + " outside of [0, " + this.firstFreeIndex + "]");
    }

    return this.values[index];
}
    public int size(){
        return this.firstFreeIndex;
    }

}
