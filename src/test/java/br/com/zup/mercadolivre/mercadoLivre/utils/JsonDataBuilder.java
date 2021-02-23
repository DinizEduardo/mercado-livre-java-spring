package br.com.zup.mercadolivre.mercadoLivre.utils;

import java.time.LocalDateTime;

public class JsonDataBuilder {

    private String json = "{";

    public JsonDataBuilder chaveValor(String chave, String valor) {
        if(!json.equals("{")) {
            this.json += ", ";
        }
        this.json += "\"" + chave + "\" : \"" + valor + "\"";
        return this;
    }

    public JsonDataBuilder chaveValor(String chave, long valor) {
        if(!json.equals("{")) {
            this.json += ", ";
        }
        this.json += "\"" + chave + "\" : " + valor;
        return this;
    }



    public String constroi() {
        json += "}";
        return json;
    }

}
