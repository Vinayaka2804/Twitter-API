package challenge.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.model.Followers;
import com.challenge.model.Message;

import challenge.jdbc.TwitterRepository;
import challenge.jdbc.TwitterDAOImplementation;

@Service
public class TwitterService {
	
	
	@Autowired
	TwitterRepository vin;
	//List all messages based on the ID (Basic Authentication)
		public List<Message> findAllMessages() {
		
			return vin.findAll();
		}
	
	//List all messages based on the ID
		public List<Message> findMsgforId(String id){
		
			return vin.findMsgId(id);
		}
	
		//List all messages based on the ID and search criteria
		public List<Message> filterAllMyMessage(String searchmsg, String id){
			
			return vin.filterMyMessage(searchmsg, id);
		}
		
		//List all the Users followers
		public List<Followers> listMyFollowers(String id) {
			
			return vin.listFollowers(id);
		}
		
		//Add new Users followers
		public String addfollow(List<Followers> addmyfollower,String userID){
			
			return vin.follow(addmyfollower,userID);
		}
		
		//Unfollower Users
		public String UnfollowUsers(List<Followers> addmyfollower,String userDetails) {
			return vin.Unfollow(addmyfollower, userDetails);
			
		}
		
		//Full Text Search functionality for admin
		public List<Message> FulltextSearch(String searchmsg){
			
			return vin.searchAllMessages(searchmsg);
		}
}
