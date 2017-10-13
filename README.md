--------------------------------------------------------
|                        Grafos                        |
--------------------------------------------------------

Este programa em Java é uma estrutura de dados Grafo usando
a HashMap do proprio Java localizado em java.util.HashMap

A base do Grafo já está criada: inserir vertices, inserir arestas
caso já existe os dois vertices passados por parametro, e printar
todo o Grafo.

Para a disciplina de Teoria dos Grafos, foi implementada duas
atividades da Olimpiada de programação: Numero de Erdos e Transmissão
de Energia.

Tambem está implementado os algoritmos de Kruskal e de Prim, um dos principais de geração de árvore mínima, mais conhecida como minimal spanning tree, árvore geradora de custo minimo ou árvore de extensão mínima. Porém, como a árvore mínima pode ter várias para cada grafo, o resultado depende da ordem de inserção dos vértices.

Para quem tem o programa Graphviz instalado, foi adicionado a classe ToGraphviz, que chama este programa e desenho o grafo. Isto é muito bom para podermos ver o grafo como ele está. O programa pode ser baixado aqui: http://www.graphviz.org/pub/graphviz/stable/windows/graphviz-2.38.msi. Depois é necessário colocar o executavel nas Variaveis de Ambiente.

Meu computador->Propriedades->Configuraçoes avançadas do sistema->Váriaveis de ambiente->Váriaveis de sistema->Path->Editar. Coloque um ; no final e cole o endereço dos executaveis do Graphviz: C:\Program Files (x86)\Graphviz2.38\bin

Pronto.