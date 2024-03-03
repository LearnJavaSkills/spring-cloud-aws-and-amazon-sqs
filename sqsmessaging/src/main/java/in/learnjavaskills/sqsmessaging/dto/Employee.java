package in.learnjavaskills.sqsmessaging.dto;

public class Employee
{
    private String name;
    private String designation;
    private String laptop;

    public Employee(String name, String designation, String laptop)
    {
        this.name = name;
        this.designation = designation;
        this.laptop = laptop;
    }

    public Employee()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesignation()
    {
        return designation;
    }

    public void setDesignation(String designation)
    {
        this.designation = designation;
    }

    public String getLaptop()
    {
        return laptop;
    }

    public void setLaptop(String laptop)
    {
        this.laptop = laptop;
    }

    @Override
    public String toString()
    {
        return "Employee{" + "name='" + name + '\'' + ", designation='" + designation + '\'' + ", laptop='" + laptop + '\'' + '}';
    }
}
