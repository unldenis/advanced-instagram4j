import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;

public class Example {

	public static void main(String[] args) {

		IGClient client = null ;
        try {
            client = IGClient.builder()
                    .username("username")
                    .password("password")
                    .login();
        } catch (IGLoginException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        AdvancedStories stories = new AdvancedStories(client);
        stories.loadUserStoriesUrl("user");
        for(String k : stories.getStories().keySet()) {
        	
        	System.out.println(stories.getStories().get(k)==1 ? "Image->"+k : "Video->" + k);
        }

        System.out.println("End");
	}
	
}
