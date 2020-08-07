
public class MainProgram {

    public static void main(String[] args) {
        // test your classes here
        
        
        Employees t = new Employees(); 
       Person h = new Person("Arto", Education.PHD);
        t.add(h); t.print(Education.PHD); 
        
        
     //  output should contain "Arto, PHD"
        
        
        
        
//        Employees university = new Employees();
//        university.add(new Person("Petrus", Education.PHD));
//        university.add(new Person("Arto", Education.HS));
//        university.add(new Person("Elina", Education.PHD));
//
//        university.print();
//
//        university.fire(Education.HS);
//
//        System.out.println("==");
//
//        university.print();
    }
}
