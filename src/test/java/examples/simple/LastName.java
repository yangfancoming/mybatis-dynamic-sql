
package examples.simple;

public class LastName {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static LastName of(String name) {
        LastName lastName = new LastName();
        lastName.setName(name);
        return lastName;
    }
}
