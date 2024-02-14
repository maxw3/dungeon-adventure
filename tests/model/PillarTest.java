package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PillarTest {
    Pillar myTestPillar = new Pillar("Abstraction");
    @Test
    void testGetName() {
       assertEquals("Abstraction", myTestPillar.getName());
    }

    @Test
    void testGetType() {
        assertEquals("PILLAR", myTestPillar.getType());
    }

    @Test
    void testToString() {
        assertEquals("Abstraction", myTestPillar.getName());
    }
}