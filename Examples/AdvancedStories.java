import java.util.ArrayList;
import java.util.List;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.models.feed.Reel;
import com.github.instagram4j.instagram4j.models.user.User;
import com.github.instagram4j.instagram4j.requests.feed.FeedReelsTrayRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaInfoRequest;

/**
* This class has the function of obtaining the url of 
* the stories of a particular user or of the stories of the last N users.
* 
* Please see <a href="https://github.com/unldenis/advanced-instagram4j/blob/main/Examples/exAdvancedStories.java">this</a> class for examples
* @author <a href="https://github.com/unldenis?tab=repositories">unldenis</a>
* 
*/
public class AdvancedStories {

	private IGClient client;
	private List<Story> fin;
	
	public AdvancedStories(IGClient client) {
		this.client = client;
		fin = new ArrayList<>();
	}
	/**
	 * Method used to save a user's stories.
	 * @param username username from which get the stories
	 * @since 0.1
	 */
	public void loadStoriesUrl(String username) {
		fin.clear();
		List<Reel> reels = new ArrayList<>();
        new FeedReelsTrayRequest().execute(client)
        	.thenAccept(response -> {
        		reels.addAll(response.getTray());      
        }).join();
        String s = "";
        for(Reel reel: reels) 
    	//User
    	if(reel.getUser().getUsername().equals(username)) {   		
    		for(int j = 0; j<reel.getMedia_count(); j++) {    
    			s = reel.getMedia_ids()[j]+"_"+reel.getUser().getPk();        		
                new MediaInfoRequest(s).execute(client)
                .thenAccept(r -> {
                	//check if video or img
                	switch(textBetweenWords(r.toString(), "media_type=", ", code")) {
                		//image
                		case "1":
                			fin.add(new Story(textBetweenWords(r.toString().substring(1000), "candidates=[ImageVersionsMeta(url=", ", width="), 1, reel.getUser()));
                			break;                  		
                		//video
                		case "2":
                			fin.add(new Story(textBetweenWords(r.toString(), "type=101, url=", "), VideoVersionsMeta(height="), 2, reel.getUser()));
                			break;
                	}                   	
                }).join();
    		}
    		break;        		      
    	}     	        
	}
	/**
	 * Method used to save newest N user's stories.
	 * @param lastN lastN users from which get the stories
	 * @since 0.1
	 */
	public void loadStoriesUrl(int lastN) {
		fin.clear();
		List<Reel> reels = new ArrayList<>();
        new FeedReelsTrayRequest().execute(client)
        	.thenAccept(response -> {
        		reels.addAll(response.getTray());      
        }).join();
        String s = "";
		
        for(int y=0;y<lastN;y++) {
        	Reel reel = reels.get(y);
    		for(int j = 0; j<reel.getMedia_count(); j++) {    
    			s = reel.getMedia_ids()[j]+"_"+reel.getUser().getPk();        		
                new MediaInfoRequest(s).execute(client)
                .thenAccept(r -> {
                	//check if video or img
                	switch(textBetweenWords(r.toString(), "media_type=", ", code")) {
                		//image
                		case "1":
                			fin.add(new Story(textBetweenWords(r.toString().substring(1000), "candidates=[ImageVersionsMeta(url=", ", width="), 1, reel.getUser()));
                			break;                  		
                		//video
                		case "2":
                			fin.add(new Story(textBetweenWords(r.toString(), "type=101, url=", "), VideoVersionsMeta(height="), 2, reel.getUser()));
                			break;
                	}                   	
                }).join();
    		}	
        }
        	
     	        
	}
	
	
	
	String textBetweenWords(String sentence, String firstWord, String secondWord)
	{
	    return sentence.substring(sentence.indexOf(firstWord) + firstWord.length(), 
	        sentence.indexOf(secondWord));
	}
	/**
	 * Get stories we loaded earlier
	 * @return List of Story
	 * @since 0.1
	 */
	public List<Story> getStories()
	{
		return fin;
	}	
	
}

class Story {
	
	private String url;
	private int type;
	private User user;
	
	public Story(String url, int type, User user) {
		this.url = url;
		this.type = type;
		this.user = user;
	}
	
	public String toString() {
		return url;
	}
	public String getUrl() {		
		return url;
	}
	
	public int getType() {
		return type;
	}
	
	public boolean isImage() {
		return type==1;
	}

	public User getUser() {
		return user;
	}

}


