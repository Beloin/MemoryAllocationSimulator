# Separação de atividades:

## Entradas do Sistema

Criar as configurações de entrada, algo como:

classe EntradaConf:
    - estratégia: Enum
    - QuantidadeProcessos
    - Tamanho Memória
    - Tamanho SO
    ...

Caso tenha interface, deverá criar o Label necessário para adicionar essas opções.
Algo como foi feito no primeiro trabalho. Caso não, deverá apenas colocar no terminal de forma organizada e
explícita.

## Processos e Memória

Deverão ser criados os processos e a memória de acordo com os dados obtidos nas configurações iniciais,
lembrando que deverão também ser computados os valores aleatórios dentro do limite especificado.

Para Processos e Memória, podemos ter:

class ProcessConfiguration:
    - memória do processo
    - duração
    - tempo para instanciação (Deverá respeitar: Tci = T + Tc(i-1), onde Tc0 = 0)

class MemoryConfiguration:
    - Tamanho Memória
    - Tamanho Ocupado SO
    - Estratégia

No final deverá ser fácil adiquirir as informações de todos os processos e da memória.

## Gerenciamento da memória

Aqui deveremos ter o processo em si de gerenciamento da memória e execução em Real-Time dos processos.

## Cálculos extras

Aqui teremos todos os cálculos adicionais que não pertencem unicamente ao gerenciamewnto de memória.
Os cálculos seguintes:
    - Tempo médio de Espera
    - Tempo de espera
