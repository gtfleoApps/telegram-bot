package telegram_bot.conversa;

public class Conversa {
    private String mensagemRecebida;
    private String mensagemDevolvida;
    private TipoDeAssunto assunto = TipoDeAssunto.SAUDACAO;
    // private Pessoa pessoa = new Pessoa();

    public String getMensagemRecebida() {
        return mensagemRecebida;
    }

    public void setMensagemRecebida(String mensagemRecebida) {
        this.mensagemRecebida = mensagemRecebida;
    }

    public String getMensagemDevolvida() {
        return mensagemDevolvida;
    }

    public void setMensagemDevolvida(String mensagemDevolvida) {
        this.mensagemDevolvida = mensagemDevolvida;
    }

    public TipoDeAssunto getAssunto() {
        return assunto;
    }

    public void setAssunto(TipoDeAssunto assunto) {
        this.assunto = assunto;
    }

    // public Pessoa getPessoa() {
    //     return pessoa;
    // }

    // public void setPessoa(Pessoa pessoa) {
    //     this.pessoa = pessoa;
    // }

    public void trataMensagemRecebida(String mensagemRecebida) {

        // Setando mensagem recebida da conversa:
        this.setMensagemRecebida(mensagemRecebida);

        // Para tratamento de Funcoes de Resposta:
        Resposta resposta = new Resposta();

        // Transitando a pessoa da conversa para uso na resposta:
        // resposta.setPessoa(this.pessoa);

        // Para tratamento de Expressoes Padrao na mensagem:
        Expressao expressao = new Expressao();
        String msgTratada = expressao.validaExpressao(mensagemRecebida);
        System.out.println("Assunto: " + this.assunto);

        switch (msgTratada) {
            // Data de Hoje:
            case "1":
                setMensagemDevolvida(resposta.respondeSolicitacao("DataDeHoje"));
                break;

            // Hora atual:
            case "2":
                setMensagemDevolvida(resposta.respondeSolicitacao("HoraAgora"));
                break;

            // Endereco por CEP:
            case "3":
                this.assunto = TipoDeAssunto.CEP;
                setMensagemDevolvida("Qual é o CEP para pesquisa?");
                break;

            // Mensagem no formato de CEP:
            case "CEP":
                resposta.setmensagemRecebida(mensagemRecebida);
                if (this.assunto == TipoDeAssunto.CEP) {
                    setMensagemDevolvida(resposta.respondeSolicitacao("EnderecoPorCep"));
                } else {
                    setMensagemDevolvida("Você informou um CEP, certo? Então... "
                            + System.lineSeparator()
                            + resposta.respondeSolicitacao("EnderecoPorCep"));
                }
                this.assunto = TipoDeAssunto.SAUDACAO;
                break;

            // Expressao:
            case "tudoBem":
                setMensagemDevolvida("Tudo bem e vc?");
                break;

            // Expressao:
            case "ola":
                resposta.setAssunto(TipoDeAssunto.SAUDACAO);
                resposta.setmensagemRecebida(mensagemRecebida);
                setMensagemDevolvida(resposta.respondeSolicitacao("ola"));
                this.assunto = resposta.getAssunto();
                break;

            // Expressao:
            case "obrigado":
                setMensagemDevolvida("De nada! :)");
                break;

            // Mensagem de fim de conversa:
            case "tchau":
                setMensagemDevolvida("Até logo! :)");
                break;

            // Conversa padrao ou Nao identificada:
            default:
                if (this.assunto != TipoDeAssunto.SAUDACAO && this.assunto != TipoDeAssunto.NOME) {
                    setMensagemDevolvida("Desculpa, mas não entendi...");
                } else {
                    resposta.setAssunto(this.assunto);
                    resposta.setmensagemRecebida(mensagemRecebida);
                    setMensagemDevolvida(resposta.respondeSolicitacao("SaudacaoInicial"));
                }
                break;
        }

        // Recebe pessoa da reposta para reenviar durante a conversa:
        // this.pessoa = resposta.getPessoa();
    }

}
