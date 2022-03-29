package compass.uol.mscatalog.exceptions;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String id) {
        super(String.format("Categoria de id %s não encontrada", id));
    }
}
