public class PlaylistCompleta<T> {
    private class No<T> {
        T titulo;
        No<T> proximo;
        No<T> anterior;

        No(T titulo) {
            this.titulo = titulo;
            this.proximo = null;
            this.anterior = null;
        }
    }

    private No<T> cabeca;
    private No<T> cauda;
    private No<T> atual; // música tocando agora

    public PlaylistCompleta() {
        this.cabeca = null;
        this.cauda = null;
        this.atual = null;
    }

    // Exercício 1: adicionar música no final
    public void adicionarFim(T titulo) {
    }

    // Exercício 2: avançar para a próxima música (botão )
    public T tocarProxima() {
        return null;
    }

    // Exercício 3: voltar para a música anterior (botão )
    public T tocarAnterior() {
        return null;
    }

    // Exercício 4: remover a música atual da playlist
    public void removerAtual() {
    }

    public void exibirPlaylist() {
        No<T> aux = cabeca;
        while (aux != null) {
            String marcador = (aux == atual) ? " tocando" : "";
            System.out.println(" " + aux.titulo + marcador);
            aux = aux.proximo;
        }
    }
}