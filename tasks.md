# Separação de atividades:

## (100) Entradas do Sistema

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

## (200) Processos e Memória

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

## (300) Gerenciamento da memória

Aqui deveremos ter o processo em si de gerenciamento da memória e execução em Real-Time dos processos.  
A cada um segundo:
- Atualizar processo por processo.
- Adicionar um novo processo:
    - Usar o algoritmo de inserção.
    - Verificar o tempo de inserção
    - Atualizar a fila de processos.
    - Calcular o novo espaço da memória.
- Remover processo da memória
    - Calcular novo espaço da memória
    - Calcular e salvar tempo gasto (Talvez ter um enum para o estado do processo)
- Atualizar informações para serem apresentadas.

## (400) Cálculos extras

Aqui teremos todos os cálculos adicionais que não pertencem unicamente ao gerenciamewnto de memória.
Os cálculos seguintes:
- Tempo médio de Espera
- Tempo de espera

## Interface Visual

Deveremos mostrar esses dados de alguma forma para o usuário, temos como opções:  
- GUI, usando JavaFX (X)  
- CLI usando o próprio terminal mesmo
