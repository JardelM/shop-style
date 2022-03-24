package compass.uol.mscatalog.mscatalog.exceptions;

public class VariacaoNotFoundException extends RuntimeException {

    public VariacaoNotFoundException(String id) {
        super(String.format("Variação com id %s inexistente", id));
    }
}
