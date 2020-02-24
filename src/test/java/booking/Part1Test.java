package booking;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.Test;

public class Part1Test
{
  @Test
  public void testURLBuilder() throws Exception
  {
    String supplier = "dave";
    double pickupLat = 1.0;
    double pickupLon = 1.0;

    double dropoffLat = 2.0;
    double dropoffLon = 2.0;

    URL url = Part1.URLBuilder(supplier, pickupLat, pickupLon, dropoffLat, dropoffLon);

    assertEquals(url.toString(), "https://techtest.rideways.com/dave?pickup=1.0,1.0&dropoff=2.0,2.0");
  }
}
