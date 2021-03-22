import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.models.feed.Reel;
import com.github.instagram4j.instagram4j.requests.feed.FeedReelsTrayRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaInfoRequest;

public class AdvancedStories {

	IGClient client;
	Map<String, Integer> fin;
	
	public AdvancedStories(IGClient client) {
		this.client = client;
		fin = new HashMap<>();
	}
	
	public void loadUserStoriesUrl(String username) {	
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
                			fin.put(textBetweenWords(r.toString().substring(1000), "candidates=[ImageVersionsMeta(url=", ", width="), 1);
                			break;                  		
                		//video
                		case "2":
                			fin.put(textBetweenWords(r.toString(), "type=101, url=", "), VideoVersionsMeta(height="), 2);
                			break;
                	}                   	
                }).join();
    		}
    		break;        		      
    	}     	        
	}
	
	static String textBetweenWords(String sentence, String firstWord, String secondWord)
	{
	    return sentence.substring(sentence.indexOf(firstWord) + firstWord.length(), 
	        sentence.indexOf(secondWord));
	}
	
	public Map<String,Integer> getStories()
	{
		return fin;
	}	
	
}


