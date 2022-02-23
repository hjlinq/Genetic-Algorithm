public class Main {

    private static String desiredString = "Hello World";

    private static int length = desiredString.length();

    private static int populationSize = 20;

    private static char[] charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !.".toCharArray();


    private static String getRandomString(){
        StringBuilder result = new StringBuilder("");
        for(int i = 0; i < length; i++) {
            result.append(charset[((int) (Math.round(Math.random() * charset.length)))%charset.length]);
        }
        return result.toString();
    }

    private static int calculateFitness(individual individual){
        String genome = individual.genome;
        int fitness = 0;
        for(int i = 0; i < genome.length(); i++)
        {
            if(genome.charAt(i) == desiredString.charAt(i)){
                fitness++;
            }
        }
        return fitness;
    }

    private static void sortByFitness(individual[] indArray){

        int length = indArray.length;

        for(int i = 0; i < length; i++) {
            for (int j = length - 1; j > i; j--) {
                if (indArray[j].fitness > indArray[i].fitness) {
                    individual temp = indArray[j];
                    indArray[j] = indArray[i];
                    indArray[i] = temp;
                }
            }
        }
    }

    private static individual sex(individual male, individual female){
        individual child = new individual();
        int length = (male.genome.length()+female.genome.length())/2;
        StringBuilder genome = new StringBuilder("");
        for(int i = 0; i < length; i++){
            genome.append((char)(Math.round((male.genome.charAt(i) + female.genome.charAt(i))/2.0)));
        }
        child.genome = genome.toString();
        return child;
    }

    private static void mutate(individual mutant){
        individual random = new individual(getRandomString());
        int length = mutant.genome.length();

        StringBuilder genome = new StringBuilder(mutant.genome);

        double frequency = 0.1; //number between 0 and 1

        frequency = frequency+0.5;

        for(int i = 0; i < length; i++){

            int yesOrNo = (int) Math.round(Math.random()*frequency);

            if(yesOrNo == 1){
                genome.setCharAt(i,random.genome.charAt(i));
            }
        }
        mutant.genome = genome.toString();
    }

    public static void main(String[] args) {

        individual[] population = new individual[populationSize];

        System.out.println(population.length);

        //seed population

        for (int i = 0; i < populationSize; i++) {
            population[i] = new individual(getRandomString());
        }

        //main loop

        int loopCount = 1;

        do{

            //calculate fitness

            for (int i = 0; i < populationSize; i++) {
                population[i].fitness = calculateFitness(population[i]);
                population[i].print();
            }

            //sort by fitness

            sortByFitness(population);

            //reproduce top x genomes
            int top = 5;
            int count = top + 1;
            for (int i = 0; i < top; i++) {
                for (int j = top; j > i; j--) {

                    //try commenting out the sex or mutation lines below to see the effect on evolution speed:

                    //sex:
                    population[count] = sex(population[i], population[j]);

                    //mutation:
                    mutate(population[count]);

                    //
                    count++;
                }
                if(count == populationSize)
                    break;

            }
            System.out.println("generation number " + loopCount);
            loopCount++;
        }while(!(population[0].fitness == length));

        System.out.println("*******************************************************************");
        System.out.println("Successfully evolved individual after " + loopCount + " generations.");
        System.out.println("final population:");
        for (int i = 0; i < populationSize; i++) {
            population[i].fitness = calculateFitness(population[i]);
            population[i].print();
        }

    }
}
