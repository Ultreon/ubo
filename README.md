# Ultreon Binary Object
**File Extension:** `.ubo`  
**API Language:** `Java 17`  

## Usage
Assuming you use gradle.
1.  In `build.gradle`, define the repository as follows:  
    ```gradle
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
    ```
2.  Then, in `build.gradle`, define the dependency:  
    ```gradle
    dependencies {
        implementation 'com.github.Ultreon:ultreon-data:0.1.0'
    }
    ```
3.  We can now proceed to the using section. The `DataIo` class is capable of reading and writing UBO data.  
    Example:
    ```java
    import com.ultreon.data.DataIo;
    import com.ultreon.data.types.MapType;
    import java.io.File;
    
    public class DataTest {
        public static void main(String[] args) {
            MapType type = new MapType();
            type.putString("greetings", "Hello World");
            
            DataIo.write(type, new File("data.ubo"));
        }
    }
    ```
4.  You can now build it. You can change the example in step 3 to suit your needs. 

Here's the [jitpack listing](https://jitpack.io/#Ultreon/ultreon-data) for the current versions and builds you can use. 
