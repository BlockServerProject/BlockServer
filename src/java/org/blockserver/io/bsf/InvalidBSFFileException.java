package org.blockserver.io.bsf;

import java.io.IOException;

@SuppressWarnings("serial")
public class InvalidBSFFileException extends IOException{
	public InvalidBSFFileException(int lack){
		this(String.format("Unexpected end of file: %d more bytes required", lack));
	}
	public InvalidBSFFileException(String message){
		super("Error parsing BSF file: ".concat(message));
	}
}
