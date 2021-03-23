import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;

public class exAdvancedUser {

	public static void main(String[] args) {
		
		//Login with Instagram4j
		IGClient client = null ;
        try {
            client = IGClient.builder()
                    .username("username")
                    .password("password")
                    .login();
        } catch (IGLoginException e) {
            e.printStackTrace();
        }
        
        
        AdvancedUser us = new AdvancedUser(client);
        //Get users username who do not match the follow
        us.loadFollowers();
        us.loadFollowing();
        us.getDontFollowBack().forEach(p -> System.out.println(p.getUsername()));
        
        
        //Get "user" followers profile
        us.loadFollowers("user");
        us.getFollowers().forEach(System.out::println);
        
        System.out.println("End");

                     
	}
	
}
