public class PlaylistCompleta<T> {

    private class No<T> {
        T titulo;
        No<T> proximo;
        No<T> anterior;

        No(T titulo) {
            this.titulo   = titulo;
            this.proximo  = null;
            this.anterior = null;
        }
    }

    private No<T> cabeca;
    private No<T> cauda;
    private No<T> atual;
    private int   tamanho;

    public PlaylistCompleta() {
        this.cabeca  = null;
        this.cauda   = null;
        this.atual   = null;
        this.tamanho = 0;
    }

    // ─────────────────────────────────────────────────────────────
    // Exercício 1 — Adicionar música no final
    //
    // Existem dois casos:
    //
    //   Caso A: a playlist está vazia
    //           → o novo nó é ao mesmo tempo cabeca e cauda
    //
    //   Caso B: a playlist já tem músicas
    //           → precisamos ligar o novo nó ao último existente
    //
    // Antes (Caso B):
    //   cabeca → [Arrependidaco] ⇄ [Gravity] ← cauda
    //
    // Depois:
    //   cabeca → [Arrependidaco] ⇄ [Gravity] ⇄ [Desencana] ← cauda
    // ─────────────────────────────────────────────────────────────
    public void adicionarFim(T titulo) {

        // cria o novo nó com o título recebido
        No<T> novo = new No<>(titulo);

        // Caso A — lista vazia: novo nó é o único elemento
        if (cabeca == null) {
            cabeca = novo;
            cauda  = novo;
            atual  = novo;   // começa tocando a primeira música adicionada

        // Caso B — lista já tem elementos: encadeia no final
        } else {
            novo.anterior = cauda;   // novo aponta para o antigo último
            cauda.proximo = novo;    // antigo último aponta para o novo
            cauda         = novo;    // cauda passa a ser o novo nó
        }

        tamanho++;
    }

    // ─────────────────────────────────────────────────────────────
    // Exercício 2 — Avançar para a próxima música  ⏭
    //
    // Casos que precisamos tratar:
    //   Caso A: nenhuma música tocando (atual == null)
    //   Caso B: já estamos na última música (proximo == null)
    //   Caso C: existe próxima → move atual para ela
    // ─────────────────────────────────────────────────────────────
    public T tocarProxima() {

        // Caso A — playlist vazia
        if (atual == null) {
            System.out.println("[Playlist vazia]");
            return null;
        }

        // Caso B — já é a última música, não tem próxima
        if (atual.proximo == null) {
            System.out.println("[Já está na última música]");
            return atual.titulo;
        }

        // Caso C — avança um nó para frente
        atual = atual.proximo;
        return atual.titulo;
    }

    // ─────────────────────────────────────────────────────────────
    // Exercício 3 — Voltar para a música anterior  ⏮
    //
    // Casos que precisamos tratar:
    //   Caso A: nenhuma música tocando (atual == null)
    //   Caso B: já estamos na primeira música (anterior == null)
    //   Caso C: existe anterior → move atual para ela
    // ─────────────────────────────────────────────────────────────
    public T tocarAnterior() {

        // Caso A — playlist vazia
        if (atual == null) {
            System.out.println("[Playlist vazia]");
            return null;
        }

        // Caso B — já é a primeira música, não tem anterior
        if (atual.anterior == null) {
            System.out.println("[Já está na primeira música]");
            return atual.titulo;
        }

        // Caso C — volta um nó para trás
        atual = atual.anterior;
        return atual.titulo;
    }

    // ─────────────────────────────────────────────────────────────
    // Exercício 4 — Remover a música atual
    //
    // Quatro casos possíveis:
    //
    //   Caso A: nada tocando
    //
    //   Caso B: apenas uma música na lista
    //           → lista fica vazia após remoção
    //
    //   Caso C: removendo a primeira música (cabeca)
    //           Antes:  [Arrependidaco] → [Gravity] → ...
    //           Depois:                   [Gravity] → ...
    //
    //   Caso D: removendo a última música (cauda)
    //           Antes:  ... → [Gravity] → [As It Was]
    //           Depois: ... → [Gravity]
    //
    //   Caso E: removendo do meio
    //           Antes:  ... → [Gravity] → [Desencana] → [As It Was] → ...
    //           Depois: ... → [Gravity] →               [As It Was] → ...
    //           (reconecta os vizinhos diretamente entre si)
    // ─────────────────────────────────────────────────────────────
    public void removerAtual() {

        // Caso A — nada tocando
        if (atual == null) {
            System.out.println("[Nada tocando para remover]");
            return;
        }

        System.out.println("[Removendo: " + atual.titulo + "]");

        No<T> removido = atual;   // guarda referência do nó a remover

        // Caso B — única música na lista
        if (tamanho == 1) {
            cabeca = null;
            cauda  = null;
            atual  = null;

        // Caso C — removendo a cabeca
        } else if (removido == cabeca) {
            cabeca          = removido.proximo;  // cabeca avança um nó
            cabeca.anterior = null;              // novo primeiro não tem anterior
            atual           = cabeca;            // passa a tocar o novo primeiro

        // Caso D — removendo a cauda
        } else if (removido == cauda) {
            cauda         = removido.anterior;   // cauda recua um nó
            cauda.proximo = null;                // novo último não tem próximo
            atual         = cauda;               // passa a tocar o novo último

        // Caso E — removendo do meio
        } else {
            // faz os vizinhos se "verem" diretamente, pulando o removido
            removido.anterior.proximo = removido.proximo;
            removido.proximo.anterior = removido.anterior;
            atual = removido.proximo;            // continua da música seguinte
        }

        // boas práticas: apaga os ponteiros do nó removido
        removido.proximo  = null;
        removido.anterior = null;

        tamanho--;
    }

    // ─────────────────────────────────────────────────────────────
    // Utilitários (já fornecidos)
    // ─────────────────────────────────────────────────────────────
    public void exibirPlaylist() {
        if (cabeca == null) {
            System.out.println("  (playlist vazia)");
            return;
        }
        No<T> aux = cabeca;
        while (aux != null) {
            String marcador = (aux == atual) ? "  <<< tocando" : "";
            System.out.println("  " + aux.titulo + marcador);
            aux = aux.proximo;
        }
    }

    public int getTamanho() { return tamanho; }

    // ─────────────────────────────────────────────────────────────
    // MAIN
    // ─────────────────────────────────────────────────────────────
    public static void main(String[] args) {

        PlaylistCompleta<String> playlist = new PlaylistCompleta<>();

        // ── Montagem inicial da playlist ──────────
        System.out.println("=== Montando a playlist ===");
        playlist.adicionarFim("Arrependidaco - Ferrugem");
        playlist.adicionarFim("Gravity - John Mayer");
        playlist.adicionarFim("Desencana - Thiaguinho");
        playlist.adicionarFim("As It Was - Harry Styles");
        playlist.exibirPlaylist();
        // Saída:
        //   Arrependidaco - Ferrugem  <<< tocando
        //   Gravity - John Mayer
        //   Desencana - Thiaguinho
        //   As It Was - Harry Styles

        // ── Navegação ─────────────────────────────
        System.out.println("\n=== Navegação ===");
        System.out.println("Atual : " + playlist.atual.titulo);
        // Atual : Arrependidaco - Ferrugem

        System.out.println(">> Próxima: " + playlist.tocarProxima());
        // >> Próxima: Gravity - John Mayer

        System.out.println(">> Próxima: " + playlist.tocarProxima());
        // >> Próxima: Desencana - Thiaguinho

        System.out.println("<< Anterior: " + playlist.tocarAnterior());
        // << Anterior: Gravity - John Mayer

        playlist.exibirPlaylist();
        // Saída:
        //   Arrependidaco - Ferrugem
        //   Gravity - John Mayer  <<< tocando
        //   Desencana - Thiaguinho
        //   As It Was - Harry Styles

        // ── Caso de borda: voltar além da primeira ─
        System.out.println("\n=== Caso de borda: voltar além da primeira ===");
        playlist.tocarAnterior();
        // Anterior: Arrependidaco - Ferrugem

        playlist.tocarAnterior();
        // [Já está na primeira música]

        // ── Remoção do meio ───────────────────────
        System.out.println("\n=== Removendo música atual (meio da lista) ===");
        playlist.tocarProxima();   // → Gravity
        playlist.tocarProxima();   // → Desencana
        playlist.exibirPlaylist();
        // Saída:
        //   Arrependidaco - Ferrugem
        //   Gravity - John Mayer
        //   Desencana - Thiaguinho  <<< tocando
        //   As It Was - Harry Styles

        playlist.removerAtual();
        // [Removendo: Desencana - Thiaguinho]

        playlist.exibirPlaylist();
        // Saída:
        //   Arrependidaco - Ferrugem
        //   Gravity - John Mayer
        //   As It Was - Harry Styles  <<< tocando

        // ── Remoção da cauda ──────────────────────
        System.out.println("\n=== Removendo música atual (cauda) ===");
        playlist.tocarProxima();   // → As It Was (última)
        playlist.exibirPlaylist();
        // Saída:
        //   Arrependidaco - Ferrugem
        //   Gravity - John Mayer
        //   As It Was - Harry Styles  <<< tocando

        playlist.removerAtual();
        // [Removendo: As It Was - Harry Styles]

        playlist.exibirPlaylist();
        // Saída:
        //   Arrependidaco - Ferrugem
        //   Gravity - John Mayer  <<< tocando

        // ── Esvaziando a playlist ─────────────────
        System.out.println("\n=== Removendo tudo ===");
        while (playlist.getTamanho() > 0) {
            playlist.removerAtual();
        }
        // [Removendo: Gravity - John Mayer]
        // [Removendo: Arrependidaco - Ferrugem]

        playlist.exibirPlaylist();
        // (playlist vazia)

        playlist.tocarProxima();
        // [Playlist vazia]
    }
}
