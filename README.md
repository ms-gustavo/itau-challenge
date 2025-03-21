   <h1>API de Transações - Itaú</h1>
    <p>Esta é uma API REST desenvolvida como parte de um desafio técnico para Backend Júnior no Itaú.</p>

   <h2>Visão Geral</h2>
    <p>A API permite o envio de transações financeiras, limpeza de transações e consulta de estatísticas sobre as transações realizadas nos últimos 60 segundos.</p>

  <h2>Tecnologias Utilizadas</h2>
    <ul>
        <li>Java 17</li>
        <li>Spring Boot 3+</li>
        <li>Spring Web</li>
        <li>Spring Actuator</li>
        <li>JUnit 5 e Mockito (Testes)</li>
        <li>SLF4J e Logback (Logs)</li>
        <li>Swagger (Documentação da API)</li>
    </ul>

  <h2>Como Rodar a Aplicação</h2>
    <h3>Pré-requisitos</h3>
    <ul>
        <li>Java 17+</li>
        <li>Maven ou Gradle</li>
    </ul>
    <h3>Passos</h3>
    <pre>
    git clone git@github.com:ms-gustavo/itau-challenge.git
    cd itau-challenge
    mvn spring-boot:run
    </pre>
    <p>Ou com Gradle:</p>
    <pre>
    gradle bootRun
    </pre>

   <h2>Endpoints Disponíveis</h2>
    <h3>1. Criar uma Transação</h3>
    <pre>POST /transacao</pre>
    <p><strong>Exemplo de Requisição:</strong></p>
    <pre>{
  "valor": 123.45,
  "dataHora": "2025-03-21T12:34:56.789-03:00"
}</pre>
    <p><strong>Respostas Possíveis:</strong></p>
    <ul>
        <li><strong>201 Created:</strong> Transação registrada com sucesso</li>
        <li><strong>422 Unprocessable Entity:</strong> Transação inválida (ex: valor negativo)</li>
        <li><strong>400 Bad Request:</strong> A API não compreendeu a requisição do cliente (exemplo: JSON inválido)</li>
    </ul>
    
  <h3>2. Apagar Todas as Transações</h3>
    <pre>DELETE /transacao</pre>
    <p><strong>Resposta:</strong> 200 OK - Todas as transações foram removidas.</p>

   <h3>3. Obter Estatísticas</h3>
    <pre>GET /transacao/estatistica</pre>
    <p><strong>Exemplo de Resposta:</strong></p>
    <pre>{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.456,
  "min": 12.34,
  "max": 123.56
}</pre>
    <p><strong>Resposta:</strong> 200 OK - Retorna as estatísticas das transações dos últimos 60 segundos.</p>

  <h3>4. Health Check</h3>
    <pre>GET /actuator/health</pre>
    <p><strong>Exemplo de Resposta:</strong></p>
    <pre>{ "status": "UP" }</pre>
    <p><strong>Endpoint:</strong> <a href="http://localhost:8080/actuator/health" target="_blank">http://localhost:8080/actuator/health</a></p>

   <h2>Logs e Monitoramento</h2>
    <p>A API utiliza SLF4J com Logback para capturar eventos importantes. Exemplo de log gerado ao adicionar uma transação:</p>
    <pre>[INFO] Recebendo nova transação: Transacao(valor=100.00, dataHora=2025-03-21T12:30:00)</pre>

   <h2>📄 Documentação da API</h2>
    <p>A documentação da API pode ser acessada via Swagger.</p>
    <p><strong>Endpoint:</strong> <a href="http://localhost:8080/swagger-ui.html" target="_blank">http://localhost:8080/swagger-ui.html</a></p>

   <h2>Testes Automatizados</h2>
    <p>Os testes foram implementados utilizando JUnit 5 e Mockito.</p>
    <h3>Rodar os Testes</h3>
    <pre>mvn test</pre>
    <p>Ou com Gradle:</p>
    <pre>gradle test</pre>

  <h2>Referência do Desafio</h2>
    <p>O desafio técnico foi baseado nas especificações disponíveis no repositório oficial:</p>
    <p><a href="https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior" target="_blank">Desafio Itaú - Backend Júnior</a></p>

  <h2>Licença</h2>
    <p>Este projeto é open-source e pode ser utilizado livremente.</p>
