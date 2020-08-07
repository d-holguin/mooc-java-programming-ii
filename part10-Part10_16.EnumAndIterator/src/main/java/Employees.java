
import java.util.ArrayList;
import java.util.Iterator;
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
 */
public class Employees {

    private List<Person> employeeList;

    public Employees() {
        this.employeeList = new ArrayList<>();
    }

    public void add(Person personToAdd) {

        employeeList.add(personToAdd);
    }

    public void add(List<Person> personToAdd) {

        Iterator<Person> iterator = personToAdd.iterator();

        iterator.forEachRemaining(person -> employeeList.add(person));

    }

    public void fire(Education education) {

        Iterator<Person> iterator = employeeList.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getEducation() == education) {
                iterator.remove();
            }
        }

    }

    public void print() {
        
        Iterator<Person> iterator = employeeList.iterator();
        
         iterator.forEachRemaining(employee-> System.out.println(employee));
        
        
        
        

//        this.employeeList.stream()
//                .forEach(person -> System.out.println(person));

    }

    public void print(Education education) {
        
      Iterator<Person> iterator = employeeList.iterator();

        while (iterator.hasNext()) {
            Person nextPerson = iterator.next(); // important for printing can't used iterator.next() to print obj needs to be a var
            
            if (nextPerson.getEducation() == education) {
                System.out.println(nextPerson);
                 
            }
        }
        
        
        
        
        
        
        
//
//        this.employeeList.stream()
//                .filter(employee-> employee.getEducation() == education)
//                .forEach(person -> System.out.println(person));

    }

}
