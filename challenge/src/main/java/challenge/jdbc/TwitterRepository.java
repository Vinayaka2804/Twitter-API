package challenge.jdbc;
import java.util.List;

import com.challenge.model.Followers;
import com.challenge.model.Message;


public interface TwitterRepository {

	List<Message> findAll();
	List<Message> findMsgId(String id);
	List<Followers> listFollowers(String id);
	String follow(List<Followers> addmyfollower,String userDetails);
	String Unfollow(List<Followers> addmyfollower, String userDetails);
	List<Message> filterMyMessage(String searchmsg, String username);
	List<Message> searchAllMessages(String searchmsg);
	
}





	


	
