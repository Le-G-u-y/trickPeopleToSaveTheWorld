package de.tpsw.domain.enumeration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The AnimalType enumeration.
 */
public enum AnimalType {
    PIG, CHICKEN, COW;

    private static final List<AnimalType> VALUES =
        Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static AnimalType getRandomAnimalType(){
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
