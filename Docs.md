Javadocs for BlockServer
====
* javadocs are starting with ```/**``` and ending with ```*/```
* every class and method should be documented
* every documentation should have a description and a tag part
* every description part of a documentation should include at least one or two sentences which are describing the object shortly
* further and detailed informations should be seperated by a html line seperator (```<br>```)
* the description and the tags should be seperated by a line
* classes/ enums/ interfaces should have at least following tags: ```@author```, ```@version``` (later)
* method documentations should include at least all tags which are beeing used for this method like ```@param``` and ```@return```
* additional tags like ```@see```, ```@exception``` and ```@deprecated``` should be used if necessary
* ```@author``` should be 'Blockserver team' all the time
* notice that your docs can make it easier for new devs and could improve the manpower behind this project


Example documentation:

```
/**
 * Main class for the core.
 *
 * @author BlockServer team
 * @see Server
 * @version 2.4
 * @since 1.0
 */
 public class testClass {
    /**
     * A small method for testing purposes.
     * <br>
     * Additional stuff about this method.
     *
     * @param i short description
     * @return true, if short description
     * @since 2.0
     */
    public boolean testMethod(int i) {...}
 }
 ```