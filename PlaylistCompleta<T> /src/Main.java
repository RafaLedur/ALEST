public static void main(String[] args) {
 
PlaylistCompleta<String> playlist = new PlaylistCompleta<>();
 
// ── Montagem inicial da playlist ──────────
System.out.println("=== Montando a playlist ===");
playlist.adicionarFim("Arrependidaco - Ferrugem");
playlist.adicionarFim("Gravity - John Mayer");
playlist.adicionarFim("Desencana - Thiaguinho");
playlist.adicionarFim("As It Was - Harry Styles");
playlist.exibirPlaylist();
 
// ── Navegação ─────────────────────────────
System.out.println("\n=== Navegação ===");
System.out.println("Atual : " + playlist.atual.titulo);
 
System.out.println(">> Próxima: " + playlist.tocarProxima());
System.out.println(">> Próxima: " + playlist.tocarProxima());
System.out.println("<< Anterior: " + playlist.tocarAnterior());
 
playlist.exibirPlaylist();
 
// ── Tentativa de voltar além do início ────
System.out.println("\n=== Caso de borda: voltar além da primeira ===");
playlist.tocarAnterior(); // volta para a 1ª
playlist.tocarAnterior(); // deve avisar
 
// ── Remoção ───────────────────────────────
System.out.println("\n=== Removendo música atual (meio da lista) ===");
playlist.tocarProxima();
playlist.tocarProxima();
playlist.exibirPlaylist();
playlist.removerAtual();
playlist.exibirPlaylist();
 
// ── Remoção da cauda ──────────────────────
System.out.println("\n=== Removendo música atual (cauda) ===");
playlist.tocarProxima(); // vai para a última
playlist.exibirPlaylist();
playlist.removerAtual();
playlist.exibirPlaylist();
 
 
// ── Esvaziando a playlist ─────────────────
System.out.println("\n=== Removendo tudo ===");
while (playlist.getTamanho() > 0) {
playlist.removerAtual();
}
playlist.exibirPlaylist();
playlist.tocarProxima();

}
@Override
public T removerRegistro(int posicao) {
    verificarIndice(posicao); // Verifica se a posição é válida

    No<T> atual = cabeca; // Começa no primeiro nó
    No<T> anterior = null; // Não há nó anterior inicialmente

    if (posicao == 0) { // Caso especial: Remover o primeiro nó
        T removido = cabeca.dado; // Guarda o dado do nó a ser removido
        cabeca = cabeca.proximo; // Atualiza a cabeça para o próximo nó
        tamanho--;
        return removido; // Retorna o dado removido
    }

    for (int i = 0; i < posicao; i++) { // Percorre até a posição desejada
        anterior = atual; // Atualiza o anterior
        atual = atual.proximo; // Avança para o próximo nó
    }

    anterior.proximo = atual.proximo; // Pula o nó atual
    tamanho--; // Atualiza o tamanho
    return atual.dado; // Retorna o dado removido
}