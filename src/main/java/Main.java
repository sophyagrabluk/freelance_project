import com.tms.config.SpringConfig;
import com.tms.domain.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
//        Service ourService = (Service) context.getBean("service");
//        System.out.println(ourService);
    }
}
