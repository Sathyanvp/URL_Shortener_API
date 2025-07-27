package Url_shortner.url_shortner.exception;

public class CustomAliasAlreadyInUseException extends RuntimeException {
 public CustomAliasAlreadyInUseException(String message) {
     super(message);
 }
}