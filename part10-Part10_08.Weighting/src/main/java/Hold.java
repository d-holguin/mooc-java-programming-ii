

import java.util.ArrayList;

public class Hold {

    private ArrayList<Suitcase> suitcases;
    private int maximumWeight;

    public Hold(int maximumWeight) {
        this.maximumWeight = maximumWeight;
        this.suitcases = new ArrayList<>();
    }

    public void addSuitcase(Suitcase Suitcase) {
        if (this.totalWeight() + Suitcase.totalWeight() > maximumWeight) {
            return;
        }

        this.suitcases.add(Suitcase);
    }

    public int totalWeight() {
           int sum = 0;
        //int index = 0;
        
         sum = suitcases.stream()
                 .map(suitcases-> suitcases.totalWeight())
                .reduce(0, (a, b) -> a + b);
        
        
     
              
        
        return sum;
    }

    public void printItems() {
       
        
        suitcases.stream()
             //
//                .map(Item-> Item())
//                
               .forEach(Item->  Item.printItems());
        
        
        
    }

    @Override
    public String toString() {
        if (this.suitcases.isEmpty()) {
            return "no suitcases (0 kg)";
        }

        if (this.suitcases.size() == 1) {
            return "1 suitcase (" + this.totalWeight() + " kg)";
        }

        return this.suitcases.size() + " suitcases (" + this.totalWeight() + " kg)";
    }
}
