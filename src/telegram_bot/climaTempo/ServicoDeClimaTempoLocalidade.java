package telegram_bot.climaTempo;

import com.google.gson.Gson;

import telegram_bot.seguranca.Token;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.io.IOException;

// Documentação: http://apiadvisor.climatempo.com.br/doc/index.html#api-Locale-GetCityByNameAndState
public class ServicoDeClimaTempoLocalidade {
    static Token climaTempoToken = new Token();				
    static String climaToken = climaTempoToken.getClimaToken();
    static String webServiceP1 = "http://apiadvisor.climatempo.com.br/api/v1/locale/city?province=";
    static String webServiceP2 = "&token=";
    static int codigoSucesso = 200;
    // http://apiadvisor.climatempo.com.br/api/v1/locale/city?province=DF&token=1828d89549f884eb27909ebe41744b73 - listaPorUf    


    public static List<LocalidadeDeClimaTempo> buscaLocalidadeClimaTempoPor(String uf) throws Exception {
        String urlParaChamada = webServiceP1 + uf + webServiceP2 + climaToken;

        try {
            URL url = new URL(urlParaChamada);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            if (conexao.getResponseCode() != codigoSucesso)
                throw new RuntimeException("HTTP error code : " + conexao.getResponseCode());

            BufferedReader resposta = new BufferedReader(new InputStreamReader((conexao.getInputStream())));
            String jsonEmString = converteJsonEmString(resposta);

            Gson gson = new Gson();
            // AQUI: Criar tratamento de lista de Localidades
            LocalidadeDeClimaTempo localidade = gson.fromJson(jsonEmString, LocalidadeDeClimaTempo.class);
            return (List<LocalidadeDeClimaTempo>) localidade;
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