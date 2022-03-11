package telegram_bot.conversa;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Expressao {

    // Realiza tratamento da mensagem recebida, identificando um padrao:
    public String validaExpressao(String mensagemRecebida) {

        // Converte em minuscula e tira espaco em branco antes e depois da string:
        String expressao = mensagemRecebida.toLowerCase().trim();

        // Remove acentos ou outros sinais graficos:
        String nfdNormalizedString = Normalizer.normalize(expressao, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        expressao = pattern.matcher(nfdNormalizedString).replaceAll("");        

        // Verifica se eh um CEP:
        if (expressao.matches("\\d\\d\\d\\d\\d\\d\\d\\d") || expressao.matches("\\d\\d\\d\\d\\d-\\d\\d\\d")) {
            return "CEP";
        }

        // Varifica se eh uma pergunta:
        boolean ehPergunta = expressao.charAt(expressao.length()-1) == '?';
        System.out.println("Eh pergunta?: " + ehPergunta);

        // Verifica se eh uma pergunta do tipo "como vai?":
        if (expressao.matches(".*como vai.*|.*e voce.*|.*e vc.*") ||
                (expressao.matches(".*tudo bem.*|.*tudo certo.*|.*td bem.*|.*td certo.*") && ehPergunta)) {
            return "tudoBem";
        }

        // Varifica uma saudacao:
        if (expressao.matches("^oi|^ola")) {
            return "ola";
        }

        // Varifica um fim de papo:
        if (expressao.matches("^tchau.*|^adeus.*|.*ate logo.*|.*ate mais.*")) {
            return "tchau";
        }

        // Varifica um agradecimento:
        if (expressao.matches(".*obrigad.*$")) {
            return "obrigado";
        }

        return expressao;
    }

}
