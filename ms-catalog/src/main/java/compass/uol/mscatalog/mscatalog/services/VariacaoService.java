package compass.uol.mscatalog.mscatalog.services;

import compass.uol.mscatalog.mscatalog.dto.VariacaoDto;
import compass.uol.mscatalog.mscatalog.dto.VariacaoFormDto;

public interface VariacaoService {

    VariacaoDto createVariation(VariacaoFormDto body);

    VariacaoDto updateVariation(String id, VariacaoFormDto variacaoFormDto);

    void deleteVariation(String id);

    VariacaoDto getVariation(String id);
}
