import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class DynamicSort {
    private Object port;

    public void createSortPortInstance() {
        Object instance;

        try {
            //System.out.println("pathToJar : " + Configuration.instance.getPathToJar());
            URL[] urls = {new File(Configuration.instance.getPathToJar()).toURI().toURL()};
            URLClassLoader urlClassLoader = new URLClassLoader(urls, DynamicSort.class.getClassLoader());
            Class clazz = Class.forName("Sort",true,urlClassLoader);
            //System.out.println("clazz     : " + clazz.toString());

            instance = clazz.getMethod("getInstance",new Class[0]).invoke(null,new Object[0]);
            port = clazz.getDeclaredField("port").get(instance);
            //System.out.println("port      : " + port.hashCode());

            Method getVersion = instance.getClass().getMethod("getVersion");

            String version = (String)getVersion.invoke(instance);
            //System.out.println("version   : " + version);
        } catch (Exception e) {
            System.out.println("--- exception");
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Integer> execute(ArrayList<Integer> values) {
        try {
            Method method = port.getClass().getMethod("sort", ArrayList.class);
            ArrayList<Integer> result = (ArrayList<Integer>) method.invoke(port, values);
            return result;
            } catch (Exception e) {
            e.printStackTrace();
            }
            return null;
    }

    public static void main(String[] args) {
        DynamicSort dynamicSort = new DynamicSort();
        Scanner s = new Scanner(System.in);
        main: while (true){
            System.out.println();
            System.out.println("---------------------------------------------------");
            System.out.println();
            dynamicSort.createSortPortInstance();
            System.out.println("Commands:\n(1) show components\n(2) show current component\n" +
                    "(3) set current component\n(4) execute\n(5) quit programm");
            System.out.println("Please enter Mode:");
            System.out.print(">> ");
            int mode = s.nextInt();
            s.reset();
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            Properties properties = new Properties();
            switch (mode){
                case 1:
                    SortType[] types = SortType.values();
                    System.out.println("Available sort algorithms:");
                    for (SortType type: types) {
                        System.out.println("\t - " + type.name());
                    }
                    break;
                case 2:
                    try {
                        fileInputStream = new FileInputStream(Configuration.instance.propertiesDirectory + "sort.props");
                        properties.load(fileInputStream);
                        System.out.println(properties.getProperty("sortType"));
                        fileInputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Please enter component:");
                    System.out.print(">> ");
                    String type = s.next();
                    try {
                        fileInputStream = new FileInputStream(Configuration.instance.propertiesDirectory + "sort.props");
                        fileOutputStream = new FileOutputStream(Configuration.instance.propertiesDirectory + "sort.props");
                        properties.load(fileInputStream);
                        properties.setProperty("sortType", type);
                        properties.store(fileOutputStream, null);
                        fileInputStream.close();
                        fileOutputStream.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.out.println("Please enter all numbers you want to be sorted, separate with comma!");
                    System.out.print(">> ");
                    String values = s.next();
                    ArrayList<Integer> val = new ArrayList<>();
                    for (String string: values.split(",")){
                        val.add(Integer.parseInt(string));
                    }
                    ArrayList<Integer> result = dynamicSort.execute(val);
                    for (int i: result){
                        System.out.print(i + ", ");
                    }
                    System.out.println();
                    break;
                case 5:
                    break main;
                default:
                    System.out.println();
                    break;
            }
        }
    }
}