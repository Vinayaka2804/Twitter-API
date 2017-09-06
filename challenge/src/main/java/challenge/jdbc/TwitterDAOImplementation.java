package challenge.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Repository;

import com.challenge.model.Followers;
import com.challenge.model.Message;

@Repository
@EnableWebSecurity
public class TwitterDAOImplementation implements TwitterRepository {

	@Autowired
	private JdbcOperations jdbc;
	@Autowired
	private NamedParameterJdbcTemplate namedjdbc;
	private static final String SQL_FIND_ALL = "SELECT * FROM MESSAGES" ;
	private static final String SQL_FILTER_BY_ID = "select * from messages where person_id IN (select follower_person_id as f_id from followers where person_id=:id)";
	private static final String SQL_LIST_FOLLOWERS = "select * from followers where person_id IN(select follower_person_id from followers where person_id=:id)";
	private static final String SQL_FOLLOW ="INSERT INTO followers(person_id, follower_person_id)VALUES(:person_id, :follower_person_id)";
	private static final String SQL_UNFOLLOW ="DELETE FROM followers WHERE person_id=:person_id and follower_person_id = :follower_person_id";
	private static final String SQL_FULLTEXT_SEARCH ="SELECT T.* FROM FT_SEARCH_DATA(:searchstr, 0, 0) FT, MESSAGES T WHERE FT.TABLE='MESSAGES' AND T.ID=FT.KEYS[0]";
	private static final String SQL_ALL_MESSAGES = "select * from messages where person_id IN (select follower_person_id as f_id from followers where person_id=:id)";
	Connection conn;

	
	@Override
	public List<Message> findAll() {
		
		return jdbc.query(SQL_FIND_ALL, new CustomerRowMapper());
	}

	//List all messages based on the ID (Basic Authentication)
	@Override
	public List<Message> findMsgId(String id) {
		
			
			SqlParameterSource namedparam = new MapSqlParameterSource("id",id);
			return namedjdbc.query(SQL_ALL_MESSAGES,namedparam,new CustomerRowMapper());
	}
	
	
	//List all messages based on the ID and search criteria
	@Override
	public List<Message> filterMyMessage(String searchmsg, String id) {
		
		
		SqlParameterSource namedparam = new MapSqlParameterSource("id",id);
		List<Message> vin=  namedjdbc.query(SQL_FILTER_BY_ID,namedparam,new CustomerRowMapper());
		
			List<Message> res = new ArrayList<Message>();
			
			for(Message myfilter: vin){
				if(myfilter.getContent().equals(searchmsg)){
					res.add(myfilter);
				}
			}
			
			return res;
		}
	
	
	//List all USERS followers		
	@Override
	public List<Followers> listFollowers(String id) {

		SqlParameterSource namedparam = new MapSqlParameterSource("id",id);
		return namedjdbc.query(SQL_LIST_FOLLOWERS, namedparam,new CustomerRowMapperFollowers());
		
	}

	
	// An endpoint to start following another user.
	
	@Override
	public String follow(List<Followers> addmyfollower,String userID) {
		int count =0;
		int rowaffected =0;
		String person_id = userID;
		for(Followers vin: addmyfollower){
			String follower_person_id = vin.getFollower_person_id()+"";
			SqlParameterSource namedparam = new MapSqlParameterSource("person_id",person_id).addValue("follower_person_id", follower_person_id);
			rowaffected = namedjdbc.update(SQL_FOLLOW, namedparam);
			if(rowaffected!=0)
				count++;
		}

		if(rowaffected!=0){
			
			return "You has successfully followed:"+count+" Users";
		}
		else{
			return "No change has been reflected";
		}
	}
	
	// An endpoint to start Un-following another user.
	
	@Override
	public String Unfollow(List<Followers> addmyfollower,String userDetails) {
		int count=0;
		int rowaffected =0;
		String person_id = userDetails;
		for(Followers vin: addmyfollower){
				
			String follower_person_id = vin.getFollower_person_id()+"";
			SqlParameterSource namedparam = new MapSqlParameterSource("person_id",person_id).addValue("follower_person_id",follower_person_id);
			rowaffected = namedjdbc.update(SQL_UNFOLLOW, namedparam);
			if(rowaffected!=0)
				count++;
		}

		if(rowaffected!=0){
			
			return "User has successfully Unfollowed:"+count+" Users";
		}
		else{
			return "No User has been Unfollowed";
		}
	}

	//Search functionality message by admin

	@Override
	public List<Message> searchAllMessages(String searchmsg) {
		
		
		String searchstr = "\'"+searchmsg+"\'";
		SqlParameterSource namedparam = new MapSqlParameterSource("searchstr",searchstr);
			
		return namedjdbc.query(SQL_FULLTEXT_SEARCH,namedparam,new CustomerRowMapper());

	}
	
	private class CustomerRowMapper implements RowMapper<Message> {

		@Override
		public Message mapRow(ResultSet rs, int row) throws SQLException {
			
			Message firstmsg = new Message();
			firstmsg.setContent(rs.getString("content"));
			firstmsg.setId(rs.getInt("id"));
			firstmsg.setPerson_id(rs.getInt("person_id"));
			return firstmsg;
		}  
		
	}

	private class CustomerRowMapperFollowers implements RowMapper<Followers> {

		@Override
		public Followers mapRow(ResultSet rs, int row) throws SQLException {
						
			Followers followersname = new Followers();
			followersname.setId(rs.getInt("id"));
			followersname.setFollower_person_id(rs.getInt("person_id"));
			followersname.setPerson_id(rs.getInt("follower_person_id"));
			return followersname;
			}  
		
	}


}
