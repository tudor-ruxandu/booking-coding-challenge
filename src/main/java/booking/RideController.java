package booking;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.*;

@RestController
public class RideController {

	@GetMapping("/booking")
	public ArrayList<Ride> getRideList(@RequestParam(value = "pickup") String[] pickupCoord,
																		 @RequestParam(value = "dropoff") String[] dropoffCoord,
																		 @RequestParam(value = "passengers", required=false) String passengers) throws Exception
	{
		// Parse the coordinates
		double pickupLat = Double.parseDouble(pickupCoord[0]);
		double pickupLon = Double.parseDouble(pickupCoord[1]);

		double dropoffLat = Double.parseDouble(dropoffCoord[0]);
		double dropoffLon = Double.parseDouble(dropoffCoord[1]);

		// Parse passenger number if it was specified
		int passengerNo = 1;
		if (passengers != null)
		{
			passengerNo = Integer.parseInt(passengers);
		}
		
		// Construct URLs for all possible suppliers
		URL daveUrl = Part1.URLBuilder("dave", pickupLat, pickupLon, dropoffLat, dropoffLon);
		URL ericUrl = Part1.URLBuilder("eric", pickupLat, pickupLon, dropoffLat, dropoffLon);
		URL jeffUrl = Part1.URLBuilder("jeff", pickupLat, pickupLon, dropoffLat, dropoffLon);

		// Get all the responses from the URLs and place in list for future processing
		ArrayList<Response> responses = new ArrayList<Response>();
		responses.add(new Response(daveUrl));
		responses.add(new Response(ericUrl));
		responses.add(new Response(jeffUrl));

		// Get a list of the cheapest rides for each car type and sort it
		ArrayList<Ride> rideList = Part1.constructCheapestRidesList(responses, passengerNo);
		Collections.sort(rideList);

		return rideList;
	}
}