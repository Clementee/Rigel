    package ch.epfl.rigel.coordinates;

    import ch.epfl.rigel.math.Angle;
    import ch.epfl.rigel.math.Polynomial;
    import org.junit.jupiter.api.Test;

    import java.time.ZoneId;
    import java.time.ZoneOffset;
    import java.time.ZonedDateTime;

    import static ch.epfl.rigel.astronomy.Epoch.J2000;
    import static ch.epfl.rigel.math.Angle.ofArcsec;
    import static org.junit.jupiter.api.Assertions.*;

    class EclipticToEquatorialConversionTest {
        private final static EclipticToEquatorialConversion test = new EclipticToEquatorialConversion(ZonedDateTime.of(1980,4,22,14,36, 57,67, ZoneId.of("UTC")));

        @Test
        void apply() {
            assertEquals(9.581478170200256, (new  EclipticToEquatorialConversion(ZonedDateTime.of(2009,7,6,0,0,0,0, ZoneOffset.UTC)))
                  .apply(EclipticCoordinates.of(Angle.ofDMS(139,41,10), Angle.ofDMS(4,52,31))).raHr(), 10e-7);
            assertEquals( 0.34095012064184566 , (new  EclipticToEquatorialConversion(ZonedDateTime.of(2009,7,6,0,0,0,0,ZoneOffset.UTC)))
                    .apply(EclipticCoordinates.of(Angle.ofDMS(139,41,10),Angle.ofDMS(4,52,31))).dec(), 10e-7);
        }
        @Test
        void epsilonCalculusWorks(){
            assertEquals(0.40907122964931697,Polynomial.of(ofArcsec(0.00181), ofArcsec(-0.0006), ofArcsec(-46.815), Angle.ofDMS(23, 26, 21.45))
                    .at(J2000.julianCenturiesUntil(ZonedDateTime.of(2009,7,6,0,0,0,0, ZoneOffset.UTC))));
        }
        @Test
        void testHashCode() {
            assertThrows(UnsupportedOperationException.class, ()->{  test.hashCode();});
        }

        @Test
        void testEquals() {
            assertThrows(UnsupportedOperationException.class, ()->{test.equals(test);});
        }
    }