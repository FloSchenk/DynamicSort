import java.io.FileInputStream;
import java.util.Properties;

public enum Configuration {
    instance;

    public String userDirectory = System.getProperty("user.dir");
    public String fileSeparator = System.getProperty("file.separator");
    public String propertiesDirectory = userDirectory + fileSeparator + "properties" + fileSeparator;

    public String getPathToJar(){
        return userDirectory + fileSeparator + getSortType() + fileSeparator + "jar" + fileSeparator + getSortType() +"sort.jar";
    }

    public SortType getSortType() {
        try {
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(propertiesDirectory + "sort.props");
            properties.load(fileInputStream);
            fileInputStream.close();
            if (properties.getProperty("sortType").equals("smooth"))
                return SortType.smooth;
            else if (properties.getProperty("sortType").equals("stooge"))
                return SortType.stooge;
            else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}