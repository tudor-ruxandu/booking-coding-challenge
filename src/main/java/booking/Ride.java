package booking;

/**
 * This class represents a ride object, which has a supplier
 * a car type, a price, and an alloed number of passengers
 */
public class Ride implements Comparable<Ride>
{
  String supplier;
  String carType;
  int price;
  int passengers = -1;

  /**
   * Constructor for when the necessary info is given
   * @param givenSupplier the supplier for this ride
   * @param givenCarType  the car type of this ride
   * @param givenPrice    the price of this ride
   */
  public Ride(String givenSupplier, String givenCarType, int givenPrice)
  {
    supplier = givenSupplier;
    carType = givenCarType;
    price = givenPrice;
    setPassengers(carType);
  }

  /**
   * Constructor for when the necessary info is set later
   */
  public Ride()
  {
    supplier = null;
    carType = null;
    price = 0;
  }

  /**
   * Set this ride's supplier
   * @param newSupplier
   */
  public void setSupplier(String newSupplier)
  {
    supplier = newSupplier;
  }

  /**
   * Set this ride's car type
   * @param newCarType
   */
  public void setCarType(String newCarType)
  {
    carType = newCarType;
    setPassengers(carType);
  }

  /**
   * Set this ride's price
   * @param newPrice
   */
  public void setPrice(int newPrice)
  {
    price = newPrice;
  }

  /**
   * Set this ride's passengers based on car type
   * @param carType
   */
  private void setPassengers(String carType)
  {
		switch (carType) {
			case "STANDARD":
			case "EXECUTIVE":
			case "LUXURY":
				passengers = 4;
				break;
			case "PEOPLE_CARRIER":
			case "LUXURY_PEOPLE_CARRIER":
				passengers = 6;
				break;
			case "MINIBUS":
				passengers = 16;
				break;
		}
	}

  /**
   * 
   * @return  this ride's supplier
   */
  public String getSupplier()
  {
    return supplier;
  }

  /**
   * 
   * @return this ride's car type
   */
  public String getCarType()
  {
    return carType;
  }

  /**
   * 
   * @return this ride's price
   */
  public int getPrice()
  {
    return price;
  }

  /**
   * 
   * @return this ride's number of allowed passengers
   */
  public int getPassengers()
  {
    return passengers;
  }

  /**
   * This method allows Ride to be Comparable, and allows the use
   * of Collections.sort() on collections of Rides
   */
  public int compareTo(Ride otherRide)
  {
    return price - otherRide.price;
  }

  /**
   * String representation of a Ride
   */
  public String toString()
  {
    return carType + " - " + supplier + " - " + price;
  }
}