package ps.monjed.ptuk;

public class Student {

    private int id;
    private String name;
    private int mark;

    public Student(String name, int mark) {
        //Monjed's Solution
        this.name = name;
        this.mark = mark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        //Monjed's Solution
        return name;
    }

    public int getId() {
        return id;
    }

    //Monjed's Solution
    public int getMark() {
        return mark;
    }

}
