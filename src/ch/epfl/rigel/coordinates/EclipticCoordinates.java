    package ch.epfl.rigel.coordinates;

    import java.util.Locale;

    import ch.epfl.rigel.math.Angle;
    import ch.epfl.rigel.math.ClosedInterval;
    import ch.epfl.rigel.math.RightOpenInterval;

    import static ch.epfl.rigel.Preconditions.checkInInterval;

    /**
     * An ecliptic coordinate
     *
     * @author Baptiste Lecoeur (316223)
     * @author Clement Sanh (311427)
     */
    public final class EclipticCoordinates extends SphericalCoordinates {

        //initializing the two intervals for the ecliptic coordinates
        private final static RightOpenInterval LON_INTERVAL = RightOpenInterval.of(0,Angle.TAU);
        private final static ClosedInterval LAT_INTERVAL = ClosedInterval.symmetric(Angle.TAU/2.0);

        /**
         * EclipticCoordinates package-private constructor
         * 
         * @param longitude  (double) : gives the longitude of the position
         * @param latitude   (double) : gives the latitude of the position
         */
        EclipticCoordinates(double longitude, double latitude) { 
            
            super(longitude, latitude);
        }

        /**
         * Public method used to call the private constructor while throwing an exception if not working
         * @param lon    (double) : gives the longitudinal value in rad of the position
         * @param lat    (double) : gives the latitude value in rad of the position
         *
         * @return      call the constructor with the entered parameters or throw exception
         */
        public static EclipticCoordinates of(double lon, double lat) {
            
            return new EclipticCoordinates(checkInInterval(LON_INTERVAL , lon), checkInInterval(LAT_INTERVAL , lat));
        }

        /**
         * Public method overrode returning the longitude in radians
         *
         * @return (double) : returns the longitude in radians
         */
        @Override
        public double lon() {
            return super.lon();
        }

        /**
         * Public method overrode returning the longitude in degrees
         *
         * @return (double) : returns the longitude in degrees
         */
        @Override
        public double lonDeg() {
            return super.lonDeg();
        }

        /**
         * Public method overrode returning the latitude in radians
         *
         * @return (double) : returns the latitude in radians
         */
        @Override
        public double lat() {
            return super.lat();
        }

        /**
         * Public method overrode returning the latitude in degrees
         *
         * @return (double) : returns the latitude in degrees
         */
        @Override
        public double latDeg() { 
            return super.latDeg();
        }

        /**
         * Public method overrode returning the ecliptic coordinates under the form of a string
         *
         * @return (String) : returns the ecliptic coordinates as a string 
         */
        @Override
        public String toString() { 
            return String.format(Locale.ROOT,"(λ=%.4f°, β=%.4f°)", lonDeg(), latDeg());
        }
    }
