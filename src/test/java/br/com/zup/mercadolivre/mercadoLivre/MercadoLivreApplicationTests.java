package br.com.zup.mercadolivre.mercadoLivre;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@SpringBootTest
@ActiveProfiles("test")
public class MercadoLivreApplicationTests {

    @Test
    public void contextLoads() {
        Assert.isTrue(true);
    }

}
