package br.com.zup.mercadolivre.mercadoLivre.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UploaderFake {

    /**
     * @param imagens
     * @return links fakes de imagens
     */

    public Set<String> envia(List<MultipartFile> imagens) {
        return imagens
                .stream()
                .map(img -> "http://bucket.io/"
                    + UUID.randomUUID().toString() + "."
                    + img.getOriginalFilename().split("\\.")[1])
                .collect(Collectors.toSet());
        // http://bucket.io/1234-huuh-1234-img.jpg
    }

}
