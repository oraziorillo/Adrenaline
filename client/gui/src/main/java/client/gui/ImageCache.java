package client.gui;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageCache {
   private static final HashMap<String,Image> cached = new HashMap<>();
   public static Image loadImage(String path,double prefHeight){
      Image returned = cached.get( path );
      if(returned == null)
         returned = loadNewImage( path,prefHeight );
      return returned;
   }
   
   private static Image loadNewImage(String path, double prefHeight){
      Image loaded = new Image( path,0, prefHeight,true,false  );
      cached.put( path,loaded );
      return loaded;
   }
   private ImageCache(){}
}
