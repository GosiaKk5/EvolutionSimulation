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


            assertEquals(3, map1.getEquatorStart());
            assertEquals(4,  map1.getEquatorEnd());

        //2
        EquatorialForestMap map2 = new EquatorialForestMap(3,5, 0);


        assertEquals(2, map2.getEquatorStart());
        assertEquals(2,  map2.getEquatorEnd());

        //3
        EquatorialForestMap map3 = new EquatorialForestMap(5,13,25);

        assertEquals(5, map3.getEquatorStart());
        assertEquals(7,  map3.getEquatorEnd());

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
