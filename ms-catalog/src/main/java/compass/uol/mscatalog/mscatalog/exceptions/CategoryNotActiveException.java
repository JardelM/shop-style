package compass.uol.mscatalog.mscatalog.exceptions;

public class CategoryNotActiveException extends RuntimeException {

    public CategoryNotActiveException(String catId) {
        super(String.format("Categoria com id %s não está ativa!" , catId));
    }
}
