package telegram_bot;

import java.util.List;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

import telegram_bot.conversa.Conversa;
import telegram_bot.seguranca.Token;

public class Main {

	public static void main(String[] args) {
		// Criacao do objeto bot com as informacoes de acesso.
		// Buscando token de um controlador:
		Token telegramToken = new Token();				
		TelegramBot bot = new TelegramBot(telegramToken.getTelegramToken());

		// Objeto responsavel por receber as mensagens.
		GetUpdatesResponse updatesResponse;

		// Objeto responsavel por gerenciar o envio de respostas.
		SendResponse sendResponse;

		// Objeto responsavel por gerenciar o envio de acoes do chat.
		BaseResponse baseResponse;

		// Controle de off-set, isto e, a partir deste ID sera lido as mensagens
		// pendentes na fila.
		int m = 0;

		// Instanciando a conversa para as funcionalidades do chatbot:
		Conversa conversa = new Conversa();

		// Loop infinito pode ser alterado por algum timer de intervalo curto.
		while (true) {						
			// Executa comando no Telegram para obter as mensagens pendentes a partir de um
			// off-set (limite inicial).
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			// Lista de mensagens.
			List<Update> updates = updatesResponse.updates();

			// Analise de cada acao da mensagem.
			for (Update update : updates) {

				// Atualizacao do off-set.
				m = update.updateId() + 1;
				System.out.println("IdMensagem: " + m);

				// Tratamento de mensagem recebida
				conversa.trataMensagemRecebida(update.message().text());
				System.out.println("Recebendo mensagem: " + conversa.getMensagemRecebida());

				// Envio de "Escrevendo" antes de enviar a resposta.
				baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));

				// Verificacao de acao de chat foi enviada com sucesso.
				System.out.println("Resposta de Chat Action Enviada? " + baseResponse.isOk());

				// Envio da mensagem de resposta.
				sendResponse = bot.execute(new SendMessage(update.message().chat().id(), conversa.getMensagemDevolvida()));

				// Verificacao de mensagem enviada com sucesso.
				System.out.println("Mensagem Enviada? " + sendResponse.isOk());
			}
		}
	}
}
