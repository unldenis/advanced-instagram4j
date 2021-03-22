import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;

public class exAdvancedStories {

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
        
        
        AdvancedStories stories = new AdvancedStories(client);
        //The user MUST be followed by us
        stories.loadUserStoriesUrl("user"); 
        //Print all urls of user stories
        for(String k : stories.getStories().keySet())         	
        	System.out.println(stories.getStories().get(k)==1 ? "Image->"+k : "Video->" + k);
      
	}
	
}
