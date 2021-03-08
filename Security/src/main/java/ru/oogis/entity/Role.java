package ru.oogis.entity;

public class Role {

    private Long id = generatedId++;
    private String name;

    public Role( String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private static long generatedId = 0;
}
