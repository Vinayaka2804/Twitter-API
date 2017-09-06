package challenge.mycontroller;

import java.util.List;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.challenge.model.Followers;
import com.challenge.model.Message;
import com.challenge.model.people;
import challenge.jdbc.Config;
import challenge.jdbc.TwitterRepository;
import challenge.service.TwitterService;

//import com.challenge.model.people;

@RestController
public class TwitterController {

	
	@Autowired
	TwitterRepository repo;
	@Autowired
	TwitterService twitterservice;
	
	//Show Messages for a particular User
		
	@RequestMapping("/messages")
	public  List<Message> showMessagebyId(@AuthenticationPrincipal final UserDetails userDetails)
	{
	   String nameofuser = userDetails.getUsername();
	    return twitterservice.findMsgforId(nameofuser);
	}
	
	
	//filter Messages for a particular User
	
		@RequestMapping("/mymessages")
		public  List<Message> filterMessagebyId(@RequestParam("search") String searchtext, @AuthenticationPrincipal final UserDetails userDetails)
		{
		   String nameofuser = userDetails.getUsername();
		    return twitterservice.filterAllMyMessage(searchtext, nameofuser);
		}
	
	
	//Endpoints to get the list of people the user is following as well as the followers of the user.
	
	@RequestMapping("/followers")
	public  List<Followers> ListFollowers(@AuthenticationPrincipal final UserDetails userDetails)
	{	
			String nameofuser = userDetails.getUsername();
			return twitterservice.listMyFollowers(nameofuser);
	}
	
	
	// Endpoint to follow other Users	
	
	@RequestMapping(method = RequestMethod.POST, value="/follow")
	public String followUsers(@Valid @RequestBody  List<Followers> addfollower,@AuthenticationPrincipal final UserDetails userDetails )
	{
		
		String username = userDetails.getUsername();
		return twitterservice.addfollow(addfollower, username);
	}
	
	
	// Endpoint to Unfollow other Users	
	
		@RequestMapping(method = RequestMethod.POST, value="/unfollow")
		public String UnfollowUsers(@RequestBody  List<Followers> unfollowusers,@AuthenticationPrincipal final UserDetails userDetails)
		{
			String username = userDetails.getUsername();
			return twitterservice.UnfollowUsers(unfollowusers, username);
		}
		
	
	//End point to do Full Text Search for all messages on Application admin
	
	@RequestMapping("/searchmessages")
	public  List<Message> searchMessages(@RequestParam("search") String searchtext,@AuthenticationPrincipal final UserDetails userDetails)
	{
		String nameofuser = userDetails.getUsername();
		return twitterservice.FulltextSearch(searchtext);
	}
	
	
}
