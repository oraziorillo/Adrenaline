package common.dto_model;

public abstract class AbstractCardDTO implements DTO {

    /*
   private static final String IMAGE_PATH_PREFIX = "/images/";
   protected String extension = ".png";
   protected String name;

    public String getImagePath(){
       return IMAGE_PATH_PREFIX;
    }

    public AbstractCardDTO(String name){
       if(name.contains( "\\." )){
          String[] splitted = name.split( "\\." );
          extension = "."+ splitted[1];
          this.name = splitted[0];
       }else {
          this.name = name;
       }
    }


    public abstract boolean isDefaultCard();

     */

}
