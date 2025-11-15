Análise de Caixa Branca – Método `verificarUsuario`

Este repositório contém a análise estrutural do método `verificarUsuario()` utilizando **técnicas de Caixa Branca**, incluindo:

- Construção do grafo de fluxo de controle  
- Cálculo da complexidade ciclomática  
- Identificação dos caminhos básicos  
- Exemplos de validação / invalidação  
- Tabela resumo da análise


 Planilha 

<img width="698" height="663" alt="image" src="https://github.com/user-attachments/assets/02949b3b-6f22-4cba-8bbc-079682f50e13" />



Estrutura do Grafo (Nós)

<img width="853" height="1280" alt="image" src="https://github.com/user-attachments/assets/939213ad-facf-4139-9a23-70170a19ba81" />



O grafo do método contém os seguintes nós:

1. **N1** – Início  
2. **N2** – Declaração / inicialização da variável `sql`  
3. **N3** – Chamada `conectarBD()`  
4. **N4** – Montagem da instrução SQL  
5. **N5** – Criação do Statement / execução da query  
6. **N6** – Decisão `rs.next()`  
7. **N7** – Caminho quando existe resultado → `result = true`  
8. **N8** – Atribuição de `nome = rs.getString("nome")`  
9. **N9** – Caminho quando não existe resultado → `result = false`  
10. **N10** – Retorno final (`return result`)



Cálculo da Complexidade Ciclomática

A fórmula usada foi:

```
M = E - N + 2P
```

Onde:

- **E** = número de arestas = 10  
- **N** = número de nós = 10  
- **P** = componentes conectados = 1

1) Nós (N) — já identificados
Usamos os mesmos nós que relacionamos antes:
•	N1 = Início
•	N2 = Declaração/inicialização da variável sql
•	N3 = Chamada conectarBD()
•	N4 = Montagem da instrução SQL
•	N5 = Criação do Statement / execução da query
•	N6 = Verificação rs.next() (decisão)
•	N7 = Quando há resultado: result = true
•	N8 = Atribui nome = rs.getString("nome")
•	N9 = Quando não há resultado: result fica false
•	N10 = Retorna result (fim do método)
Portanto N = 10.
________________________________________
2) Arestas (E) — transições entre nós
Listando as transições do grafo:
1.	N1 → N2
2.	N2 → N3
3.	N3 → N4
4.	N4 → N5
5.	N5 → N6 (ramo que leva à verificação)
6.	N5 → N9 (ramo direto para o caso sem resultado)
7.	N6 → N7
8.	N7 → N8
9.	N8 → N10
10.	N9 → N10
Portanto E = 10.


 Resultado

```
M = 10 - 10 + 2(1) = 2
---
```
Caminhos Basicos

Caminhos Básicos do Método verificarUsuario
Como a complexidade ciclomática foi 2, isso significa que o grafo possui dois caminhos independentes.
Eles representam os dois possíveis resultados do teste lógico rs.next().
________________________________________
Caminho Básico 1 — Usuário ENCONTRADO
Fluxo completo quando a consulta retorna um registro válido:
N1 → N2 → N3 → N4 → N5 → N6 → N7 → N8 → N10
Explicação:
•	O método inicia, monta o SQL, executa a query
•	O rs.next() é verdadeiro
•	O código atualiza result = true e busca o nome do usuário
•	Por fim retorna result
________________________________________
Caminho Básico 2 — Usuário NÃO encontrado
Fluxo do programa quando o banco não retorna nenhum valor:
N1 → N2 → N3 → N4 → N5 → N9 → N10
Explicação:
•	O método faz o mesmo fluxo inicial
•	O rs.next() é falso
•	O sistema segue para o ponto em que result permanece false
•	O método retorna esse valor  




