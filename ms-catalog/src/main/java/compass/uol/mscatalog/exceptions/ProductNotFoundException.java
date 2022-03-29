package compass.uol.mscatalog.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String id) {
        super(String.format("Produto de id %s não encontrado", id));
    }
}
