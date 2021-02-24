package br.com.zup.mercadolivre.mercadoLivre.utils;

import java.time.LocalDateTime;
import java.util.List;

public class JsonDataBuilder {

    private String json = "{";

    public JsonDataBuilder chaveValor(String chave, String valor) {
        if(!json.equals("{")) {
            this.json += ", ";
        }
        this.json += "\"" + chave + "\" : \"" + valor + "\"";
        return this;
    }

    public JsonDataBuilder chaveValor(String chave, String[] list) {
        if(!json.equals("{")) {
            this.json += ", ";
        }
        this.json += "\"" + chave + "\" : [";
        int n = 0;

        for (String s : list) {
            if(n != 0) {
                this.json += ", ";
            }
            this.json += s;
            n++;
        }
        this.json += "]";
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
