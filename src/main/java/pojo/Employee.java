package pojo;



import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
public class Employee {
    private Long empId;
    private String empName;

    @Id @GeneratedValue
    @Column(name = "id")
    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    @Column(name = "name")
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
}


