package org.estudos.br;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConsultaIBGETest {

    // Mock para simular a conexão HTTP
    @Mock
    private HttpURLConnection connectionMock;

    // JSON de resposta simulada
    private static final String JSON_RESPONSE = "{\"id\":35,\"sigla\":\"SP\",\"nome\":\"São Paulo\",\"regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}";    // Método executado antes de cada teste
    @BeforeEach
    public void setup() throws IOException {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento do mock
        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }

    // Teste para verificar se o método consultarEstado retorna o JSON esperado para o estado de São Paulo
    @Test
    public void testConsultarEstadoComMock() throws IOException {
        // Sigla do estado a ser consultado
        String estadoUf = "SP";

        // Act (Execução do método a ser testado)
        String response = ConsultaIBGE.consultarEstado(estadoUf);

        // Verificamos se o JSON retornado é o mesmo que o JSON de resposta simulada
        assertEquals(JSON_RESPONSE, response, "O JSON retornado não corresponde ao esperado.");
    }

    @Test
    @DisplayName("Pegando o Status Code")
    public void testConsultarEstadoStatusCode() throws IOException {

        // Sigla do estado a ser consultado
        String estadoUf = "SP";

        // Cria uma conexão HTTP com a URL da API do IBGE para consultar informações sobre o estado "SP" vindo da variável {estadoUf}
        HttpURLConnection connection = (HttpURLConnection) new URL("https://servicodados.ibge.gov.br/api/v1/localidades/estados/" + estadoUf).openConnection();
        // Define o método da requisição como GET
        connection.setRequestMethod("GET");
        // Obtém o status code da resposta da API
        int statusCode = connection.getResponseCode();

        // Verifica se o status code retornado é igual a 200 (OK) e dá uma msg de erro se não for
        assertEquals(200, statusCode, "O status code da resposta não é 200.");
    }


}