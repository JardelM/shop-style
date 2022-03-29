package compass.uol.mscatalog.services;

import compass.uol.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.dto.VariacaoFormDto;
import compass.uol.mscatalog.dto.VariacaoProdutoDto;

public interface VariacaoService {

    VariacaoDto createVariation(VariacaoFormDto body);

    VariacaoDto updateVariation(String id, VariacaoFormDto variacaoFormDto);

    void deleteVariation(String id);

    VariacaoProdutoDto getVariation(String id);
}
