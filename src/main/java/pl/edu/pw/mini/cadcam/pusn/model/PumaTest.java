package pl.edu.pw.mini.cadcam.pusn.model;

import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

class PumaTest {

    @Test
    void angleDist() {
        assertTrue(abs(Puma.angleDist(PI / 6, 11 * PI / 6) - PI / 3) < 1e-6f);
        assertTrue(abs(Puma.angleDist(11 * PI / 6, PI / 6) - PI / 3) < 1e-6f);
        assertTrue(abs(Puma.angleDist(PI / 6, PI / 3) - PI / 6) < 1e-6f);
        assertTrue(abs(Puma.angleDist(PI / 3, PI / 6) - PI / 6) < 1e-6f);
        assertTrue(abs(Puma.angleDist(-PI / 6, PI / 6) - PI / 3) < 1e-6f);
        assertTrue(abs(Puma.angleDist(PI / 6, -PI / 6) - PI / 3) < 1e-6f);
    }
}