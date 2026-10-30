public class Main {
    // Cria os 8 chamados na ordem de chegada: o id 1 chegou primeiro
    static Chamado[] criarChamados() {
        int[] prioridades = {3, 3, 5, 4, 5, 1, 4, 2};
        Chamado[] chamados = new Chamado[prioridades.length];
        for (int i = 0; i < prioridades.length; i++) {
            chamados[i] = new Chamado(i + 1, prioridades[i]);
        }
        return chamados;
    }

    // Item 2.1: Insertion Sort decrescente por prioridade.
    // Usa < (e não <=): quem tem a mesma prioridade da chave não é deslocado, então a ordem de chegada é preservada (estável).
    static void insertionSort(Chamado[] v) {
        int deslocamentos = 0;
        for (int i = 1; i < v.length; i++) {
            Chamado chave = v[i];
            int j = i - 1;
            // desloca para a direita só quem tem prioridade menor que a chave
            while (j >= 0 && v[j].prioridade < chave.prioridade) {
                v[j + 1] = v[j];
                j--;
                deslocamentos++;
            }
            // a chave é escrita uma única vez, na lacuna aberta
            v[j + 1] = chave;
        }
        System.out.println("Insertion Sort - deslocamentos: " + deslocamentos);
    }

    // Item 2.2: Selection Sort decrescente por prioridade.
    // Comparação estrita (>): em empate, o primeiro encontrado é mantido.
    // A troca pode levar um elemento para trás de outro de mesma prioridade, por isso é instável.
    static void selectionSort(Chamado[] v) {
        int trocas = 0;
        for (int i = 0; i < v.length - 1; i++) {
            int maior = i;
            for (int j = i + 1; j < v.length; j++) {
                if (v[j].prioridade > v[maior].prioridade) {
                    maior = j;
                }
            }
            // troca só quando necessário
            if (maior != i) {
                Chamado aux = v[i];
                v[i] = v[maior];
                v[maior] = aux;
                trocas++;
            }
        }
        System.out.println("Selection Sort - trocas: " + trocas);
    }

    static void imprimirIds(Chamado[] v) {
        for (Chamado c : v) {
            System.out.print(c.id + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Chamado[] chamados = criarChamados();

        // Parte 1: fila de prioridade com heap máximo
        System.out.println("=== Parte 1 ===");
        FilaPrioridade fila = new FilaPrioridade(chamados.length);
        for (Chamado c : chamados) {
            fila.inserir(c);
        }
        System.out.print("Vetor do heap: ");
        fila.imprimirVetor();

        System.out.print("Ordem de atendimento: ");
        Chamado c;
        while ((c = fila.removerMaior()) != null) {
            System.out.print(c + " ");
        }
        System.out.println();

        // Parte 2: ordenações simples sobre cópias do vetor original
        System.out.println("\n=== Parte 2 ===");
        Chamado[] copiaInsertion = chamados.clone();
        insertionSort(copiaInsertion);
        System.out.print("Ordem Insertion Sort: ");
        imprimirIds(copiaInsertion);

        Chamado[] copiaSelection = chamados.clone();
        selectionSort(copiaSelection);
        System.out.print("Ordem Selection Sort: ");
        imprimirIds(copiaSelection);

        // Item 2.3: análise de estabilidade
        // Ordem de chegada dentro de cada prioridade: P5: #3, #5 | P4: #4, #7 | P3: #1, #2 | P1: #6 | P2: #8
        // Insertion Sort: mantém todos os grupos na ordem de chegada (#3 #5 | #4 #7 | #1 #2). É estável.
        // Selection Sort: no grupo P3, #2 aparece antes de #1, quebrando a ordem de chegada.
        //   Causa: no passo i = 3, a troca leva o #1 da posição 3 para a posição 6, passando por cima do #2 (posição 4).
        //   Trocas à distância desfazem a ordem relativa de elementos iguais. Por isso é instável.
        // Fila de prioridade (Parte 1): nesta sequência, #1 saiu antes de #2 e #4 antes de #7.
        //   Isso não é garantido em geral: a ordem de desempate depende da estrutura do heap e da ordem das inserções.
    }
}
