import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Random generator;
    static ArrayList<String> names;

    TroopTypes Tank = TroopTypes.TANK;
    TroopTypes Rifle = TroopTypes.RIFLEMAN;
    TroopTypes Machine = TroopTypes.MACHINEGUNNER;
    TroopTypes Anti = TroopTypes.ANTIARMOR;

    static String pickRandom(ArrayList<String> array){
        int rnd = generator.nextInt(array.size());
        return array.get(rnd);
    }
    
    enum TroopTypes{
        RIFLEMAN,
        MACHINEGUNNER,
        ANTIARMOR,
        TANK
    }

    class Troop{
        //fields
        private int health;
        private String name = "";
        private TroopTypes troopType;
        private int damage;

        //constructor
        public Troop(Main.TroopTypes troopType) {
            this.troopType = troopType;
            name = pickRandom(names);
            health = setHealth(troopType);
            damage = setDamage(troopType);
        }


        //methods
        private void engage(Squad enemySquad){
            Troop[] enemies = enemySquad.getTroops();

            generator = new Random();

            // Sets it so that there is only a 1/3 change of hitting
            for(Troop enemy: enemies){
                int rnd = generator.nextInt(3);
                if(rnd == 1){
                    enemy.loseHealth(damage);
                }

            }

            //suppression effect for machinegunners
            if(troopType == Machine){
                int rnd = generator.nextInt(8);
                if(rnd == 1){
                   enemySquad.suppressionEffect();
                }
    
            }
        }

        private int setHealth(TroopTypes type){

            if(type == TroopTypes.TANK){
                return 15;
            }
            return 10;
        }

        private int setDamage(TroopTypes type){
            if(type == TroopTypes.TANK){
                return 3;
            }
            return 1;
        }

        private void loseHealth(int dmg){
            health-= dmg;
        }

    }


    class Squad {
        //fields
        private int[] pos = new int[2];

        private boolean underCover = false;
        private boolean suppressed = false;

        private Troop[] troops = new Troop[6];
        private Troop[] casualties = new Troop[6];

        private boolean friendly;

        public char squadSign = ' ';

        //constructor


        //methods
        private void move(){

            //Friendly squads move up while the enemy moves down
            if(friendly){
                pos[0]++;
            }
            else {
                pos[0]--;
            }
        }

        private void attack(Squad target){

            if(suppressed){
                int rnd = generator.nextInt(2);
                if(rnd == 1){
                    for(Troop t: troops){
                        t.engage(target);
                    }
                } else{
                    System.out.print("Attack failed, under suppressive fire");
                }
            } else{
                for(Troop t: troops){
                    t.engage(target);
                }
            }

        }

        private void hold(){

        }

        private void digFoxHole(){

            underCover = true;
        }

        public int[] getPosition(){

            return pos;
        }

        public Troop[] getTroops() {
            return troops;
        }

        private void suppressionEffect(){
            suppressed = true;
        }

        private void deathCheck(){
            for(Troop t: troops){
                
            }
        }

    }

    class Map {
        //fields
        public char[][] map = new char[20][20];

        public Squad[] enemySquad = new Squad[5];
        public Squad[] friendlySquad = new Squad[5];

        public Squad[] graveYard = new Squad[10];

        //constructor

        //methods
        public void drawMap(){

        }

        public void placeSquads(){

        }

        public void step(){

        }


    }



    public static void main (String args[]) throws FileNotFoundException{

        //Generates random name array for troops
        generator = new Random();

        Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("name.txt")));
            s.useLocale(Locale.US);
            s.useDelimiter(",\\s*");

            while(s.hasNext()){
                names.add(s.next());
            }
        } finally {
            s.close();
        }

        //Troops in each squad, you can edit their class here
        


    }
}
