# Ultreon Binary Object
**File Extension:** `.ubo`  
**API Language:** `Java 8`  

## Usage
Assuming you use gradle.
1.  In `build.gradle`, define the repository as follows:  
    ```gradle
    repositories {
        // ...
        maven { url "https://github.com/Ultreon/ultreon-data/raw/main/.mvnrepo/" }
    }
    ```
2.  Then, in `build.gradle`, define the dependency:  
    ```gradle
    dependencies {
        implementation 'io.github.ultreon:ubo:1.2.1+patch.1'
    }
    ```
3. We can now proceed to the using section. The `DataIo` class is capable of reading and writing UBO data.  
    Example:
   ```java
   import dev.ultreon.ubo.DataIo;
   import dev.ultreon.ubo.types.MapType;
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

~~Here's the [jitpack listing](https://jitpack.io/#Ultreon/ultreon-data) for the current versions and builds you can use.~~ (Temporarily not used)  
Check out the releases page for latest releases.

## Naming conventions
The following conventions are for map keys:
 * `MapType` and `ListType` are in `PascalCase`.
 * Any other types are in `camelCase`.

Do note that in some cases keys can have a different case.  
For example if the key is used for identifiers. (Like those: `namespace:id`)  
Tho it's generally not recommended to use map keys for objects like identifiers or numbers.
