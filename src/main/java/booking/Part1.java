package booking;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class queries an api to obtain a list of the cheapest
 * available rides from one specified point to another, given
 * a number of passengers
 */
public class Part1
{
  // Global variable containing all available car types
  private static final String[] CAR_TYPES = {"STANDARD", "EXECUTIVE", "LUXURY",
                                             "PEOPLE_CARRIER", "LUXURY_PEOPLE_CARRIER", "MINIBUS"};

  public static void main (String[] args) throws Exception
  {

    // Validate inputs
    if (args.length < 4 || args.length > 5)
    {
      System.err.println("Wrong number of arguments.");
      System.err.println("Please provide at least 4 arguments and at most 5.");
      return;
    }

    double pickupLat, pickupLon;
    double dropoffLat, dropoffLon;
    try
    {
      pickupLat = Double.parseDouble(args[0]);
      pickupLon = Double.parseDouble(args[1]);
      dropoffLat = Double.parseDouble(args[2]);
      dropoffLon = Double.parseDouble(args[3]);
    }
    catch (Exception e)
    {
      System.err.println("Coordinates must be provided as real numbers in the form:");
      System.err.println("<pickup lat> <pickup lon> <dropoff lat> <dropoff lon>");
      return;
    }

    int passengers = 1;
    if (args.length == 5)
    {
      try
      {
        passengers = Integer.parseInt(args[4]);
        if (passengers > 16 || passengers < 1)
        {
          throw new RuntimeException("Invalid number of passengers");
        }
      }
      catch (Exception e)
      {
        System.err.println("Number of passengers must be an integer between 1 and 16");
        return;
      }
    }

    // Construct URLs for all possible suppliers
    URL daveUrl = URLBuilder("dave", pickupLat, pickupLon, dropoffLat, dropoffLon);
    URL ericUrl = URLBuilder("eric", pickupLat, pickupLon, dropoffLat, dropoffLon);
    URL jeffUrl = URLBuilder("jeff", pickupLat, pickupLon, dropoffLat, dropoffLon);

    // Get all the responses from the URLs and place in list for future processing
    ArrayList<Response> responses = new ArrayList<Response>();
    responses.add(new Response(daveUrl));
    responses.add(new Response(ericUrl));
    responses.add(new Response(jeffUrl));

    // Get a list of the cheapest rides for each car type and sort it
    ArrayList<Ride> rideList = constructCheapestRidesList(responses, passengers);
    Collections.sort(rideList);
    
    // Output the rides in ascending order
    for (Ride ride : rideList)
    {
      System.out.println(ride);
    }
  }
  
  /**
   * 
   * @param supplier    the id of the supplier whose api we are querying
   * @param pickupLat   pickup latitude
   * @param pickupLon   pickup longitude
   * @param dropoffLat  dropoff latitude
   * @param dropoffLon  dropoff longitude
   * @return            the query url necessary to get the ride options
   * @throws MalformedURLException
   */
  public static URL URLBuilder(String supplier,
                                double pickupLat, double pickupLon,
                                double dropoffLat, double dropoffLon)
                                throws MalformedURLException
  {
    StringBuilder urlStringBuilder = new StringBuilder("https://techtest.rideways.com/");
    urlStringBuilder.append(supplier);
    
    urlStringBuilder.append("?pickup=");
    urlStringBuilder.append(pickupLat);
    urlStringBuilder.append(',');
    urlStringBuilder.append(pickupLon);
    
    urlStringBuilder.append("&dropoff=");
    urlStringBuilder.append(dropoffLat);
    urlStringBuilder.append(',');
    urlStringBuilder.append(dropoffLon);
    
    String urlString = urlStringBuilder.toString();
    
    return new URL(urlString);
  }

  /**
   * 
   * @param responses   a list of responses from all supplier APIs for some specific coordinates
   * @param passengers  number of passengers that wish to get a ride
   * @return            a list of all the cheapest rides available for each individual car type
   */
  public static ArrayList<Ride> constructCheapestRidesList(ArrayList<Response> responses, int passengers)
  {
    ArrayList<Ride> rideList = new ArrayList<Ride>();
    for (String carType : CAR_TYPES)
    {
      Ride currentRide = new Ride();
      int minPrice = Integer.MAX_VALUE;
      for (Response response : responses)
      {
        // If the query was succesful for the given supplier, and the supplier offers the given
        // car type as an option
        if (response.getRideOptions() != null && response.getRideOptions().containsKey(carType))
        {
          int price = response.getRideOptions().get(carType);

          // If the current price is lower than the minimum price we saw so far
          if (price < minPrice)
          {
            // Update the current cheapest ride
            minPrice = price;
            currentRide.setPrice(price);
            currentRide.setSupplier(response.getSupplier());
            currentRide.setCarType(carType);
          }
        }
      }
      if (currentRide.getSupplier() != null && currentRide.getPassengers() >= passengers)
      {
        rideList.add(currentRide);
      }
    }
    return rideList;
  }
}