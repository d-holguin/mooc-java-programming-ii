

public class Program {

    public static void main(String[] args) {
      
		Hashmap<String, Integer>map=new Hashmap<>();
		map.add("test1", 1);
		map.add("test2",2);
		System.out.println(map.get("test2"));
                System.out.println(map.get("test1"));
		System.out.println(map.remove("test1"));
		System.out.println(map.get("test1"));
		
    }

}
