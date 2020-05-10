public class Item {

    private String name;
    private double weight;

    public Item(){}

    public Item(String n, double w){
        name = n;
        weight = w;
    }

    public String getName(){
        return name;
    }

    public void setName( String name){
        this.name = name;
    }

    public double getWeight(){
        return weight;
    }

}
