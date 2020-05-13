package cache;

import com.fasterxml.classmate.AnnotationConfiguration;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import pojo.Employee;

import java.util.List;

public class Cache {
    public static void main(String[] arg) {
        // 在configure("cfg/hibernate.cfg.xml")方法中，如果不指定资源路径，默认在类目录的resource下寻找名为hibernate.cfg.xml的文件
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();

        // 2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
        SessionFactory sessionFactory = null;

        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }


        /**** 上面是配置准备，下面开始我们的数据库操作 ******/
        Session session1 = sessionFactory.openSession();
        Session session2 = sessionFactory.openSession();

        // creating transaction object
        Transaction transaction1 = session1.beginTransaction();
        Transaction transaction2 = session2.beginTransaction();

        Query q1=session1.createQuery("select max(empId) from Employee");
        List<Integer> list1=q1.list();
        System.out.println("session1----" + list1.get(0));

        Employee employee1 = new Employee();
        employee1.setEmpName("hong");  //
        session1.persist(employee1);

        System.out.println("session1----" + employee1.getEmpId());

        Query q2=session1.createQuery("select max(empId) from Employee");
        List<Integer> list2=q2.list();
        System.out.println("session1----" + list2.get(0));

        Query q3=session2.createQuery("select max(empId) from Employee");
        List<Integer> list3=q3.list();
        System.out.println("session2----" + list3.get(0));


        Employee employee2 = new Employee();
        employee2.setEmpName("yuan");
        session2.save(employee2);


        System.out.println("session1----" + employee2.getEmpId());

        Query q4=session2.createQuery("select max(empId) from Employee");
        List<Integer> list4=q4.list();
        System.out.println("session2----" + list4.get(0));

        transaction1.commit();   // 缓存同步到数据库
        session1.close();

        transaction2.commit();
        session2.close();


    }







}
