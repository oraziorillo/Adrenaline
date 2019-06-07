package client.view.gui.model_dtos;

public abstract class Card {
   private static final String IMAGEPATHPREFIX = "/images/";
   protected String extension = ".png";
   protected String name;
   public String getImagePath(){
      return IMAGEPATHPREFIX;
   }
   protected Card(String name){
      if(name.contains( "\\." )){
         String[] splitted = name.split( "\\." );
         extension = "."+ splitted[1];
         this.name = splitted[0];
      }else {
         this.name = name;
      }
   }
   public abstract boolean isDefaultCard();
}
