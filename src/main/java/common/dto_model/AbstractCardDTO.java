package common.dto_model;

public abstract class AbstractCardDTO implements DTO {


   private static final String IMAGE_PATH_PREFIX = "/images/";
   protected String extension = ".png";
   protected String name;

    public String getImagePath(){
       return IMAGE_PATH_PREFIX;
    }

    public abstract boolean isDefaultCard();

}
