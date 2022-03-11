package telegram_bot.climaTempo;

import com.google.gson.Gson;

import telegram_bot.seguranca.Token;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.IOException;

public class ServicoDeClimaTempo {    
    static Token climaTempoToken = new Token();
    static String climaToken = climaTempoToken.getClimaToken();
    static String webService = "http://apiadvisor.climatempo.com.br/api/v1/weather/locale/";
    static String webServiceHours = "/current?token=";
    static int codigoSucesso = 200;
    // http://apiadvisor.climatempo.com.br/api/v1/weather/locale/3477/current?token=your-app-token
    // static String localidade = "3477";

    public static ClimaTempo buscaClimaTempoPor(String localidadeId) throws Exception {
        String urlParaChamada = webService + localidadeId + webServiceHours + climaToken;
        System.out.println("Url buscaClimaTempoPorLocalidade: " + urlParaChamada);

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String jsonEmString = converteJsonEmString(resposta);

            Gson gson = new Gson();
            // Para esse metodo funcionar, os atributos da classe devem estar com o mesmo identificador (nome) do json:
            ClimaTempo previsao = gson.fromJson(jsonEmString, ClimaTempo.class);

            return previsao;
        } catch (Exception e) {
            throw new Exception("ERRO: " + e);
        }
    }

    // Converte Json em String:
    public static String converteJsonEmString(BufferedReader buffereReader) throws IOException {
        String resposta, jsonEmString = "";
        while ((resposta = buffereReader.readLine()) != null) {
            jsonEmString += resposta;
        }
        return jsonEmString;
    }
}