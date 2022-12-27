package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class EquatorialForestMap extends AbstractMap {
    private ArrayList<Vector2d> fertileFree = new ArrayList<Vector2d>(); // wolne pola żyzne
    private ArrayList<Vector2d>  notFertileFree = new ArrayList<Vector2d>(); // wolne pola nieżyzne
    private final HashSet<Vector2d> fertileAll = new HashSet<>(); // wszystkie pola żyzne

    public EquatorialForestMap(int width, int height, int noStartPlants){
        super(width, height, noStartPlants);
        setFertileFields();
        setNotFertileFields();
        addPlants(noStartPlants);
    }

    private void setFertileFields(){
        int noFertileFields = (int) Math.round(((double) this.width * (double) this.height)/5); // liczba żyznych pól
        int row =  (height-1) / 2; // wiersz z ktorego zaczynamy dodawanie żyznych pól
        int rowChanger = 0; //zmiana wiersza
        int signOfRowChanger = -1;

        for(int i = 0; i < noFertileFields; i++){

            if(i % this.width == 0){ // jeśli z danego wiersza dodamy wszystkie pola to przechodzimy na nastepny
                row += signOfRowChanger * rowChanger; // np. przy height = 5 - row przyjmuje wartosci: 2,3,1,4,0
                rowChanger += 1;
                signOfRowChanger *= (-1);
            }

            this.fertileAll.add(new Vector2d(i % this.width, row));
            this.fertileFree.add(new Vector2d(i % this.width, row));

        }
    }

    private void setNotFertileFields(){
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                Vector2d position = new Vector2d(x, y);
                if(!fertileAll.contains(position)){
                    notFertileFree.add(position);
                }
            }
        }
    }

    public HashSet<Vector2d> getFertileFields(){
        return fertileAll;
    }

    public void addPlants(int noPlants){ // wydziel do funkcji

        Random random = new Random();
        for(int i = 0; i < noPlants; i++){
            int indexProbability = random.nextInt(0, 10);
            if(indexProbability < 8 && this.fertileFree.size() > 0){

                int index = random.nextInt(0,this.fertileFree.size());
                Vector2d newPlantPosition = this.fertileFree.get(index);
                Plant newPlant = new Plant(newPlantPosition);
                this.fertileFree.remove(index);
                this.plants.put(newPlantPosition, newPlant);

            } else if (this.notFertileFree.size() > 0) {

                int index = random.nextInt(0,this.notFertileFree.size());
                Vector2d newPlantPosition = this.notFertileFree.get(index);
                Plant newPlant = new Plant(newPlantPosition);
                this.notFertileFree.remove(index);
                this.plants.put(newPlantPosition, newPlant);
            }
        }
    }

    public void removePlant(Vector2d position){ // usuwa rosline na danej pozycji
        plants.remove(position);
        if(fertileAll.contains(position)){
            fertileFree.add(position);
        }else{
            notFertileFree.add(position);
        }
    }

}
