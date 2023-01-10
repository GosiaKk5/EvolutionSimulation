package org.example;

import java.util.ArrayList;
import java.util.Random;

public class EquatorialForestMap extends AbstractMap {

    private final ArrayList<Vector2d> fertileFree = new ArrayList<>(); // wolne pola żyzne
    private final ArrayList<Vector2d> notFertileFree = new ArrayList<>(); // wolne pola nieżyzne
    private final int equatorStart;
    private final int equatorEnd;


    public EquatorialForestMap(int width, int height, int noStartPlants){
        super(width, height, noStartPlants);

        int equatorialHeight = getEquatorialHeight();
        this.equatorStart = (this.height - equatorialHeight) / 2;
        this.equatorEnd = this.equatorStart + equatorialHeight - 1;

        setFertileFields();
        addPlants(noStartPlants);
    }

    private int getEquatorialHeight(){

        int equatorialHeight = this.height / 5;

        if ( ((double)equatorialHeight / (double)this.height) < 0.20){
            equatorialHeight += 1;

        }
        return equatorialHeight;
    }

    private void setFertileFields(){
        for(int y = 0; y < this.height; y++){
            for(int x = 0; x < this.width; x++){
                Vector2d position = new Vector2d(x, y);
                if(y >= this.equatorStart && y <= this.equatorEnd){
                    this.fertileFree.add(position);
                }else{
                    this.notFertileFree.add(position);
                }
            }
        }
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
        if(position.y >= this.equatorStart && position.y <= this.equatorEnd){
            this.fertileFree.add(position);
        }else{
            this.notFertileFree.add(position);
        }

    }

    public int getEquatorStart(){
        return this.equatorStart;
    }

    public int getEquatorEnd(){
        return this.equatorEnd;
    }

    public int getNoFreeFields(){
        int noFreeFields = this.fertileFree.size() + this.notFertileFree.size();
        return noFreeFields;
    }

}
