// 0 - nome do pacote
 
// 1 - bibliotecas
 
import io.restassured.response.Response; // Classe Resposta do REST-assured
 
import org.junit.jupiter.api.Test;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
 
import static io.restassured.RestAssured.given; // função given
import static org.hamcrest.Matchers.*;          // Classe de verificadores do Hamcrest
 
// 2 - classe
public class TestPet {
    // 2.1 atributos
    static String ct = "application/json"; // content-type
    static String uriPet = "https://petstore.swagger.io/v2/pet";
 
    // 2.2 funções e métodos
    // 2.2.1 funções e métodos comuns / uteis
 
    // Função de leitura de Json
    public static String lerArquivoJson(String arquivoJson) throws IOException{
        return new String(Files.readAllBytes(Paths.get(arquivoJson)));  
    }
 
    // 2.2.2 métodos de teste
    @Test
    public void testPostPet() throws IOException{
        // Configura
 
        // carregar os dados do arquivo json do pet
        String jsonBody = lerArquivoJson("src/test/resources/json/pet1.json");
       
        int petId = 115293301; // código esperado do pet
 
        // começa o teste via REST-assured
 
        given()                                 // Dado que
            .contentType(ct)                    // o tipo do conteúdo é
            .log().all()                        // mostre tudo na ida
            .body(jsonBody)                     // envie o corpo da requisição
        // Executa    
        .when()                                 // Quando
            .post(uriPet)                       // Chamamos o endpoint fazendo post
        // Valida
        .then()                                 // Então
            .log().all()                        // mostre tudo na volta
            .statusCode(200) // verifique se o status code é 200
            .body("name", is("Snoopy"))    // verifica se o nome é Snoopy
            .body("id", is(petId))         // verifique o código do pet
            .body("category.name", is("cachorro")) // se é cachorro
            .body("tags[0].name", is("vacinado"))  // se está vacinado
        ; // fim do given
 
    }
 
}
 
