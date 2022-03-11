package telegram_bot.conversa;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import telegram_bot.climaTempo.ClimaTempo;
import telegram_bot.climaTempo.ServicoDeClimaTempo;
import telegram_bot.endereco.Endereco;
import telegram_bot.endereco.ServicoDeCep;

public class Resposta {

    private String mensagemRecebida;
    private TipoDeAssunto assunto;
    // private Pessoa pessoa;

    public void setmensagemRecebida(String mensagemRecebida) {
        this.mensagemRecebida = mensagemRecebida;
    }

    // public Pessoa getPessoa() {
    //     return pessoa;
    // }

    // public void setPessoa(Pessoa pessoa) {
    //     this.pessoa = pessoa;
    // }

    public TipoDeAssunto getAssunto() {
        return assunto;
    }

    public void setAssunto(TipoDeAssunto assunto) {
        this.assunto = assunto;
    }

    // De acordo com o tipo de solicitacao, monta uma resposta:
    public String respondeSolicitacao(String solicitacao) {
        String msg = "";
        switch (solicitacao) {
            // Saudacao com opcoes de ajuda:
            case "SaudacaoInicial":
                // Recupera hora para selecionar a saudacao:
                DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH");
                Integer hora = Integer.valueOf(LocalDateTime.now().format(horaFormatter));

                if (hora > 3 && hora < 12) {
                    msg = "Bom dia";
                } else if (hora < 18) {
                    msg = "Boa tarde";
                } else {
                    msg = "Boa noite";
                }
                
                System.out.println(
                        "Validando em 'saudacaoInicial': (" + this.assunto + "/" + this.mensagemRecebida + ")");
                // Se o nome foi solicitado na saudacao inicial antes:
                // if (this.assunto == TipoDeAssunto.NOME) {
                //     this.pessoa.setNome(this.mensagemRecebida);
                // }

                // Se o nome da pessoa ainda nao foi respondido:
                // if (this.pessoa == null || this.pessoa.getNome().isEmpty()) {
                //     this.assunto = TipoDeAssunto.NOME;
                //     msg += "! Meu nome é Bobot. Qual é o seu nome?";
                // } else {
                    this.assunto = TipoDeAssunto.SAUDACAO;
                    // msg += "," + pessoa.getNome() + ". Como posso ajudar?"
                    msg += "! Como posso ajudar?"
                            + System.lineSeparator() + System.lineSeparator()
                            + opcoesDeAjuda();
                // }
                System.out.println("Assunto em 'saudacao': " + this.assunto);
                break;

            // Ola!:
            case "ola":
                // Se o nome foi solicitado na saudacao inicial antes:
                // System.out.println("Validando em 'ola': (" + this.assunto + "/" + this.mensagemRecebida + ")");
                // if (this.assunto == TipoDeAssunto.NOME) {
                //     this.pessoa.setNome(this.mensagemRecebida);
                // }

                // Se o nome da pessoa ainda nao foi respondido:
                // if (this.pessoa == null || this.pessoa.getNome().isEmpty()) {
                //     this.assunto = TipoDeAssunto.NOME;
                //     msg += "Olá! Meu nome é Bobot. Qual é o seu nome?";
                // } else {
                    this.assunto = TipoDeAssunto.SAUDACAO;
                    msg += "Olá, tudo bem?";
                // }
                System.out.println("Assunto em 'ola': " + this.assunto);
                break;

            // Opcoes de ajuda:
            case "OpcoesDeAjuda":
                msg = opcoesDeAjuda();
                break;

            // Data de hoje:
            case "DataDeHoje":
                DateTimeFormatter dataPadrao = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String dataHoje = LocalDateTime.now().format(dataPadrao);
                msg = "Hoje é dia " + dataHoje;
                break;

            // Hora atual:
            case "HoraAgora":
                DateTimeFormatter horaPadrao = DateTimeFormatter.ofPattern("HH:mm");
                String horaAgora = LocalDateTime.now().format(horaPadrao);
                msg = "Agora são " + horaAgora;
                break;

            // Endereço por CEP:
            case "EnderecoPorCep":
                String cep = this.mensagemRecebida.replaceAll("\\-", "");
                System.out.println("Cep: " + cep);
                try {
                    Endereco endereco = ServicoDeCep.buscaEnderecoPelo(cep);
                    if (endereco.getLogradouro() == null) {
                        msg = "O endereço do CEP informado não foi encontrado.";
                    } else {
                        msg = "O endereço do CEP " + endereco.getCep() + " é: "
                                + endereco.getLogradouro() + " "
                                + endereco.getComplemento() + ", "
                                + endereco.getBairro() + ", "
                                + endereco.getLocalidade();
                    }
                } catch (Exception e) {
                    msg = "Erro ao buscar o CEP: " + e;
                }
                break;

            // Previsao do Tempo:
            case "TempoAgora":
                try {
                    String localidadeId = "3477"; // AQUI: TRATAR RECEBIMENTO DE LOCALIDADE
                    ClimaTempo previsao = ServicoDeClimaTempo.buscaClimaTempoPor(localidadeId);
                    msg = "O tempo em " + previsao.getName() + " é: "
                            + previsao.getId() + ".";
                } catch (Exception e) {
                    msg = "Erro ao buscar o tempo: " + e;
                }
                break;
        }
        return msg;
    }

    // Monta uma mensagem padrao com as opcoes de ajuda do bot:
    public static String opcoesDeAjuda() {
        return ("Digite uma das opções abaixo:" + System.lineSeparator()
                + "1-Para saber a data de hoje;" + System.lineSeparator()
                + "2-Para saber a hora agora;" + System.lineSeparator()
                + "3-Descobrir um endereço pelo CEP;" + System.lineSeparator()
                + "4-Informações sobre o tempo pelo CEP." + System.lineSeparator()
                + "Ou diga 'tchau' para encerrar o papo.");
    }
}
