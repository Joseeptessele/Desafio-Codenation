package com.challenge.codenation;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.DigestUtils;
import org.springframework.util.SocketUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    public Challenge challenge;
    public String[] alphabet =  new String[]{"a","b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};


    public List<String> alphabetList = Arrays.asList(alphabet);
    @GetMapping
    public String home() throws Exception{
        String url = "https://api.codenation.dev/v1/challenge/dev-ps/generate-data?token=aee260d7cb8f697522410989cc2112f876d73aef";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();


        challenge = getJSON();
        challenge.setDecifrado(decryptMessage());
        challenge.setResumo_criptografico(sha1(challenge.getDecifrado()));


        updateJSON();

        return "<h1>Desafio Codenation - DBServer</h1>";
    }



    public Challenge getJSON() {
        try (FileReader fileReader = new FileReader(("/Users/joseptessele/answer.json"))) {

            JsonObject deserialize = (JsonObject) Jsoner.deserialize(fileReader);

            Mapper mapper = new DozerBeanMapper();

            Challenge challenge = mapper.map(deserialize, Challenge.class);

            return challenge;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | JsonException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateJSON() throws JSONException {

        JsonObject challengeCompleted = new JsonObject();

        challengeCompleted.put("token", challenge.getToken());
        challengeCompleted.put("numero_casas", challenge.getNumero_casas());
        challengeCompleted.put("cifrado", challenge.getCifrado());
        challengeCompleted.put("decifrado",challenge.getDecifrado());
        challengeCompleted.put("resumo_criptografico", challenge.getResumo_criptografico());

        try (FileWriter file = new FileWriter("/Users/joseptessele/answer.json")) {
            file.write(challengeCompleted.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ARQUIVO ATUALIZADO!");
    }

    public String decryptMessage() throws NoSuchAlgorithmException {
        char[] encryptedTextArray = challenge.getCifrado().toCharArray();
        String decryptedText = "";
        String[] specialCharacters = new String[]{".", ","};
        List<String> specialCharactersList = Arrays.asList(specialCharacters);

        char auxCipher;


        for(int i = 0; i < encryptedTextArray.length; i++){
            auxCipher = encryptedTextArray[i];

            if(Character.isWhitespace(auxCipher) || specialCharactersList.contains(Character.toString(auxCipher))){
                decryptedText += auxCipher;
            } else {
                for(int j = 0; j < alphabet.length; j++){
                    if(alphabet[j].contains(Character.toString(auxCipher))){

                        if(j >= challenge.getNumero_casas()){
                            decryptedText += alphabet[(alphabetList.indexOf(alphabet[j])) - challenge.getNumero_casas()];
                        } else {
                            decryptedText += alphabet[alphabet.length - (challenge.getNumero_casas() - j)];
                        }
                    }
                }
            }
        }
        return decryptedText;
    }

    public String sha1(String input) throws NoSuchAlgorithmException, NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
