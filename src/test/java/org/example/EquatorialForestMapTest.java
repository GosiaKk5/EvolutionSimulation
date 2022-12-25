package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EquatorialForestMapTest {

    @Test
    void setFertileFieldsTest(){

        //1
        EquatorialForestMap map1 = new EquatorialForestMap(3,8, 0);
        HashSet<Vector2d> fertileAll1 = map1.getFertileFields();

        Vector2d[] FertileAllResult = {
                new Vector2d(0,3),
                new Vector2d(1,3),
                new Vector2d(2,3),
                new Vector2d(0,4),
                new Vector2d(1,4),
        };

        for(int i = 0; i < fertileAll1.size(); i++){
            assertTrue(fertileAll1.contains(FertileAllResult[i]));
        }

        //2
        EquatorialForestMap map2 = new EquatorialForestMap(5,1, 0);
        HashSet<Vector2d> fertileAll2 = map2.getFertileFields();

        Vector2d[] FertileAllResult2 = {
                new Vector2d(0,0),
        };

        for(int i = 0; i < fertileAll2.size(); i++){
            assertTrue(fertileAll2.contains(FertileAllResult2[i]));
        }
    }

    @Test
    void removePlant(){
        int noStartPlants = 50;
        EquatorialForestMap map = new EquatorialForestMap(10,5, noStartPlants);
        map.removePlant(new Vector2d(0,0));
        map.removePlant(new Vector2d(9,4));

        assertTrue(noStartPlants == (map.getPlants().size()+2));
        assertFalse(map.getPlants().containsKey(new Vector2d(0,0)));
        assertFalse(map.getPlants().containsKey(new Vector2d(9,4)));
        assertTrue(map.getPlants().containsKey(new Vector2d(1,4)));
    }

}
