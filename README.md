# advanced-instagram4j
<a href="https://github.com/instagram4j/instagram4j">Instagram4j</a> API extension for Instagram's private api (Android emulation)
<br><br>
<h2>Quick Usage</h2>
<br>
<h4><a href="https://github.com/unldenis/advanced-instagram4j/blob/main/Api/AdvancedStories.java">AdvancedStories</a></h4>
AdvancedStories stories = new AdvancedStories(client);
stories.loadUserStoriesUrl("user"); 
for(String k : stories.getStories().keySet())       	
  System.out.println(stories.getStories().get(k)==1 ? "Image->"+k : "Video->" + k);
