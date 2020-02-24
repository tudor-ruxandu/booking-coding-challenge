package booking;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class RideTest
{

  private Ride ride;
  private String supplier, carType;
  private int price;

  @Before
  public void setUp()
  {
    supplier = "DAVE";
    carType = "STANDARD";
    price = 500;

    ride = new Ride(supplier, carType, price);
  }

  @Test
  public void testGetters()
  {
    assertEquals(ride.getSupplier(), "DAVE");
    assertEquals(ride.getCarType(), "STANDARD");
    assertEquals(ride.getPrice(), 500);
  }

  @Test
  public void testPassengerNumberSetCorrectly()
  {
    assertEquals(ride.getPassengers(), 4);
  }

  @Test
  public void testCompareTo()
  {
    Ride cheaperRide = new Ride("DAVE", "STANDARD", 450);

    assertTrue(cheaperRide.compareTo(ride) < 0);
  }

  @Test
  public void testToStringFormatsStringCOrrectly()
  {
    assertEquals(ride.toString(), "STANDARD - DAVE - 500");
  }


}
