package booking;

import java.net.*;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This class takes a URL, connects to it, reads its JSON
 * and parses it to create a HashMap of Ride options and to
 * find out who the supplier is for the rides
 */
public class Response
{
  String supplier;
  HashMap<String,Integer> rideOptions;

  /**
   * Constructor for a Response
   * @param queryUrl the url which will be queried
   */
  public Response(URL queryUrl)
  {
    try
    {
      getRideOptionsFromUrl(queryUrl);
    }
    catch (Exception e)
    {
      // Set instance variables to null if there was any problem
      // which means that this reponse will be ignored later
      supplier = null;
      rideOptions = null;
    }
  }

  /**
   * This method connects to a URL, parses its JSON,
   * and collects the necessary information for the reponse
   * and sets it
   * @param url  the url which will be queried
   * @throws Exception
   */
  private void getRideOptionsFromUrl(URL url) throws Exception
  {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setConnectTimeout(2000);

    int responseCode = connection.getResponseCode();

    if (responseCode != HttpURLConnection.HTTP_OK)
      throw new RuntimeException("Request failed with response code:" + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuilder contentStringBuilder = new StringBuilder();
    String inputLine;

    while ((inputLine = in.readLine()) != null)
    {
      contentStringBuilder.append(inputLine);
    }

    String content = contentStringBuilder.toString();

    JSONParser parser = new JSONParser();
    JSONObject contentObj = (JSONObject) parser.parse(content);
    
    supplier = (String) contentObj.get("supplier_id");
    JSONArray options = (JSONArray) contentObj.get("options");

    rideOptions = new HashMap<String, Integer>();
    for (Object obj : options)
    {
      JSONObject ride = (JSONObject) obj;
      String carType = (String) ride.get("car_type");
      int price = (int)(long) ride.get("price");

      rideOptions.put(carType, price);
    }
  }

  /**
   * Sets the supplier for this response
   * @param newSupplier
   */
  public void setSupplier(String newSupplier)
  {
    supplier = newSupplier;
  }

  /**
   * Set the ride options for this response
   * @param newRideOptions
   */
  public void setRideOptions(HashMap<String,Integer> newRideOptions)
  {
    rideOptions = newRideOptions;
  }

  /**
   * Get the supplier for this response
   * @return
   */
  public String getSupplier()
  {
    return supplier;
  }

  /**
   * Get the ride options for this response
   * @return
   */
  public HashMap<String, Integer> getRideOptions()
  {
    return rideOptions;
  }
}