import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;

public class exAdvancedStories {

	public static void main(String[] args) {
		
		//Login with Instagram4j
		IGClient client = null ;
        try {
            client = IGClient.builder()
                    .username("denisbbaby")
                    .password("mischles1")
                    .login();
        } catch (IGLoginException e) {
            e.printStackTrace();
        }
        
        
        AdvancedStories stories = new AdvancedStories(client);  
        //The user MUST be followed by us
        stories.loadStoriesUrl("alberto.panzino"); 
        //Print all images stories urls of user
        for(Story s : stories.getStories()) 
        	if(s.isImage())
        		System.out.println(s);
        
        
        //Load stories of the newest first 5 users to post a story
        stories.loadStoriesUrl(5); 
        //Print all usernames and urls loaded
        for(Story s : stories.getStories()) 
        	System.out.println(s.getUser().getUsername() + "->" + s.getUrl());
        
        
        
        System.out.println("End");
        
               
      
	}
	
}
