# INSTRUKCJA WPROWADZANIA DANYCH
W okienku startowym symulacji jest możliwość dodania ścieżki do pliku opisującego parametry potrzebne do zainicjowania symulacji. 

## Strukura pliku

Plik powienien mieć określoną strukturę. Przykład

```txt
width,30
height,30
positionHandler,piekielny portal
numberOfStartPlants,0
plantEnergy,2
numberOfPlantsGrowDaily,5
map,toksyczne trupy
numberOfStartAnimals,15
animalStartEnergy,10
breedReadyEnergy,6
breedHandoverEnergy,5
minNumberOfMutations,0
maxNumberOfMutations,0
mutationHandler,lekka korekta
genotypeLength,3
orientationHandler,nieco szalenstwa
pathForStatistics,null
```
Ważne jest by w linijce nie było zbędnych spacji.

## Ograniczenia wprowadzanych wartości
- **width** (szerokość mapy) - liczba od 5 do 50
- **height** (wysokość mapy) - liczba od 5 do 50
- **positionHandler** (wariant obsługi krawędzi) - ```piekielny portal``` lub ```kula ziemska```
- **numberOfStartPlants** (liczba startowych roślin) - liczba większa równa 0
- **plantEnergy** (energia jaką uzyskuje zwierzę po zjedzeniu rośliny) - liczba większa od 0
- **numberOfPlantsGrowDaily** (liczba roślin, które wyrastają każdeo dnia) - liczba większa równa 0
- **map** (waraint sposobu wzrostu roślin) - ```zalesione rowniki``` lub ```toksyczne trupy```
- **numberOfStartAnimals** (początkowa liczba zwierząt) - liczba większa od 0
- **animalStartEnergy** (startowa ilość energii zwierzęcia) - liczba większa od 0
- **breedReadyEnergy** (energia jaką powinno mieć zwierzę, by móc się rozmnażać) - liczba większa od 1 
- **breedHandoverEnergy** (energia jaką oddaje zwierzę tworząc potomka) - liczba większa od 0 i mniejsza od breedReadyEnergy
- **minNumberOfMutations** (minimalna liczba mutacji u potomków) - liczba większa równa 0, mniejsza równa genotypeLenght, mniejsza równa maxNumberOfMutations,
- **maxNumberOfMutations** (maksymalna liczba mutacji u potomków) - liczba większa równa 0, mniejsza równa genotypeLenght
- **mutationHandler** (warianty mutacji) - ```pelna losowosc``` lub ```lekka korekta``` 
- **genotypeLength** (długość genotypu) - liczba od 0 do 30
- **orientationHandler** (wariant zachowania) - ```pelna predestynacja``` lub ```nieco szalenstwa``` 
- **pathForStatistics** (ścieżka do pliku w którym chcemy zapisać statystyki syulacji) - ```null``` (jeżeli nie chcemy zapisywać) lub ścieżka
