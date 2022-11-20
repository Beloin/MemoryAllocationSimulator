# Alocador de Memória - Simulador

## Entrada:

 1. Quantidade de Processos
 1. Estratégia: ff, bf, wf (Strategy?)
 1. Tamanho da memória real
 1. Tamanho da memória ocupado pelo SO
 1. [M¹, M²] -> Intervalo para gferar aleatoriamente a memória de cada processo.
 1. [Tc¹, Tc²] -> Intervalo para geração do Tempo de "instanciação" de cada processo em relação ao processo anterior. Deverá ser adicionado um processo após o outro, ou seja: Tci = T + Tc(i-1).
 1. [Tp¹, Tp²] -> Intervalo para gerar a duração de cada processo.


## Saída:

 1. Mapa de memória -> Representação visual do mapa. Contém a área ocupada por cada processo e todas as áreas livres.
 1. Processos alocados em memória.
 1. Processos na Fila em aguardo.
 1. Instantes: Criação, Alocação e conclusão.
 1. Tempo de espera de cada processo.
 1. Tempo médio de espera de cada processo concluído.
 1. Percentual de memória utilizada.

## Funcionamento:

Deveremos pedir inicialmente todos os dados de criação e ordenação de processos, além de pedir também as informações da memória. 
A simulação deverá ser em "real-time" sem que haja nenhum processo de compactação.  

