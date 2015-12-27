Syntax of BlockServer (Java)
===
* Indents should be in tabs.
* Open braces for block expressions (e.g. methods, classes, if-blocks, for-blocks, etc.) should not occupy an independent line.
* There can be an empty line between methods and/or fields.
* There can be an empty line between two different sections of code.
* Packages must be of or of subpackages of `org.blockserver`.
* The three main code sections in a file (package, import, class/interface/enum declaration) should be separated with empty lines.
* Imports of Java classes and custom classes can have an empty line between.
* Do not make redundant imports like `java.lang.*` imports and unused imports.
* Use Lombok for Getters and Setters
* Use the Apache Logger library to print out text to console, with the following exceptions:
  * It is for debug purposes and will not affect user interface.
  * The server or the logger is not initialized.
* Constructors, as a good practice, should point to the same `this(...)` constructor in order to avoid bugs.
* Do not add redundant `this.` tokens unless necessary.
* Add the `@Override` and `@SuppressWarnings(...)` annotations if required.
* Unless name duplicated, do not fully qualify class names in class/interface/enum body.

Example code:

```java
package org.blockserver.examples;

import java.io.File;
import java.io.FilenameFilter;

import net.blockserver.Server;

@SuppressWarnings("serial")
class ExampleException extends Exception{
	private String ext;

	public ExampleException(){
		this("Hello world!");
	}
	public ExampleException(String message){
		this(message, "log");
	}
	public ExampleException(String message, String ext){
		super(message);
		this.ext = ext;
	}

	public FilenameFilter getFilter(Pattern pattern){
		return new Foo(pattern);
	}

	private class Foo implements FilenameFilter{
		private Pattern pattern;

		public Foo(Pattern pattern){
			this.pattern = pattern;
		}

		@Override
		public boolean accept(File dir, String name){
			return pattern.bar(ext);
		}
	}

	public abstract static class Pattern extends com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern{
		public abstract boolean bar(String ext);
	}
}
```
