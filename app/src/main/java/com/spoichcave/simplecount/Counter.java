package com.spoichcave.simplecount;

public class Counter {

    private int id;
    private String name;
    private int value;

    public Counter(){
        this.id = -1;
        this.name = null;
        this.value = 0;
    }

    public Counter(int id, String name, int value){
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public void increment(){
        this.value++;
    }

    public void decrement(){
        if(this.value > 0) value--;
    }

    public void setName(String name){ this.name = name; }

    public String getName(){ return  this.name; }

    public int getValue(){ return value; }

    public void setValue(int value){ this.value = value;}

    public int getId(){ return id; }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return Integer.toString(this.value);
    }
}
