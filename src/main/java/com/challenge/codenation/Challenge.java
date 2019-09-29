package com.challenge.codenation;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


    public class Challenge implements Jsonable {

        private int numero_casas;
        private String token;
        private String cifrado;
        private String decifrado;
        private String resumo_criptografico;



        @Override
        public String toJson() {
            final StringWriter writable = new StringWriter();
            try {
                this.toJson(writable);
            } catch (final IOException e) {
            }
            return writable.toString();
        }

        public void toJson(Writer writer) throws IOException {

            final JsonObject json = new JsonObject();
            json.put("numero_casas", this.getNumero_casas());
            json.put("token", this.getToken());
            json.put("cifrado", this.getCifrado());
            json.put("decifrado", this.getDecifrado());
            json.put("resumo_criptografico", this.getResumo_criptografico());
            json.toJson(writer);

        }

        @Override
        public String toString() {
            return "Challenge{" +
                    "numero_casas=" + numero_casas +
                    ", token='" + token + '\'' +
                    ", cifrado='" + cifrado + '\'' +
                    ", decifrado='" + decifrado + '\'' +
                    ", resumo_criptografico='" + resumo_criptografico + '\'' +
                    '}';
        }

        public int getNumero_casas() {
            return numero_casas;
        }

        public void setNumero_casas(int numero_casas) {
            this.numero_casas = numero_casas;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCifrado() {
            return cifrado;
        }

        public void setCifrado(String cifrado) {
            this.cifrado = cifrado;
        }

        public String getDecifrado() {
            return decifrado;
        }

        public void setDecifrado(String decifrado) {
            this.decifrado = decifrado;
        }

        public String getResumo_criptografico() {
            return resumo_criptografico;
        }

        public void setResumo_criptografico(String resumo_criptografico) {
            this.resumo_criptografico = resumo_criptografico;
        }
    }



