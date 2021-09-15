import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
public class MyClass {
    static Map<String, AtomicLong> orders = new ConcurrentHashMap<>();
    
    static void processOrders(){
        for(String city: orders.keySet()){
            for(int i =0 ; i <50 ; i++){
                orders.get(city).getAndIncrement();
            }
        }
    }
    
    public static void main(String args[]) throws InterruptedException {
      orders.put("Mumbai",new AtomicLong());
      orders.put("Beijing",new AtomicLong());
      orders.put("London",new AtomicLong());
      orders.put("New York",new AtomicLong());
      
      ExecutorService service = Executors.newFixedThreadPool(2);
      
      service.submit(MyClass :: processOrders );
      service.submit(MyClass :: processOrders );
      
      service.awaitTermination(1,TimeUnit.SECONDS);
      service.shutdown();
      
      System.out.println(orders);
    }
}