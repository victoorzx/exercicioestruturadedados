public class FilaPrioridade {
    private Chamado[] heap;
    private int tamanho;

    public FilaPrioridade(int capacidade) {
        heap = new Chamado[capacidade];
        tamanho = 0;
    }

    // Item 1.1: a prioridade é a maior prioridade numérica; em empate, vence quem chegou antes (menor id)
    private boolean temPrioridadeSobre(Chamado a, Chamado b) {
        if (a.prioridade != b.prioridade) {
            return a.prioridade > b.prioridade;
        }
        return a.id < b.id;
    }

    // Item 1.2: coloca no próximo espaço livre (último nível, mais à esquerda) e faz o chamado subir
    public void inserir(Chamado c) {
        if (tamanho == heap.length) {
            System.out.println("Fila cheia. Chamado " + c + " não foi inserido.");
            return;
        }
        heap[tamanho] = c;
        subir(tamanho);
        tamanho++;
    }

    // Enquanto o chamado tiver prioridade sobre o pai, troca com ele
    private void subir(int i) {
        while (i > 0) {
            int pai = (i - 1) / 2;
            if (!temPrioridadeSobre(heap[i], heap[pai])) {
                break;
            }
            trocar(i, pai);
            i = pai;
        }
    }

    // Item 1.3: devolve a raiz (maior prioridade), move o último elemento para a raiz e faz descer
    public Chamado removerMaior() {
        if (tamanho == 0) {
            return null;
        }
        Chamado maior = heap[0];
        tamanho--;
        heap[0] = heap[tamanho];
        heap[tamanho] = null;
        if (tamanho > 0) {
            descer(0);
        }
        return maior;
    }

    // Troca o nó com o filho de maior prioridade, considerando só filhos dentro do heap (índice < tamanho)
    private void descer(int i) {
        while (true) {
            int esq = 2 * i + 1;
            int dir = 2 * i + 2;
            int melhor = i;
            if (esq < tamanho && temPrioridadeSobre(heap[esq], heap[melhor])) {
                melhor = esq;
            }
            if (dir < tamanho && temPrioridadeSobre(heap[dir], heap[melhor])) {
                melhor = dir;
            }
            if (melhor == i) {
                break;
            }
            trocar(i, melhor);
            i = melhor;
        }
    }

    private void trocar(int i, int j) {
        Chamado aux = heap[i];
        heap[i] = heap[j];
        heap[j] = aux;
    }

    public void imprimirVetor() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println();
    }
}
