import java.util.HashSet;
import java.util.Set;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.actions.feed.FeedIterable;
import com.github.instagram4j.instagram4j.models.user.Profile;
import com.github.instagram4j.instagram4j.requests.friendships.FriendshipsFeedsRequest;

/**
* This class has the main functions of obtaining information from a user such as followers or following.
* 
* Please see <a href="https://github.com/unldenis/advanced-instagram4j/blob/main/Examples/exAdvancedUser.java">this</a> class for examples
* @author <a href="https://github.com/unldenis?tab=repositories">unldenis</a>
* 
*/
public class AdvancedUser {
	private IGClient client;
	private Set<Profile> tFollowers;
	private Set<Profile> tFollowing;

	public AdvancedUser(IGClient client) {
		this.client = client;
		tFollowers = new HashSet<>();
		tFollowing = new HashSet<>();
	}
	/**
	 * Method used to load user followers.
	 * @since 0.1
	 */
	public void loadFollowers() {
		tFollowers.clear();
	    FeedIterable.of(client, new FriendshipsFeedsRequest(client.getSelfProfile().getPk(), FriendshipsFeedsRequest.FriendshipsFeeds.FOLLOWERS))
	        .stream()
	        .forEach(res -> {
	            res.getUsers().forEach(p -> tFollowers.add(p));     
	    });
	}
	/**
	 * Method used to load followers.
	 * @param username username from which load followers
	 * @since 0.1
	 */
	public void loadFollowers(String username) {
		
        client.actions().users().findByUsername(username).thenAccept(u -> {
        	tFollowers.clear();
    	    FeedIterable.of(client, new FriendshipsFeedsRequest(u.getUser().getPk(), FriendshipsFeedsRequest.FriendshipsFeeds.FOLLOWERS))
    	        .stream()
    	        .forEach(res -> {
    	            res.getUsers().forEach(p -> tFollowers.add(p));     
    	    });
        }).join();		
	}
	/**
	 * Method used to load followers.
	 * @since 0.1
	 */	
	public void loadFollowing() {
		tFollowing.clear();
	    FeedIterable.of(client, new FriendshipsFeedsRequest(client.getSelfProfile().getPk(), FriendshipsFeedsRequest.FriendshipsFeeds.FOLLOWING))
	        .stream()
	        .forEach(res -> {
	            res.getUsers().forEach(p -> tFollowing.add(p));     
	    });
	}
	/**
	 * Method used to load followers.
	 * @param username username from which load following
	 * @since 0.1
	 */
	public void loadFollowing(String username) {
		
        client.actions().users().findByUsername(username).thenAccept(u -> {
        	tFollowing.clear();
    	    FeedIterable.of(client, new FriendshipsFeedsRequest(u.getUser().getPk(), FriendshipsFeedsRequest.FriendshipsFeeds.FOLLOWING))
    	        .stream()
    	        .forEach(res -> {
    	            res.getUsers().forEach(p -> tFollowing.add(p));     
    	    });
        }).join();		
	}
	/**
	 * Get followers we loaded earlier
	 * @return List of Followers
	 * @since 0.1
	 */
	public Set<Profile> getFollowers() {
		return tFollowers;
	}
	/**
	 * Get following we loaded earlier
	 * @return List of Following
	 * @since 0.1
	 */
	public Set<Profile> getFollowing() {
		return tFollowing;
	}
	
	/**
	 * Get users who do not match us
	 * @return List of non following back
	 * @since 0.1
	 */
	public Set<Profile> getDontFollowBack() {
		Set<Profile> nf=   new HashSet<Profile>(tFollowing);
		nf.removeAll(tFollowers);
		return nf;		 
	}
	
	
	
}
