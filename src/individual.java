public class individual{
    int fitness;
    String genome;

    individual()
    {
        fitness = 0;
        genome = "";
    }

    individual(String genome){
        this.genome = genome;
    }

    public void print(){
        System.out.println("fitness: " + fitness + " genome: " + genome);
    }
}